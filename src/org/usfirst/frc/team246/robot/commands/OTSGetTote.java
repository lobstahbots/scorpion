package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Vector2D toteAquiredLocation = Robot.otsReciever.getToteCorner();
    	double parallelShiftAngle = rightIntakeFirst ? 90 + Robot.otsReciever.getToteAngle() : Robot.otsReciever.getToteAngle() - 180; // relative to 0 degrees on Y-axis
    	// I'm triple sure the above calculation is right (Michael)
    	toteAquiredLocation.setMagnitude(toteAquiredLocation.getMagnitude() + RobotMap.OTS_GET_TOTE_OVERSHOOT); // drive 10in past corner to acquire
    	Vector2D parallelToteShift = new Vector2D(false, RobotMap.LEFT_RIGHT_TOTE_OFFSET, parallelShiftAngle);
    	toteAquiredLocation = Vector2D.addVectors(toteAquiredLocation, parallelToteShift); // shift parallel to short side of tote
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
