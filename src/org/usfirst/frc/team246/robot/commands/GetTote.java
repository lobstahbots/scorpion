package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GetTote extends CommandGroup {
    
    public  GetTote(boolean left) {
    	
    	//Drive forwards into tote until we either have it or hit the wrong side
        addSequential(new DeadReckoningDrive(new Vector2D(false, 2, 0)) {
			
			@Override
			protected boolean isFinished() {
				System.out.println("part 1, " + left);
				if(left)
				{
					return Robot.getters.leftInTolerance() && !Robot.getters.rightInTolerance();
				}
				else
				{
					return Robot.getters.rightInTolerance() && !Robot.getters.leftInTolerance();
				}
			}
        });
		
		//Spin until we hit the right side
		addSequential(new DeadReckoningDrive(left ? -2 : 2, new Vector2D(true, 0, 0)) {
			
			@Override
			protected boolean isFinished() {
				System.out.println("part 2: " + left);
				if(left)
				{
					return !Robot.getters.leftInTolerance();
				}
				else
				{
					return !Robot.getters.rightInTolerance();
				}
			}
		});
		
		//Continue to drive into the tote until we get it
		addSequential(new DeadReckoningDrive(new Vector2D(false, 2, 0)) {
			
			@Override
			protected boolean isFinished() {
				System.out.println("part 1, " + left);
				return false;
			}
        });
    }
    
    @Override
    public boolean isFinished()
    {
    	return super.isFinished() || Robot.getters.hasTote();
    }
}
