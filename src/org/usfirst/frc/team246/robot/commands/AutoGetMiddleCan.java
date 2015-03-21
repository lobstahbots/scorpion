package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGetMiddleCan extends CommandGroup {
    
    public  AutoGetMiddleCan() {
        addParallel(new ZeroNavX(0));
        addParallel(new AutoSetDriveSpeed(5));
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 1.25, 0), true));
    }
}
