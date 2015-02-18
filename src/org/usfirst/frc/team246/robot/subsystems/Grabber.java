package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Grabber extends Subsystem {

    public void initDefaultCommand() {
        setDefaultCommand(new CloseGrabber());
    }
    
    public void open()
    {
    	if(RobotMap.grabberEncoder.getDistance() < RobotMap.GRABBER_OPEN) RobotMap.grabberMotor.set(RobotMap.GRABBER_OPEN_SPEED);
    	else RobotMap.grabberMotor.set(RobotMap.GRABBER_HOLD_SPEED);
    }
    public void close()
    {
    	if(RobotMap.grabberEncoder.getDistance() > RobotMap.GRABBER_OPEN) RobotMap.grabberMotor.set(RobotMap.GRABBER_CLOSE_SPEED);
    	else RobotMap.grabberMotor.set(0);
    }
}

