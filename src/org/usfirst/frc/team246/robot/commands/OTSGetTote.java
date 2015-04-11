package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OTSGetTote extends Command {
	
	private boolean rightIntakeFirst;
	private boolean resetOdometry;
	private Vector2D lastCommandedPosition;
	
	// Don't reset odometry
    public OTSGetTote(boolean rightIntakeFirst, Vector2D lastCommandedPosition) {
    	this.rightIntakeFirst = rightIntakeFirst;
    	this.resetOdometry = false;
    	this.lastCommandedPosition = lastCommandedPosition;
    }
    
    // Reset odometry
    public OTSGetTote(boolean rightIntakeFirst) {
    	this(rightIntakeFirst, new Vector2D(true, 0, 0));
    	this.resetOdometry = true;
    }
    
    private AutoAlignAndDrive driveIntoTote;
    private static final double LEFT_RIGHT_TOTE_OFFSET = 4;
    // to hit short side of tote first, shift over this many inches

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Vector2D toteAquiredLocation = Robot.otsReciever.getToteCorner();
    	toteAquiredLocation.setMagnitude(toteAquiredLocation.getMagnitude() + 10); // drive 10in past corner to acquire
    	double toteAquiredX = rightIntakeFirst ? toteAquiredLocation.getX() - LEFT_RIGHT_TOTE_OFFSET : toteAquiredLocation.getX() + LEFT_RIGHT_TOTE_OFFSET;
    	toteAquiredLocation.setX(toteAquiredX);
    	toteAquiredLocation = Vector2D.addVectors(toteAquiredLocation, lastCommandedPosition); // correction if odometry not reset
    	driveIntoTote = new AutoAlignAndDrive(toteAquiredLocation, resetOdometry);
    	driveIntoTote.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveIntoTote.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
