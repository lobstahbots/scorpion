package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoTest extends CommandGroup {
    
    public  AutoTest() {
    	addParallel(new ZeroNavX(90));
    	addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
		addParallel(new Outgest()); //Slide for the first can
		addSequential(new AutoSpin(135)); //Spin to the right angle
		addSequential(new AutoAlignAndDrive(new Vector2D(true, 0, 2), true)); //Drive towards the center of the field
		addSequential(new WaitCommand(1));
		addSequential(new AutoDriveSimple(new Vector2D(true, -4.5, 2), false)
		{
			@Override
			protected boolean isFinished()
			{
				return Robot.drivetrain.odometry.pidGet() > getCrabVector().getMagnitude() - .2;
			}
		});
		addParallel(new Intake());// Intake the second tote
		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(true, -8, 2), false)); //Drive into the second tote
    }
}
