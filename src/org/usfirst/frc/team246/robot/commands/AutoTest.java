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
public class AutoTest extends CommandGroup {
    
    public  AutoTest() {
    	addParallel(new ZeroNavX(90));
    	addParallel(new CloseGrabber());
//    	addParallel(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
    	addSequential(new MoveArm(ArmSetpoints.AUTON_POSITION_1));
		addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
//		addParallel(new Outgest()); //Slide for the first can
//		addSequential(new AutoSpin(135)); //Spin to the right angle
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 2), true)); //Drive towards the center of the field
		addSequential(new WaitCommand(1));
		addSequential(new EngageScorpionMode() {
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
			
			protected void end() {}
		});
//		addSequential(new AutoDriveSimple(new Vector2D(true, -4.5, 1.5782), false)
//		{
//			@Override
//			protected boolean isFinished()
//			{
//				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
//			}
//		});
//		addParallel(new Intake());// Intake the second tote
//		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -8, 1.25), false)); //Drive into the second tote
		addParallel(new MoveForklift(LiftSetpoints.RECEIVE_ARM_TOTE, true));
		addSequential(new WaitCommand(.25));
//		addParallel(AutoSetDriveSpeed.modifyCrab(5)); //Set the speed to 5
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -8, 9), false));
//		addSequential(new AutoSpin(90));
//		addSequential(new Outgest(), .25);
//		addParallel(new Intake());
//		addSequential(new WaitCommand(.5));
//		//addParallel(new MoveForklift(LiftSetpoints.FIX_ARM_TOTE, true));
		addParallel(new OpenGrabber());
		addSequential(new WaitCommand(1));
		addSequential(new MoveForklift(LiftSetpoints.BETWEEN_TOTES, true));
//		addParallel(new Outgest() {
//			@Override
//			protected void execute() {
//		    	Robot.getters.set(-.2, -.2);
//		    }
//
//		});
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -5,  9), false));
    	
//    	addParallel(new ZeroNavX(90));
//    	addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
//		addParallel(new Outgest()); //Slide for the first can
//		addSequential(new AutoSpin(135)); //Spin to the right angle
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 2), true)); //Drive towards the center of the field
//		addSequential(new WaitCommand(1));
//		addSequential(new AutoDriveSimple(new Vector2D(true, -4.5, 2), false)
//		{
//			@Override
//			protected boolean isFinished()
//			{
//				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
//			}
//		});
//		addParallel(new Intake());// Intake the second tote
//		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -8, 2), false)); //Drive into the second tote
    }
}
