package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSetDriveSpeed extends Command {

	double topCrabSpeed;
	boolean changeCrab;
	double topSpinSpeed;
	boolean changeSpin;
	
    private AutoSetDriveSpeed(double topCrabSpeed, boolean changeCrab, double topSpinSpeed, boolean changeSpin) {
        this.topCrabSpeed = topCrabSpeed;
        this.changeCrab = changeCrab;
        this.topSpinSpeed = topSpinSpeed;
        this.changeSpin = changeSpin;
    }
    public static AutoSetDriveSpeed modifyCrabAndSpin(double topCrabSpeed, double topSpinSpeed)
    {
    	return new AutoSetDriveSpeed(topCrabSpeed, true, topSpinSpeed, true);
    }
    public static AutoSetDriveSpeed modifyCrab(double topCrabSpeed)
    {
    	return new AutoSetDriveSpeed(topCrabSpeed, true, 0, false);
    }
    public static AutoSetDriveSpeed modifySpin(double topSpinSpeed)
    {
    	return new AutoSetDriveSpeed(0, false, topSpinSpeed, true);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	if(changeCrab) Robot.drivetrain.maxCrabSpeed = topCrabSpeed;
    	if(changeSpin) Robot.drivetrain.maxSpinSpeed = topSpinSpeed;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
