package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.StopGrabber;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Grabber extends PIDSubsystem {

	public Grabber()
	{
		super(RobotMap.GRABBER_kP, RobotMap.GRABBER_kI, RobotMap.GRABBER_kD, .02, RobotMap.GRABBER_kF);
		setAbsoluteTolerance(.01);
		LiveWindow.addActuator("Grabber", "grabberPID", this.getPIDController());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new StopGrabber());
    }
    
    public void open()
    {
    	disable();
    	grabberSetpoint = RobotMap.GRABBER_CLOSED;
    	if(RobotMap.grabberEncoder.getDistance() < RobotMap.GRABBER_UNSAFE_MIN)
    	{
    		RobotMap.grabberMotor.set(0);
    	}
    	else if(RobotMap.grabberEncoder.getDistance() > RobotMap.GRABBER_OPEN)
    	{
    		RobotMap.grabberMotor.set(-.75);
    	}
    	else
    	{
    		RobotMap.grabberMotor.set(0);
    	}
    }
    public void openWide()
    {
    	disable();
    	grabberSetpoint = RobotMap.GRABBER_CLOSED;
    	if(RobotMap.grabberEncoder.getDistance() < RobotMap.GRABBER_UNSAFE_MIN)
    	{
    		RobotMap.grabberMotor.set(0);
    	}
    	else if(RobotMap.grabberEncoder.getDistance() > RobotMap.GRABBER_OPEN_WIDE)
    	{
    		RobotMap.grabberMotor.set(-.75);
    	}
    	else
    	{
    		RobotMap.grabberMotor.set(0);
    	}
    }
    public double grabberSetpoint = RobotMap.GRABBER_CLOSED;
    public void close()
    {
    	disable();
    	if(RobotMap.grabberMotor.getCurrent() > RobotMap.GRABBER_CURRENT_LIMIT && grabberSetpoint == RobotMap.GRABBER_CLOSED) 
    	{
    			grabberSetpoint = Math.min(RobotMap.GRABBER_CLOSED, RobotMap.grabberEncoder.getDistance() + RobotMap.GRABBER_CURRENT_OFFSET);
    	}
    	if(RobotMap.grabberEncoder.getDistance() > RobotMap.GRABBER_UNSAFE_MAX)
    	{
    		RobotMap.grabberMotor.set(0);
    	}
    	else if(RobotMap.grabberEncoder.getDistance() < grabberSetpoint)
    	{
    		RobotMap.grabberMotor.set(.5);
    	}
    	else
    	{
    		RobotMap.grabberMotor.set(0);
    	}
    }

	@Override
	protected double returnPIDInput() {
		return RobotMap.grabberEncoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.grabberMotor.set(output);
	}
	
	public boolean inToleranceOpen()
	{
		 return Math.abs(RobotMap.grabberEncoder.getDistance() - RobotMap.GRABBER_OPEN) < 5;
	}
	public boolean inToleranceOpenWide()
	{
		return Math.abs(RobotMap.grabberEncoder.getDistance() - RobotMap.GRABBER_OPEN_WIDE) < 5;
	}
}

