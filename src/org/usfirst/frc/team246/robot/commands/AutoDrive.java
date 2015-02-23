package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
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
		Robot.drivetrain.odometry.resetAll(); // BUG: need odometry object
		
		execute();
        
		Robot.drivetrain.enableCrabTwist(true);
        Robot.drivetrain.enableAbsoluteTwist(true);
    }
    
    protected void execute() {
    	Robot.drivetrain.setFOV(updateHeading());
    	
        Vector2D crabVector = getCrabVector();
        crabVector.setAngle(crabVector.getAngle() - Robot.drivetrain.getFOV());
        Vector2D COR = getCOR();
        
//        Robot.drivetrain.driveAbsoluteTwist(crabVector.getMagnitude(), crabVector.getAngle(), heading);
    }
    
    protected void end()
    {
    	Robot.drivetrain.enableAbsoluteTwist(false);
    }

	@Override
	protected Vector2D getCrabVector() {
		return new Vector2D(true, x ,y);
	}

	@Override
	protected double getSpinRate() {
		return 0;
	}

	@Override
	protected Vector2D getCOR() {
		return null;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void interrupted() {	
	}
}
