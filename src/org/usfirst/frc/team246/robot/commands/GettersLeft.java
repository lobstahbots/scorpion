package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.AlertMessage;
import org.usfirst.frc.team246.robot.overclockedLibraries.UdpAlertService;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GettersLeft extends Command {

	public GettersLeft() {
		requires(Robot.getters);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	UdpAlertService.sendAlert(new AlertMessage("Intaking").playSound("slurp.wav"));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.getters.set(1, -1);
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
