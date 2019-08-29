package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.Privilege;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PrivilegeRepository extends MongoRepository<Privilege, ObjectId>
{

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
