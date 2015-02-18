package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.subsystems.Arm;
import org.usfirst.frc.team246.robot.subsystems.Forklift;

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
    	/*
    	if(setpoint == ArmSetpoints.ON_LIFT)
    	{
    		if(Robot.arm.getVector().getX() > 0 || Robot.arm.getWrist() > 0)
    		{
    			toFrontCommand = new MoveArmToFront();
    			toFrontCommand.start();
    		}
    	}
    	else
    	{
    		if(Robot.arm.getVector().getX < 0 )
    	}
    	*/
    	a.set(setpoint);
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
