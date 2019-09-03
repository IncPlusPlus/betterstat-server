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
	
	private String email;
	
	private String password;
	
	private boolean enabled;
	
//	private boolean isUsing2FA;
	
	private String secret;
	
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
		return email;
	}
	
	public void setEmail(final String username)
	{
		this.email = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(final String password)
	{
		this.password = password;
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
		result = (prime * result) + ((email == null) ? 0 : email.hashCode());
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
		return email.equals(client.email);
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(get_id())
				.append(", firstName=").append(firstName)
				.append(", lastName=").append(lastName)
				.append(", email=").append(email)
				.append(", password=").append(password)
				.append(", enabled=").append(enabled)
//				.append(", isUsing2FA=").append(isUsing2FA)
				.append(", secret=").append(secret)
				.append(", roles=").append(getRoles())
				.append("]");
		return builder.toString();
	}
	
}
