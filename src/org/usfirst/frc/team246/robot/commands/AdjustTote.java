package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.AlertMessage;
import org.usfirst.frc.team246.robot.overclockedLibraries.UdpAlertService;
import org.usfirst.frc.team246.robot.subsystems.Getters;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class AdjustTote extends Command {
	
	PIDController leftPID;
	PIDController rightPID;

    public AdjustTote() {
        requires(Robot.getters);
        
        leftPID = new PIDController(RobotMap.GETTER_ADJUSTING_kP, RobotMap.GETTER_ADJUSTING_kI, RobotMap.GETTER_ADJUSTING_kD, RobotMap.leftRangeFinder, RobotMap.leftGetterMotor);
        LiveWindow.addActuator("Getters", "leftAdjustingPID", leftPID);
        rightPID = new PIDController(RobotMap.GETTER_ADJUSTING_kP, RobotMap.GETTER_ADJUSTING_kI, RobotMap.GETTER_ADJUSTING_kD, RobotMap.rightRangeFinder, RobotMap.rightGetterMotor);
        LiveWindow.addActuator("Getters", "rightAdjustingPID", rightPID);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftPID.enable();
    	leftPID.setSetpoint(RobotMap.LEFT_RANGE_FINDER_PID_SETPOINT);
    	rightPID.enable();
    	rightPID.setSetpoint(RobotMap.RIGHT_RANGE_FINDER_PID_SETPOINT);
    	
    	UdpAlertService.sendAlert(new AlertMessage("Intaking").playSound("slurp.wav"));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	leftPID.disable();
    	rightPID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
