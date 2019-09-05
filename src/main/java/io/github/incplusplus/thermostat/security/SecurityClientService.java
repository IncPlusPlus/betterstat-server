package io.github.incplusplus.thermostat.security;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public interface SecurityClientService
{
	String validatePasswordResetToken(ObjectId id, String token);
}
