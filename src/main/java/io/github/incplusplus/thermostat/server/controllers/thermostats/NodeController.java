package io.github.incplusplus.thermostat.server.controllers.thermostats;

import com.mongodb.MongoClient;
import io.github.incplusplus.thermostat.exceptions.NodeNotFoundException;
import io.github.incplusplus.thermostat.models.Node;
import io.github.incplusplus.thermostat.models.StatusReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/nodes")
public class NodeController
{
	private static final Log log = LogFactory.getLog(NodeController.class);
	private MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "nodes"));
	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(method = RequestMethod.POST, value = "/addNode", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Node addNode(@RequestParam(name = "name", required = true) String name,
	                    @RequestParam(name = "heatingSupported", required = false, defaultValue = "false") boolean heatingSupported,
	                    @RequestParam(name = "airConditioningSupported", required = false, defaultValue = "false") boolean airConditioningSupported,
	                    @RequestParam(name = "fanSupported", required = false, defaultValue = "false") boolean fanSupported,
	                    HttpServletResponse response)
	{
		Node n = new Node(name, heatingSupported, airConditioningSupported, fanSupported);
		mongoOps.insert(n);
		log.info("Inserted new Node: " + n);
//		TODO add check to see if this Node already exists and return a CONFLICT if it does
//		TODO maybe add check to see if object made it into Mongo
		
		return n;
	}
	
	@RequestMapping(value = "/getNodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Node> getNodes()
	{
		
		log.info("Listing all nodes...");
		
		return mongoOps.findAll(Node.class);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id:[\\da-f]+}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Node getNode(@PathVariable("id") String id)
	{
		
		log.info("getNode entered: id= " + id);
		
		Query query = new Query(Criteria.where("id").is(id));
		Node foundNode = mongoOps.findOne(query, Node.class);
//		System.out.println(foundNode);
		if(foundNode != null)
		{return foundNode;}
		throw new NodeNotFoundException("Node with id '"+id+"' was not found.");
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id:[\\da-f]+}/updateStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus updateStatus(@PathVariable("id") String id,
	                               @RequestParam(name = "mostRecentIp", required = true) String mostRecentIp,
	                               @RequestParam(name = "temperatureInFahrenheit", required = true) int temperatureInFahrenheit,
	                               @RequestParam(name = "heatingActive", required = true) boolean heatingActive,
	                               @RequestParam(name = "airConditioningActive", required = true) boolean airConditioningActive,
	                               @RequestParam(name = "fanActive", required = true) boolean fanActive)
	{
		
		log.info("updateStatus entered: id= " + id);
		
		Query query = new Query(Criteria.where("id").is(id));
		Node foundNode = mongoOps.findOne(query, Node.class);
		StatusReport statusReport = new StatusReport(mostRecentIp, temperatureInFahrenheit, heatingActive, airConditioningActive, fanActive);
		if (foundNode != null)
		{
			foundNode.setLastStatus(statusReport);
			mongoOps.save(foundNode);
			return HttpStatus.OK;
		}
		throw new NodeNotFoundException("Node with id '"+id+"' was not found.");
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id:[\\da-f]+}")
	@ResponseBody
	public void deleteNode(@PathVariable("id") String id, HttpServletResponse response)
	{
		log.info("updateStatus entered: id= " + id);
		
		Query query = new Query(Criteria.where("id").is(id));
		Node foundNode = mongoOps.findOne(query, Node.class);
		if (foundNode != null)
		{
			mongoOps.remove(foundNode);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		else
		{
			throw new NodeNotFoundException("Node with id '"+id+"' was not found.");
		}
	}
}
