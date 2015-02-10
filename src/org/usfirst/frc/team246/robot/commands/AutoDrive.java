package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

public class AutoDrive extends FieldCentricDrivingCommand{
	
	double x;
	double y;
	double heading;
	
	public AutoDrive(double x, double y, double heading)
	{
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
	protected void initialize() {
		
		execute();
        
        Robot.drivetrain.enableAbsoluteTwist(true);
    }
    
    protected void execute() {
    	Robot.drivetrain.setFOV(updateHeading());
    	
        Vector2D crabVector = getCrabVector();
        crabVector.setAngle(crabVector.getAngle() - Robot.drivetrain.getFOV());
        Vector2D COR = getCOR();
        
        Robot.drivetrain.driveAbsoluteTwist(crabVector.getMagnitude(), crabVector.getAngle(), heading);
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
