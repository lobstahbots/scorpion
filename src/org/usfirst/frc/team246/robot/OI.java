package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.commands.ChangeArmBend;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.commands.GoFast;
import org.usfirst.frc.team246.robot.commands.Intake;
import org.usfirst.frc.team246.robot.commands.LiftTote;
import org.usfirst.frc.team246.robot.commands.MoveArm;
import org.usfirst.frc.team246.robot.commands.MoveArmDown1;
import org.usfirst.frc.team246.robot.commands.MoveArmToBack;
import org.usfirst.frc.team246.robot.commands.MoveArmToFront;
import org.usfirst.frc.team246.robot.commands.MoveArmUp1;
import org.usfirst.frc.team246.robot.commands.MoveForklift;
import org.usfirst.frc.team246.robot.commands.MoveForkliftDown1;
import org.usfirst.frc.team246.robot.commands.MoveForkliftUp1;
import org.usfirst.frc.team246.robot.commands.OpenGrabber;
import org.usfirst.frc.team246.robot.commands.Outgest;
import org.usfirst.frc.team246.robot.commands.PushTotes;
import org.usfirst.frc.team246.robot.commands.RetractPusher;
import org.usfirst.frc.team246.robot.commands.RobotCentricCrabWithTwist;
import org.usfirst.frc.team246.robot.commands.StopGetters;
import org.usfirst.frc.team246.robot.overclockedLibraries.LogitechF310;
import org.usfirst.frc.team246.robot.overclockedLibraries.Toggle;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    
    public LogitechF310 driver; //TODO: instantiate these when we get the controllers
    public LogitechF310 operator;
    
    public OI()
    {
    	driver = new LogitechF310(0);
    	operator = new LogitechF310(1);
    	
    	//driver.getLB().whileHeld(new CrabWithAbsoluteTwist());
    	driver.getLT().whileHeld(new GoFast());
    	new Toggle() {
			
			@Override
			public boolean get() {
				return driver.getX2().get();
			}
			
			@Override
			public boolean getToggler() {
				return Robot.drivetrain.getCurrentCommand().getName().equals("RobotCentricCrabWithTwist");
			}
		}.toggle(new CrabWithTwist(), new RobotCentricCrabWithTwist());
    	
		new Toggle() {
			
			@Override
			public boolean get()
			{
				return driver.getRT().get();
			}
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == Intake.class;
			}
		}.toggle(new StopGetters(), new Intake());
		
		new Toggle() {
			
			@Override
			public boolean get()
			{
				return driver.getRB().get();
			}
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == Outgest.class;
			}
		}.toggle(new StopGetters(), new Outgest());
    	
        operator.getUp().whenPressed(new MoveForkliftUp1());
        operator.getDown().whenPressed(new MoveForkliftDown1());
        operator.getLeft().whenPressed(new MoveForklift(LiftSetpoints.GROUND, true));
        operator.getLeft().whenReleased(new MoveForklift(LiftSetpoints.SCORING_PLATFORM, true));
		
		operator.getLB().whileHeld(new PushTotes());
		operator.getLB().whenReleased(new RetractPusher());
		
		operator.getA().whenPressed(new MoveArm(ArmSetpoints.GROUND_FALL));
		operator.getB().whenPressed(new MoveArm(ArmSetpoints.STEP));
		operator.getX2().whenPressed(new MoveArm(ArmSetpoints.GROUND_UP));
		operator.getY2().whenPressed(new MoveArm(ArmSetpoints.TOP_OF_STACK));
		operator.getStart().whenPressed(new MoveArm(ArmSetpoints.STORAGE));
		/*
		new Toggle() {
			
			@Override
			public boolean get() {
				return operator.getRight().get();
			}
			
			@Override
			public boolean getToggler() {
				return Robot.arm.bendUp;
			}
		}.toggle(new ChangeArmBend(false), new ChangeArmBend(true));
		new Toggle() {
			
			@Override
			public boolean get()
			{
				return operator.getBack().get();
			}
			
			@Override
			public boolean getToggler()
			{
				return RobotMap.armShoulderPot.get() >= 0;
			}
		}.toggle(new MoveArmToFront(), new MoveArmToBack());
		*/
		
		operator.getRB().whileHeld(new OpenGrabber());
		
    }
}

