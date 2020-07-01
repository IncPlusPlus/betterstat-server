package io.github.incplusplus.betterstat.persistence.repositories;

import io.github.incplusplus.betterstat.persistence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

  User findByEmail(String email);
}
