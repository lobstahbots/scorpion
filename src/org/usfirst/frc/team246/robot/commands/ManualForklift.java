package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.subsystems.Forklift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualForklift extends Command {

	Forklift f = Robot.forklift;
    public ManualForklift() {
        requires(f);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	f.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double triggersVal = Robot.oi.operator.getRightTriggerAxis() - Robot.oi.operator.getLeftTriggerAxis();
    	if(triggersVal != 0)
    	{
    		f.setSetpoint(triggersVal + Robot.forklift.getPosition());
    	}
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
