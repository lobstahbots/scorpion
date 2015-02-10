package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.Intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Getters extends Subsystem {
	
	VictorSP leftMotor = RobotMap.leftGetterMotor;
	VictorSP rightMotor = RobotMap.rightGetterMotor;
	DoubleSolenoid leftCylinder =  RobotMap.leftGetterCylinder;
	DoubleSolenoid rightCylinder = RobotMap.rightGetterCylinder;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        setDefaultCommand(new Intake());
    }
    
    public void set(double left, double right)
    {
    	leftMotor.set(left);
    	rightMotor.set(right);
    }
}