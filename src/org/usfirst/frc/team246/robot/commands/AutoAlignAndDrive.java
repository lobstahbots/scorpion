package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoAlignAndDrive extends CommandGroup {
    
    public  AutoAlignAndDrive(Vector2D targetLocation) {
        addSequential(new AlignWheels(targetLocation.getAngle()));
        addSequential(new AutoDriveSimple(targetLocation));
    }
}
