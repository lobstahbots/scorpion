package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TransitionSimple extends Command {

	public boolean inFront;
	
    public TransitionSimple(boolean inFront) {
        requires(Robot.arm);
        this.inFront = inFront;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(inFront) Robot.arm.transitionIndex = RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length - 1;
    	else Robot.arm.transitionIndex = 0;
    		goToSetpoint();
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	goToSetpoint();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.oi.transitioner.getStart().get();
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
    	Robot.arm.set(RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT[Robot.arm.transitionIndex]);
    }
}
