package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

/**
 *
 */
public abstract class DeadReckoningDrive extends DrivingCommand {
	
	Vector2D crabVector;
	double spinRate;
	Vector2D COR;

	public DeadReckoningDrive(Vector2D crabVector, double spinRate, Vector2D COR) {
		super();
		this.crabVector = crabVector;
		this.spinRate = spinRate;
		this.COR = COR;
	}
	public DeadReckoningDrive(Vector2D crabVector) {
		this(crabVector, 0, new Vector2D(true, 0, 0));
	}
	public DeadReckoningDrive(double spinRate)
	{
		this(new Vector2D(true, 0, 0), spinRate, new Vector2D(true, 0, -11.67));
	}
	public DeadReckoningDrive(double spinRate, Vector2D COR) {
		this(new Vector2D(true, 0, 0), spinRate, COR);
	}
	
	@Override
	protected Vector2D getCrabVector() {
		return crabVector;
	}

	@Override
	protected double getSpinRate() {
		return spinRate;
	}

	@Override
	protected Vector2D getCOR() {
		return COR;
	}

	@Override
	protected double updateHeading() {
		return 0;
	}

	@Override
	protected void initialize() {}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {}

    
}
