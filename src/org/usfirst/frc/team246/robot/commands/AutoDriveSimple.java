package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

public class AutoDriveSimple extends FieldCentricDrivingCommand{
	
//	RESET ODOMETRY DATA
	
	private Vector2D targetLocation; // x,y relative to robot, angle field centric
	private double heading; // field centric
	private boolean zero;
	
	public AutoDriveSimple(Vector2D targetLocation, boolean zeroOdometry)
	{
		super();
		this.targetLocation = targetLocation;
		zero = zeroOdometry;
	}
	
	
	
	protected void initialize() {
		if(zero) Robot.drivetrain.odometry.resetAll();
		heading = RobotMap.navX.getYaw();
		Robot.drivetrain.setAccelerationRamping(true);
		Robot.drivetrain.enableAbsoluteCrab(true);
		execute();
    }
    
	@Override
    protected void execute() {
    	Robot.drivetrain.setFOV(updateHeading());
    	Robot.drivetrain.absoluteCrabPID.setSetpoint(Vector2D.subtractVectors(targetLocation, Robot.drivetrain.odometry.getFieldCentricLinearDisplacement()).getMagnitude() + Robot.drivetrain.odometry.pidGet());
        
    	Robot.drivetrain.drivetrainPID.setTwist(getSpinRate());
        Robot.drivetrain.drivetrainPID.setCOR(getCOR());
        Vector2D crabDirection = Vector2D.subtractVectors(targetLocation, Robot.drivetrain.odometry.getFieldCentricLinearDisplacement());
        if(Robot.drivetrain.absoluteCrabPID.get() < 0) crabDirection.setAngle(crabDirection.getAngle() + 180);
        Robot.drivetrain.drivetrainPID.setCrabDirection(crabDirection.getAngle() - Robot.drivetrain.getFOV());
	}
    
    protected void end() {
    	Robot.drivetrain.setAccelerationRamping(false);
    	Robot.drivetrain.enableAbsoluteCrab(false);
    }

	@Override
	protected Vector2D getCrabVector() {
		return targetLocation;
	}

	@Override
	protected double getSpinRate() {
		return 0;
	}

	@Override
	protected Vector2D getCOR() {
		return RobotMap.ROBOT_CIRCLE_CENTER;
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(Vector2D.subtractVectors(targetLocation, Robot.drivetrain.odometry.getFieldCentricLinearDisplacement()).getMagnitude()) < .2;
	}

	@Override
	protected void interrupted() {	
		end();
	}
}