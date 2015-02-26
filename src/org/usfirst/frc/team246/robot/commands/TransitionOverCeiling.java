package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TransitionOverCeiling extends Command {

	double shoulderGoal;
	
    public TransitionOverCeiling(double shoulderGoal) {
        requires(Robot.arm);
        this.shoulderGoal = shoulderGoal;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.pidOn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.synchronizedMoveBelowCeiling(shoulderGoal, -90);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.shoulder.onTarget() && Robot.arm.elbow.onTarget() && Robot.arm.wrist.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.shoulder.setOutputRange(-RobotMap.ARM_MAX_SPEED, RobotMap.ARM_MAX_SPEED);
    	Robot.arm.elbow.setOutputRange(-RobotMap.ARM_MAX_SPEED, RobotMap.ARM_MAX_SPEED);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
