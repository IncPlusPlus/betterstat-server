package io.github.incplusplus.thermostat.persistence.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Collection;

public class User
{
	protected String username;
	protected String password;
	protected Collection<Role> roles;
	@Id
	private ObjectId _id;
}
