package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class LogitechF310 extends Joystick{
	
	protected int leftXAxis = 0;
	protected int leftYAxis = 1;
	protected int rightXAxis = 2;
	protected int rightYAxis = 3;
	protected int triggersAxis = 4;
	
	protected int x = 0;
	protected int y = 1;
	protected int a = 2;
	protected int b = 3;
	protected int lb = 4;
	protected int rb = 5;
	protected int back = 6;
	protected int start = 7;

	public LogitechF310(int port) {
		super(port);
	}
	
	public double getLeftXAxis()
	{
		return getRawAxis(leftXAxis);
	}
	public double getLeftYAxis()
	{
		return getRawAxis(leftYAxis);
	}
	public double getRightXAxis()
	{
		return getRawAxis(rightXAxis);
	}
	public double getRightYAxis()
	{
		return getRawAxis(rightYAxis);
	}
	public double getTriggersAxis()
	{
		return getRawAxis(triggersAxis);
	}
	
	public Button getX2()
	{
		return new JoystickButton(this, x);
	}
	public Button getY2()
	{
		return new JoystickButton(this, x);
	}
	public Button getA()
	{
		return new JoystickButton(this, x);
	}
	public Button getB()
	{
		return new JoystickButton(this, x);
	}
	public Button getLB()
	{
		return new JoystickButton(this, x);
	}
	public Button getRB()
	{
		return new JoystickButton(this, x);
	}
	public Button getBack()
	{
		return new JoystickButton(this, x);
	}
	public Button getStart()
	{
		return new JoystickButton(this, x);
	}
	public Button getLT()
	{
		return new Button() {
			
			@Override
			public boolean get() {
				return getTriggersAxis() < -.5;
			}
		};
	}
	public Button getRT()
	{
		return new Button() {
					
			@Override
			public boolean get() {
				return getTriggersAxis() < -.5;
			}
		};
	}
	public Button getUp()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 315 || getPOV() == 0 || getPOV() == 45;
			}
		};
	}
	public Button getLeft()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 45 || getPOV() == 90 || getPOV() == 135;
			}
		};
	}
	public Button getDown()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 135 || getPOV() == 180 || getPOV() == 225;
			}
		};
	}
	public Button getRight()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 225 || getPOV() == 270 || getPOV() == 315;
			}
		};
	}
}
