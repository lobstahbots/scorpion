package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

public class SwerveTank extends DrivingCommand {

	@Override
	protected Vector2D getCrabVector() {
		double lx = Robot.oi.driver.getLeftXAxis();
		double ly = Robot.oi.driver.getLeftYAxis();
		double rx = Robot.oi.driver.getRightXAxis();
		double ry = Robot.oi.driver.getRightYAxis();
		
		return new Vector2D(false, -Math.pow((ry + ly)/2,3), -(lx + rx)*45);
	}

	@Override
	protected double getSpinRate() {
		return Math.pow((Robot.oi.driver.getLeftYAxis() - Robot.oi.driver.getRightYAxis())/2,3);
	}

	@Override
	protected Vector2D getCOR() {
		return new Vector2D(true, 0, -11.67);
	}

	@Override
	protected void initialize() {}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}

	@Override
	protected double updateHeading() {
		return 0;
	}

}
