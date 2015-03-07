package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
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
    	if(!Robot.trojan) addSequential(new MoveArm(ArmSetpoints.AUTON_POSITION_1));
		addParallel(new AutoSetDriveSpeed(5)); //Set the speed to 5
		addParallel(new Outgest()); //Slide for the first can
		addParallel(new AutoSpin(135)); //Spin to the right angle
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.ABOVE_CAN, true));
		if(Robot.trojan) addSequential(new WaitCommand(1.5)); //Wait for fork to get above can
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 1.75, 0))); //Drive towards the center of the field
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 4.5, 90)));
		addParallel(new Intake());// Intake the second tote
		addParallel(new AutoSetDriveSpeed(3)); //Set the speed to 3
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 3.5, 90))); //Drive into the second tote
		addSequential(new WaitCommand(.5));
		addParallel(new AutoSetDriveSpeed(5)); //Set the speed to 5
		addSequential(new AutoSpin(180));
		addSequential(new AutoSlideCan() {
			@Override
			protected void initialize()
			{
				setTimeout(.5);
			}
			@Override
			protected boolean isFinished()
			{
				return isTimedOut();
			}
		});
		addParallel(new Intake());
		if(Robot.trojan) addSequential(new WaitCommand(1));
		if(!Robot.trojan) addParallel(new OpenGrabber());
		addSequential(new Outgest(), .5);
		addSequential(new Intake(), 1);
		/*
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.GROUND, true) {
			boolean waiting = false;
			
			@Override
			protected void execute()
			{
				if(getters.hasTote()) 
				{
					super.execute();
					waiting = false;
				}                                           //THIS ANONYMOUS CLASS NEED TO BE COMMENTED OUT
				else
				{
					waiting = true;
					UdpAlertService.sendAlert(new AlertMessage("Failed to get tote"));
				}
			}
			@Override
			protected boolean isFinished()
			{
				return super.isFinished() && !waiting;
			}
		});
		if(!Robot.trojan) addParallel(new MoveArm(ArmSetpoints.AUTON_POSITION_2));
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, false));
		if(!Robot.trojan) addParallel(new CloseGrabber());
		addParallel(new AutoSetDriveSpeed(3));
		addSequential(new AutoSpin(90){
			@Override
			protected boolean isFinished()
			{
				return RobotMap.navX.getYaw() < 135;
			}
		});
		addSequential(new AutoSpin(0));
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 7.5, 0)));
		if(!Robot.trojan) addSequential(new MoveForklift(LiftSetpoints.GROUND, false));
		*/
		
		/*
		addParallel(new AutoSetDriveSpeed(3)); //Set the speed to 3
		addParallel(new AutoSlideCan()); //Slide for the first can
		addParallel(new AutoSpin(135)); //Spin to the right angle
		addSequential(new WaitCommand(1.5)); //Wait for fork to get above can
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 2, 0))); //Drive towards the center of the field
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 4, 95)));
		addParallel(new Intake());
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 2.25, 95)));
		addSequential(new WaitCommand(1.5));
		addParallel(new AutoSlideCan());
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 6.5, 95)));
		addParallel(new Intake());
		addSequential(new AutoAlignAndDrive(new Vector2D(false, .5, 95)));
		addSequential(new WaitCommand(.5));
		addParallel(new AutoSetDriveSpeed(5)); //Set the speed to 5
		addSequential(new AutoAlignAndDrive(new Vector2D(false, 8.5, 0)));
		*/
		
//		addParallel(new Intake());
//		addSequential(new AutoAlignAndDrive(new Vector2D(false, 2, 90)));
//		addSequential(new AutoSetDriveSpeed(4));
//		addSequential(new AutoSpin(135));
//		addSequential(new AutoAlignAndDrive(new Vector2D(false, 3.5, 0)));
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -6, -1.5)));
//		addParallel(new Intake());
//		addParallel(new AutoSetDriveSpeed(2));
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -.5, -.25)));
//		addParallel(new AlignWheels(0));
//		addSequential(new WaitCommand(.75));
//		addParallel(new Outgest());
//		addSequential(new WaitCommand(.2));
//		addParallel(new Intake());
//		addSequential(new WaitCommand(.75));
//		addParallel(new AutoSetDriveSpeed(4));
//		addSequential(new AutoAlignAndDrive(new Vector2D(false, 1.75, 0)));
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -6.25, -1.5)));
//		addParallel(new AutoSetDriveSpeed(2));
//		addSequential(new AutoAlignAndDrive(new Vector2D(true, -.5, -.25)));
//		addParallel(new AlignWheels(0));
//		addSequential(new WaitCommand(.75));
//		addParallel(new Outgest());
//		addSequential(new WaitCommand(.2));
//		addParallel(new Intake());
//		addSequential(new WaitCommand(.75));
//		addParallel(new AutoSetDriveSpeed(4));
//		addSequential(new AutoAlignAndDrive(new Vector2D(false, 7.5, 0)));
    }
}
