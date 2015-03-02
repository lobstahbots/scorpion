package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDriveSimple extends FieldCentricDrivingCommand{
	
//	RESET ODOMETRY DATA
	
	private Vector2D targetLocation; // x,y relative to robot, angle field centric
	private double heading; // field centric
	
	public AutoDriveSimple(Vector2D targetLocation)
	{
		super();
		this.targetLocation = targetLocation;
	}
	
	
	
	protected void initialize() {
		Robot.drivetrain.odometry.resetAll();
		heading = RobotMap.navX.getYaw();
		Robot.drivetrain.enableAbsoluteCrab(true);
		execute();
    }
    
	@Override
    protected void execute() {
    	Robot.drivetrain.setFOV(updateHeading());
    	Robot.drivetrain.absoluteCrabPID.setSetpoint(targetLocation.getMagnitude());
        
    	Robot.drivetrain.drivetrainPID.setTwist(getSpinRate());
        Robot.drivetrain.drivetrainPID.setCOR(getCOR());
        Robot.drivetrain.drivetrainPID.setCrabDirection(targetLocation.getAngle() - Robot.drivetrain.FOV); //sets the direction to Robot Centric
        
        System.out.println("Odometry: " + Robot.drivetrain.odometry.pidGet());
        System.out.println("Setpoint: " + Robot.drivetrain.absoluteCrabPID.getSetpoint());
        System.out.println("Output: " + Robot.drivetrain.absoluteCrabPID.get());
	}
    
    protected void end() {
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
		return Math.abs(Math.abs(Robot.drivetrain.odometry.pidGet()) - targetLocation.getMagnitude()) < .2;
	}

	@Override
	protected void interrupted() {	
		end();
	}
}