package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.StopGetters;
import org.usfirst.frc.team246.robot.overclockedLibraries.AlertMessage;
import org.usfirst.frc.team246.robot.overclockedLibraries.SpeedController246;
import org.usfirst.frc.team246.robot.overclockedLibraries.UdpAlertService;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Getters extends Subsystem {
	
	SpeedController246 leftMotor = RobotMap.leftGetterMotor;
	SpeedController246 rightMotor = RobotMap.rightGetterMotor;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        setDefaultCommand(new StopGetters());
    }
    
    public void set(double left, double right)
    {
    	leftMotor.set(left);
    	rightMotor.set(right);
    }
    
    boolean hadTote = false;
    
    public boolean hasTote()
    {	
    	if(RobotMap.leftRangeFinder.get() > RobotMap.LEFT_RANGE_FINDER_IN && RobotMap.rightRangeFinder.get() > RobotMap.RIGHT_RANGE_FINDER_IN)
    	{
    		if(!hadTote) UdpAlertService.sendAlert(new AlertMessage("Tote obtained").playSound("woohoo.wav"));
    		hadTote = true;
    		return true;
    	}
    	else if(RobotMap.leftRangeFinder.get() < RobotMap.LEFT_RANGE_FINDER_OUT || RobotMap.rightRangeFinder.get() < RobotMap.RIGHT_RANGE_FINDER_OUT)
    	{
    		if(hadTote) UdpAlertService.sendAlert(new AlertMessage("Tote lost").playSound("doh.wav"));
    		hadTote = false;
    		return false;
    	}
    	else
    	{
    		return hadTote;
    	}
    }
}