package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ReleaseStack extends CommandGroup {
    
    public  ReleaseStack() {
        addParallel(new ManualArm());
        addParallel(new MoveForklift(LiftSetpoints.GROUND, true));
        addSequential(new OpenGrabber() {
        	
        	@Override
        	protected boolean isFinished()
        	{
        		return Robot.grabber.inToleranceOpen();
        	}
        });
        addSequential(new PushTotes());
    }
    
    @Override
    protected void end()
    {
    	new RetractPusher().start();
    }
    @Override
    protected void interrupted()
    {
    	end();
    }
}
