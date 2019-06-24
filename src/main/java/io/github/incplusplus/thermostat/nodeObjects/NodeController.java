package io.github.incplusplus.thermostat.nodeObjects;

import com.mongodb.MongoClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/nodes")
public class NodeController
{
	private static final Log log = LogFactory.getLog(NodeController.class);
	private MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "nodes"));
	private static final String template = "Hello, %s";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(method = RequestMethod.POST, value = "/addNode", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus addNode(@RequestParam(name = "name", required = true) String name,
	                          @RequestParam(name = "heatingSupported", required = false, defaultValue = "false") boolean heatingSupported,
	                          @RequestParam(name = "airConditioningSupported", required = false, defaultValue = "false") boolean airConditioningSupported,
	                          @RequestParam(name = "fanSupported", required = false, defaultValue = "false") boolean fanSupported)
	{
		Node n = new Node(name, heatingSupported, airConditioningSupported, fanSupported);
		mongoOps.insert(n);
		log.info("Inserted new Node: " + n);
		return HttpStatus.CREATED;
	}
	
	@RequestMapping(value = "/getNodes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Node> getNodes()
	{
		
		log.info("findAll entered...");
		
		return mongoOps.findAll(Node.class);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id:[\\da-f]+}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Node getNode(@PathVariable("id") String id)
	{
		
		log.info("getNode entered: id= " + id);
		
		Query query = new Query(Criteria.where("id").is(id));
		Node foundNode = mongoOps.findOne(query, Node.class);
		System.out.println(foundNode);
		return foundNode;
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
		return HttpStatus.NOT_FOUND;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id:[\\da-f]+}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus deleteNode(@PathVariable("id") String id)
	{
		
		log.info("updateStatus entered: id= " + id);
		
		Query query = new Query(Criteria.where("id").is(id));
		Node foundNode = mongoOps.findOne(query, Node.class);
		if (foundNode != null)
		{
			mongoOps.remove(foundNode);
			return HttpStatus.OK;
		}
		return HttpStatus.NOT_FOUND;
	}
}
