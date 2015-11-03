package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OTSGetTote extends Command {
	
	private boolean rightIntakeFirst;
    
    public OTSGetTote(boolean rightIntakeFirst) {
    	requires(Robot.drivetrain);
    	this.rightIntakeFirst = rightIntakeFirst;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double parallelShiftAngle;
    	Vector2D getterLocation;
    	if (rightIntakeFirst) {
    		parallelShiftAngle = Robot.otsReciever.getToteAngle() - 90;
    		getterLocation = RobotMap.RIGHT_GRABBER_LOCATION;
    	} else {
    		parallelShiftAngle = Robot.otsReciever.getToteAngle();
    		getterLocation = RobotMap.LEFT_GRABBER_LOCATION;
    	}
    	Vector2D parallelToteShift = new Vector2D(false, RobotMap.PARALLEL_TOTE_SHIFT_MAGNITUDE, parallelShiftAngle);
    	Vector2D getterTarget = Vector2D.addVectors(Robot.otsReciever.getToteCorner(), parallelToteShift); // where on the tote getter should hit
    	Vector2D crabDirection = Vector2D.subtractVectors(getterTarget, getterLocation);
    	Robot.drivetrain.drive(1, crabDirection.getAngle(), 0, RobotMap.ROBOT_CIRCLE_CENTER.getX(), RobotMap.ROBOT_CIRCLE_CENTER.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.getters.hasToteWithoutSound();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.drive(0, 0, 0, RobotMap.ROBOT_CIRCLE_CENTER.getX(), RobotMap.ROBOT_CIRCLE_CENTER.getY());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
