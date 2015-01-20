package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.StopGetters;
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
        setDefaultCommand(new StopGetters());
    }
    
    public void forward(){
    	leftMotor.set(1);
    	rightMotor.set(1);
    }
    public void backward(){
    	leftMotor.set(-1);
    	rightMotor.set(-1);
    }
    public void stop(){
    	leftMotor.set(0);
    	rightMotor.set(0);	
    }
    public void deployLeftGetter(){
    	leftCylinder.set(DoubleSolenoid.Value.kForward);
    }
    public void deployRightGetter(){
    	rightCylinder.set(DoubleSolenoid.Value.kForward);
    }
    public void retractLeftGetter(){
    	leftCylinder.set(DoubleSolenoid.Value.kReverse);
    }
    public void retractRightGetter(){
    	rightCylinder.set(DoubleSolenoid.Value.kReverse);
    }
    public DoubleSolenoid.Value getLeftGetterPosition()
    {
    	return leftCylinder.get();
    }
    public DoubleSolenoid.Value getRightGetterPosition()
    {
    	return rightCylinder.get();
    }
}