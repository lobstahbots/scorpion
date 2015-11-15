package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

public class AlignWheels extends FieldCentricDrivingCommand {

	double angle;
	
	public AlignWheels(double angle)
	{
		super();
		this.angle = angle;
	}
	
	@Override
	protected Vector2D getCrabVector() {
		return new Vector2D(false, 0.0001, angle);
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
	protected void initialize() {
	}

	@Override
	protected boolean isFinished() {
		for(int i = 0; i < Robot.drivetrain.swerves.length; i++)
		{
			if(!Robot.drivetrain.swerves[i].anglePID.onTarget()) return false;
		}
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
