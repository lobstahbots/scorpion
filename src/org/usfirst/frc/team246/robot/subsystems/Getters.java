package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.Intake;
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
        setDefaultCommand(new Intake());
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
    		UdpAlertService.sendAlert(new AlertMessage("Tote obtained"));
    		return true;
    	}
    	else
    	{
    		UdpAlertService.sendAlert(new AlertMessage("Tote lost"));
    		return false;
    	}
    }
    
    public boolean leftInTolerance()
    {
    	return Math.abs(RobotMap.leftGetterPot.get()) < RobotMap.GETTER_POTS_TOLERANCE;
    }
    public boolean rightInTolerance()
    {
    	return Math.abs(RobotMap.rightGetterPot.get()) < RobotMap.GETTER_POTS_TOLERANCE;
    }
}