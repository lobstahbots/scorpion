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
public class Auto20PointsCan3 extends CommandGroup {
    
    public  Auto20PointsCan3() {
    	addParallel(new ZeroNavX(90));
    	addParallel(new OffsetGrabber(20));
    	addParallel(new CloseGrabber());
    	addParallel(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
    	addSequential(new MoveArm(ArmSetpoints.AUTON_POSITION_1));
		addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
		addParallel(new Outgest()); //Slide for the first can
		addSequential(new AutoSpin(135)); //Spin to the right angle
		addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 2.5), true)); //Drive towards the center of the field
		addSequential(new WaitCommand(1));
		addParallel(new EngageScorpionMode() {
			@Override
			protected void initialize()
			{
				waypoints = new ArmSetpoints[RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length + 1];
				for(int i = 0; i < RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length; i++)
				{
					waypoints[i] = RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT[i];
				}
				waypoints[RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length] = ArmSetpoints.AUTON_POSITION_3;
				super.initialize();
			}
		});
		addSequential(new AutoDriveSimple(new Vector2D(true, -4.5, 2.5), false)
		{
			@Override
			protected boolean isFinished()
			{
				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
			}
		});
		addParallel(new Intake());// Intake the second tote
		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -8, 2.5), false)); //Drive into the second tote
		addParallel(new MoveForklift(LiftSetpoints.RECEIVE_ARM_TOTE, true));
		addSequential(new WaitCommand(.25));
		addParallel(AutoSetDriveSpeed.modifyCrab(5)); //Set the speed to 5
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -8, 11.25), false));
		addSequential(new Outgest(), .25);
		addParallel(new Intake());
		addSequential(new WaitCommand(.25));
		addSequential(new OpenGrabber());
		addSequential(new WaitCommand(2));
		addParallel(new ExitScorpionMode());
		addParallel(new AutoSpin(-60));
		addSequential(new MoveForklift(LiftSetpoints.BETWEEN_TOTES, true));
		addParallel(new Outgest() {
			@Override
			protected void execute() {
		    	Robot.getters.set(-.2, -.2);
		    }

		});
		addSequential(new AutoAlignAndDrive(Vector2D.addVectors(new Vector2D(true, -8, 6.25), new Vector2D(false, 8, -60)), true));
    }
}
