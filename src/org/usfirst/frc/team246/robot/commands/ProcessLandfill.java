package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.subsystems.Grabber;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ProcessLandfill extends Command {

    public ProcessLandfill() {
    }

    AdjustTote adjustTote = new AdjustTote();
    MoveForklift moveForklift = new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true);
    CloseGrabber closeGrabber = new CloseGrabber();
    OpenGrabber openGrabber = new OpenGrabber();
    OpenGrabberWide openGrabberWide = new OpenGrabberWide();
    Command scorpionHold = null;
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	adjustTote.start();
    }
    
    enum LiftState { WAITING, LOWERING, LIFTING }
    
    LiftState liftState = LiftState.WAITING;

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(Robot.arm.getCurrentCommand().getClass() == ScorpionHold.class) scorpionHold = Robot.arm.getCurrentCommand();
    	
    	if(Robot.hasTote && Robot.oi.processingConfirmLowerButton.get() && liftState != LiftState.LIFTING)
    	{
    		if(liftState == LiftState.WAITING)
    		{
	    		liftState = LiftState.LOWERING;
	    		moveForklift = new MoveForklift(LiftSetpoints.GROUND, true);
	    		moveForklift.start();
    		}
    		openGrabber();
    	}
    	else if(liftState == LiftState.LOWERING && Robot.forklift.onTarget() && (!Robot.oi.processingOpenGrabberButton.get() || Robot.grabber.onTarget()))
    	{
    		liftState = LiftState.LIFTING;
    		if(Robot.oi.processingDontRaiseLiftButton.get()) moveForklift = new MoveForklift(LiftSetpoints.SCORING_PLATFORM, true);
    		else moveForklift = new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true);
    		moveForklift.start();
    		openGrabber();
    	}
    	else if(liftState == LiftState.LIFTING && !Robot.forklift.onTarget())
    	{
    		openGrabber();
    	}
    	else
    	{
    		liftState = LiftState.WAITING;
    		(new ManualForklift()).start();
    		if(scorpionHold != null) scorpionHold.start();
    		closeGrabber.start();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	adjustTote.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    private void openGrabber()
    {
    	if(Robot.oi.processingOpenGrabberButton.get()) 
		{
			openGrabberWide.start();
			if(Robot.grabber.inToleranceOpenWide()) scorpionHold.start();
			else new ManualArm().start();
		}
		else 
		{
			scorpionHold.start();
			closeGrabber.start();
		}
    }
}