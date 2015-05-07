package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GetTote extends CommandGroup {
    
    public  GetTote() {
        addSequential(new MoveForklift(LiftSetpoints.GROUND, true));
        addSequential(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, false));
    }
}
