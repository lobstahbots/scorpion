package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoMineLandfill extends CommandGroup {
    
    public  AutoMineLandfill() {
        addParallel(new ZeroNavX(0));
        addParallel(new AdjustTote());
        addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(3, 5));
        addSequential(new AutoSpin(-45));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 1.5), true));
        addSequential(new WaitCommand(2));
        addParallel(AutoSetDriveSpeed.modifyCrab(5));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, .5), false));
        addSequential(new AutoSpin(-45));
        addParallel(AutoSetDriveSpeed.modifyCrab(3));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 2, .5), false));
        addSequential(new WaitCommand(2));
        addParallel(AutoSetDriveSpeed.modifyCrab(5));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, .5), false));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 1.5), false));
        addSequential(new AutoSpin(-45));
        addParallel(AutoSetDriveSpeed.modifyCrab(3));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 2, 1.5), false));
        addSequential(new WaitCommand(2));
        addParallel(AutoSetDriveSpeed.modifyCrab(5));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 2, 2), false));
        addSequential(new AutoSpin(-45));
        addParallel(AutoSetDriveSpeed.modifyCrab(3));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 2, 2), false));
        addSequential(new WaitCommand(2));
        addParallel(AutoSetDriveSpeed.modifyCrab(5));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 4, 0), false));
        addSequential(new AutoSpin(-45));
        addParallel(AutoSetDriveSpeed.modifyCrab(3));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 4, 1), false));
        addSequential(new WaitCommand(2));
        addParallel(AutoSetDriveSpeed.modifyCrab(5));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 5, 0), false));
        addSequential(new AutoSpin(-45));
        addParallel(AutoSetDriveSpeed.modifyCrab(3));
        addSequential(new AutoAlignAndDrive(new Vector2D(true, 5, 1), false));
    }
}
