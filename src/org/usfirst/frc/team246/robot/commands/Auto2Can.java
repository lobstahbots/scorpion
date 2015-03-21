package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap.ArmSetpoints;
import org.usfirst.frc.team246.robot.overclockedLibraries.ArmMotor.ArmJoint;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto2Can extends CommandGroup {
    
    public  Auto2Can(boolean facingLeft) {
        if(facingLeft) addParallel(new ZeroNavX(90));
        else addParallel(new ZeroNavX(-90));
        addParallel(new AutoSetDriveSpeed(3));
        addSequential(new MoveArm(ArmSetpoints.STORAGE));
        addSequential(new AutoAlignAndDrive(new Vector2D(false, 6, 0), true));
    }
}
