package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveArmToBack extends CommandGroup {
    
    public  MoveArmToBack() {
    	addSequential(new MoveArm(ArmSetpoints.ON_LIFT));
        addSequential(new TransitionOverCeiling(ArmSetpoints.CURLED_TAIL.getShoulder()));
        addSequential(new MoveArm(ArmSetpoints.CURLED_TAIL));
    }
}
