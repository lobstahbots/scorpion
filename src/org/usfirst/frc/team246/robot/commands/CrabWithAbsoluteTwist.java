package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

/**
 *
 * @author michaelsilver
 */
public class CrabWithAbsoluteTwist extends CrabWithTwist{
	
	double defaultHeading = 181;
    
    protected void initialize() {
    	defaultHeading = 181;
    	
        execute();
        
        Robot.drivetrain.enableAbsoluteTwist(true);
    }
    
    protected void execute() {
    	Robot.drivetrain.setFOV(updateHeading());
    	
        Vector2D crabVector = getCrabVector();
        crabVector.setAngle(crabVector.getAngle() - Robot.drivetrain.getFOV());
        Vector2D COR = getCOR();
        
        double header;
        if(Robot.oi.driver.getRightMagnitude() <= .05)
        {
        	if(defaultHeading == 181) defaultHeading = Robot.drivetrain.FOV;
        	header = defaultHeading;
        }
        else
        {
        	defaultHeading = 181;
        	header = Robot.oi.driver.getRightAngle();
        }
        
        Robot.drivetrain.driveAbsoluteTwist(crabVector.getMagnitude(), crabVector.getAngle(), header);
    }
    
    protected void end()
    {
    	Robot.drivetrain.enableAbsoluteTwist(false);
    }
}
