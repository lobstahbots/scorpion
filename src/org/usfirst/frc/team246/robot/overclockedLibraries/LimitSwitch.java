package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.DigitalInput;

public class LimitSwitch extends DigitalInput {
	private int maxSwitchCount;
	private int onSwitchCount = 0;
	private int offSwitchCount = 0;
	
	private boolean isOn = false;
//	private boolean onOff = true;
	
//	boolean switchState;
//	boolean previousState;
	
	public LimitSwitch(int channel, int maxSwitchCount){
		super(channel);
		this.maxSwitchCount = maxSwitchCount;
	}
	
	@Override
	public boolean get(){
//		switchState = super.get();
//		if(onOff){
//			previousState = switchState;
//		}
//		onOff = !onOff;
//		
//		
//		if(switchState = previousState){
//			if(switchState){
//				onSwitchCount++;
//			} else {
//				offSwitchCount++;
//			}
//			if(onSwitchCount == maxSwitchCount - 1){
//				isOn = true;
//				onSwitchCount = 0;
//			} else if (offSwitchCount == maxSwitchCount - 1){
//				isOn = false;
//				offSwitchCount = 0;
//			}
//		} else {
//			onSwitchCount = 0;
//			offSwitchCount = 0;
//		}
//		return isOn;
		
		if(super.get())
    	{
    		onSwitchCount++;
    		if(onSwitchCount < maxSwitchCount)
    		{
    			isOn = isOn? true : false;
    		}
    		else
    		{
    			isOn = true;
    			onSwitchCount = 0;
    		}
    	}
		else
    	{
    		offSwitchCount++;
    		if(offSwitchCount < maxSwitchCount)
    		{
    			isOn = !isOn? false : true;
    		}
    		else
    		{
    			isOn = false;
    			offSwitchCount = 0;
    		}
    	}
		return isOn;
	}
}
