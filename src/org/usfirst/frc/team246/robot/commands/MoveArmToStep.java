package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArmToStep extends Command {

    public MoveArmToStep() {
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.pidOn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.set(ArmSetpoints.STEP.getShoulder() + RobotMap.navX.getRoll(), ArmSetpoints.STEP.getElbow() + RobotMap.navX.getRoll(), ArmSetpoints.STEP.getWrist());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.oi.operator.getLeftMagnitude() > .25 || Robot.oi.operator.getRightMagnitude() > .25;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
