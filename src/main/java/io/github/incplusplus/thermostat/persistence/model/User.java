package io.github.incplusplus.thermostat.persistence.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Collection;

public abstract class User
{
	@Id
	private ObjectId _id;
	private String username;
	private String password;
	private Collection<Role> roles;
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public ObjectId get_id()
	{
		return _id;
	}
	
	public void set_id(ObjectId _id)
	{
		this._id = _id;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public Collection<Role> getRoles()
	{
		return roles;
	}
	
	public void setRoles(Collection<Role> roles)
	{
		this.roles = roles;
	}
	
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [username=").append(username).append(", password=").append(password).append("]");
		return builder.toString();
	}
}
