package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.commands.MoveForklift;
import org.usfirst.frc.team246.robot.commands.MoveForkliftDown1;
import org.usfirst.frc.team246.robot.commands.MoveForkliftUp1;
import org.usfirst.frc.team246.robot.overclockedLibraries.Joystick246;
import org.usfirst.frc.team246.robot.overclockedLibraries.LogitechF310;

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
        
        /*
        operator.getLeft().whenPressed(new MoveForklift(ArmSetpoints.SCORING_PLATFORM));
        operator.getRight().whenPressed(new MoveForklift(ArmSetpoints.GROUND));
        operator.getBack().whenPressed(new MoveForklift(ArmSetpoints.STEP));
        operator.getUp().whenPressed(new MoveForkliftUp1());
        operator.getDown().whenPressed(new MoveForkliftDown1());
        */
    }
}

