package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GetToteSimple extends Command {

	boolean longWays;
	boolean leftLandfill;
	
    public GetToteSimple(boolean longways, boolean leftLandfill) {
        this.longWays = longWays;
        this.leftLandfill = leftLandfill;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Vector2D driveV = Vector2D.addVectors(Robot.drivetrain.odometry.getFieldCentricLinearDisplacement(), new Vector2D(false, 3, 0));
    	new AutoAlignAndDrive(driveV, false).start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return SmartDashboard.getBoolean("haveTote");
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
