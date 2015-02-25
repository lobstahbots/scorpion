package org.usfirst.frc.team246.robot.overclockedLibraries;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class LogitechF310 extends Joystick{
	
	protected int leftXAxis = 0;
	protected int leftYAxis = 1;
	protected int rightXAxis = 4;
	protected int rightYAxis = 5;
	protected int leftTriggerAxis = 2;
	protected int rightTriggerAxis = 3;
	
	protected int x = 3;
	protected int y = 4;
	protected int a = 1;
	protected int b = 2;
	protected int lb = 5;
	protected int rb = 6;
	protected int back = 7;
	protected int start = 8;
	protected int leftStick = 9;
	protected int rightStick = 10;

	public LogitechF310(int port) {
		super(port);
	}
	
	public double getLeftXAxis()
	{
		if(Math.abs(getRawAxis(leftXAxis)) > .05)
			return getRawAxis(leftXAxis);
		else
			return 0;
	}
	public double getLeftYAxis()
	{
		if(Math.abs(getRawAxis(leftYAxis)) > .05)
			return getRawAxis(leftYAxis);
		else
			return 0;
	}
	public double getRightXAxis()
	{
		if(Math.abs(getRawAxis(rightXAxis)) > .05)
			return getRawAxis(rightXAxis);
		else
			return 0;
	}
	public double getRightYAxis()
	{
		if(Math.abs(getRawAxis(rightYAxis)) > .05)
			return getRawAxis(rightYAxis);
		else
			return 0;
	}
	public double getLeftTriggerAxis()
	{
		if(Math.abs(getRawAxis(leftTriggerAxis)) > .05)
			return getRawAxis(leftTriggerAxis);
		else
			return 0;
	}
	public double getRightTriggerAxis()
	{
		if(Math.abs(getRawAxis(rightTriggerAxis)) > .05)
			return getRawAxis(rightTriggerAxis);
		else
			return 0;
	}
	public double getLeftMagnitude()
	{
		return Math.sqrt(Math.pow(getLeftXAxis(), 2) + Math.pow(getLeftYAxis(), 2));
	}
	public double getLeftAngle()
	{
		return Math.toDegrees(Math.atan2(getLeftXAxis(), -getLeftYAxis()));
	}
	public double getRightMagnitude()
	{
		return Math.sqrt(Math.pow(getRightXAxis(), 2) + Math.pow(getRightYAxis(), 2));
	}
	public double getRightAngle()
	{
		return Math.toDegrees(Math.atan2(getRightXAxis(), -getRightYAxis()));
	}
	
	public Button getX2()
	{
		return new JoystickButton(this, x);
	}
	public Button getY2()
	{
		return new JoystickButton(this, y);
	}
	public Button getA()
	{
		return new JoystickButton(this, a);
	}
	public Button getB()
	{
		return new JoystickButton(this, b);
	}
	public Button getLB()
	{
		return new JoystickButton(this, lb);
	}
	public Button getRB()
	{
		return new JoystickButton(this, rb);
	}
	public Button getBack()
	{
		return new JoystickButton(this, back);
	}
	public Button getStart()
	{
		return new JoystickButton(this, start);
	}
	public Button getLeftStick()
	{
		return new JoystickButton(this, leftStick);
	}
	public Button getRightStick()
	{
		return new JoystickButton(this, rightStick);
	}
	public Button getLT()
	{
		return new Button() {
			
			@Override
			public boolean get() {
				return getLeftTriggerAxis() > .5;
			}
		};
	}
	public Button getRT()
	{
		return new Button() {
					
			@Override
			public boolean get() {
				return getRightTriggerAxis() > .5;
			}
		};
	}
	public Button getUp()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 0;
			}
		};
	}
	public Button getRight()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 90;
			}
		};
	}
	public Button getDown()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 180;
			}
		};
	}
	public Button getLeft()
	{
		return new Button() {
			
			@Override
			public boolean get()
			{
				return getPOV() == 270;
			}
		};
	}
	
	@Override
    public double getDirectionRadians()
    {
    	return -super.getDirectionRadians();
    }
}
