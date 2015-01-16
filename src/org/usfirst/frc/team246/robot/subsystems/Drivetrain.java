package org.usfirst.frc.team246.robot.subsystems;

import org.usfirst.frc.team246.nav6.IMUAdvanced;
import org.usfirst.frc.team246.robot.SwerveModule;
import org.usfirst.frc.team246.robot.commands.CrabWithTwist;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This is the heart of the swerve code. All vector calculations are done here.
 *
 * @author michaelsilver
 */
public class Drivetrain extends Subsystem {
    
	//every SwerveModule object is referenced twice: once in the array and once in its own variable
    public SwerveModule[] swerves;
    public SwerveModule frontModule;
    public SwerveModule leftModule;
    public SwerveModule rightModule;
    
    public double FOV = 0; //the front of the vehicle in degrees. May be used in different ways by different control schemes.
    
    public IMUAdvanced navX;
    
    public Drivetrain()
    {
    	frontModule = new SwerveModule(RobotMap.frontWheelEncoder, RobotMap.frontModuleEncoder, RobotMap.frontWheelMotor, RobotMap.frontModuleMotor, RobotMap.FRONT_BACK_LENGTH/2, 0, "frontModule");
    	leftModule = new SwerveModule(RobotMap.leftWheelEncoder, RobotMap.leftModuleEncoder, RobotMap.leftWheelMotor, RobotMap.leftModuleMotor, -RobotMap.FRONT_BACK_LENGTH/2, -RobotMap.LEFT_RIGHT_WIDTH/2, "leftModule");
    	rightModule = new SwerveModule(RobotMap.rightWheelEncoder, RobotMap.rightModuleEncoder, RobotMap.rightWheelMotor, RobotMap.rightModuleMotor, -RobotMap.FRONT_BACK_LENGTH/2, RobotMap.LEFT_RIGHT_WIDTH/2, "rightModule");
    	swerves = new SwerveModule[3];
    	swerves[0] = frontModule;
    	swerves[1] = leftModule;
    	swerves[2] = rightModule;
    	
        //We were having occasional errors with the creation of the nav6 object, so we make 5 attempts before allowing the error to go through and being forced to redeploy.
        int count = 0;
        int maxTries = 5;
        while(true) {
            try {
                navX = new IMUAdvanced(new SerialPort(57600,SerialPort.Port.kMXP), (byte)50);
                if(navX != null) break;
            } catch (Exception e) {
                if (++count == maxTries)
                {
                    e.printStackTrace();
                    break;
                }
                Timer.delay(.01);
            }
        }
        
        LiveWindow.addSensor("Drivetrain", "Gyro", navX);
        
        absoluteTwistPID = new PIDController(ABSOLUTE_TWIST_kP, ABSOLUTE_TWIST_kI, ABSOLUTE_TWIST_kD, ABSOLUTE_TWIST_kF, navX, absoluteTwistPIDOutput, ABSOLUTE_TWIST_PERIOD);
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
            moduleSetpoints[i] = new Vector2D(true, -moduleLocations[i].getY(), moduleLocations[i].getX());
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
        	swerves[i].setAngle(moduleSetpoints[0].getAngle());
        	swerves[i].setWheelSpeed(moduleSetpoints[0].getMagnitude());
        }
    }
    
    //Code for turning the robot to a certain angle relative to the field
    
    public static final double ABSOLUTE_TWIST_kP = 1;
    public static final double ABSOLUTE_TWIST_kI = 0;
    public static final double ABSOLUTE_TWIST_kD = 0;
    public static final double ABSOLUTE_TWIST_kF = 0;
    public static final double ABSOLUTE_TWIST_PERIOD = 20;
    public AbsoluteTwistPIDOutput absoluteTwistPIDOutput = new AbsoluteTwistPIDOutput();
    
    PIDController absoluteTwistPID;
    
    /**
     *@author Paul Terrasi
     */
     
    public class AbsoluteTwistPIDOutput implements PIDOutput
    {
        public double speed = 0;
        public double direction = 0;
        
        public void pidWrite(double output) {
            drive(speed, direction, output, 0, 0);
        }

    }
    
    public void driveAbsoluteTwist(double speed, double direction, double absoluteAngle){
        absoluteTwistPIDOutput.speed = speed;
        absoluteTwistPIDOutput.direction = direction;
        absoluteTwistPID.setSetpoint(absoluteAngle - FOV);
    }
    
    public void enableAbsoluteTwist(boolean on) {
        if(on) absoluteTwistPID.enable();
        else absoluteTwistPID.disable();
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
        return navX.getYaw();
    }
    
    double lastTimeWasMoving = Long.MAX_VALUE;
    public boolean isMoving()
    {
        if(navX.isMoving())
        {
            return Timer.getFPGATimestamp() - lastTimeWasMoving > .5;
        }
        else
        {
            lastTimeWasMoving = Timer.getFPGATimestamp();
            return false;
        }
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
    
    public void zeroAngles()
    {
        for(int i=0; i<swerves.length; i++)
        {
            swerves[i].resetModuleEncoder();
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
}
