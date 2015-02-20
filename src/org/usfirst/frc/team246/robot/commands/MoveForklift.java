package org.usfirst.frc.team246.robot.commands;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.subsystems.Forklift;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveForklift extends Command {

	Forklift f = Robot.forklift;
	public LiftSetpoints setpointEnum;
	double setpoint;
	boolean reset;
	
    public MoveForklift(LiftSetpoints setpoint, boolean resetTotesHigh) {
        requires(f);
        setpointEnum = setpoint;
        this.setpoint = setpoint.getValue();
        reset = resetTotesHigh;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	f.enable();
    	if(reset) f.totesHigh = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	f.setSetpoint(setpoint + f.getToteAdder());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(Robot.oi.operator.getLeftTriggerAxis() != 0 || Robot.oi.operator.getRightTriggerAxis() != 0)
    	{
    		return true;
    	}
    	else
    	{
    		if(getGroup() == null)
    		{
    			return false;
    		}
    		else
    		{
    			return f.onTarget();
    		}
    	}
	}

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
