package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;
import edu.wpi.first.wpilibj.command.Command;

public abstract class Toggle extends Trigger {
	
	public abstract boolean getToggler();
	
	boolean changed = false;
	
	public void toggle(final Command trueCommand, final Command falseCommand)
	{   
        new Trigger() {
			
			@Override
			public boolean get() {
				boolean result = Toggle.this.get() && Toggle.this.getToggler() && !changed;
				if(Toggle.this.get() && Toggle.this.getToggler())
				{
					changed = true;
				}
				if(!Toggle.this.get())
				{
					changed = false;
				}
				return result;
			}
		}.whenActive(trueCommand);
		
		new Trigger() {
			
			@Override
			public boolean get() {
				boolean result = Toggle.this.get() && !Toggle.this.getToggler() && !changed;
				if(Toggle.this.get() && !Toggle.this.getToggler())
				{
					changed = true;
				}
				if(!Toggle.this.get())
				{
					changed = false;
				}
				return result;
			}
		}.whenActive(falseCommand);
	}
}
