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
    	Vector2D v = Robot.arm.getVector();
    	//Robot.arm.set(v.getX() + Robot.oi.operator.getRightXAxis(), v.getY() + Robot.oi.operator.getRightYAxis(), Robot.arm.getWrist() + Robot.oi.operator.getLeftYAxis());
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
