package io.github.incplusplus.thermostat.security;

import org.bson.types.ObjectId;

public interface SecurityClientService
{
	String validatePasswordResetToken(ObjectId id, String token);
}
