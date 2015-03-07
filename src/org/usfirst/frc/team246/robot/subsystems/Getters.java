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
        boolean leftToteLimitSwitchOn = RobotMap.leftToteLimitSwitch.get();
        boolean rightToteLimitSwitchOn = RobotMap.rightToteLimitSwitch.get();
        
    	if(leftToteLimitSwitchOn && rightToteLimitSwitchOn)
    	{
    		if(!hadTote) UdpAlertService.sendAlert(new AlertMessage("Tote obtained").playSound("woohoo.wav"));
    		hadTote = true;
    		return true;
    	}
    	else if(!leftToteLimitSwitchOn || !rightToteLimitSwitchOn) 
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