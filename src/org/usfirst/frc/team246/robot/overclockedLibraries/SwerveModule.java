package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Paul
 */
public class SwerveModule 
{
    double x; //the horizontal distance between this wheel and the center of the robot
    double y; //the vertical distance between this wheel and the center of the robot
    
    public String name;
    
    public Encoder wheelEncoder; //the encoder measuring wheel speed

    public AnalogPot modulePot; //the encoder measure module angle

    public SpeedController wheelMotor; //the motor controlling wheel speed

    public SpeedController moduleMotor; //the motor controlling module angle
    
    public PIDController speedPID; //the PID controller for wheel speed
    public PIDController anglePID; //the PID controller for module angle
    
    public boolean invertSpeed = false; // true when the wheel is pointing backwards
    
    public boolean unwinding = false; //if true, then the wheels will return to pointing forwards with the wires completely untwisted
    
    public double maxSpeed;
    
    public SwerveModule(Encoder wheelEncoder, AnalogPot modulePot, SpeedController wheelMotor, SpeedController moduleMotor, double maxSpeed, double x, double y, String name)
    {
        // set globals
        
        this.x = x;
        this.y = y;
        
        this.name = name;
        
        this.wheelEncoder = wheelEncoder;
        this.modulePot = modulePot;
        this.wheelMotor = wheelMotor;
        this.moduleMotor = moduleMotor;
        
        this.maxSpeed = maxSpeed;
        
        //initialize PID controllers
        
        speedPID = new PIDController(RobotMap.WHEEL_kP, RobotMap.WHEEL_kI, RobotMap.WHEEL_kD, RobotMap.WHEEL_kF, wheelEncoder, wheelMotor);
        anglePID = new PIDController(RobotMap.MODULE_kP, RobotMap.MODULE_kI, RobotMap.MODULE_kD, RobotMap.MODULE_kF, modulePot, moduleMotor, .02);
        
        speedPID.setOutputRange(-1, 1);
        anglePID.setOutputRange(-1, 1);
        
        LiveWindow.addSensor("SwerveModule", name + "speedPID", speedPID);
        LiveWindow.addSensor("SwerveModule", name + "anglePID", anglePID);
    }
    
//    coordinates
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    // PID Methods
    
    //whenever possible, call setAngle before setSpeed
    
    public void setAngle(double angle){

        if(!unwinding)
        {
            //The following is uses a weighted rating system to decide which direction we rotate the module

            //constants for the weighted average
            final double K_DELTA = RobotMap.K_MODULE_ANGLE_DELTA;
            final double K_TWIST = RobotMap.K_MODULE_ANGLE_TWIST;
            final double K_REVERSE = RobotMap.K_MODULE_ANGLE_REVERSE;

            //ensure that anglePID is enabled before running
            anglePID.enable();

            //converts the inputed angle into its reference angle
            angle = angle % 360;

            double setPointForward = angle; // angle setpoint if we want the wheel to move forward
            double setPointBackward = angle + 180; // angle setpoint if we want the wheel to move backwards

            //The following code ensures that our 2 potential setpoints are the ones closest to our current angle
            while(Math.abs(setPointForward - modulePot.get()) > 180)
            {
                if(setPointForward - modulePot.get() < 0 && setPointForward < RobotMap.MAX_MODULE_ANGLE - 360) setPointForward += 360; //if we need to add 360 to get closer to moduleEncoder, do so
                else if (setPointForward - modulePot.get() > 0 && setPointForward > -RobotMap.MAX_MODULE_ANGLE + 360) setPointForward -= 360; //else subtract 360
                else break;
            }

            while(Math.abs(setPointBackward - modulePot.get()) > 180)
            {
                if(setPointBackward - modulePot.get() < 0 && setPointBackward < RobotMap.MAX_MODULE_ANGLE - 360) setPointBackward += 360; //if we need to add 360 to get closer to moduleEncoder, do so
                else if (setPointBackward - modulePot.get() > 0 && setPointBackward > -RobotMap.MAX_MODULE_ANGLE + 360) setPointBackward -= 360; //else subtract 360
                else break;
            }

            //rating for how desirable each setpoint is. Higher numbers are better
            double forwardsRating = 0;
            double backwardsRating = 0;

            //Rating for the distance between where the module is currently pointing and each of the setpoints
            forwardsRating -= K_DELTA*Math.abs(setPointForward - modulePot.get());
            backwardsRating -= K_DELTA*Math.abs(setPointBackward - modulePot.get());

            //Rating boost if this setpoint is closer to the 0 (where the wire is completely untwisted) that the current module angle
            if(setPointForward > 0){
                forwardsRating += (modulePot.get() - setPointForward)*K_TWIST; // positive => we are unwinding (moving closer to zero)
            } else {
                forwardsRating += (setPointForward - modulePot.get())*K_TWIST; // negative => we are winding up (moving farther from zero)
            }

            if(setPointBackward > 0){
                backwardsRating += (modulePot.get() - setPointBackward)*K_TWIST; // positive => we are unwinding (moving closer to zero)
            } else {
                backwardsRating += (setPointBackward - modulePot.get())*K_TWIST; // negative => we are winding up (moving farther from zero)
            }

            //Rating for if the how much the velocity will need to change in order the make the wheel go further. Forwards rating gets a positive boost if wheel is already moving forwards, if the wheel is currently moving backwards it gets a deduction.
            forwardsRating += K_REVERSE * wheelEncoder.getRate();

            //Decision making time
            if(forwardsRating > backwardsRating)
            {
                anglePID.setSetpoint(setPointForward);
                invertSpeed = false;
            }
            else
            {
                anglePID.setSetpoint(setPointBackward);
                invertSpeed = true;
            }
        }
    }
    
    public void setWheelSpeed(double speed){
        if(invertSpeed) speed = -speed;
        if(!Robot.gasMode)
        {
            if(Robot.test2)
            {
                speedPID.setPID(SmartDashboard.getNumber("speedP", RobotMap.WHEEL_kP), SmartDashboard.getNumber("speedI", RobotMap.WHEEL_kI), SmartDashboard.getNumber("speedD", RobotMap.WHEEL_kD), SmartDashboard.getNumber("speedF", RobotMap.WHEEL_kF));
            }
            speedPID.enable();
            speedPID.setSetpoint(maxSpeed*speed);
        }
        else
        {
        	speedPID.disable();
            wheelMotor.set(speed);
        }
    }
    
    //Makes the wheels point forwards with the wires being completely untwisted. 
    //Once this method is called, setAngle(double angle) will be disabled until stopUnwinding is called()
    public void unwind()
    {
        unwinding = true;
        if(!anglePID.isEnable())anglePID.enable();
        anglePID.setSetpoint(0);
    }
    
    //Stops the wheels from trying to point forwards and restores control to setAngle(double angle)
    public void stopUnwinding()
    {
        unwinding = false;
    }
    
    public void setMaxSpeed(double max)
    {
    	maxSpeed = max;
    }
    
    public void anglePIDOn(boolean on){
        if (on) anglePID.enable();
        else anglePID.disable();
    }
    
    public void speedPIDOn(boolean on){
        if (on) speedPID.enable();
        else speedPID.disable();
    }
    
    public double getAngleSetpoint() {
        return anglePID.getSetpoint();
    }
    
    public double getSpeedSetpoint() {
        return speedPID.getSetpoint();
    }
    
    public double getAngleOutput() {
        return moduleMotor.get();
    }
    
    public double getSpeedOutput() {
        return wheelMotor.get();
    }

    // Wheel Encoder Methods
    
    public double getWheelSpeed() {
        return wheelEncoder.getRate();
    }
    
    public double getWheelDistance() {
        return wheelEncoder.getDistance();
    }
    
    public void resetWheelEncoder(){
        wheelEncoder.reset();
    }
    
    // Module Encoder Methods
    
    public double getModuleAngle() {
        return modulePot.get();
    }
}
