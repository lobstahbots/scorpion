package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ScorpionHoldBack extends Command {

    public ScorpionHoldBack() {
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.elbow.setOutputRange(-0.3, 0.3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.forklift.onTarget() && Robot.forklift.getPIDController().get() > 0) 
    	{
    		Robot.arm.elbow.setOutputRange(0, 0.3);
    	}
    	else Robot.arm.elbow.setOutputRange(-0.3, 0.3);
    	Robot.arm.set(ArmSetpoints.SCORPION_HOLD);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.elbow.setOutputRange(-RobotMap.ARM_MAX_SPEED, RobotMap.ARM_MAX_SPEED);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
