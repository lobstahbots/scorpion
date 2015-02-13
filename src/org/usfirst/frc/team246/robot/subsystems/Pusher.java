package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.RetractPusher;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Pusher extends PIDSubsystem {

    // Initialize your subsystem here
    public Pusher() {
        super(RobotMap.PUSHER_kP, RobotMap.PUSHER_kI, RobotMap.PUSHER_kD);
        this.setAbsoluteTolerance(.05);
    }
    
    public void initDefaultCommand() {
    	if(!Robot.trojan)
    	{
    		setDefaultCommand(new RetractPusher());
    	}
    }
    
    protected double returnPIDInput() {
    	if(Robot.trojan)
    	{
    		return 0;
    	}
    	else
    	{
    		return RobotMap.pusherPot.get();
    	}
    }
    
    protected void usePIDOutput(double output) {
        RobotMap.pusherMotor.set(output);
    }
}
