package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.commands.CloseGrabber;
import org.usfirst.frc.team246.robot.commands.CrabWithAbsoluteTwist;
import org.usfirst.frc.team246.robot.commands.DeployLeftGetter;
import org.usfirst.frc.team246.robot.commands.DeployRightGetter;
import org.usfirst.frc.team246.robot.commands.Intake;
import org.usfirst.frc.team246.robot.commands.MoveForklift;
import org.usfirst.frc.team246.robot.commands.MoveForkliftDown1;
import org.usfirst.frc.team246.robot.commands.MoveForkliftUp1;
import org.usfirst.frc.team246.robot.commands.OpenGrabber;
import org.usfirst.frc.team246.robot.commands.Outake;
import org.usfirst.frc.team246.robot.commands.RetractLeftGetter;
import org.usfirst.frc.team246.robot.commands.RetractRightGetter;
import org.usfirst.frc.team246.robot.overclockedLibraries.Joystick246;
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
    
	public Joystick246 driverLeftJoystick;
    public Joystick246 driverRightJoystick;
    public Joystick246 operatorJoystick;
    
    public LogitechF310 driver; //TODO: instantiate these when we get the controllers
    public LogitechF310 operator;
    
    public OI()
    {
        driverLeftJoystick = new Joystick246(0);
        driverLeftJoystick.setDeadband(.1);
        driverRightJoystick = new Joystick246(1);
        driverRightJoystick.setDeadband(.1);
        operatorJoystick = new Joystick246(3);
        operatorJoystick.setDeadband(.1);
        
        (new JoystickButton(driverLeftJoystick, 1)).whileHeld(new CrabWithAbsoluteTwist());
        
        (new JoystickButton(driverRightJoystick, 2)).whileHeld(new Intake());
        /*
        operator.getLeft().whenPressed(new MoveForklift(ArmSetpoints.SCORING_PLATFORM));
        operator.getRight().whenPressed(new MoveForklift(ArmSetpoints.GROUND));
        operator.getBack().whenPressed(new MoveForklift(ArmSetpoints.STEP));
        operator.getUp().whenPressed(new MoveForkliftUp1());
        operator.getDown().whenPressed(new MoveForkliftDown1());
        
        driver.getLT().whileHeld(new Intake());
        driver.getRT().whileHeld(new Outake());
        
        new Toggle() {
			
			@Override
			public boolean get() {
				return driver.getLeft().get();
			}
			
			@Override
			public boolean getToggler() {
				return Robot.getters.getLeftGetterPosition() == DoubleSolenoid.Value.kReverse;
			}
		}.toggle(new DeployLeftGetter(), new RetractLeftGetter());
		
		new Toggle() {
			
			@Override
			public boolean get() {
				return driver.getRight().get();
			}
			
			@Override
			public boolean getToggler() {
				return Robot.getters.getRightGetterPosition() == DoubleSolenoid.Value.kReverse;
			}
		}.toggle(new DeployRightGetter(), new RetractRightGetter());
		
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
    	*/
    }
}

