package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveArm extends Command {

	Arm a = Robot.arm;

	ArmSetpoints setpoint;
	MoveArmToBack toBackCommand;
	MoveArmToFront toFrontCommand;
	
    public MoveArm(ArmSetpoints setpoint) {
        requires(a);
        this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	a.pidOn(true);
    	a.set(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(RobotMap.armShoulderPot.get() <= a.getVector().getAngle()) a.bendUp = true;
    	else a.bendUp = false;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.oi.operator.getLeftMagnitude() > .25 || Robot.oi.operator.getRightMagnitude() > .25 || (a.shoulder.onTarget() && a.elbow.onTarget() && a.wrist.onTarget());
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
