package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Get5thTote extends CommandGroup {
    
    public  Get5thTote() {
    	addParallel(new ManualArm());
    	addParallel(new MoveForklift(LiftSetpoints.GROUND, true));
    	addSequential(new OpenGrabberWide() {
        	
        	@Override
        	protected boolean isFinished()
        	{
        		return Robot.grabber.inToleranceOpenWide();
        	}
        });
    	addParallel(new OpenGrabberWide());
        addParallel(new ScorpionHoldBack());
    }
    
    @Override
    public void end()
    {
    	new ScorpionHold().start();
    }
    
    @Override
    public void interrupted()
    {
    	end();
    }
}
