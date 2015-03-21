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
        addParallel(new CloseGrabber());
        addSequential(new WaitCommand(3));
        addParallel(new OpenGrabber());
    }
}
