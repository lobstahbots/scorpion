package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLogoTest extends CommandGroup {
    
    public  AutoLogoTest() {
    	Vector2D GO_FORWARD = new Vector2D(false, 1, RobotMap.NORTH);
        Vector2D GO_BACK = new Vector2D(false, 1, RobotMap.SOUTH);
        Vector2D GO_LEFT = new Vector2D(false, 1, RobotMap.WEST);
        Vector2D GO_RIGHT = new Vector2D(false, 1, RobotMap.EAST);
    	
        addSequential(new AutoDrive(GO_FORWARD, RobotMap.NORTH));
        addSequential(new AutoDrive(GO_RIGHT, RobotMap.EAST));
        addSequential(new AutoDrive(GO_BACK, RobotMap.SOUTH));
        addSequential(new AutoDrive(GO_LEFT, RobotMap.WEST));
    }
}
