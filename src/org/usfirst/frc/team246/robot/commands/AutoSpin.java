package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

/**
 *
 */
public class AutoSpin extends DrivingCommand {
    
	double heading = 0;
	
	public AutoSpin(double heading)
	{
		super();
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
        
        Robot.drivetrain.absoluteTwistPID.setSetpoint(heading);
        Robot.drivetrain.drivetrainPID.setCrabSpeed(crabVector.getMagnitude());
        Robot.drivetrain.drivetrainPID.setCrabDirection(crabVector.getAngle());
        Robot.drivetrain.drivetrainPID.setCOR(RobotMap.ROBOT_CIRCLE_CENTER);
        
        System.out.println(Robot.drivetrain.absoluteTwistPID.get());
    }
    
    protected void end()
    {
    	Robot.drivetrain.enableAbsoluteTwist(false);
    }

	@Override
	protected Vector2D getCrabVector() {
		return new Vector2D(false, 0,  0);
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
		for(int i = 0; i < Robot.drivetrain.swerves.length; i++)
		{
			if(Robot.drivetrain.swerves[i].getWheelSpeed() > .2) return false;
		}
		double result = Math.abs(((RobotMap.navX.getYaw() + 360)%360) - ((heading + 360)%360));
		return result < 5 || result > 355;
	}

	@Override
	protected void interrupted() {
		end();
	}
	@Override
	protected double updateHeading() {
		return 0;
	}
}