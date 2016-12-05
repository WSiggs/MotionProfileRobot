package org.usfirst.frc.team4908.util;

public class Setpoint 
{
	private double position;
	private double velocity;
	private double acceleration;
	
	public void setPosition(double position)
	{
		this.position = position;
	}
	
	public void setVelocity(double velocity)
	{
		this.velocity = velocity;
	}
	
	public void setAcceleration(double acceleration)
	{
		this.acceleration = acceleration;
	}
	
	public double getPosition()
	{
		return position;
	}
	
	public double getVelocity()
	{
		return velocity;
	}
	
	public double getAcceleration()
	{
		return acceleration;
	}

}
