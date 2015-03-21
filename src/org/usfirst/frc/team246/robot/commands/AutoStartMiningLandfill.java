package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStartMiningLandfill extends CommandGroup {
    
    public  AutoStartMiningLandfill() {
        addParallel(new ZeroNavX(90));
        addParallel(new AutoSetDriveSpeed(4));
        addSequential(new AutoSpin(45));
        addSequential(new AutoAlignAndDrive(new Vector2D(false, 1, 45), true));
        addSequential(new AutoSpin(45));
        addSequential(new AutoAlignAndDrive(new Vector2D(false, 1, 45), true));
    }
}
