package io.github.incplusplus.thermostat.tempRelay;

import com.mongodb.MongoClient;
import io.github.incplusplus.thermostat.exceptions.NodeNotFoundException;
import io.github.incplusplus.thermostat.nodeObjects.Node;
import io.github.incplusplus.thermostat.nodeObjects.NodeController;
import io.github.incplusplus.thermostat.nodeObjects.StatusReport;
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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/relay")
public class TemporaryRelayController
{
	private static final Log log = LogFactory.getLog(NodeController.class);
	private MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "tmpRly"));
	
	private DesiredRelayState ensureInitializedAndReturn()
	{
		List<DesiredRelayState> relayStates = mongoOps.findAll(DesiredRelayState.class);
		if(relayStates.size()==0)
		{
			log.info("relayStates.size()==0!!! Providing a new DesiredRelayState\n" +
					"Don't forget to save this (it has already been inserted!");
			DesiredRelayState desiredRelayState= new DesiredRelayState();
			mongoOps.insert(desiredRelayState);
			return desiredRelayState;
		}
		else if(relayStates.size() == 1)
		{
			log.info("relayStates.size()==1 Grabbing current DesiredRelayState");
			return relayStates.get(0);
		}
		else
		{
			log.info("relayStates.size()==0!!! Providing a new DesiredRelayState\n" +
					"Don't forget to save this!");
			mongoOps.dropCollection(DesiredRelayState.class);
			return new DesiredRelayState();
			
		}
	}
	
	@RequestMapping(value = "/getDesiredRelayState", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DesiredRelayState getDesiredRelayState()
	{
		log.info("Finding getDesiredRelayState...");
		return ensureInitializedAndReturn();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/updateState", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DesiredRelayState updateStatus(@RequestParam(name = "newStatus", required = true) boolean newStatus)
	{
		log.info("updateStatus entered!");
		DesiredRelayState desiredRelayState = ensureInitializedAndReturn();
		desiredRelayState.setToggleState(newStatus);
		mongoOps.save(desiredRelayState);
		return desiredRelayState;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteNode()
	{
		log.info("Deleting!!!!");
		mongoOps.dropCollection(DesiredRelayState.class);
	}
}
