package io.github.incplusplus.thermostat.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ApiUser
{
	@Id
	private ObjectId _id;
	private String username;
	private String password;
	
	public ApiUser() {}
	
	public ApiUser(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public void set_id(ObjectId _id)
	{
		this._id = _id;
	}
	
	public String get_id()
	{
		return this._id.toHexString();
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUsername()
	{
		return username;
	}
}
