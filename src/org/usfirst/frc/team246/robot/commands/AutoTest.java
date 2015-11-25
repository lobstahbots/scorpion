package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoTest extends CommandGroup {
    
    public  AutoTest() {
    	addParallel(new ZeroNavX(90));
    	addParallel(new CloseGrabber());
    	addSequential(new MoveArm(ArmSetpoints.AUTON_POSITION_1));
		addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
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
		addParallel(new MoveForklift(LiftSetpoints.RECEIVE_ARM_TOTE, true));
		addSequential(new WaitCommand(.25));
		addParallel(new OpenGrabber());
		addSequential(new WaitCommand(1));
		addSequential(new MoveForklift(LiftSetpoints.BETWEEN_TOTES, true));
    }
}
