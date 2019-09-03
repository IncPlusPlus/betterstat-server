package io.github.incplusplus.thermostat.persistence.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

// TODO: Make this a DTO and then create a regular User class

/**
 * This class represents an ApiUser. This will likely be representative of
 * the users created for usage by the thermostats.
 */
public class ApiUser extends User
{
	public ApiUser(String username, String password)
	{
		super(username,password);
	}
	
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ApiUser [username=").append(getUsername())
				.append(", password=").append(getPassword())
				.append("]");
		return builder.toString();
	}
}
