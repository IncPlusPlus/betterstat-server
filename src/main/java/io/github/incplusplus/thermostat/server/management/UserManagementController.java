package io.github.incplusplus.thermostat.server.management;

import com.mongodb.MongoClient;
import io.github.incplusplus.thermostat.models.ApiUser;
import io.github.incplusplus.thermostat.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/management")
public class UserManagementController
{
	@Autowired
	private UsersRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	private void init()
	{
		if (repository.findAll().size() < 1)
		{
			ApiUser defaultUser = new ApiUser("admin", passwordEncoder.encode("password123"));
			repository.insert(defaultUser);
		}
	}
}
