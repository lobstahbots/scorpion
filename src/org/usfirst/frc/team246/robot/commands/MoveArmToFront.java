package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MoveArmToFront extends CommandGroup {
    
    public  MoveArmToFront() {
        addSequential(new MoveArm(ArmSetpoints.CURLED_TAIL));
        addSequential(new TransitionOverCeiling(ArmSetpoints.ON_LIFT.getShoulder()));
        addSequential(new MoveArm(ArmSetpoints.ON_LIFT));
    }
}
