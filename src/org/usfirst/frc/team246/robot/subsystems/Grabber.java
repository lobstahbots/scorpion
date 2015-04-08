package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Grabber extends PIDSubsystem {

	public Grabber()
	{
		super(RobotMap.GRABBER_kP, RobotMap.GRABBER_kI, RobotMap.GRABBER_kD, .02, RobotMap.GRABBER_kF);
		setInputRange(RobotMap.GRABBER_CLOSED, RobotMap.GRABBER_OPEN_WIDE);
		setAbsoluteTolerance(.01);
		LiveWindow.addActuator("Grabber", "grabberPID", this.getPIDController());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new CloseGrabber());
    }
    
    public void open()
    {
    	currentStopped = false;
    	enable();
    	setSetpoint(RobotMap.GRABBER_OPEN);
    }
    public void openWide()
    {
    	currentStopped = false;
    	enable();
    	setSetpoint(RobotMap.GRABBER_OPEN_WIDE);
    }
    boolean currentStopped = false;
    public void close()
    {
    	SmartDashboard.putNumber("Grabber current draw", RobotMap.grabberMotor.getCurrent());
    	if(RobotMap.grabberMotor.getCurrent() < 50 && !currentStopped)
    	{
    		enable();
    		setSetpoint(RobotMap.GRABBER_CLOSED);
    	}
    	else
    	{
    		currentStopped = true;
    		disable();
    		RobotMap.grabberMotor.set(0);
    	}
    }

	@Override
	protected double returnPIDInput() {
		return RobotMap.grabberPot.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.grabberMotor.set(output);
	}
}

