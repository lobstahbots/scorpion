package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.subsystems.Forklift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveForkliftUp1 extends Command {

	Forklift f = Robot.forklift;
    public MoveForkliftUp1() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(f.getCurrentCommand().getClass() == MoveForklift.class)
    	{
    		f.totesHigh++;
    	}
    	else
    	{
    		(new MoveForklift(RobotMap.LiftSetpoints.SCORING_PLATFORM, true)).start();
    	}
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
