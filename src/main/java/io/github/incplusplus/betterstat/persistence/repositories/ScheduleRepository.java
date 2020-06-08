package io.github.incplusplus.betterstat.persistence.repositories;

import io.github.incplusplus.betterstat.persistence.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {}
