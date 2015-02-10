package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;
import org.usfirst.frc.team246.robot.commands.CrabWithAbsoluteTwist;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.commands.GoFast;
import org.usfirst.frc.team246.robot.commands.MoveForklift;
import org.usfirst.frc.team246.robot.commands.MoveForkliftDown1;
import org.usfirst.frc.team246.robot.commands.MoveForkliftUp1;
import org.usfirst.frc.team246.robot.commands.OpenGrabber;
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
    	
    	driver.getLB().whileHeld(new CrabWithAbsoluteTwist());
    	driver.getLT().whileHeld(new GoFast());
    	
        operator.getLeft().whenPressed(new MoveForklift(LiftSetpoints.SCORING_PLATFORM));
        operator.getRight().whenPressed(new MoveForklift(LiftSetpoints.GROUND));
        operator.getBack().whenPressed(new MoveForklift(LiftSetpoints.STEP));
        operator.getUp().whenPressed(new MoveForkliftUp1());
        operator.getDown().whenPressed(new MoveForkliftDown1());
		
		new Toggle() {
			
			@Override
			public boolean get() {
				return operator.getRB().get();
			}
			
			@Override
			public boolean getToggler() {
				return Robot.grabber.getPosition() == DoubleSolenoid.Value.kForward;
			}
		}.toggle(new CloseGrabber(), new OpenGrabber());
		
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
    }
}

