package org.usfirst.frc.team246.robot;

import javax.tools.Diagnostic;

import org.usfirst.frc.team246.nav6.IMUAdvanced;
import org.usfirst.frc.team246.robot.overclockedLibraries.AnalogIn;
import org.usfirst.frc.team246.robot.overclockedLibraries.AnalogPot;
import org.usfirst.frc.team246.robot.overclockedLibraries.Diagnostics;
import org.usfirst.frc.team246.robot.overclockedLibraries.LimitSwitch;
import org.usfirst.frc.team246.robot.overclockedLibraries.SpeedController246;
import org.usfirst.frc.team246.robot.overclockedLibraries.Talon246;
import org.usfirst.frc.team246.robot.overclockedLibraries.Vector2D;
import org.usfirst.frc.team246.robot.overclockedLibraries.Victor246;
import org.usfirst.frc.team246.robot.overclockedLibraries.VictorSP246;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

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
	
	public static SpeedController246 backWheelMotor;
	public static SpeedController246 leftWheelMotor;
	public static SpeedController246 rightWheelMotor;
	
	public static SpeedController246 backModuleMotor;
	public static SpeedController246 leftModuleMotor;
	public static SpeedController246 rightModuleMotor;
	
	//Sensors
	
	public static Encoder backWheelEncoder;
	public static Encoder leftWheelEncoder;
	public static Encoder rightWheelEncoder;
	
	public static AnalogPot backModulePot;
	public static AnalogPot leftModulePot;
	public static AnalogPot rightModulePot;
	
	public static IMUAdvanced navX;
	
	//constants
	
	public static final double WHEEL_ENCODER_DISTANCE_PER_TICK = .5*Math.PI/256;
		
	public static double WHEEL_kP = 0.15;
	public static double WHEEL_kI = 0;
	public static double WHEEL_kD = 0;
	public static double WHEEL_kF = 0.075;
	
	public static double MODULE_kP = .05;
	public static double MODULE_kI = 0;
	public static double MODULE_kD = .08;
	public static double MODULE_kF = 0;
	
	public static double ABSOLUTE_TWIST_kP = .2;
    public static double ABSOLUTE_TWIST_kI = 0;
    public static double ABSOLUTE_TWIST_kD = 0;
	
    public static double ABSOLUTE_CRAB_kP = 1;
    public static double ABSOLUTE_CRAB_kI = 0;
    public static double ABSOLUTE_CRAB_kD = 0;
    
    public static double ABSOLUTE_CRAB_FAST_kP = 1;
    
    public static final double K_MODULE_ANGLE_DELTA = 1;
    public static final double K_MODULE_ANGLE_TWIST = 0;
    public static final double K_MODULE_ANGLE_REVERSE = 0;
    
    public static final double MAX_MODULE_ANGLE = 360; //the maximum angle which can be commanded to a module
    public static final double UNSAFE_MODULE_ANGLE = MAX_MODULE_ANGLE + 180; //the angle at which a module motor should be emergency stopped
    
    public static final double WHEEL_TOP_ABSOLUTE_SPEED = 12; //the highest speed that our wheels can move
    
    public static final double FAST_MAX_CRAB_SPEED = 7;
    public static final double FAST_MAX_SPIN_SPEED =7;
    
    public static final double SLOW_MAX_CRAB_SPEED = 2;
    public static final double SLOW_MAX_SPIN_SPEED = 3;
    
    public static final double crabZeroZone = .1;
    
    public static final Vector2D ROBOT_CIRCLE_CENTER = new Vector2D(true, 0, -11.67);
    
    public static final double NORTH = 0;
    public static final double SOUTH = 180;
    public static final double WEST = 90;
    public static final double EAST = 270;
    
    public static final double ACCELERATION_CONSTANT = 20;
    
    // for OTSGetTote
    public static final double PARALLEL_TOTE_SHIFT_MAGNITUDE = 4; // distance (from corner) along tote's short side where getter should hit 
    public static final Vector2D LEFT_GRABBER_LOCATION = new Vector2D(true, 2, 3); // TODO: set these values. Locations relative to OTS
    public static final Vector2D RIGHT_GRABBER_LOCATION = new Vector2D(true, -2, 3);
	
//Getters
	
	//Motors
	
	public static SpeedController246 leftGetterMotor;
	public static SpeedController246 rightGetterMotor;
	
	//Sensors
	
	public static AnalogIn leftRangeFinder;
	public static AnalogIn rightRangeFinder;
	
	//constants
	
	public static double LEFT_RANGE_FINDER_IN;
	public static double RIGHT_RANGE_FINDER_IN;
	public static double LEFT_RANGE_FINDER_OUT;
	public static double RIGHT_RANGE_FINDER_OUT;
	public static double LEFT_RANGE_FINDER_PID_SETPOINT;
	public static double RIGHT_RANGE_FINDER_PID_SETPOINT;
	
	public static final double GETTER_POTS_TOLERANCE = 3;
	
	public static double GETTER_ADJUSTING_kP = .002;
	public static double GETTER_ADJUSTING_kI = 0;
	public static double GETTER_ADJUSTING_kD = 0;
	
//Forklift
	
	//Motors
	
	public static SpeedController246 liftMotor;
	
	//Sensors
	
	public static AnalogPot liftPot;
	
	//Constants
	
	public static final double LIFT_kP = .5;
	public static final double LIFT_kI = 0;
	public static final double LIFT_kD = .15;
	public static final double LIFT_TOLERANCE = .25;
	
	public static final double LIFT_MAX_HEIGHT = 38.31;
	public static final double LIFT_MIN_HEIGHT = 0;
	
	public static final double LIFT_MANUAL_SPEED = 6;
	
	public static final double TOTE_HEIGHT = 12;
	 
	public enum LiftSetpoints {
		GROUND(2.2), SCORING_PLATFORM(6.61), STEP(10.61), BETWEEN_TOTES(10), ABOVE_1_TOTE(18.61), RECEIVE_ARM_TOTE(18.61), FIX_ARM_TOTE(23), ABOVE_CAN(38);
		
		private double value;
		
		private LiftSetpoints(double value)
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
	
	public static SpeedController246 pusherMotor;
	
	//Sensors
	
	public static AnalogPot pusherPot;
	
	//Constants
	
	public static final double PUSHER_kP = 6;
	public static final double PUSHER_kI = 0;
	public static final double PUSHER_kD = 0;
	public static final double PUSHER_kF = .25;
	
	public static final double PUSHER_IN = -.5;
	public static final double PUSHER_OUT = 3;
	
//Arm
	
	//Motors
	
	public static SpeedController246 armShoulderMotor;
	public static SpeedController246 armElbowMotor;
	public static SpeedController246 armWristMotor;
	
	//Sensors
	
	public static AnalogPot armShoulderPot;
	public static AnalogPot armElbowPot;
	public static AnalogPot armWristPot;
	
	//Constants
	
	public static final double ARM_SHOULDER_kP = 0.120;
	public static final double ARM_SHOULDER_kI = 0.000;
	public static final double ARM_SHOULDER_kD = 0.450;
	
	public static final double ARM_ELBOW_kP = 0.120;
	public static final double ARM_ELBOW_kI = 0.000;
	public static final double ARM_ELBOW_kD = 0.400;
	
	public static final double ARM_WRIST_kP = 0.100;
	public static final double ARM_WRIST_kI = 0.000;
	public static final double ARM_WRIST_kD = 0.400;
	
	public static final double ARM_X_MANUAL_SPEED = 5;
	public static final double ARM_Y_MANUAL_SPEED = 5;
	public static final double ARM_SHOULDER_MANUAL_SPEED = 15;
	public static final double ARM_ELBOW_MANUAL_SPEED = 15;
	public static final double ARM_WRIST_MANUAL_SPEED = 15;
	
	public static final double ARM_SEGMENT_1_LENGTH = 24;
	public static final double ARM_SEGMENT_2_LENGTH = 24;
	
	public static final double ARM_SHOULDER_MIN = -28;
	public static final double ARM_SHOULDER_MAX = 125;
	
	public static final double ARM_SHOULDER_HEIGHT = 36;
	
	public static final double ARM_GROUND_TOLERANCE = .5;
	public static final double ARM_CEILING_TOLERANCE = -1;
	public static final double ARM_CEILING_TOLERANCE_WHILE_TRANSITIONING = 2;
	public static final double ARM_LIFT_TOLERANCE = 1;
	
	public static final double ARM_WIDTH = 6;
	public static final double GRABBER_WIDTH = 2;
	public static final double GRABBER_LENGTH = 24;
	public static final double GRABBER_SMALL_LENGTH = 12;
	
	public static final double ARM_LIFT_LOCATION = -21.5;
	public static final double ARM_LIFT_HEIGHT = 54;
	
	public static final double ARM_TURNBUCKLE_SHOULDER_ELBOW_MAX = 159;
	public static final double ARM_TURNBUCKLE_SHOULDER_ELBOW_MIN = -152;
	public static final double ARM_TURNBUCKLE_SHOULDER_WRIST_MAX = 160;
	public static final double ARM_TURNBUCKLE_SHOULDER_WRIST_MIN = -183;//way_too_generous
	public static final double ARM_TURNBUCKLE_ELBOW_WRIST_MAX = 136;//good
	public static final double ARM_TURNBUCKLE_ELBOW_WRIST_MIN = -126;//too_generous
	
	public static final double ARM_MAX_SPEED = .5;
	public static final double ARM_MAX_TRANSITION_SPEED = .75;
	
	public static final double ARM_MECHANICAL_MAX_SPEED = 60; //in degrees per second
	public static final double ARM_SYNCHRONIZED_CORRECTION_PERIOD = .25;
	
	public enum ArmSetpoints {
		
		GROUND_UP_HIGH(65.3,167.3,86.7),
		GROUND_UP_LOW(109.8,175.3,84.3),
		GROUND_FALL_PREP(48.0,113.6,174.1),
		GROUND_FALL(57.8,128.2,174.2),
		STEP(103.6,102.1,83.7),
		TOP_OF_STACK(-21.0,43.5,86.0),
		STORAGE(4.3,155.1,86.1),
		STORAGE_NO_CAN(5.7, 154.9, 177.0),
		CURLED_TAIL(0,0,0),
		ON_LIFT(0,0,0),
		SCORPION_HOLD(14.2,-91.0,-90),
		
		AUTON_POSITION_1(67.0, 85.0, 169.6),
		AUTON_POSITION_2(23,170,175),
		AUTON_POSITION_3(-19.9, -81.0, -163.2),
		
		TRANSITION_1(51.3, 158.4, 90),
		TRANSITION_2(98.2, 73.0, 0),
		TRANSITION_3a(99.7, 29.1, -55.3),
		TRANSITION_3b(99.7, 29.1, -90.0),
		TRANSITION_4(38.8, -50.0, -90.0),
		TRANSITION_5(14.2, -50.0, -90);
		
		private double shoulder;
		private double elbow;
		private double wrist;
		
		private ArmSetpoints(double x, double y, double wrist)
		{
			
			this.shoulder = x;
			this.elbow = y;
			this.wrist = wrist;
		}
		
		
		public double getShoulder()
		{
			return shoulder;
		}
		public double getElbow()
		{
			return elbow;
		}
		public double getWrist()
		{
			return wrist;
		}
	}
	
	public static ArmSetpoints[] ARM_TRANSITION_ARRAY_TO_FRONT = {
		ArmSetpoints.TRANSITION_1,
		ArmSetpoints.TRANSITION_2,
		ArmSetpoints.TRANSITION_3a,
		ArmSetpoints.TRANSITION_4,
		ArmSetpoints.TRANSITION_5
	};
	public static ArmSetpoints[] ARM_TRANSITION_ARRAY_TO_BACK = {
		ArmSetpoints.TRANSITION_1,
		ArmSetpoints.TRANSITION_2,
		ArmSetpoints.TRANSITION_3b,
		ArmSetpoints.TRANSITION_4,
		ArmSetpoints.TRANSITION_5
	};
	
//Grabber
	
	//Motors
	
	public static Victor246 grabberMotor;
	
	//Sensors
	
	public static Encoder grabberEncoder;
	
	//Constants
	
	public static final double GRABBER_kP = 25;
	public static final double GRABBER_kI = 0;
	public static final double GRABBER_kD = 15;
	public static final double GRABBER_kF = 0;
	
	public static final double GRABBER_CLOSED = 94;
	public static final double GRABBER_OPEN = -155;
	public static final double GRABBER_OPEN_WIDE = .35;
	public static final double GRABBER_CURRENT_LIMIT = 11;
	public static final double GRABBER_CURRENT_OFFSET = 40;
	
	public static final double GRABBER_UNSAFE_MIN = -258;
	public static final double GRABBER_UNSAFE_MAX = 128;
	
//OTS
	
	//Motors
	
	public static SpeedController246 otsMotor;
	
	//Sensors
	
	public static AnalogIn otsRPM;
	
	//Constants
	
	public static final double OTS_kP = 1;
	public static final double OTS_kI = 1;
	public static final double OTS_kD = 1;
	public static final double OTS_kF = 1;
	
	public static final double OTS_TARGET_RPM = 300;

	public static final double OTS_PARTIAL_TOTE_DISTANCE = 12;
	public static final double OTS_FULL_TOTE_DISTANCE = 4;
	
	
	static void init()
	{
		pdp = new PowerDistributionPanel();
		
	//Drivetrain
		
		//Motors
		
		if(Robot.trojan)
		{
			backWheelMotor = new Talon246(0, 12, pdp);
			LiveWindow.addActuator("Drivetrain", "backWheelMotor", (LiveWindowSendable) backWheelMotor);
			leftWheelMotor = new Talon246(2, 13, pdp);
			LiveWindow.addActuator("Drivetrain", "leftWheelMotor", (LiveWindowSendable) leftWheelMotor);
			rightWheelMotor = new Talon246(4, 14, pdp);
			LiveWindow.addActuator("Drivetrain", "rightWheelMotor", (LiveWindowSendable) rightWheelMotor);
			
			backModuleMotor = new Victor246(1, 15, pdp);
			LiveWindow.addActuator("Drivetrain", "backModuleMotor", (LiveWindowSendable) backModuleMotor);
			leftModuleMotor = new Victor246(3, 1, pdp);
			LiveWindow.addActuator("Drivetrain", "leftModuleMotor", (LiveWindowSendable) leftModuleMotor);
			rightModuleMotor = new Victor246(15, 0, pdp);
			LiveWindow.addActuator("Drivetrain", "rightModuleMotor", (LiveWindowSendable) rightModuleMotor);
		}
		else
		{
			backWheelMotor = new VictorSP246(0, 12, pdp);
			LiveWindow.addActuator("Drivetrain", "backWheelMotor", (LiveWindowSendable) backWheelMotor);
			leftWheelMotor = new VictorSP246(2, 13, pdp);
			LiveWindow.addActuator("Drivetrain", "leftWheelMotor", (LiveWindowSendable) leftWheelMotor);
			rightWheelMotor = new VictorSP246(4, 14, pdp);
			LiveWindow.addActuator("Drivetrain", "rightWheelMotor", (LiveWindowSendable) rightWheelMotor);
			
			backModuleMotor = new Victor246(1, 15, pdp);
			LiveWindow.addActuator("Drivetrain", "backModuleMotor", (LiveWindowSendable) backModuleMotor);
			leftModuleMotor = new Victor246(3, 1, pdp);
			LiveWindow.addActuator("Drivetrain", "leftModuleMotor", (LiveWindowSendable) leftModuleMotor);
			rightModuleMotor = new Victor246(5, 0, pdp);
			LiveWindow.addActuator("Drivetrain", "rightModuleMotor", (LiveWindowSendable) rightModuleMotor);
		}
		
		//Sensors
		
		backWheelEncoder = new Encoder(0,1);
	    backWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    backWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "backWheelEncoder", backWheelEncoder);
	    Diagnostics.addEncoder(backWheelEncoder, "Back Wheel Speed", backWheelMotor);
		leftWheelEncoder = new Encoder(2,3);
	    leftWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    leftWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "leftWheelEncoder", leftWheelEncoder);
	    Diagnostics.addEncoder(leftWheelEncoder, "Left Wheel Speed", leftWheelMotor);
		rightWheelEncoder = new Encoder(4,5);
	    rightWheelEncoder.setDistancePerPulse(WHEEL_ENCODER_DISTANCE_PER_TICK);
	    rightWheelEncoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate); // have encoder measure rate, not distance
	    LiveWindow.addSensor("Drivetrain", "rightWheelEncoder", rightWheelEncoder);
	    Diagnostics.addEncoder(rightWheelEncoder, "Right Wheel Speed", rightWheelMotor);
		
	    if(Robot.trojan)
	    {
		    backModulePot = new AnalogPot(0, 1800, -900, true);
		    LiveWindow.addSensor("Drivetrain", "backModulePot", backModulePot);
		    Diagnostics.addAnalogPot(backModulePot, "Back Swerve Steering", backModuleMotor);
		    leftModulePot = new AnalogPot(1, 1800, -900, true);
		    LiveWindow.addSensor("Drivetrain", "leftModulePot", leftModulePot);
		    Diagnostics.addAnalogPot(leftModulePot, "Left Swerve Steering", leftModuleMotor);
		    rightModulePot = new AnalogPot(2, 1800, -900, true);
		    LiveWindow.addSensor("Drivetrain", "rightModulePot", rightModulePot);
		    Diagnostics.addAnalogPot(rightModulePot, "Right Swerve Steering", rightModuleMotor);
	    }
	    else
	    {
	    	backModulePot = new AnalogPot(0, 1800, -900, true);
		    LiveWindow.addSensor("Drivetrain", "backModulePot", backModulePot);
		    Diagnostics.addAnalogPot(backModulePot, "Back Swerve Steering", backModuleMotor);
		    leftModulePot = new AnalogPot(1, 1800, -900, true);
		    LiveWindow.addSensor("Drivetrain", "leftModulePot", leftModulePot);
		    Diagnostics.addAnalogPot(leftModulePot, "Left Swerve Steering", leftModuleMotor);
		    rightModulePot = new AnalogPot(2, 1800, -900, true);
		    LiveWindow.addSensor("Drivetrain", "rightModulePot", rightModulePot);
		    Diagnostics.addAnalogPot(rightModulePot, "Right Swerve Steering", rightModuleMotor);
	    }
	    
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
        
        //constants
        
        if(Robot.trojan)
        {
	        WHEEL_kP = 0.15;
	    	WHEEL_kI = 0;
	    	WHEEL_kD = 0;
	    	WHEEL_kF = 0.075;
	    	
	    	MODULE_kP = .05;
	    	MODULE_kI = 0;
	    	MODULE_kD = .08;
	    	MODULE_kF = 0;
        }
        else
        {
        	WHEEL_kP = 0.15;
	    	WHEEL_kI = 0;
	    	WHEEL_kD = 0;
	    	WHEEL_kF = 0.075;
	    	
	    	MODULE_kP = .055;
	    	MODULE_kI = 0;
	    	MODULE_kD = .050;
	    	MODULE_kF = 0;
        }
		
  //Getters
	
  		//Motors
  		
  		leftGetterMotor = new Victor246(6, 0, pdp);
  		LiveWindow.addActuator("Getters", "leftGetterMotor", (LiveWindowSendable) leftGetterMotor);
  		rightGetterMotor = new Victor246(7, 0, pdp);
  		LiveWindow.addActuator("Getters", "rightGetterMotor", (LiveWindowSendable) rightGetterMotor);
  		
  		//Sensors
  		
  		if(Robot.trojan)
  		{
  			leftRangeFinder = new AnalogIn(3, true);
  			LiveWindow.addSensor("Getters", "leftRangeFinder", leftRangeFinder);
  			Diagnostics.addAnalogIn(leftRangeFinder, "Left Tote Sensor");
  			rightRangeFinder = new AnalogIn(4, true);
  			LiveWindow.addSensor("Getters", "rightRangeFinder", rightRangeFinder);
  			Diagnostics.addAnalogIn(rightRangeFinder, "Right Tote Sensor");
  			
  		}
  		else
  		{
  			leftRangeFinder = new AnalogIn(2, false);
  			LiveWindow.addSensor("Getters", "leftRangeFinder", leftRangeFinder);
  			Diagnostics.addAnalogIn(leftRangeFinder, "Left Tote Sensor");
  			rightRangeFinder = new AnalogIn(3, false);
  			LiveWindow.addSensor("Getters", "rightRangeFinder", rightRangeFinder);
  			Diagnostics.addAnalogIn(rightRangeFinder, "Right Tote Sensor");
  		}
  		
  		//Constants
  		
  		if(Robot.trojan)
  		{
  			GETTER_ADJUSTING_kP = 2;
  			GETTER_ADJUSTING_kI = 0;
  			GETTER_ADJUSTING_kD = 0;
  			LEFT_RANGE_FINDER_IN = 0.906;
  			RIGHT_RANGE_FINDER_IN = 0.840;
  			LEFT_RANGE_FINDER_OUT = 0.862;
  			RIGHT_RANGE_FINDER_OUT = 0.807;
  			LEFT_RANGE_FINDER_PID_SETPOINT = .95;
  			RIGHT_RANGE_FINDER_PID_SETPOINT = .93;
  		}
  		else
  		{
  			GETTER_ADJUSTING_kP = .002;
  			GETTER_ADJUSTING_kI = 0;
  			GETTER_ADJUSTING_kD = 0;
  			LEFT_RANGE_FINDER_IN = 565;
  			RIGHT_RANGE_FINDER_IN = 575;
  			LEFT_RANGE_FINDER_OUT = 500;
  			RIGHT_RANGE_FINDER_OUT = 530;
  			LEFT_RANGE_FINDER_PID_SETPOINT = 625;
  			RIGHT_RANGE_FINDER_PID_SETPOINT = 635;
  		}
  		
	//Forklift
		
		//Motors
		
		liftMotor = new Victor246(8, 0, pdp);
		LiveWindow.addActuator("Forklift", "liftMotor", (LiveWindowSendable) liftMotor);
		
		//Sensors
		
		if(!Robot.trojan)
		{
			liftPot = new AnalogPot(3, 54.8, -9.79, true); //TODO: Get this constant
			LiveWindow.addSensor("Forklift", "liftPot", liftPot);
			Diagnostics.addAnalogPot(liftPot, "Forklift", liftMotor);
		}
		 
		
	//Pusher
		
		//Motors
		
		pusherMotor = new VictorSP246(9, 0, pdp);
		LiveWindow.addActuator("Pusher", "pusherMotor", (LiveWindowSendable) pusherMotor);
		
		//Sensors
		
		if(!Robot.trojan)
		{
			pusherPot = new AnalogPot(4, 5.15, -2.025, true); //TODO: Get this constant
			LiveWindow.addSensor("Pusher", "pusherEncoder", pusherPot);
			Diagnostics.addAnalogPot(pusherPot, "Pusher", pusherMotor);
		}
		
	//Arm
		
		//Motors
		
		armShoulderMotor = new VictorSP246(10, 0, pdp);
		LiveWindow.addActuator("Arm", "armShoulderMotor", (LiveWindowSendable) armShoulderMotor);
		armElbowMotor = new VictorSP246(11, 0, pdp);
		LiveWindow.addActuator("Arm", "armElbowMotor", (LiveWindowSendable) armElbowMotor);
		armWristMotor = new VictorSP246(12, 0, pdp);
		LiveWindow.addActuator("Arm", "armWristMotor", (LiveWindowSendable) armWristMotor);
		
		//Sensors
		
		armShoulderPot = new AnalogPot(5, 391.83673469387755102040816326531, -204.2, true);
		LiveWindow.addSensor("Arm", "armShoulderPot", armShoulderPot);
		Diagnostics.addAnalogPot(armShoulderPot, "Shoulder", armShoulderMotor);
		armElbowPot = new AnalogPot(6, 391.83673469387755102040816326531, -173.4, true);
		LiveWindow.addSensor("Arm", "armElbowPot", armElbowPot);
		Diagnostics.addAnalogPot(armElbowPot, "Elbow", armElbowMotor);
		armWristPot = new AnalogPot(7, 391.83673469387755102040816326531, -188.8, true);
		LiveWindow.addSensor("Arm", "armWristPot", armWristPot);
		Diagnostics.addAnalogPot(armWristPot, "Wrist", armWristMotor);
		
	//Grabber
		
		//Pneumatics
			
		grabberMotor = new Victor246(13, 8, pdp);
		grabberMotor.setSafetyEnabled(true);
		LiveWindow.addActuator("Grabber", "grabberMotor", grabberMotor);
		
		//Sensors
		
		grabberEncoder = new Encoder(6, 7);
		LiveWindow.addSensor("Grabber", "grabberEncoder", grabberEncoder);
		Diagnostics.addEncoder(grabberEncoder, "Grabber", grabberMotor);
		
	//OTS
		
		//Motors
		
		otsMotor = new VictorSP246(14, 0 ,pdp);
		LiveWindow.addActuator("OTS", "otsMotor", (LiveWindowSendable) otsMotor);
		
		//Sensors
		
		otsRPM = new AnalogIn(0, false);
		LiveWindow.addSensor("OTS", "otsRPM", otsRPM);		
	}
}
