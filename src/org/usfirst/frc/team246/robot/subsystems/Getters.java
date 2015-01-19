package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Getters extends Subsystem {
	
	VictorSP motor= RobotMap.leftGetterMotor;
	VictorSP motor2= RobotMap.rightGetterMotor;
	DoubleSolenoid leftpneumatic=  RobotMap.leftGetterCylinder;
	DoubleSolenoid rightpneumatic= RobotMap.rightGetterCylinder;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void forward(){
    	motor.set(1);
    	motor2.set(1);
    }
    public void backward(){
    	motor.set(-1);
    	motor2.set(-1);
    }
    public void stop(){
    	motor.set(0);
    	motor2.set(0);	
    }
    public void Deployleftgetter(){
    	leftpneumatic.set(DoubleSolenoid.Value.kForward);
    }
    public void Deployrightgetter(){
    	rightpneumatic.set(DoubleSolenoid.Value.kForward);
    }
    public void Retractleftgetter(){
    	leftpneumatic.set(DoubleSolenoid.Value.kReverse);
    }
    public void Retractrightgetter(){
    	rightpneumatic.set(DoubleSolenoid.Value.kReverse);
    }
    }