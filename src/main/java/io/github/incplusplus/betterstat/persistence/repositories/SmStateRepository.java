package io.github.incplusplus.betterstat.persistence.repositories;

import java.util.Optional;
import org.springframework.statemachine.data.mongodb.MongoDbRepositoryState;
import org.springframework.statemachine.data.mongodb.MongoDbStateRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmStateRepository extends MongoDbStateRepository {

  Optional<MongoDbRepositoryState> findFirstByMachineIdAndState(String machineId, String State);
}
