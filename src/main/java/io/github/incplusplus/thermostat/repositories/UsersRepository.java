package io.github.incplusplus.thermostat.repositories;

import io.github.incplusplus.thermostat.models.ApiUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<ApiUser, String> {
	ApiUser findByUsername(String username);
}
