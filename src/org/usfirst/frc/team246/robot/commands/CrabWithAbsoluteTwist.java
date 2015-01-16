package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

/**
 *
 * @author michaelsilver
 */
public class CrabWithAbsoluteTwist extends CrabWithTwist{
    
    protected void initialize() {
        execute();
        
        Robot.drivetrain.enableAbsoluteTwist(true);
    }
    
    protected void execute() {
        Vector2D crabVector = getCrabVector();
        crabVector.setAngle(crabVector.getAngle() + Robot.drivetrain.getFOV());
        Vector2D COR = getCOR();
        COR.setAngle(COR.getAngle() + Robot.drivetrain.getFOV());
        
        Robot.drivetrain.driveAbsoluteTwist(crabVector.getMagnitude(), crabVector.getAngle(), Robot.oi.driverLeftJoystick.getDirectionDegrees());
    }
    
    protected void end()
    {
        Robot.drivetrain.enableAbsoluteTwist(false);
    }
}
