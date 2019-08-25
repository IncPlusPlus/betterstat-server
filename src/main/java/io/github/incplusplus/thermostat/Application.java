package io.github.incplusplus.thermostat;

import com.mongodb.MongoClientOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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