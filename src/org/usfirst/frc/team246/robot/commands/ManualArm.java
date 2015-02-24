package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ManualArm extends Command {

    public ManualArm() {
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.pidOn(true);
    	Robot.arm.shoulder.setSetpoint(RobotMap.armShoulderPot.get());
    	Robot.arm.elbow.setSetpoint(RobotMap.armElbowPot.get());
    	Robot.arm.wrist.setSetpoint(RobotMap.armWristPot.get());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double s = RobotMap.armShoulderPot.get() - Robot.oi.operator.getRightYAxis() * RobotMap.ARM_SHOULDER_MANUAL_SPEED;
    	if(Robot.oi.operator.getRightYAxis() == 0) s = Robot.arm.shoulder.getSetpoint();
    	else Robot.arm.currentSetpoint = null;
    	double e = RobotMap.armElbowPot.get() + Robot.oi.operator.getRightXAxis() * RobotMap.ARM_ELBOW_MANUAL_SPEED;
    	if(Robot.oi.operator.getRightXAxis() == 0) e = Robot.arm.elbow.getSetpoint();
    	else Robot.arm.currentSetpoint = null;
    	double w = Robot.arm.getWrist() - Robot.oi.operator.getLeftYAxis() * RobotMap.ARM_WRIST_MANUAL_SPEED;
    	if(Robot.oi.operator.getLeftYAxis() == 0) w = Robot.arm.getTargetWrist();
    	else Robot.arm.currentSetpoint = null;
    	Robot.arm.set(s, e, w);
    	
    	
    	/* 
    	double x = Robot.arm.getVector().getX() - Robot.oi.operator.getRightXAxis() * RobotMap.ARM_X_MANUAL_SPEED;
    	if(Robot.oi.operator.getRightXAxis() == 0) x = Robot.arm.getTargetVector().getX();
    	else Robot.arm.currentSetpoint = null;
    	double y = Robot.arm.getVector().getY() + Robot.oi.operator.getRightYAxis() * RobotMap.ARM_Y_MANUAL_SPEED;
    	if(Robot.oi.operator.getRightYAxis() == 0) y = Robot.arm.getTargetVector().getY();
    	else Robot.arm.currentSetpoint = null;
    	double w = Robot.arm.getWrist() - Robot.oi.operator.getLeftYAxis() * RobotMap.ARM_WRIST_MANUAL_SPEED;
    	if(Robot.oi.operator.getLeftYAxis() == 0) w = Robot.arm.getTargetWrist();
    	else Robot.arm.currentSetpoint = null;
    	Robot.arm.setCartesian(x, y, w);
    	 */
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
