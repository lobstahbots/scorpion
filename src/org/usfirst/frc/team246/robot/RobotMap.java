package org.usfirst.frc.team246.robot;

import org.usfirst.frc.team246.nav6.IMUAdvanced;
import org.usfirst.frc.team246.robot.overclockedLibraries.Talon246;
import org.usfirst.frc.team246.robot.overclockedLibraries.Victor246;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static PowerDistributionPanel pdp;
    
//Drivetrain
	
	//Motors
	
	public static Talon246 backWheelMotor;
	public static Talon246 leftWheelMotor;
	public static Talon246 rightWheelMotor;
	
	public static Victor246 backModuleMotor;
	public static Victor246 leftModuleMotor;
	public static Victor246 rightModuleMotor;
	
	//Sensors
	
	public static Encoder backWheelEncoder;
	public static Encoder leftWheelEncoder;
	public static Encoder rightWheelEncoder;
	
	public static Encoder backModuleEncoder;
	public static Encoder leftModuleEncoder;
	public static Encoder rightModuleEncoder;
	
	public static IMUAdvanced navX;
	
	public static DigitalInput encoderZeroing;
	
	//constants
	
	public static final double WHEEL_ENCODER_DISTANCE_PER_TICK = .0123;
	public static final double MODULE_ENCODER_DISTANCE_PER_TICK = 360./(256.*2.);
		
	public static final double WHEEL_kP = 0;
	public static final double WHEEL_kI = 1;
	public static final double WHEEL_kD = 0;
	public static final double WHEEL_kF = 0;
	
	public static final double MODULE_kP = .035;
	public static final double MODULE_kI = 0;
	public static final double MODULE_kD = .025;
	public static final double MODULE_kF = 0;
	
    public static final double K_MODULE_ANGLE_DELTA = 1;
    public static final double K_MODULE_ANGLE_TWIST = 0;
    public static final double K_MODULE_ANGLE_REVERSE = 0;
    
    public static final double MAX_MODULE_ANGLE = 1*360 + 180; //the maximum angle which can be commanded to a module
    public static final double UNSAFE_MODULE_ANGLE = MAX_MODULE_ANGLE + 360; //the angle at which a module motor should be emergency stopped
    
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
	
	//Constants
	
	public static final double LIFT_kP = 1;
	public static final double LIFT_kI = 0;
	public static final double LIFT_kD = 0;
	public static final double LIFT_TOLERANCE = .5;
	
	public static final double LIFT_MAX_HEIGHT = 3;
	public static final double LIFT_MIN_HEIGHT = 0;
	
	public enum ArmSetpoints {
		GROUND(-5), SCORING_PLATFORM(2), STEP(6.25);
		
		private double value;
		
		private ArmSetpoints(double value)
		{
			this.value = value;
		}
		
		public double getValue()
		{
			return value;
		}
	}
	
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
		pdp = new PowerDistributionPanel();
		
	//Drivetrain
		
		//Motors
		backWheelMotor = new Talon246(0, 12, pdp);
		LiveWindow.addActuator("Drivetrain", "backWheelMotor", backWheelMotor);
		leftWheelMotor = new Talon246(2, 13, pdp);
		LiveWindow.addActuator("Drivetrain", "leftWheelMotor", leftWheelMotor);
		rightWheelMotor = new Talon246(4, 14, pdp);
		LiveWindow.addActuator("Drivetrain", "rightWheelMotor", rightWheelMotor);
		
		backModuleMotor = new Victor246(1, 15, pdp);
		LiveWindow.addActuator("Drivetrain", "backModuleMotor", backModuleMotor);
		leftModuleMotor = new Victor246(3, 1, pdp);
		LiveWindow.addActuator("Drivetrain", "leftModuleMotor", leftModuleMotor);
		rightModuleMotor = new Victor246(5, 0, pdp);
		LiveWindow.addActuator("Drivetrain", "rightModuleMotor", rightModuleMotor);
		
		//Sensors
		
		backWheelEncoder = new Encoder(0,1);
	    backWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    backWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "backWheelEncoder", backWheelEncoder);
		leftWheelEncoder = new Encoder(4,5);
	    leftWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    leftWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "leftWheelEncoder", leftWheelEncoder);
		rightWheelEncoder = new Encoder(8,9);
	    rightWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    rightWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "rightWheelEncoder", rightWheelEncoder);
		
	    backModuleEncoder = new Encoder(2,3);
	    backModuleEncoder.setDistancePerPulse(MODULE_ENCODER_DISTANCE_PER_TICK); 
	    backModuleEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); // have encoder measure distance
	    LiveWindow.addSensor("Drivetrain", "backModuleEncoder", backModuleEncoder);
		leftModuleEncoder = new Encoder(6,7);
	    leftModuleEncoder.setDistancePerPulse(MODULE_ENCODER_DISTANCE_PER_TICK); 
	    leftModuleEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); // have encoder measure distance
	    LiveWindow.addSensor("Drivetrain", "leftModuleEncoder", leftModuleEncoder);
		rightModuleEncoder = new Encoder(10,11);
	    rightModuleEncoder.setDistancePerPulse(MODULE_ENCODER_DISTANCE_PER_TICK); 
	    rightModuleEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kDistance); // have encoder measure distance
	    LiveWindow.addSensor("Drivetrain", "rightModuleEncoder", rightModuleEncoder);
	    
	  //We were having occasional errors with the creation of the nav6 object, so we make 5 attempts before allowing the error to go through and being forced to redeploy.
        int count = 0;
        int maxTries = 5;
        while(true) {
            try {
                navX = new IMUAdvanced(new SerialPort(57600,SerialPort.Port.kUSB), (byte)50);
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
	    
	    encoderZeroing = new DigitalInput(12);
	    LiveWindow.addSensor("Drivetrain", "encoderZeroing", encoderZeroing);
		
	//Getters
		
		//Motors
		
		leftGetterMotor = new VictorSP(6);
		LiveWindow.addActuator("Getters", "leftGetterMotor", leftGetterMotor);
		rightGetterMotor = new VictorSP(7);
		LiveWindow.addActuator("Getters", "rightGetterMotor", rightGetterMotor);
		
		//Pneumatics
		
//		leftGetterCylinder = new DoubleSolenoid(0,1);
//		LiveWindow.addActuator("Getters", "leftGetterCylinder", leftGetterCylinder);
//		rightGetterCylinder = new DoubleSolenoid(2,3);
//		LiveWindow.addActuator("Getters", "rightGetterCylinder", rightGetterCylinder);
		
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
		
//		armShoulderMotor = new VictorSP(10);
//		LiveWindow.addActuator("Arm", "armShoulderMotor", armShoulderMotor);
//		armElbowMotor = new VictorSP(11);
//		LiveWindow.addActuator("Arm", "armElbowMotor", armElbowMotor);
//		armWristMotor = new VictorSP(12);
//		LiveWindow.addActuator("Arm", "armWristMotor", armWristMotor);
		
		//Sensors
		
		armShoulderPot = new AnalogPotentiometer(2, 360, 0); //TODO: Get these constants
		LiveWindow.addActuator("Arm", "armShoulderPot", armShoulderPot);
		armElbowPot = new AnalogPotentiometer(3, 360, 0); //TODO: Get these constants
		LiveWindow.addActuator("Arm", "armElbowPot", armElbowPot);
		armWristPot = new AnalogPotentiometer(4, 360, 0); //TODO: Get these constants
		LiveWindow.addActuator("Arm", "armWristPot", armWristPot);
		
	//Grabber
		
		//Pneumatics
		
//		leftGrabberCylinder = new DoubleSolenoid(4,5);
//		LiveWindow.addActuator("Grabber", "leftGrabberCylinder", leftGrabberCylinder);
//		rightGrabberCylinder = new DoubleSolenoid(6,7);
//		LiveWindow.addActuator("Grabber",  "rightGrabberCylinder", rightGrabberCylinder);
		
		//Sensors
		
		canDetector = new DigitalInput(13);
		LiveWindow.addActuator("Grabber", "canDetector", canDetector);
	}
}
