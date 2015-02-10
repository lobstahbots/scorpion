package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManualArm extends Command {

    public ManualArm() {
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.pidOn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*
    	Vector2D v = Robot.arm.getVector();
    	double x = v.getX() + Robot.oi.operator.getRightXAxis();
    	if(Robot.oi.operator.getRightXAxis() == 0) x = Robot.arm.getTargetVector().getX();
    	double y = v.getY() + Robot.oi.operator.getRightYAxis();
    	if(Robot.oi.operator.getRightYAxis() == 0) y = Robot.arm.getTargetVector().getY();
    	double w = Robot.arm.getWrist() + Robot.oi.operator.getLeftYAxis();
    	if(Robot.oi.operator.getLeftYAxis() == 0) w = Robot.arm.getTargetWrist();
    	Robot.arm.set(x, y, w);
    	*/
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
