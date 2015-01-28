package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;
import org.usfirst.frc.team246.robot.overclockedLibraries.V4BPIDController;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {
	
	V4BPIDController shoulder;
	V4BPIDController elbow;
	V4BPIDController wrist;
	
	boolean bendOut = false;

    // Initialize your subsystem here
    public Arm() {
        shoulder = new V4BPIDController(RobotMap.ARM_SHOULDER_kP, RobotMap.ARM_SHOULDER_kI, RobotMap.ARM_SHOULDER_kD, RobotMap.armShoulderPot, RobotMap.armShoulderMotor, null);
        shoulder.setInputRange(RobotMap.ARM_SHOULDER_MIN, RobotMap.ARM_SHOULDER_MAX);
        elbow = new V4BPIDController(RobotMap.ARM_ELBOW_kP, RobotMap.ARM_ELBOW_kI, RobotMap.ARM_ELBOW_kD, RobotMap.armElbowPot, RobotMap.armElbowMotor, shoulder);
        elbow.setInputRange(RobotMap.ARM_ELBOW_MIN, RobotMap.ARM_ELBOW_MAX);
        wrist = new V4BPIDController(RobotMap.ARM_WRIST_kP, RobotMap.ARM_WRIST_kI, RobotMap.ARM_WRIST_kD, RobotMap.armWristPot, RobotMap.armWristMotor, null);
        wrist.setInputRange(RobotMap.ARM_WRIST_MIN, RobotMap.ARM_WRIST_MAX);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //The location of the arm is described, in feet, by the vector between the shoulder joint and the wrist joint
    //wristAngle should be a number from -180 thru 180 with 0 being towards the right
    //coordinate systems are a mess in this method. I will try to document them better/standardize them in the future.
    public void move(double x, double y, double wristAngle)
    {	
    	if(y < -RobotMap.ARM_SHOULDER_HEIGHT + RobotMap.ARM_GROUND_TOLERANCE + RobotMap.ARM_WIDTH) y = -RobotMap.ARM_SHOULDER_HEIGHT + RobotMap.ARM_GROUND_TOLERANCE + RobotMap.ARM_WIDTH;
    	if(y > 78/12 - RobotMap.ARM_SHOULDER_HEIGHT - RobotMap.ARM_CEILING_TOLERANCE + RobotMap.ARM_WIDTH) y = 78/12 - RobotMap.ARM_SHOULDER_HEIGHT - RobotMap.ARM_CEILING_TOLERANCE + RobotMap.ARM_WIDTH;
    	
    	Vector2D hypotenuse = new Vector2D(true, x, y);
    	
    	if(hypotenuse.getMagnitude() > RobotMap.ARM_SEGMENT_1_LENGTH + RobotMap.ARM_SEGMENT_2_LENGTH) hypotenuse.setMagnitude(RobotMap.ARM_SEGMENT_1_LENGTH + RobotMap.ARM_SEGMENT_2_LENGTH);
    	
    	double shoulderAngle = lawOfCosines(RobotMap.ARM_SEGMENT_2_LENGTH, RobotMap.ARM_SEGMENT_1_LENGTH, hypotenuse.getMagnitude());
    	double elbowAngle = lawOfCosines(hypotenuse.getMagnitude(), RobotMap.ARM_SEGMENT_1_LENGTH, RobotMap.ARM_SEGMENT_2_LENGTH);
    	
    	if(bendOut)
    	{
    		shoulderAngle= hypotenuse.getAngle() + shoulderAngle;
    	}
    	else
    	{
    		shoulderAngle = hypotenuse.getAngle() - shoulderAngle;
    		elbowAngle = 360 - elbowAngle;
    	}
    	
    	double elbowGlobalAngle = 90 + shoulderAngle + elbowAngle - 180; //normal coordinate system
    	
    	wristAngle += 180 - elbowGlobalAngle;
    	
    	Vector2D grabberVector1 = new Vector2D(true, RobotMap.GRABBER_LENGTH, RobotMap.GRABBER_WIDTH);
    	grabberVector1.setAngle(grabberVector1.getAngle() - 180 + hypotenuse.getAngle() - 180 + wristAngle);
    	
    	Vector2D grabberVector2 = new Vector2D(true, RobotMap.GRABBER_LENGTH, -RobotMap.GRABBER_WIDTH);
    	grabberVector2.setAngle(grabberVector2.getAngle() + elbowGlobalAngle + wristAngle);
    	
    	if(y < - RobotMap.ARM_SHOULDER_HEIGHT + RobotMap.ARM_GROUND_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector1.getY()) y = - RobotMap.ARM_SHOULDER_HEIGHT + RobotMap.ARM_GROUND_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector1.getY();
    	if(y < - RobotMap.ARM_SHOULDER_HEIGHT + RobotMap.ARM_GROUND_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector2.getY()) y = - RobotMap.ARM_SHOULDER_HEIGHT + RobotMap.ARM_GROUND_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector2.getY();
    	if(y > 78/12 - RobotMap.ARM_SHOULDER_HEIGHT - RobotMap.ARM_CEILING_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector1.getY()) y = 78/12 - RobotMap.ARM_SHOULDER_HEIGHT - RobotMap.ARM_CEILING_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector1.getY();
    	if(y > 78/12 - RobotMap.ARM_SHOULDER_HEIGHT - RobotMap.ARM_CEILING_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector2.getY()) y = 78/12 - RobotMap.ARM_SHOULDER_HEIGHT - RobotMap.ARM_CEILING_TOLERANCE + RobotMap.ARM_WIDTH + grabberVector2.getY();
    	
    	shoulderAngle = lawOfCosines(RobotMap.ARM_SEGMENT_2_LENGTH, RobotMap.ARM_SEGMENT_1_LENGTH, hypotenuse.getMagnitude());
    	elbowAngle = lawOfCosines(hypotenuse.getMagnitude(), RobotMap.ARM_SEGMENT_1_LENGTH, RobotMap.ARM_SEGMENT_2_LENGTH);
    	
    	if(bendOut)
    	{
    		shoulderAngle= hypotenuse.getAngle() + shoulderAngle;
    	}
    	else
    	{
    		shoulderAngle = hypotenuse.getAngle() - shoulderAngle;
    		elbowAngle = 360 - elbowAngle;
    	}
    	
    	Vector2D segment1 = new Vector2D(false, RobotMap.ARM_SEGMENT_1_LENGTH, shoulderAngle);
    	
    	if(x < RobotMap.ARM_LIFT_LOCATION)
    	{
    		double suspectX = RobotMap.ARM_LIFT_LOCATION - segment1.getX();
    		double suspectY = Math.tan(90 + shoulderAngle + elbowAngle - 180) * suspectX;
    		if(suspectY + segment1.getY() < RobotMap.ARM_LIFT_HEIGHT)
    		{
    			Vector2D maxSegment2 = new Vector2D(true, suspectX, RobotMap.ARM_LIFT_HEIGHT - segment1.getY());
    			elbowAngle = maxSegment2.getAngle() - 90 + shoulderAngle;
    		}
    	}
    	
    	Vector2D fullArm = Vector2D.addVectors(segment1, new Vector2D(false, RobotMap.ARM_SEGMENT_2_LENGTH, elbowGlobalAngle + 90));
    	fullArm = Vector2D.addVectors(fullArm, grabberVector2);
    	if(shoulderAngle < 0)
    	{
    		if(fullArm.getX() > RobotMap.ARM_LIFT_LOCATION)
    		{
    			fullArm.setX(RobotMap.ARM_LIFT_LOCATION);
    			Vector2D segments12 = Vector2D.subtractVectors(fullArm, grabberVector2);
    			
    			x = segments12.getX();
    			y = segments12.getY();
    			
    			shoulderAngle = lawOfCosines(RobotMap.ARM_SEGMENT_2_LENGTH, RobotMap.ARM_SEGMENT_1_LENGTH, hypotenuse.getMagnitude());
    	    	elbowAngle = lawOfCosines(hypotenuse.getMagnitude(), RobotMap.ARM_SEGMENT_1_LENGTH, RobotMap.ARM_SEGMENT_2_LENGTH);
    	    	
    	    	if(bendOut)
    	    	{
    	    		shoulderAngle= hypotenuse.getAngle() + shoulderAngle;
    	    	}
    	    	else
    	    	{
    	    		shoulderAngle = hypotenuse.getAngle() - shoulderAngle;
    	    		elbowAngle = 360 - elbowAngle;
    	    	}
    		}
    		if(fullArm.getY() > RobotMap.ARM_LIFT_HEIGHT)
    		{
    			fullArm.setY(RobotMap.ARM_LIFT_HEIGHT);
    			Vector2D segments12 = Vector2D.subtractVectors(fullArm, grabberVector2);
    			
    			x = segments12.getX();
    			y = segments12.getY();
    			
    			shoulderAngle = lawOfCosines(RobotMap.ARM_SEGMENT_2_LENGTH, RobotMap.ARM_SEGMENT_1_LENGTH, hypotenuse.getMagnitude());
    	    	elbowAngle = lawOfCosines(hypotenuse.getMagnitude(), RobotMap.ARM_SEGMENT_1_LENGTH, RobotMap.ARM_SEGMENT_2_LENGTH);
    	    	
    	    	if(bendOut)
    	    	{
    	    		shoulderAngle= hypotenuse.getAngle() + shoulderAngle;
    	    	}
    	    	else
    	    	{
    	    		shoulderAngle = hypotenuse.getAngle() - shoulderAngle;
    	    		elbowAngle = 360 - elbowAngle;
    	    	}
    		}
    	}
    }
    
    public Vector2D getVector()
    {
    	return Vector2D.addVectors(getSegment1Vector(), getSegment2Vector());
    }
    
    public Vector2D getSegment1Vector()
    {
    	return new Vector2D(false, RobotMap.ARM_SEGMENT_1_LENGTH, RobotMap.armShoulderPot.get());
    }
    
    public Vector2D getSegment2Vector()
    {
    	return new Vector2D(false, RobotMap.ARM_SEGMENT_2_LENGTH, RobotMap.armElbowPot.get());
    }
    
    public double getWrist()
    {
    	return RobotMap.armWristPot.get();
    }
    
    //returns an angle in a triangle given 3 sides
    public double lawOfCosines(double opp, double a, double b)
    {
    	return Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(opp, 2))/(2*a*b));
    }
}
