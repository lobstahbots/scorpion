package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.robot.overclockedLibraries.Talon246;
import org.usfirst.frc.team246.robot.overclockedLibraries.Victor246;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
//Drivetrain
	
	//Motors
	
	public static Talon246 frontWheelMotor;
	public static Talon246 leftWheelMotor;
	public static Talon246 rightWheelMotor;
	
	public static Victor246 frontModuleMotor;
	public static Victor246 leftModuleMotor;
	public static Victor246 rightModuleMotor;
	
	//Sensors
	
	public static Encoder frontWheelEncoder;
	public static Encoder leftWheelEncoder;
	public static Encoder rightWheelEncoder;
	
	public static Encoder frontModuleEncoder;
	public static Encoder leftModuleEncoder;
	public static Encoder rightModuleEncoder;
	
	public static DigitalInput encoderZeroing;
	
	//constants
	
	public static final double WHEEL_ENCODER_DISTANCE_PER_TICK = .0123;
	public static final double MODULE_ENCODER_DISTANCE_PER_TICK = .703;
	
	public static final double WHEEL_kP = 0;
	public static final double WHEEL_kI = 1;
	public static final double WHEEL_kD = 0;
	public static final double WHEEL_kF = 0;
	
	public static final double MODULE_kP = 1;
	public static final double MODULE_kI = 0;
	public static final double MODULE_kD = 0;
	public static final double MODULE_kF = 0;
	
    public static final double K_MODULE_ANGLE_DELTA = 1;
    public static final double K_MODULE_ANGLE_TWIST = 0;
    public static final double K_MODULE_ANGLE_REVERSE = 0;
    
    public static final double MAX_MODULE_ANGLE = 1*360 + 180; //the maximum angle which can be commanded to a module
    public static final double UNSAFE_MODULE_ANGLE = MAX_MODULE_ANGLE + 360; //the angle at which a module motor should be emergency stopped
    
    public static final double LEFT_RIGHT_WIDTH = 34.5; //distance between "left" and "right" modules
    public static final double FRONT_BACK_LENGTH = 32.5; //distance between "front" and "back" modules
    public static final double WHEEL_TOP_ABSOLUTE_SPEED = 11; //the highest speed that our wheels can move
	
//Getters
	
	//Motors
	
	public static VictorSP leftGetterMotor;
	public static VictorSP rightGetterMotor;
	
	//Pneumatics
	
	public static DoubleSolenoid leftGetterCylinder;
	public static DoubleSolenoid rightGetterCylinder;
	
//Forklift
	
	//Motors
	
	public static VictorSP liftMotor;
	
	//Sensors
	
	public static AnalogPotentiometer liftPot;
	
//Pusher
	
	//Motors
	
	public static VictorSP pusherMotor;
	
	//Sensors
	
	public static AnalogPotentiometer pusherPot;
	
//Arm
	
	//Motors
	
	public static VictorSP armShoulderMotor;
	public static VictorSP armElbowMotor;
	public static VictorSP armWristMotor;
	
	//Sensors
	
	public static AnalogPotentiometer armShoulderPot;
	public static AnalogPotentiometer armElbowPot;
	public static AnalogPotentiometer armWristPot;
	
//Grabber
	
	//Pneumatics
	
	public static DoubleSolenoid leftGrabberCylinder;
	public static DoubleSolenoid rightGrabberCylinder;
	
	//Sensors
	
	public static DigitalInput canDetector;
	
	public static void init()
	{
	//Drivetrain
		
		//Motors
		frontWheelMotor = new Talon246(0);
		LiveWindow.addActuator("Drivetrain", "frontWheelMotor", frontWheelMotor);
		leftWheelMotor = new Talon246(2);
		LiveWindow.addActuator("Drivetrain", "leftWheelMotor", leftWheelMotor);
		rightWheelMotor = new Talon246(4);
		LiveWindow.addActuator("Drivetrain", "rightWheelMotor", rightWheelMotor);
		
		frontModuleMotor = new Victor246(1);
		LiveWindow.addActuator("Drivetrain", "frontModuleMotor", frontModuleMotor);
		leftModuleMotor = new Victor246(3);
		LiveWindow.addActuator("Drivetrain", "leftModuleMotor", leftModuleMotor);
		rightModuleMotor = new Victor246(5);
		LiveWindow.addActuator("Drivetrain", "rightModuleMotor", rightModuleMotor);
		
		//Sensors
		
		frontWheelEncoder = new Encoder(0,1);
	    frontWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    frontWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "frontWheelEncoder", frontWheelEncoder);
		leftWheelEncoder = new Encoder(4,5);
	    leftWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    leftWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "leftWheelEncoder", leftWheelEncoder);
		rightWheelEncoder = new Encoder(8,9);
	    rightWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    rightWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "rightWheelEncoder", rightWheelEncoder);
		
	    frontModuleEncoder = new Encoder(2,3);
	    frontModuleEncoder.setDistancePerPulse(MODULE_ENCODER_DISTANCE_PER_TICK); 
	    frontModuleEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); // have encoder measure distance
	    LiveWindow.addSensor("Drivetrain", "frontModuleEncoder", frontModuleEncoder);
		leftModuleEncoder = new Encoder(6,7);
	    leftModuleEncoder.setDistancePerPulse(MODULE_ENCODER_DISTANCE_PER_TICK); 
	    leftModuleEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); // have encoder measure distance
	    LiveWindow.addSensor("Drivetrain", "leftModuleEncoder", leftModuleEncoder);
		rightModuleEncoder = new Encoder(10,11);
	    rightModuleEncoder.setDistancePerPulse(MODULE_ENCODER_DISTANCE_PER_TICK); 
	    rightModuleEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); // have encoder measure distance
	    LiveWindow.addSensor("Drivetrain", "rightModuleEncoder", rightModuleEncoder);
	    
	    encoderZeroing = new DigitalInput(12);
	    LiveWindow.addSensor("Drivetrain", "encoderZeroing", encoderZeroing);
		
	//Getters
		
		//Motors
		
		leftGetterMotor = new VictorSP(6);
		LiveWindow.addActuator("Getters", "leftGetterMotor", leftGetterMotor);
		rightGetterMotor = new VictorSP(7);
		LiveWindow.addActuator("Getters", "rightGetterMotor", rightGetterMotor);
		
		//Pneumatics
		
		leftGetterCylinder = new DoubleSolenoid(0,1);
		LiveWindow.addActuator("Getters", "leftGetterCylinder", leftGetterCylinder);
		rightGetterCylinder = new DoubleSolenoid(2,3);
		LiveWindow.addActuator("Getters", "rightGetterCylinder", rightGetterCylinder);
		
	//Forklift
		
		//Motors
		
		liftMotor = new VictorSP(8);
		LiveWindow.addActuator("Forklift", "liftMotor", liftMotor);
		
		//Sensors
		
		liftPot = new AnalogPotentiometer(0, 5); //TODO: Get this constant
		LiveWindow.addSensor("Forklift", "liftPot", liftPot);
		 
		
	//Pusher
		
		//Motors
		
		pusherMotor = new VictorSP(9);
		LiveWindow.addActuator("Pusher", "pusherMotor", pusherMotor);
		
		//Sensors
		
		pusherPot = new AnalogPotentiometer(1, 1); //TODO: Get this constant
		LiveWindow.addSensor("Pusher", "pusherPot", pusherPot);
		
	//Arm
		
		//Motors
		
		armShoulderMotor = new VictorSP(10);
		LiveWindow.addActuator("Arm", "armShoulderMotor", armShoulderMotor);
		armElbowMotor = new VictorSP(11);
		LiveWindow.addActuator("Arm", "armElbowMotor", armElbowMotor);
		armWristMotor = new VictorSP(12);
		LiveWindow.addActuator("Arm", "armWristMotor", armWristMotor);
		
		//Sensors
		
		armShoulderPot = new AnalogPotentiometer(2, 360, 0); //TODO: Get these constants
		LiveWindow.addActuator("Arm", "armShoulderPot", armShoulderPot);
		armElbowPot = new AnalogPotentiometer(3, 360, 0); //TODO: Get these constants
		LiveWindow.addActuator("Arm", "armElbowPot", armElbowPot);
		armWristPot = new AnalogPotentiometer(4, 360, 0); //TODO: Get these constants
		LiveWindow.addActuator("Arm", "armWristPot", armWristPot);
		
	//Grabber
		
		//Pneumatics
		
		leftGrabberCylinder = new DoubleSolenoid(4,5);
		LiveWindow.addActuator("Grabber", "leftGrabberCylinder", leftGrabberCylinder);
		rightGrabberCylinder = new DoubleSolenoid(6,7);
		LiveWindow.addActuator("Grabber",  "rightGrabberCylinder", rightGrabberCylinder);
		
		//Sensors
		
		canDetector = new DigitalInput(13);
		LiveWindow.addActuator("Grabber", "canDetector", canDetector);
	}
}
