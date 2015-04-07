package org.usfirst.frc.team246.robot.overclockedLibraries;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Diagnostics implements Runnable {
	
	public static Diagnostics instance;
	
	private static ArrayList<DiagnosticsAnalogIn> analogIns = new ArrayList<DiagnosticsAnalogIn>();
	private static ArrayList<DiagnosticsAnalogPot> analogPots = new ArrayList<DiagnosticsAnalogPot>();
	private static ArrayList<DiagnosticsEncoder> encoders = new ArrayList<DiagnosticsEncoder>();
	
	public static void initialize()
	{
		if(instance == null)
		{
			instance = new Diagnostics();
			(new Thread(instance)).start();
		}
	}
	
	public static void addAnalogIn(AnalogIn analogIn, String name)
	{
		analogIns.add(instance.new DiagnosticsAnalogIn(analogIn, name));
	}
	public static void addAnalogPot(AnalogPot analogPot, String name)
	{
		analogPots.add(instance.new DiagnosticsAnalogPot(analogPot, name));
	}
	public static void addEncoder(Encoder encoder, String name)
	{
		encoders.add(instance.new DiagnosticsEncoder(encoder, name));
	}

	@Override
	public void run() {
		for(int i = 0; i < analogIns.size(); i++)
		{
			DiagnosticsAnalogIn ai = analogIns.get(i);
			SmartDashboard.putNumber(ai.name, ai.sensor.get());
			
		}
	}
	
	private class DiagnosticsAnalogIn
	{
		public AnalogIn sensor;
		public String name;
		
		public DiagnosticsAnalogIn(AnalogIn sensor, String name)
		{
			this.sensor = sensor;
			this.name = name;
		}
	}
	private class DiagnosticsAnalogPot
	{
		public AnalogPot sensor;
		public String name;
		
		public DiagnosticsAnalogPot(AnalogPot sensor, String name)
		{
			this.sensor = sensor;
			this.name = name;
		}
	}
	private class DiagnosticsEncoder
	{
		public Encoder sensor;
		public String name;
		
		public DiagnosticsEncoder(Encoder sensor, String name)
		{
			this.sensor = sensor;
			this.name = name;
		}
	}
}
