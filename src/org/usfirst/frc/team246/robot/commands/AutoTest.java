package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoTest extends CommandGroup {
    
    public  AutoTest() {
    	addParallel(new ZeroNavX(0));
    	for(int i = 0; i < 10; i++)
    	{
	    	addParallel(AutoSetDriveSpeed.modifyCrab(2));
	    	addSequential(new AutoAlignAndDrive(new Vector2D(true, -10, 0), true));
		    addParallel(AutoSetDriveSpeed.modifyCrab(5));
		    addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 0), false));
    	}
    }
}
