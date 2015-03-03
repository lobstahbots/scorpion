package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {
	private int maxSwitchCount;
	private int switchCount;
	private boolean isOn;
	
	
	
	public LimitSwitch(int channel, int maxSwitchCount){
		super(channel);
		this.maxSwitchCount = maxSwitchCount;
	}
	
	@Override
	public boolean get(){
//		boolean currentValue = super.get();
		if(super.get())
    	{
    		switchCount++;
    		if(switchCount < maxSwitchCount)
    		{
    			isOn = false;
    		}
    		else
    		{
    			isOn = true;
    			switchCount = 0;
    		}
    	}
		return isOn;
	}
}
