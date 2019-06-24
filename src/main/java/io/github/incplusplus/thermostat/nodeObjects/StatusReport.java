package io.github.incplusplus.thermostat.nodeObjects;

import org.springframework.data.annotation.Id;

public class StatusReport
{
	private String mostRecentIp;
	private int temperatureInFahrenheit;
	private boolean heatingActive;
	private boolean airConditioningActive;
	private boolean fanActive;
	private boolean containsMockData;
	
	public StatusReport()
	{
		this.mostRecentIp = "";
		this.temperatureInFahrenheit = 0;
		this.heatingActive = false;
		this.airConditioningActive = false;
		this.fanActive = false;
		this.containsMockData=true;
	}
	
	public StatusReport(String mostRecentIp, int temperatureInFahrenheit, boolean heatingActive, boolean airConditioningActive, boolean fanActive)
	{
		this.mostRecentIp = mostRecentIp;
		this.temperatureInFahrenheit = temperatureInFahrenheit;
		this.heatingActive = heatingActive;
		this.airConditioningActive = airConditioningActive;
		this.fanActive = fanActive;
		this.containsMockData=false;
	}
	
	public String getMostRecentIp()
	{
		return mostRecentIp;
	}
	
	public void setMostRecentIp(String mostRecentIp)
	{
		this.mostRecentIp = mostRecentIp;
	}
	
	public int getTemperatureInFahrenheit()
	{
		return temperatureInFahrenheit;
	}
	
	public void setTemperatureInFahrenheit(int temperatureInFahrenheit)
	{
		this.temperatureInFahrenheit = temperatureInFahrenheit;
	}
	
	public boolean isHeatingActive()
	{
		return heatingActive;
	}
	
	public void setHeatingActive(boolean heatingActive)
	{
		this.heatingActive = heatingActive;
	}
	
	public boolean isAirConditioningActive()
	{
		return airConditioningActive;
	}
	
	public void setAirConditioningActive(boolean airConditioningActive)
	{
		this.airConditioningActive = airConditioningActive;
	}
	
	public boolean isFanActive()
	{
		return fanActive;
	}
	
	public void setFanActive(boolean fanActive)
	{
		this.fanActive = fanActive;
	}
	
	@Override
	public String toString()
	{
		return "StatusReport{" +
				"mostRecentIp='" + mostRecentIp + '\'' +
				", temperatureInFahrenheit=" + temperatureInFahrenheit +
				", heatingActive=" + heatingActive +
				", airConditioningActive=" + airConditioningActive +
				", fanActive=" + fanActive +
				'}';
	}
	
	public void setContainsMockData(boolean containsMockData)
	{
		this.containsMockData = containsMockData;
	}
	
	public boolean isContainsMockData()
	{
		return containsMockData;
	}
}