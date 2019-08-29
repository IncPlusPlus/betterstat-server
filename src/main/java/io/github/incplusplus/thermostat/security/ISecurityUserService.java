package io.github.incplusplus.thermostat.security;

import org.bson.types.ObjectId;

public interface ISecurityUserService {

    String validatePasswordResetToken(ObjectId id, String token);

}
