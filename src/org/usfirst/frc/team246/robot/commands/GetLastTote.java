package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GetLastTote extends CommandGroup {
	
	boolean lifting = false;
    
    public  GetLastTote() {
        addSequential(new MoveForklift(LiftSetpoints.GROUND, true));
        addSequential(new MoveForklift(LiftSetpoints.SCORING_PLATFORM, true) {
        	@Override
        	public void initialize()
        	{
        		lifting = true;
        		super.initialize();
        	}
        });
    }
    
    @Override
    public boolean isFinished()
    {
    	return super.isFinished() || (!Robot.hasTote && !lifting);
    }
}
