package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExitScorpionMode extends Command {
	
	ArmSetpoints[] waypoints = RobotMap.ARM_TRANSITION_ARRAY;
	
    public ExitScorpionMode() {
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	int bestSetpoint = 0;
    	double bestSetpointError = Double.MAX_VALUE;
    	for(int i = 0; i < waypoints.length; i++)
    	{
    		double error = Robot.arm.sumSquareError(waypoints[i].getShoulder(), waypoints[i].getElbow(), waypoints[i].getElbow());
    		if(error < bestSetpointError)
    		{
    			bestSetpoint = i;
    			bestSetpointError = error;
    		}
    	}
    	Robot.arm.transitionIndex = bestSetpoint;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.arm.transitionIndex == 1 && Robot.arm.sumSquareError(waypoints[Robot.arm.transitionIndex].getShoulder(), waypoints[Robot.arm.transitionIndex].getElbow(), waypoints[Robot.arm.transitionIndex].getWrist()) < 100) Robot.arm.transitionIndex--;
    	goToSetpoint();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.transitionIndex == 0 && Robot.arm.sumSquareError(waypoints[Robot.arm.transitionIndex].getShoulder(), waypoints[Robot.arm.transitionIndex].getElbow(), waypoints[Robot.arm.transitionIndex].getWrist()) < 50;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.set(RobotMap.armShoulderPot.get(), RobotMap.armElbowPot.get(), RobotMap.armWristPot.get());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private void goToSetpoint()
    {
    	Robot.arm.set(RobotMap.ARM_TRANSITION_ARRAY[Robot.arm.transitionIndex]);
    }
}