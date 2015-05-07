package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.Robot.RobotMode;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EngageScorpionMode extends Command {
	
	protected ArmSetpoints[] waypoints = RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT;
	
    public EngageScorpionMode() {
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//  TELEOP:
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
    	if(Robot.arm.transitionIndex < waypoints.length - 1 && Robot.arm.sumSquareError(waypoints[Robot.arm.transitionIndex].getShoulder(), waypoints[Robot.arm.transitionIndex].getElbow(), waypoints[Robot.arm.transitionIndex].getWrist()) < 100) Robot.arm.transitionIndex++;
    	if(Robot.arm.transitionIndex == 3) Robot.arm.shoulder.setOutputRange(-.75, .75);
    	else Robot.arm.shoulder.setOutputRange(-RobotMap.ARM_MAX_SPEED, RobotMap.ARM_MAX_SPEED);
    	goToSetpoint();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.transitionIndex == waypoints.length - 1 && Robot.arm.sumSquareError(waypoints[Robot.arm.transitionIndex].getShoulder(), waypoints[Robot.arm.transitionIndex].getElbow(), waypoints[Robot.arm.transitionIndex].getWrist()) < 50;
    }

    // Called once after isFinished returns true
    protected void end() {
    	new ScorpionHold().start();  // if testing auton scorpion, comment out this line
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private void goToSetpoint()
    {
    	Robot.arm.set(waypoints[Robot.arm.transitionIndex]);
    }
}
