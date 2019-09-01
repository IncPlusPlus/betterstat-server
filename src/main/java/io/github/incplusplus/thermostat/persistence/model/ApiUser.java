package io.github.incplusplus.thermostat.persistence.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

// TODO: Make this a DTO and then create a regular User class

/**
 * This class represents an ApiUser. This will likely be representative of
 * the users created for
 */
public class ApiUser extends User
{
	
	public ApiUser() {}
	
	public ApiUser(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ApiUser [username=").append(username)
				.append(", password=").append(password)
				.append("]");
		return builder.toString();
	}
}
