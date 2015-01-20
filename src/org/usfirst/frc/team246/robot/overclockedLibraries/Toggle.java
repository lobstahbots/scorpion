package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;
import edu.wpi.first.wpilibj.command.Command;

public abstract class Toggle extends Trigger {
	
	public abstract boolean getToggler();
	
	public void toggle(final Command trueCommand, final Command falseCommand)
	{   
        new Trigger() {
			
			@Override
			public boolean get() {
				return Toggle.this.get() && Toggle.this.getToggler();
			}
		}.whenActive(trueCommand);
		
		new Trigger() {
			
			@Override
			public boolean get() {
				return Toggle.this.get() && !Toggle.this.getToggler();
			}
		}.whenActive(falseCommand);
	}
}
