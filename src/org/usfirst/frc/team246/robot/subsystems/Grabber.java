package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends PIDSubsystem {
    
    public Grabber()
    {
    	super(RobotMap.GRABBER_kP, RobotMap.GRABBER_kI, RobotMap.GRABBER_kD);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CloseGrabber());
    }
    
    public void open()
    {
    	this.setSetpoint(RobotMap.GRABBER_OPEN_POSITION);
    }
    public void close()
    {
    	this.setSetpoint(RobotMap.GRABBER_CLOSED_POSITION);
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return RobotMap.grabberPot.get(); 
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.grabberMotor.set(output);
	}
}

