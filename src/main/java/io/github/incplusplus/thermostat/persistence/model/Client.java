package io.github.incplusplus.thermostat.persistence.model;

import org.bson.types.ObjectId;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.data.annotation.Id;

import java.util.Collection;

/**
 * This class will represent a client that will be using the API.
 * Instead of the basic information that might be used for authentication from a thermostat,
 * this class will be based around an actual human user of the API.
 */
public class Client extends User
{
	
	private String firstName;
	
	private String lastName;
	
	private boolean enabled;
	
//	private boolean isUsing2FA;
	
	private String secret;
	
	private String email;
	
	public Client()
	{
		this.secret = Base32.random();
		this.enabled = false;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getEmail()
	{
		return getUsername();
	}
	
	public void setEmail(final String username)
	{
		this.email=username;
		setUsername(username);
	}
	
	@Override
	public void setUsername(final String username)
	{
		this.email=username;
		super.setUsername(username);
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
	}
	
//	public boolean isUsing2FA()
//	{
//		return isUsing2FA;
//	}
	
//	public void setUsing2FA(boolean isUsing2FA)
//	{
//		this.isUsing2FA = isUsing2FA;
//	}
	
	public String getSecret()
	{
		return secret;
	}
	
	public void setSecret(String secret)
	{
		this.secret = secret;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((getUsername() == null) ? 0 : getUsername().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Client client = (Client) obj;
		return getUsername().equals(client.getUsername());
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(get_id())
				.append(", firstName=").append(firstName)
				.append(", lastName=").append(lastName)
				.append(", email=").append(getUsername())
				.append(", password=").append(getPassword())
				.append(", enabled=").append(enabled)
//				.append(", isUsing2FA=").append(isUsing2FA)
				.append(", secret=").append(secret)
				.append(", roles=").append(getRoles())
				.append("]");
		return builder.toString();
	}
	
}
