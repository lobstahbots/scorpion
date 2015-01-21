package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;

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
        setDefaultCommand();
    }
    
    protected double returnPIDInput() {
    	return RobotMap.pusherPot.get();
    }
    
    protected void usePIDOutput(double output) {
        RobotMap.pusherMotor.set(output);
    }
}
