package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.Client;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, ObjectId>
{
//	User findByUsername(String username);
	Client findByEmail(String email);
//	@Override
//	void delete(User user);
}
