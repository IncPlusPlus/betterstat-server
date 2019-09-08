package io.github.incplusplus.thermostat.persistence.repositories;

import io.github.incplusplus.thermostat.persistence.model.PasswordResetToken;
import io.github.incplusplus.thermostat.persistence.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, ObjectId>
{

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByClient(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

//    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);
    void deleteAllByExpiryDateLessThanEqual(Date now);
}
