package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.ApiUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiUserRepository extends MongoRepository<ApiUser, String> {
	ApiUser findByUsername(String username);
}
