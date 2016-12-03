
package org.usfirst.frc.team4908.robot;

import org.usfirst.frc.team4908.util.MotionProfile;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{
	MotionProfile mp;
	
	Encoder rightEncoder;
    Encoder leftEncoder;
    
    CANTalon frontLeft;
	CANTalon frontRight;
	CANTalon backLeft;
	CANTalon backRight;
		
	Joystick stick1;
    Joystick stick2;
    
    RobotDrive drive;
    
    double kA;
    double kV;
    
    double kP;
    double kI;
    double kD;
    
	double[] mpArray;

    int driveCommand;
    
    double startTime;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	frontLeft = new CANTalon(1);
    	frontRight = new CANTalon(2);
    	backLeft = new CANTalon(4);
    	backRight = new CANTalon(3);        
        
        stick1 = new Joystick(0);
        stick2 = new Joystick(1);
        
        leftEncoder = new Encoder(3, 2, true);
        leftEncoder.setDistancePerPulse((2*Math.PI) / 1440); // radians per pulse
        leftEncoder.setSamplesToAverage(64);
        rightEncoder = new Encoder(7, 6, false);
        rightEncoder.setDistancePerPulse((2*Math.PI) / 1440);
        rightEncoder.setSamplesToAverage(64);
        
    	drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

    	mp = new MotionProfile(2.0, 5.0, -2.0); 
    	
    	kA = 1/mp.getAcceleration();
    	kV = 1/mp.getMaxVelocity();
    	
    	driveCommand = 0;
    }
    
    public void autonomousInit() 
    {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() 
    {
    
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() 
    {
        if(launchpad.getRawButton(1))
        {
        	mp.calculatePointsForDistance(20);
        	driveCommand = 1;
        	startTime = System.currentTimeMillis()/1000;
        }
        
        switch(driveCommand)
        {
        	case 0:
        		drive.arcadeDrive(stick1.getRawAxis(1), stick1.getRawAxis(2));
        		break;
        	case 1:
        		mpDrive((System.currentTimeMillis()/1000) - startTime);
        		break;
        	default:
        		break;
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    
    }
    
    public void mpDrive(double time)
    {
    	double[] array;

    	if(time <= mp.totalTime)
    	{
    		array = mp.getValuesAtTime(time);
    		drive.arcadeDrive((0.2*array[2])+(0.5*array[3]), 0);
    	}
    	else
    	{
    		driveCommand = 0;
    	}
    }
}
