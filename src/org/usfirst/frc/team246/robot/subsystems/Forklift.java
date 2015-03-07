package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.commands.ManualForklift;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Forklift extends PIDSubsystem {
	
	public int totesHigh = 0;
    
    public Forklift() {
		super(RobotMap.LIFT_kP, RobotMap.LIFT_kI, RobotMap.LIFT_kD, 0.02);
		LiveWindow.addActuator("Forklift", "liftPID", this.getPIDController());
		setAbsoluteTolerance(RobotMap.LIFT_TOLERANCE);
		setOutputRange(-1, .8);
	}

	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        if(!Robot.trojan)
        {
        	setDefaultCommand(new ManualForklift());
        }
    }

	@Override
	protected double returnPIDInput() {
		if(Robot.trojan)
		{
			return 0;
		}
		else
		{
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
	}

	@Override
	protected void usePIDOutput(double output) {
		RobotMap.liftMotor.set(output);
	}
	
	public double getToteAdder()
	{
		return totesHigh*RobotMap.TOTE_HEIGHT;
	}
}

