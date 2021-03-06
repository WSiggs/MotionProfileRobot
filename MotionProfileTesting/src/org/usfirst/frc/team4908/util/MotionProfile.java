package org.usfirst.frc.team4908.util;

public class MotionProfile
{
    private double acceleration;
    private double robot_max_velocity;
    private double deceleration;

    private double timeToCruise;
    private double cruiseTime;
    private double timeToStop;

    private double accelDistance;
    private double cruiseDistance;
    private double decelDistance;

    public double totalTime;

    private double v_cruise;
    
    private Setpoint currentSetpoint;

    public MotionProfile(double acceleration, double robot_max_velocity, double deceleration)
    {
        this.acceleration = acceleration;
        this.robot_max_velocity = robot_max_velocity;
        this.deceleration = deceleration;
        
        currentSetpoint = new Setpoint();
    }

    public void calculatePointsForDistance(double distance)
    {
        v_cruise = Math.min(robot_max_velocity, calcTheoMax(distance));

        if(Math.min(robot_max_velocity, calcTheoMax(distance)) == robot_max_velocity)
        {
            timeToCruise = v_cruise / acceleration;
            timeToStop = Math.abs(v_cruise / deceleration);

            accelDistance = (.5)*acceleration*(timeToCruise*timeToCruise);
            decelDistance = Math.abs((.5)*deceleration*(timeToStop*timeToStop));
            cruiseDistance = distance - (accelDistance+decelDistance);

            cruiseTime = cruiseDistance/v_cruise;
        }
        else
        {
            timeToCruise = v_cruise / acceleration;
            timeToStop = Math.abs(v_cruise / deceleration);

            accelDistance = (.5)*acceleration*(timeToCruise*timeToCruise);
            decelDistance = Math.abs((.5)*deceleration*(timeToStop*timeToStop));
            cruiseDistance = 0.0;

            cruiseTime = 0.0;
        }
        
        // region debugging
        
        System.out.println("ROBOT VARS: " +
                        "\nAcceleration:\t " + acceleration +
                        "\nV_Cruise Value:\t\t"   +  v_cruise       +
                        "\nV_Cruise Reject:\t\t"  +  calcTheoMax(distance)       +
                        "\nRobot Max Vel.:\t " + robot_max_velocity);

        System.out.println("\nMOTION PROFILE VARS:" +
                "\nTime To Cruise:\t\t "  +  timeToCruise   +
                "\nCruise Time:\t\t "     +  cruiseTime     +
                "\nTime to Stop:\t\t "    +  timeToStop     +
                "\nAccel Distance:\t\t "  +  accelDistance  +
                "\nCruise Distance:\t "   +  cruiseDistance +
                "\nDecel Distance:\t\t "  +  decelDistance);
        // endregion debugging

		
        totalTime = timeToCruise+cruiseTime+timeToStop;
    }

    public double calcTheoMax(double distance)
    {
        return Math.sqrt(2.0*acceleration*(distance));
    }

    public void setValuesAtTime(double time)
    {
        /**
         *  All calulations use the kinematic equations
         */
    	
        if(time <= timeToCruise && time >= 0)
        {
            currentSetpoint.setPosition((0.5)*acceleration*time*time);
            currentSetpoint.setVelocity(acceleration*time);
            currentSetpoint.setAcceleration(acceleration);
        }
        else if(time > timeToCruise && time <= (timeToCruise + cruiseTime))
        {
            time -= timeToCruise;
            currentSetpoint.setPosition(accelDistance+v_cruise*time);
            currentSetpoint.setVelocity(v_cruise);
            currentSetpoint.setAcceleration(0);
        }
        else if(time > timeToCruise+cruiseTime && time <= timeToCruise+cruiseTime+timeToStop)
        {
            time -= (timeToCruise+cruiseTime);
            currentSetpoint.setPosition((accelDistance+cruiseDistance)+(v_cruise*time)+((0.5)*(deceleration)*(time)*(time)));
            currentSetpoint.setVelocity(v_cruise+(time*deceleration));
            currentSetpoint.setAcceleration(deceleration);
        }
        else
        {
            currentSetpoint.setPosition(0.0);
            currentSetpoint.setVelocity(0.0);
            currentSetpoint.setAcceleration(0.0);
        }
    }
    
    public double getAcceleration()
    {
    	return acceleration;
    }
    
    public double getMaxVelocity()
    {
    	return robot_max_velocity;
    }
    
    public double getV_Cruise()
    {
    	return v_cruise;
    }
    
    public Setpoint getSetpoint()
    {
    	return currentSetpoint;
    }
}
