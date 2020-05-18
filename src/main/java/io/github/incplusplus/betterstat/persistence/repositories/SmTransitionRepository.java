package io.github.incplusplus.betterstat.persistence.repositories;

import org.springframework.statemachine.data.mongodb.MongoDbTransitionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmTransitionRepository extends MongoDbTransitionRepository {}
