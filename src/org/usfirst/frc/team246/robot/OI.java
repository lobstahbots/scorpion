package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.commands.AdjustTote;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.commands.EngageScorpionMode;
import org.usfirst.frc.team246.robot.commands.ExitScorpionMode;
import org.usfirst.frc.team246.robot.commands.Get5thTote;
import org.usfirst.frc.team246.robot.commands.GetLastTote;
import org.usfirst.frc.team246.robot.commands.GetTote;
import org.usfirst.frc.team246.robot.commands.GettersLeft;
import org.usfirst.frc.team246.robot.commands.GettersRight;
import org.usfirst.frc.team246.robot.commands.GoFast;
import org.usfirst.frc.team246.robot.commands.Intake;
import org.usfirst.frc.team246.robot.commands.ManualArm;
import org.usfirst.frc.team246.robot.commands.ManualPusher;
import org.usfirst.frc.team246.robot.commands.MoveArm;
import org.usfirst.frc.team246.robot.commands.MoveForklift;
import org.usfirst.frc.team246.robot.commands.MoveForkliftDown1;
import org.usfirst.frc.team246.robot.commands.MoveForkliftUp1;
import org.usfirst.frc.team246.robot.commands.OpenGrabber;
import org.usfirst.frc.team246.robot.commands.Outgest;
import org.usfirst.frc.team246.robot.commands.PushTotes;
import org.usfirst.frc.team246.robot.commands.ReleaseStack;
import org.usfirst.frc.team246.robot.commands.RetractPusher;
import org.usfirst.frc.team246.robot.commands.RobotCentricCrabWithTwist;
import org.usfirst.frc.team246.robot.commands.ScorpionHold;
import org.usfirst.frc.team246.robot.commands.StopGetters;
import org.usfirst.frc.team246.robot.commands.StopGrabber;
import org.usfirst.frc.team246.robot.commands.TransitionSimple;
import org.usfirst.frc.team246.robot.commands.TransitionSimpleStepDown;
import org.usfirst.frc.team246.robot.commands.TransitionSimpleStepUp;
import org.usfirst.frc.team246.robot.overclockedLibraries.LogitechF310;
import org.usfirst.frc.team246.robot.overclockedLibraries.Toggle;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public LogitechF310 driver;
	public LogitechF310 operator;
	public LogitechF310 transitioner;
	public Joystick buttonBox;

	public Trigger processingConfirmLowerButton;
	public Trigger processingOpenGrabberButton;
	public Trigger processingDontRaiseLiftButton;
	public Trigger breakArmConstraintsButton;
	public Trigger manualPusherPushButton;
	public Trigger manualPusherPullButton;

	public boolean lastToting;

	public OI()
	{
		driver = new LogitechF310(0);
		operator = new LogitechF310(1);
		buttonBox = new Joystick(2);
		if(Robot.scorpionModeTest) transitioner = new LogitechF310(3);

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
			@Override
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == AdjustTote.class;
			}
		}.toggle(new StopGetters(), new AdjustTote());


		new Toggle() {

			@Override
			public boolean get()
			{
				return driver.getStart().get();
			}
			@Override
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
			@Override
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == Outgest.class;
			}
		}.toggle(new StopGetters(), new Outgest());

		new Toggle() {

			@Override
			public boolean get()
			{
				return driver.getLeft().get();
			}
			@Override
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == GettersLeft.class;
			}
		}.toggle(new StopGetters(), new GettersLeft());

		new Toggle() {

			@Override
			public boolean get()
			{
				return driver.getRight().get();
			}
			@Override
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == GettersRight.class;
			}
		}.toggle(new StopGetters(), new GettersRight());

		driver.getB().whenPressed(new MoveForklift(LiftSetpoints.GROUND, true));
		driver.getB().whenReleased(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true));
		new Trigger() {

			@Override
			public boolean get() {
				if(Robot.hasTote && driver.getLB().get() && !buttonBox.getRawButton(6) && Robot.forklift.getCurrentCommand().getClass() != GetLastTote.class)
				{
					if(!lastToting)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else
				{
					lastToting = false;
					return false;
				}

			}
		}.whenActive(new GetTote());
		new Trigger() {

			@Override
			public boolean get() {
				if(Robot.hasTote && driver.getLB().get() && buttonBox.getRawButton(6) && Robot.forklift.getCurrentCommand().getClass() != GetTote.class)
				{
					lastToting = true;
					return true;
				}
				else
				{
					return false;
				}
			}
		}.whenActive(new GetLastTote());

		operator.getA().whenPressed(new MoveArm(ArmSetpoints.STORAGE_NO_CAN));
		operator.getB().whenPressed(new MoveArm(ArmSetpoints.STEP));
		operator.getX2().whenPressed(new MoveArm(ArmSetpoints.STORAGE));
		new Toggle() {

			@Override
			public boolean get() {
				return operator.getY2().get();
			}

			@Override
			public boolean getToggler() {
				return Robot.arm.getCurrentCommand().getClass() == ScorpionHold.class;
			}
		}.toggle(new ManualArm(), new ScorpionHold());

		operator.getLB().whileHeld(new PushTotes());
		operator.getLB().whenReleased(new RetractPusher());
		new Toggle() {

			@Override
			public boolean get()
			{
				return operator.getRB().get();
			}

			@Override
			public boolean getToggler()
			{
				return ((Robot.grabber.getCurrentCommand() == null || Robot.grabber.getCurrentCommand().getClass() == StopGrabber.class) && RobotMap.grabberEncoder.getDistance() > -5) || Robot.grabber.getCurrentCommand().getClass() == CloseGrabber.class;
			}
		}.toggle(new OpenGrabber(), new CloseGrabber());
		new Trigger() {

			@Override
			public boolean get() {
				return operator.getLB().get() && Robot.grabber.getCurrentCommand().getClass() == CloseGrabber.class && RobotMap.grabberEncoder.getDistance() > Robot.grabber.grabberSetpoint
						&& Robot.arm.getWrist() < 135;
			}
		}.whenActive(new EngageScorpionMode());
		new Trigger() {

			@Override
			public boolean get() {
				return operator.getLB().get() && Robot.grabber.getCurrentCommand().getClass() == CloseGrabber.class && RobotMap.grabberEncoder.getDistance() > Robot.grabber.grabberSetpoint
						&& Robot.arm.getWrist() > 135;
			}
		}.whenActive(new EngageScorpionMode() {

			@Override
			protected void initialize()
			{
				waypoints = new ArmSetpoints[RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length + 1];
				for(int i = 0; i < RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length; i++)
				{
					waypoints[i] = RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT[i];
				}
				waypoints[RobotMap.ARM_TRANSITION_ARRAY_TO_FRONT.length] = ArmSetpoints.GROUND_FALL_PREP;
				super.initialize();
			}
		});

		operator.getStart().whenPressed(new EngageScorpionMode());
		operator.getBack().whenPressed(new ExitScorpionMode());

		operator.getUp().whenPressed(new MoveArm(ArmSetpoints.GROUND_UP_HIGH));
		operator.getDown().whenPressed(new MoveArm(ArmSetpoints.GROUND_UP_LOW));
		operator.getLeft().whenPressed(new MoveArm(ArmSetpoints.GROUND_FALL_PREP));
		operator.getRight().whenPressed(new MoveArm(ArmSetpoints.GROUND_FALL));

		(new JoystickButton(buttonBox, 2)).whenPressed(new MoveForkliftUp1());
		(new JoystickButton(buttonBox, 3)).whenPressed(new MoveForkliftDown1());
		(new JoystickButton(buttonBox, 1)).whenPressed(new MoveForklift(LiftSetpoints.GROUND, true));
		(new JoystickButton(buttonBox, 1)).whenReleased(new MoveForklift(LiftSetpoints.SCORING_PLATFORM, true));

		(new JoystickButton(buttonBox, 10)).whenPressed(new MoveArm(ArmSetpoints.TOP_OF_STACK));
		breakArmConstraintsButton = new JoystickButton(buttonBox, 11);

		(new JoystickButton(buttonBox, 7)).whenPressed(new ReleaseStack());

		(new JoystickButton(buttonBox, 8)).whenPressed(new ManualPusher());
		(new JoystickButton(buttonBox, 8)).whenPressed(new ManualPusher());
		manualPusherPushButton = new JoystickButton(buttonBox, 8);
		manualPusherPullButton = new JoystickButton(buttonBox, 9);

		(new JoystickButton(buttonBox, 4)).whenPressed(new MoveForklift(LiftSetpoints.STEP, false));

		processingOpenGrabberButton = new JoystickButton(buttonBox, 5);
		processingDontRaiseLiftButton = new JoystickButton(buttonBox, 6);

		new JoystickButton(buttonBox, 5).whenPressed(new Get5thTote());

		if(Robot.scorpionModeTest)
		{
			transitioner.getX2().whenPressed(new TransitionSimple(true));
			transitioner.getB().whenPressed(new TransitionSimple(false));
			transitioner.getY2().whenPressed(new TransitionSimpleStepUp());
			transitioner.getA().whenPressed(new TransitionSimpleStepDown());
		}
	}
}

