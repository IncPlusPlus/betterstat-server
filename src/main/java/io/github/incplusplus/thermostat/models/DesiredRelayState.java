package io.github.incplusplus.thermostat.models;

import org.springframework.data.annotation.Id;

public class DesiredRelayState
{
	@Id
	private String id;
	public boolean enabled;
	
	public DesiredRelayState()
	{
		enabled = false;
	}
	
	public void setToggleState(boolean newState)
	{
		enabled = newState;
	}
}
