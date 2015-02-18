package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Intake extends Command {
	
	
	public Intake() {
		requires(Robot.getters);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.oi.driver.getRB().get()) Robot.getters.set(-1, -1);
    	else if(/*Robot.getters.hasTote()*/ false) Robot.getters.set(0,  0);
    	else Robot.getters.set( Robot.oi.driver.getRightTriggerAxis(),  Robot.oi.driver.getRightTriggerAxis());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
