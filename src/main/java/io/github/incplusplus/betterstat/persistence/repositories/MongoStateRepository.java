package io.github.incplusplus.betterstat.persistence.repositories;

import org.springframework.statemachine.data.mongodb.MongoDbStateMachineRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoStateRepository extends MongoDbStateMachineRepository {}
