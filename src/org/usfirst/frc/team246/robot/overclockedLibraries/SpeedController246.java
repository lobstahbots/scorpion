package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.SpeedController;

public interface SpeedController246 extends SpeedController {

	public void overridingSet(double speed);
	public void returnControl();
	public double getCurrent();
}
