package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Grabber extends PIDSubsystem {

	public Grabber()
	{
		super(RobotMap.GRABBER_kP, RobotMap.GRABBER_kI, RobotMap.GRABBER_kD);
		setInputRange(RobotMap.GRABBER_CLOSED, RobotMap.GRABBER_OPEN_WIDE);
		setAbsoluteTolerance(10);
		LiveWindow.addActuator("Grabber", "grabberPID", this.getPIDController());
	}
	
    public void initDefaultCommand() {
    	if(!Robot.trojan) setDefaultCommand(new CloseGrabber());
    }
    
    public void open()
    {
    	setSetpoint(RobotMap.GRABBER_OPEN);
    }
    public void openWide()
    {
    	setSetpoint(RobotMap.GRABBER_OPEN_WIDE);
    }
    public void close()
    {
    	setSetpoint(RobotMap.GRABBER_CLOSED);
    }

	@Override
	protected double returnPIDInput() {
		return RobotMap.grabberEncoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.grabberMotor.set(output);
	}
}

