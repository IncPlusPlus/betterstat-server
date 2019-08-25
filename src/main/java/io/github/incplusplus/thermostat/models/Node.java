package io.github.incplusplus.thermostat.models;

import org.springframework.data.annotation.Id;

public class Node
{
	@Id
	private String id;
	private StatusReport lastStatus;
	private String name;
	private boolean heatingSupported;
	private boolean airConditioningSupported;
	private boolean fanSupported;
	
//	public Node(StatusReport lastStatus, String name, boolean heatingSupported, boolean airConditioningSupported, boolean fanSupported)
//	{
//		this.lastStatus = lastStatus;
//		this.name = name;
//		this.heatingSupported=heatingSupported;
//		this.airConditioningSupported=airConditioningSupported;
//		this.fanSupported=fanSupported;
//	}
	
	
	public Node(String name, boolean heatingSupported, boolean airConditioningSupported, boolean fanSupported)
	{
		this.lastStatus = new StatusReport();
		this.name = name;
		this.heatingSupported=heatingSupported;
		this.airConditioningSupported=airConditioningSupported;
		this.fanSupported=fanSupported;
	}
	
	public StatusReport getLastStatus()
	{
		return lastStatus;
	}
	
	public void setLastStatus(StatusReport lastStatus)
	{
		this.lastStatus = lastStatus;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isHeatingSupported()
	{
		return heatingSupported;
	}
	
	public void setHeatingSupported(boolean heatingSupported)
	{
		this.heatingSupported = heatingSupported;
	}
	
	public boolean isAirConditioningSupported()
	{
		return airConditioningSupported;
	}
	
	public void setAirConditioningSupported(boolean airConditioningSupported)
	{
		this.airConditioningSupported = airConditioningSupported;
	}
	
	public boolean isFanSupported()
	{
		return fanSupported;
	}
	
	public void setFanSupported(boolean fanSupported)
	{
		this.fanSupported = fanSupported;
	}
	
	@Override
	public String toString()
	{
		return "Node{" +
				"lastStatus=" + lastStatus +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", heatingSupported=" + heatingSupported +
				", airConditioningSupported=" + airConditioningSupported +
				", fanSupported=" + fanSupported +
				'}';
	}
	
	
}
