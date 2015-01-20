package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
	DoubleSolenoid leftCylinder= RobotMap.leftGrabberCylinder;
	DoubleSolenoid rightCylinder= RobotMap.rightGrabberCylinder;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void open()
    {
    	leftCylinder.set(DoubleSolenoid.Value.kReverse);
    	rightCylinder.set(DoubleSolenoid.Value.kReverse);
    }
    public void close()
    {
    	leftCylinder.set(DoubleSolenoid.Value.kForward);
    	rightCylinder.set(DoubleSolenoid.Value.kForward);
    }
    public DoubleSolenoid.Value getPosition()
    {
    	return leftCylinder.get();
    }
}

