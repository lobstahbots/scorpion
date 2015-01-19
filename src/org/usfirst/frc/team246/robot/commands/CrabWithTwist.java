/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.Robot;

/**
 *
 * @author michaelsilver
 */
public class CrabWithTwist extends FieldCentricDrivingCommand{

//    driverLeftJoystick is controlling "twist"
//    driverRightJoystick is controlling "crab"
    
    protected Vector2D getCrabVector() {
        return new Vector2D(true, -Robot.oi.driverRightJoystick.getX(), Robot.oi.driverRightJoystick.getY());
    }

    protected double getSpinRate() {
        if(Robot.test2)
        {
            return SmartDashboard.getNumber("spinRate", 0) / RobotMap.WHEEL_TOP_ABSOLUTE_SPEED;
        } 
        System.out.println("Joystick: " + Robot.oi.driverLeftJoystick.getX());
        return Robot.oi.driverLeftJoystick.getX();
    }

    protected Vector2D getCOR() {
        return new Vector2D(true, 0, -11.67);
    }

    protected void initialize() {
        
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        end();
    }
    
}
