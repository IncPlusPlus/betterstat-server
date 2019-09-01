package io.github.incplusplus.thermostat.server.controllers.clients.management;

import io.github.incplusplus.thermostat.persistence.repositories.ApiUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/management")
public class UserManagementController
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApiUserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	private void init()
	{
//		if (repository.findAll().size() < 1)
//		{
//			UserDto userDto = new UserDto();
//			userDto.setEmail("noreply@dummydomainthatdoesnotexist.nowhere");
//			userDto.setPassword("password123");
//			userDto.setMatchingPassword("password123");
////			User defaultUser = userService.registerNewUserAccount(userDto);
////			User defaultUser = new User("admin", passwordEncoder.encode("password123"));
////			logger.warn("There was no existing accounts so I added one myself. Info below:\n" + defaultUser);
////			repository.insert(defaultUser);
//		}
	}
}
