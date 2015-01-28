package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.OTSController;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class OTS extends PIDSubsystem {

    // Initialize your subsystem here
    public OTS() {
    	super("OTS", RobotMap.OTS_kP, RobotMap.OTS_kI, RobotMap.OTS_kD, RobotMap.OTS_kF);
    }
    
    public void initDefaultCommand() {
        setDefaultCommand(new OTSController());
    }
    
    protected double returnPIDInput() {
    	return Robot.otsRPM;
    }
    
    protected void usePIDOutput(double output) {
        RobotMap.otsMotor.set(output);
    }
}
