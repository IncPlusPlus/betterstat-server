package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
	User findByUsername(String username);
}
