package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.overclockedLibraries.SwerveModule;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This is the heart of the swerve code. All vector calculations are done here.
 *
 * @author michaelsilver
 */
public class Drivetrain extends Subsystem {
    
	//every SwerveModule object is referenced twice: once in the array and once in its own variable
    public SwerveModule[] swerves;
    public SwerveModule backModule;
    public SwerveModule leftModule;
    public SwerveModule rightModule;
    
    public Odometry odometry = new Odometry();
    
    public double FOV = 0; //the front of the vehicle in degrees. May be used in different ways by different control schemes.
    
    public Drivetrain()
    {
    	backModule = new SwerveModule(RobotMap.backWheelEncoder, RobotMap.backModulePot, RobotMap.backWheelMotor, RobotMap.backModuleMotor, 2, 0, -32.5, "backModule");
    	leftModule = new SwerveModule(RobotMap.leftWheelEncoder, RobotMap.leftModulePot, RobotMap.leftWheelMotor, RobotMap.leftModuleMotor, 2, -17.25, 0, "leftModule");
    	rightModule = new SwerveModule(RobotMap.rightWheelEncoder, RobotMap.rightModulePot, RobotMap.rightWheelMotor, RobotMap.rightModuleMotor, 2, 17.25, 0, "rightModule");
    	swerves = new SwerveModule[3];
    	swerves[0] = backModule;
    	swerves[1] = leftModule;
    	swerves[2] = rightModule;
        
        absoluteTwistPID = new PIDController(RobotMap.ABSOLUTE_TWIST_kP, RobotMap.ABSOLUTE_TWIST_kI, RobotMap.ABSOLUTE_TWIST_kD, RobotMap.navX, absoluteTwistPIDOutput);
        absoluteTwistPID.setInputRange(-180, 180);
        absoluteTwistPID.setContinuous();
        
        absoluteCrabPID = new PIDController(RobotMap.ABSOLUTE_CRAB_kP, RobotMap.ABSOLUTE_CRAB_kI, RobotMap.ABSOLUTE_CRAB_kD, odometry, absoluteCrabPIDOutput);
        
        (new Thread(odometry)).start();
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CrabWithTwist());
        
    }
    
//    CRABDRIVE METHODS:
    
//    turns all modules in the same direction
    public Vector2D[] crab(double angle, double speed){
        Vector2D[] moduleVectors = new Vector2D[swerves.length];
        for(int i=0; i<moduleVectors.length; i++){
            moduleVectors[i] = new Vector2D(false, speed, angle);
        }
        return moduleVectors;
    }
    
//    turns modules tangential to arc
//    where (x-cor, y-cor) is the location of the center of the circle
//    we are turning about if we have a cartesian coordinate system with cor
//    being the robot's center of rotation = "origin"
    public Vector2D[] snake(double rate, double corXDist, double corYDist){
        Vector2D cor = new Vector2D(true, corXDist, corYDist);
        Vector2D[] moduleLocations = new Vector2D[swerves.length];
        for(int i=0; i<moduleLocations.length; i++)
        {
        	moduleLocations[i] = new Vector2D(true, swerves[i].getX(), swerves[i].getY());
        }
        
//        makes the module locations vectors have an origin at the center of rotation
        double[] moduleDists = new double[swerves.length]; //array of module distances (the magnitudes of the distance vectors)
        for(int i=0; i<moduleLocations.length; i++){
            moduleLocations[i] = Vector2D.subtractVectors(moduleLocations[i], cor);
            moduleDists[i] = moduleLocations[i].getMagnitude();
        }
        
//        find the farthest module from the center of rotation
        int farthestModule = 0;
        for(int i=0; i<moduleDists.length; i++){
            if(moduleDists[i] > moduleDists[farthestModule]){
                farthestModule = i;
            }
        }
        
//        rotate the moduleLocations vectors -90 degrees.
        Vector2D[] moduleSetpoints = new Vector2D[swerves.length];
        for(int i=0; i<moduleSetpoints.length; i++){
            moduleSetpoints[i] = new Vector2D(true, moduleLocations[i].getY(), -moduleLocations[i].getX());
            moduleSetpoints[i].setMagnitude(rate*moduleDists[i]/moduleDists[farthestModule]); //The furthest module should move at the same speed as the rate, and all of the other ones should scale directly porportionally to it based on the ratio of their distances to the center of rotation.
        }
        return moduleSetpoints;
    }
    
    //The primary driving method. Adds the crab and snake vectors together, allowing the robot to drive in any direction while rotating at the same time.
    public void drive(double speed, double direction, double spinRate, double corX, double corY)
    {
        Vector2D[] moduleSetpoints = new Vector2D[swerves.length];
        Vector2D[] crab = crab(direction, speed);
        Vector2D[] snake = snake(spinRate, corX, corY);
        
        //Add together the crab and snake vectors. Also find which wheel will be spinning the fastest.
        double largestVector = 0;
        for(int i=0; i<moduleSetpoints.length; i++){
            moduleSetpoints[i] = Vector2D.addVectors(crab[i], snake[i]);
            if(moduleSetpoints[i].getMagnitude() > largestVector) largestVector = moduleSetpoints[i].getMagnitude();
        }
        
        //normalize the vectors so that none of them have a magnitude greater than 1
        if(largestVector > 1)
        {
            for(int i = 0; i < moduleSetpoints.length; i++)
            {
                moduleSetpoints[i].setMagnitude(moduleSetpoints[i].getMagnitude() / largestVector);
            }
        }
        
        for(int i=0; i < swerves.length; i++)
        {
        	if(moduleSetpoints[i].getMagnitude() != 0)
        	{
        		swerves[i].setAngle(moduleSetpoints[i].getAngle());
        	}
        	swerves[i].setWheelSpeed(moduleSetpoints[i].getMagnitude());
        }
    }
    
    public void setMaxSpeed(double maxSpeed)
    {
    	for(int i = 0; i < swerves.length; i++)
    	{
    		swerves[i].setMaxSpeed(maxSpeed);
    	}
    }
    
    //Code for turning the robot to a certain angle relative to the field
    
    public DrivetrainPID drivetrainPID = new DrivetrainPID();
    
    private AbsoluteTwistPIDOutput absoluteTwistPIDOutput = new AbsoluteTwistPIDOutput();
    public PIDController absoluteTwistPID;
    
    /**
     *@author Paul Terrasi
     */
     
    private class AbsoluteTwistPIDOutput implements PIDOutput
    {   
        public void pidWrite(double output) {
        	drivetrainPID.setTwist(output);
        }
    }

    public void enableAbsoluteTwist(boolean on) {
        if(on) absoluteTwistPID.enable();
        else absoluteTwistPID.disable();
    }

    private AbsoluteCrabPIDOutput absoluteCrabPIDOutput = new AbsoluteCrabPIDOutput();
    public PIDController absoluteCrabPID;
    
    /**
     *@author Michael Silver
     */
     
    private class AbsoluteCrabPIDOutput implements PIDOutput
    {   
        public void pidWrite(double output) {
        	drivetrainPID.setCrabSpeed(output);
        }
    }
    
    public void enableAbsoluteCrab(boolean on) {
        if(on) absoluteCrabPID.enable();
        else absoluteCrabPID.disable();
    }
    
    public class DrivetrainPID
    {
    	private double speed;
    	private double direction;
    	private Vector2D crab = new Vector2D(false, speed, direction);
    	private Vector2D COR = new Vector2D(true, 0, 0);
    	private double spinRate = 0;
    	
    	public void setCrabSpeed(double speed){
    		this.speed = speed;
    		deploy();
    	}
    	
    	public void setCrabDirection(double direction){
    		this.direction = direction;
    		deploy();
    	}
    	
    	public void setTwist(double spinRate){
    		this.spinRate = spinRate;
    		deploy();
    	}
    	
    	public void setCOR(Vector2D COR){
    		this.COR = COR;
    		deploy();
    	}
    	
    	private void deploy(){
    		drive(crab.getMagnitude(), crab.getAngle(), spinRate, COR.getX(), COR.getY());
    	}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- congratulations you did it, your prize is the smiley face just to the right----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:( hahaha
    
    public void setFOV(double fov)
    {
        FOV = fov;
    }
    
    public double getFOV()
    {
        return FOV;
    }
    
    public double getFieldCentricHeading() //returns the direction of the robot relative to the direction the driver is facing.
    {
        return RobotMap.navX.getYaw();
    }
    
    double lastTimeWasMoving = Long.MAX_VALUE;
    public boolean isMoving()
    {
    	return !Robot.oi.driver.getA().get();
    	/*
        if(RobotMap.navX.isMoving())
        {
            return Timer.getFPGATimestamp() - lastTimeWasMoving > .5;
        }
        else
        {
            lastTimeWasMoving = Timer.getFPGATimestamp();
            return false;
        }
        */
    }
    
    public void PIDOn(boolean on)
    {
        if(on)
        {
        	for(int i=0; i<swerves.length; i++)
        	{
            	swerves[i].speedPIDOn(true);
            	swerves[i].anglePIDOn(true);
            }
        }
        else
        {
            for(int i=0; i<swerves.length; i++)
        	{
            	swerves[i].speedPIDOn(false);
            	swerves[i].anglePIDOn(false);
            }
        }
    }
    
    public void unwind()
    {
    	for(int i=0; i<swerves.length; i++)
        {
            swerves[i].unwind();
        }
    }
    
    public void stopUnwinding()
    {
        for(int i=0; i<swerves.length; i++)
        {
            swerves[i].stopUnwinding();
        }
    }
     
    public boolean isOverRotated()
    {
    	for(int i=0; i<swerves.length; i++)
        {
            if(swerves[i].getModuleAngle() > RobotMap.MAX_MODULE_ANGLE) return true;
        }
        return false;
    }
    
    public class Odometry implements Runnable, PIDSource
    {
    	private Vector2D robotCentricLinearDisplacement = new Vector2D(true, 0, 0);
    	private double robotCentricAngularDisplacement = 0;
    	
    	private Vector2D[] swervesDisplacementVectors = new Vector2D[swerves.length];
    	
    	@Override
		public void run() {
			while(true){
				setSwerveDisplacementVectors();
				calculateNetLinearDisplacement();
				calculateNetAngularDisplacement();
				Timer.delay(.001); // in seconds	
			}
		}
    	
//    	GETTERS:
    	public Vector2D getRobotCentricLinearDisplacement(){
    		return robotCentricLinearDisplacement;
    	}
    	
    	public double getRobotCentricAngularDisplacement(){
    		return robotCentricAngularDisplacement;
    	}
    	
    	public Vector2D getFieldCentricLinearDisplacement(){
    		Vector2D fieldCentricLinearDisplacement = robotCentricLinearDisplacement.cloneVector();
    		fieldCentricLinearDisplacement.setAngle(robotCentricLinearDisplacement.getAngle() + RobotMap.navX.getYaw());
    		return fieldCentricLinearDisplacement;
    	}
    	
    	public double getFieldCentricAngularDisplacement(){
			return robotCentricAngularDisplacement + RobotMap.navX.getYaw();
		}
    	
//    	RESET ODOMETRY:
    	public void resetAll(){
    		resetNetLinearDiplacement();
    		resetNetAngularDiplacement();
    	}
    	
    	public void resetNetLinearDiplacement() {
    		robotCentricLinearDisplacement = new Vector2D(true, 0, 0);
		}
    	
    	public void resetNetAngularDiplacement() {
    		robotCentricAngularDisplacement = 0;
		}
		
//    	SET DISPLACEMENT VECTORS (needed for both calculation methods below)
    	private void setSwerveDisplacementVectors() {
    		for(int i=0; i<swerves.length; i++){
    			swervesDisplacementVectors[i] = new Vector2D(false, swerves[i].wheelEncoder.getDistance(), swerves[i].modulePot.get());
    		}
		}
    	
//    	CALCULATE NETS for Odometry:
    	private void calculateNetLinearDisplacement(){
    		robotCentricLinearDisplacement = Vector2D.addVectors(robotCentricLinearDisplacement, sumOfVectors(swervesDisplacementVectors));    		
    	}
    	
    	private void calculateNetAngularDisplacement(){
    		Vector2D[] swervesDisplacementFromCenter = new Vector2D[swerves.length];
    		Vector2D[] swervesPerpendicularDiplacement = new Vector2D[swerves.length];
    		double[] swervesAngularDisplacement = new double[swerves.length];
    		
    		for(int i=0; i<swerves.length; i++){
    			swervesDisplacementFromCenter[i] = new Vector2D(true, -swerves[i].getX(), -swerves[i].getY()); // negative because x,y were relative to center
    			swervesPerpendicularDiplacement[i] = Vector2D.perpendicularProjection(swervesDisplacementVectors[i], swervesDisplacementFromCenter[i]);
    			swervesAngularDisplacement[i] = swervesPerpendicularDiplacement[i].getMagnitude() / swervesDisplacementFromCenter[i].getMagnitude(); // w = v_perp/r
    		}
    		robotCentricAngularDisplacement = robotCentricAngularDisplacement + sumOfDoubles(swervesAngularDisplacement);
    	}
    	
//    	MATH UTILITIES:
    	private Vector2D sumOfVectors(Vector2D[] vectorArray){
    		Vector2D sum = new Vector2D(true, 0, 0);
    		for(int i=0; i<vectorArray.length; i++){
    			sum = Vector2D.addVectors(sum, vectorArray[i]);
    		}
    		return sum;
    	}
    	
    	private double sumOfDoubles(double[] doubleArray){
			double sum = 0;
			for(int i=0; i<doubleArray.length; i++){
				sum = sum + doubleArray[i];
			}
			return sum;
		}

		@Override
		public double pidGet() {
			return getRobotCentricLinearDisplacement().getMagnitude();
		}
    }
}
