package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.User;
import io.github.incplusplus.thermostat.persistence.model.VerificationToken;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, ObjectId>
{

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

//    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);
    void deleteAllByExpiryDateLessThanEqual(Date now);
}
