package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.DeviceMetadata;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceMetadataRepository extends MongoRepository<DeviceMetadata, ObjectId>
{
    List<DeviceMetadata> findByUserId(ObjectId userId);
}
