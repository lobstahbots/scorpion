package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Forklift extends PIDSubsystem {
    
    public Forklift() {
		super(RobotMap.LIFT_kP, RobotMap.LIFT_kI, RobotMap.LIFT_kD);
		setAbsoluteTolerance(RobotMap.LIFT_TOLERANCE);
	}

	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	protected double returnPIDInput() {
		if(getSetpoint() > RobotMap.LIFT_MAX_HEIGHT)
		{
			setSetpoint(RobotMap.LIFT_MAX_HEIGHT);
		}
		if(getSetpoint() < RobotMap.LIFT_MIN_HEIGHT)
		{
			setSetpoint(RobotMap.LIFT_MIN_HEIGHT);
		}
		return RobotMap.liftPot.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.liftMotor.set(output);
	}
}

