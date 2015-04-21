package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Auto20PointsNoArm extends CommandGroup {
    
    public  Auto20PointsNoArm() {
    	addParallel(new ZeroNavX(90));
    	if(!Robot.trojan) addParallel(new CloseGrabber());
    	if(!Robot.trojan) addParallel(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true));
    	/*
		addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
		//addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 2.5), true));
		addParallel(AutoSetDriveSpeed.modifyCrab(1));
		//addSequential(new AutoAlignAndDrive(new Vector2D(true, -3, 2.5), false));
		//addSequential(new AutoAlignAndDrive(new Vector2D(true, -3, 0), false));
		addParallel(new AutoSlideCan());
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -4, 0), false));
		addParallel(new AdjustTote());
		addParallel(AutoSetDriveSpeed.modifyCrab(4));
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -6.5, 0), false));
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.GROUND, true));
		if(!Robot.trojan) addParallel(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true));
		addParallel(AutoSetDriveSpeed.modifyCrab(5)); //Set the speed to 5
		//addSequential(new AutoAlignAndDrive(new Vector2D(true, -6.5, 2.5), false));
		addParallel(AutoSetDriveSpeed.modifyCrab(1));
		//addSequential(new AutoAlignAndDrive(new Vector2D(true, -10, 2.5), false));
		//addSequential(new AutoAlignAndDrive(new Vector2D(true, -10, 0), false));
		addParallel(new AutoSlideCan());
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -10, 0), false));
		addParallel(AutoSetDriveSpeed.modifyCrab(4));
		addParallel(new AdjustTote());
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -14, 0), false));
		*/
    	
    	addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 6)); //Set the speed to 5
    	addParallel(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
    	addParallel(new Outgest());
    	addSequential(new AutoSpin(135));
    	addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 2), true));
    	addSequential(new AutoDriveSimple(new Vector2D(true, -4.5, 2), false)
		{
			@Override
			protected boolean isFinished()
			{
				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
			}
		});
		addParallel(new AdjustTote());// Intake the second tote
		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -7, 1.25), false)); //Drive into the second tote
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.GROUND, true));
		if(!Robot.trojan) addParallel(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
		addSequential(new AutoSpin(135));
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -7, 2), false)); //Drive into the second tote
    	addParallel(new Outgest());
    	addParallel(AutoSetDriveSpeed.modifyCrab(5));
    	addSequential(new AutoDriveSimple(new Vector2D(true, -11.5, 2), false)
		{
			@Override
			protected boolean isFinished()
			{
				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
			}
		});
		addParallel(new AdjustTote());// Intake the second tote
		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -14, 2), false)); //Drive into the second tote
    	
		addParallel(AutoSetDriveSpeed.modifyCrab(6)); //Set the speed to 5
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -14, 9), false));
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.BETWEEN_TOTES, true));
		addParallel(new Outgest() {
			@Override
			protected void execute() {
		    	Robot.getters.set(-.2, -.2);
		    }

		});
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -11,  9), false));
    }
}
