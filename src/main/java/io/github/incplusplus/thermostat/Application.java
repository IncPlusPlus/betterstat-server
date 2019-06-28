package io.github.incplusplus.thermostat;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import io.github.incplusplus.thermostat.tempRelay.DesiredRelayState;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@SpringBootApplication
public class Application
{
	private static final Log log = LogFactory.getLog(Application.class);
	
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MongoClientOptions mongoOptions() {
		return MongoClientOptions.builder().socketTimeout(15000).build();
	}
}