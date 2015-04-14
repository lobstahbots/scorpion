package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.commands.AdjustTote;
import org.usfirst.frc.team246.robot.commands.ChangeArmBend;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.commands.EngageScorpionMode;
import org.usfirst.frc.team246.robot.commands.ExitScorpionMode;
import org.usfirst.frc.team246.robot.commands.GetToteSimple;
import org.usfirst.frc.team246.robot.commands.GettersLeft;
import org.usfirst.frc.team246.robot.commands.GettersRight;
import org.usfirst.frc.team246.robot.commands.GoFast;
import org.usfirst.frc.team246.robot.commands.Intake;
import org.usfirst.frc.team246.robot.commands.LiftTote;
import org.usfirst.frc.team246.robot.commands.ManualArm;
import org.usfirst.frc.team246.robot.commands.ManualPusher;
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
import org.usfirst.frc.team246.robot.commands.ProcessLandfill;
import org.usfirst.frc.team246.robot.commands.PushTotes;
import org.usfirst.frc.team246.robot.commands.ReleaseStack;
import org.usfirst.frc.team246.robot.commands.RetractPusher;
import org.usfirst.frc.team246.robot.commands.RobotCentricCrabWithTwist;
import org.usfirst.frc.team246.robot.commands.ScorpionHold;
import org.usfirst.frc.team246.robot.commands.StopGetters;
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
    
    public LogitechF310 driver; //TODO: instantiate these when we get the controllers
    public LogitechF310 operator;
    public LogitechF310 transitioner;
    public Joystick buttonBox;
    
    public Trigger processingConfirmLowerButton;
    public Trigger processingOpenGrabberButton;
    public Trigger processingDontRaiseLiftButton;
    public Trigger breakArmConstraintsButton;
    public Trigger manualPusherPushButton;
    public Trigger manualPusherPullButton;
    
    public OI()
    {
    	driver = new LogitechF310(0);
    	operator = new LogitechF310(1);
    	buttonBox = new Joystick(2);
    	if(Robot.scorpionModeTest) transitioner = new LogitechF310(3);
    	
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
				return Robot.getters.getCurrentCommand().getClass() == AdjustTote.class;
			}
		}.toggle(new StopGetters(), new AdjustTote());
		
		new Toggle() {
			
			@Override
			public boolean get()
			{
				return driver.getLB().get();
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
		
		new Toggle() {
			
			@Override
			public boolean get()
			{
				return driver.getLeft().get();
			}
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
			public boolean getToggler()
			{
				return Robot.getters.getCurrentCommand().getClass() == GettersRight.class;
			}
		}.toggle(new StopGetters(), new GettersRight());
		
		processingConfirmLowerButton = driver.getB();
		driver.getB().whenPressed(new MoveForklift(LiftSetpoints.GROUND, true));
		driver.getB().whenReleased(new MoveForklift(LiftSetpoints.ABOVE_1_TOTE, true));
		
		driver.getBack().whenPressed(new GetToteSimple(true, false));
		driver.getStart().whenPressed(new GetToteSimple(false, false));
		driver.getLeftStick().whenPressed(new GetToteSimple(true, true));
		driver.getRightStick().whenPressed(new GetToteSimple(false, true));
		
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
				return Robot.grabber.getCurrentCommand() == null || Robot.grabber.getCurrentCommand().getClass() == CloseGrabber.class;
			}
		}.toggle(new OpenGrabber(), new CloseGrabber());
		
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
		
        processingOpenGrabberButton = new JoystickButton(buttonBox, 4);
		processingDontRaiseLiftButton = new JoystickButton(buttonBox, 6);
		
		if(Robot.scorpionModeTest)
		{
			transitioner.getX2().whenPressed(new TransitionSimple(true));
			transitioner.getB().whenPressed(new TransitionSimple(false));
			transitioner.getY2().whenPressed(new TransitionSimpleStepUp());
			transitioner.getA().whenPressed(new TransitionSimpleStepDown());
		}
    }
}

