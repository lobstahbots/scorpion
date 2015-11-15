package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoGetAllTotes extends CommandGroup {
    
    public  AutoGetAllTotes() {
    	addParallel(new ZeroNavX(90));
    	if(!Robot.trojan) addParallel(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
    	if(!Robot.trojan) addSequential(new MoveArm(ArmSetpoints.AUTON_POSITION_1));
		addParallel(AutoSetDriveSpeed.modifyCrabAndSpin(5, 5)); //Set the speed to 5
		addParallel(new Outgest()); //Slide for the first can
		addSequential(new AutoSpin(135)); //Spin to the right angle
		if(Robot.trojan) addSequential(new WaitCommand(1.5)); //Wait for fork to get above can
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 1.75, 0), true)); //Drive towards the center of the field
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 4.5, 90), true));
		addParallel(new Intake());// Intake the second tote
		addParallel(AutoSetDriveSpeed.modifyCrab(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 4.5, 90), false)); //Drive into the second tote
		addSequential(new WaitCommand(.25));
		addSequential(new AutoSpin(160));
		if(!Robot.trojan) addParallel(new OpenGrabber());
		if(!Robot.trojan) addParallel(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true));
		addParallel(new Intake());
		if(Robot.trojan) addSequential(new WaitCommand(1));
		addSequential(new Outgest(), .25);
		addSequential(new Intake(), 1);
		addParallel(new Intake());

		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.GROUND, true) {
			boolean waiting = false;
			
			@Override
			protected void execute()
			{
				if(Math.abs(RobotMap.navX.getPitch()) > 10 || Math.abs(RobotMap.navX.getRoll()) > 10) 
				{
					setpoint = LiftSetpoints.ABOVE_CAN.getValue();
					waiting = true;
				}
				super.execute();
			}
			@Override
			protected boolean isFinished()
			{
				return super.isFinished() && !waiting;
			}
		});
		if(!Robot.trojan) addParallel(new MoveArm(ArmSetpoints.AUTON_POSITION_2));
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true));
		if(!Robot.trojan) addParallel(new CloseGrabber());
		addParallel(AutoSetDriveSpeed.modifySpin(3));
		addSequential(new AutoSpin(70){
			@Override
			protected boolean isFinished()
			{
				return RobotMap.navX.getYaw() < 90;
			}
		});
		addSequential(new AutoSpin(-20));
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 5, -20), true));
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.GROUND, true)
		{
			boolean waiting = false;
			
			@Override
			protected void execute()
			{
				if(Math.abs(RobotMap.navX.getPitch()) > 10 || Math.abs(RobotMap.navX.getRoll()) > 10) 
				{
					setpoint = LiftSetpoints.ABOVE_CAN.getValue();
					waiting = true;
				}
				super.execute();
			}
			@Override
			protected boolean isFinished()
			{
				return super.isFinished() && !waiting;
			}
		});
    }
}
