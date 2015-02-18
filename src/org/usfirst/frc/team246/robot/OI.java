package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;
import org.usfirst.frc.team246.robot.commands.CrabWithAbsoluteTwist;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.commands.GetTote;
import org.usfirst.frc.team246.robot.commands.GoFast;
import org.usfirst.frc.team246.robot.commands.LiftTote;
import org.usfirst.frc.team246.robot.commands.MoveArm;
import org.usfirst.frc.team246.robot.commands.MoveArmDown1;
import org.usfirst.frc.team246.robot.commands.MoveArmUp1;
import org.usfirst.frc.team246.robot.commands.MoveForklift;
import org.usfirst.frc.team246.robot.commands.MoveForkliftDown1;
import org.usfirst.frc.team246.robot.commands.MoveForkliftUp1;
import org.usfirst.frc.team246.robot.commands.OpenGrabber;
import org.usfirst.frc.team246.robot.commands.PushTotes;
import org.usfirst.frc.team246.robot.commands.RobotCentricCrabWithTwist;
import org.usfirst.frc.team246.robot.commands.SwerveTank;
import org.usfirst.frc.team246.robot.overclockedLibraries.LogitechF310;
import org.usfirst.frc.team246.robot.overclockedLibraries.Toggle;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
    	
		driver.getY2().whileHeld(new GetTote(true));
    	driver.getB().whileHeld(new GetTote(false));
    	
        operator.getUp().whenPressed(new MoveForkliftUp1());
        operator.getDown().whenPressed(new MoveForkliftDown1());
        operator.getLeft().whenPressed(new MoveForklift(LiftSetpoints.GROUND, true));
        operator.getLeft().whenReleased(new LiftTote());
        new Toggle() {
			
			@Override
			public boolean get() {
				return operator.getRight().get();
			}
			
			@Override
			public boolean getToggler() {
				return Robot.forklift.getCurrentCommand().getClass() == MoveForklift.class && ((MoveForklift)(Robot.forklift.getCurrentCommand())).setpointEnum == LiftSetpoints.SCORING_PLATFORM;
			}
		}.toggle(new MoveForklift(LiftSetpoints.STEP, false),new MoveForklift(LiftSetpoints.SCORING_PLATFORM, false));
		
		operator.getLB().whileHeld(new PushTotes());
		
		operator.getBack().whenPressed(new MoveArm(ArmSetpoints.STORAGE));
		operator.getStart().whenPressed(new MoveArm(ArmSetpoints.STEP));
		operator.getX2().whenPressed(new MoveArm(ArmSetpoints.ON_LIFT));
		operator.getB().whenPressed(new MoveArm(ArmSetpoints.GROUND_UP));
		operator.getA().whenPressed(new MoveArmUp1());
		operator.getY2().whenPressed(new MoveArmDown1());
		new Trigger() {
			
			@Override
			public boolean get()
			{
				return Math.abs(operator.getLeftXAxis()) > .25;
			}
		}.whenActive(new MoveArm(ArmSetpoints.GROUND_FALL));
		
		operator.getRB().whileHeld(new OpenGrabber());
		
    }
}

