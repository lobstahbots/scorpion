package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LiftTote extends CommandGroup {
    
    public  LiftTote() {
        addSequential(new MoveForklift(LiftSetpoints.GROUND, true));
        addSequential(new MoveForklift(LiftSetpoints.SCORING_PLATFORM, false));
    }
}
