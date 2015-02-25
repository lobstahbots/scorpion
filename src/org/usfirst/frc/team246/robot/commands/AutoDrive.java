package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

public class AutoDrive extends FieldCentricDrivingCommand{
	
//	RESET ODOMETRY DATA
	
	private Vector2D targetLocation; // x,y relative to robot, angle field centric
	private double heading; // field centric
	
	public AutoDrive(Vector2D targetLocation, double heading)
	{
		this.targetLocation = targetLocation;
		this.heading = heading;
	}
	
	protected void initialize() {
		Robot.drivetrain.odometry.resetAll();
		Robot.drivetrain.enableAbsoluteCrab(true);
        Robot.drivetrain.enableAbsoluteTwist(true);
		execute();
    }
    
	@Override
    protected void execute() {
    	Robot.drivetrain.setFOV(updateHeading());
    	Robot.drivetrain.absoluteCrabPID.setSetpoint(targetLocation.getMagnitude());
    	Robot.drivetrain.absoluteTwistPID.setSetpoint(heading);
        
        Robot.drivetrain.drivetrainPID.setCOR(getCOR());
        Robot.drivetrain.drivetrainPID.setCrabDirection(targetLocation.getAngle() - Robot.drivetrain.getFOV()); //sets the direction to Robot Centric
        Robot.drivetrain.drivetrainPID.setCrabSpeed(Robot.drivetrain.absoluteCrabPID.get()); // sets magnitude using PID
        Robot.drivetrain.drivetrainPID.setTwist(Robot.drivetrain.absoluteTwistPID.get() - Robot.drivetrain.getFOV()); // sets twist using PID, then Robot Centric
    }
    
    protected void end() {
    	Robot.drivetrain.enableAbsoluteCrab(false);
    	Robot.drivetrain.enableAbsoluteTwist(false);
    }

	@Override
	protected Vector2D getCrabVector() {
		return targetLocation;
	}

	@Override
	protected double getSpinRate() {
		return heading;
	}

	@Override
	protected Vector2D getCOR() {
		return RobotMap.ROBOT_CIRCLE_CENTER;
	}

	@Override
	protected boolean isFinished() {
		return Robot.drivetrain.absoluteCrabPID.onTarget() && Robot.drivetrain.absoluteTwistPID.onTarget();
	}

	@Override
	protected void interrupted() {	
		end();
	}
}
