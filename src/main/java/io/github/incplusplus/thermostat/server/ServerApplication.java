package io.github.incplusplus.thermostat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@SpringBootApplication
public class ServerApplication
{
	private static final Log log = LogFactory.getLog(ServerApplication.class);
	
	public static void main(String[] args)
	{
		SpringApplication.run(ServerApplication.class, args);
	}
	
//	@Override
//	public void run(String... args)
//	{
//		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "database"));
//
//		Node n = new Node(new StatusReport("127.0.0.1",70,false,false,false),"Thermostat1",true,false,false);
//
//		mongoOps.insert(n);
//		log.info("Insert: " + n);
////		Query q = new Query(Criteria.where("id").is("5a212b985735dd44089e4782"));
//
//		Update u = new Update().set("lastStatus.temperatureInFahrenheit", 134);
//
//		mongoOps.updateFirst(query(where("name").is("Thermostat1")), u, Node.class);
//		log.info("Updated: " + n);
//	}
}
