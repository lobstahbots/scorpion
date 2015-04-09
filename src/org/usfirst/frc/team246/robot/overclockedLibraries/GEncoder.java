package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.Encoder;

public class GEncoder extends Encoder
{
	public GEncoder(int channelA, int channelB)
	{
		super(channelA, channelB);
	}
	
	public double offset = 0;
	
	@Override
	public double getDistance()
	{
		return super.getDistance() - offset;
	}
}