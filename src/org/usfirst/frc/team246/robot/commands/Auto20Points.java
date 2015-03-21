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
public class Auto20Points extends CommandGroup {
    
    public  Auto20Points() {
    	addParallel(new ZeroNavX(90));
    	addParallel(new CloseGrabber());
    	addParallel(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
    	addSequential(new MoveArm(ArmSetpoints.AUTON_POSITION_1));
		addParallel(new AutoSetDriveSpeed(5)); //Set the speed to 5
		addParallel(new Outgest()); //Slide for the first can
		addSequential(new AutoSpin(135)); //Spin to the right angle
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 1.25, 0), true)); //Drive towards the center of the field
		addSequential(new WaitCommand(1));
		addParallel(new EngageScorpionMode() {
			@Override
			protected void initialize()
			{
				waypoints = new ArmSetpoints[RobotMap.ARM_TRANSITION_ARRAY.length + 1];
				for(int i = 0; i < RobotMap.ARM_TRANSITION_ARRAY.length; i++)
				{
					waypoints[i] = RobotMap.ARM_TRANSITION_ARRAY[i];
				}
				waypoints[RobotMap.ARM_TRANSITION_ARRAY.length] = ArmSetpoints.AUTON_POSITION_3;
				super.initialize();
			}
		});
		addParallel(new AutoSetDriveSpeed(5));
		addSequential(new AutoDriveSimple(new Vector2D(false, 4.5, 90), true)
		{
			@Override
			protected boolean isFinished()
			{
				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
			}
		});
		addParallel(new Intake());// Intake the second tote
		addParallel(new AutoSetDriveSpeed(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 8, 90), false)); //Drive into the second tote
		addParallel(new MoveForklift(LiftSetpoints.RECEIVE_ARM_TOTE, true));
		addSequential(new WaitCommand(.25));
		addParallel(new AutoSetDriveSpeed(5)); //Set the speed to 5
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 5, 0), true));
		addSequential(new Outgest(), .25);
		addParallel(new Intake());
		addSequential(new WaitCommand(.25));
		addSequential(new OpenGrabber(), .75);
		addSequential(new WaitCommand(.5));
		addSequential(new MoveForklift(LiftSetpoints.BETWEEN_TOTES, true));
		addParallel(new Outgest() {
			@Override
			protected void execute() {
		    	Robot.getters.set(-.2, -.2);
		    }

		});
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 3,  -45), true));
    }
}
