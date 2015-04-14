package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;
import org.usfirst.frc.team246.robot.commands.ManualGrabber;
import org.usfirst.frc.team246.robot.commands.OpenGrabber;
import org.usfirst.frc.team246.robot.commands.StopGrabber;

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
		//setInputRange(RobotMap.GRABBER_CLOSED, RobotMap.GRABBER_OPEN_WIDE);
		setAbsoluteTolerance(.01);
		LiveWindow.addActuator("Grabber", "grabberPID", this.getPIDController());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new StopGrabber());
    }
    
    public void open()
    {
//    	currentStopped = false;
//    	enable();
//    	setSetpoint(RobotMap.GRABBER_OPEN);
    	disable();
    	if(RobotMap.grabberEncoder.getDistance() > RobotMap.GRABBER_OPEN) RobotMap.grabberMotor.set(-.75);
    	else RobotMap.grabberMotor.set(0);
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
//    	SmartDashboard.putNumber("Grabber current draw", RobotMap.grabberMotor.getCurrent());
//    	if(RobotMap.grabberMotor.getCurrent() < 50 && !currentStopped)
//    	{
//    		enable();
//    		setSetpoint(RobotMap.GRABBER_CLOSED);
//    	}
//    	else
//    	{
//    		currentStopped = true;
//    		disable();
//    		RobotMap.grabberMotor.set(0);
//    	}
    	disable();
    	if(RobotMap.grabberEncoder.getDistance() > RobotMap.GRABBER_UNSAFE_MIN) RobotMap.grabberMotor.set(-.75);
    	else if(RobotMap.grabberEncoder.getDistance() < RobotMap.GRABBER_CLOSED) RobotMap.grabberMotor.set(.5);
    	else RobotMap.grabberMotor.set(0);
    }

	@Override
	protected double returnPIDInput() {
		return RobotMap.grabberEncoder.getDistance();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.grabberMotor.set(output);
	}
	
	public boolean inTolerance()
	{
		 return Math.abs(RobotMap.grabberEncoder.getDistance()) < .01;
	}
}

