
package org.usfirst.frc.team246.robot;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.usfirst.frc.team246.robot.RobotMap.LiftSetpoints;
import org.usfirst.frc.team246.robot.commands.Auto20Points;
import org.usfirst.frc.team246.robot.commands.AutoGetAllTotes;
import org.usfirst.frc.team246.robot.commands.AutoMineLandfill;
import org.usfirst.frc.team246.robot.commands.AutoTest;
import org.usfirst.frc.team246.robot.commands.ZeroNavX;
import org.usfirst.frc.team246.robot.overclockedLibraries.AlertMessage;
import org.usfirst.frc.team246.robot.overclockedLibraries.AlertMessage.Severity;
import org.usfirst.frc.team246.robot.overclockedLibraries.AnalogIn;
import org.usfirst.frc.team246.robot.overclockedLibraries.Diagnostics;
import org.usfirst.frc.team246.robot.overclockedLibraries.SwerveModule;
import org.usfirst.frc.team246.robot.overclockedLibraries.UdpAlertService;
import org.usfirst.frc.team246.robot.overclockedLibraries.Victor246;
import org.usfirst.frc.team246.robot.subsystems.Arm;
import org.usfirst.frc.team246.robot.subsystems.Drivetrain;
import org.usfirst.frc.team246.robot.subsystems.Forklift;
import org.usfirst.frc.team246.robot.subsystems.Getters;
import org.usfirst.frc.team246.robot.subsystems.Grabber;
import org.usfirst.frc.team246.robot.subsystems.Pusher;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	
	public static boolean requestCorner = false;
	
	public static double toteCornerX = 65536;
	public static double toteCornerY = 65536;
	public static int toteAngle = 255;
	public static double toteDistance = 65536;
	
	public static boolean hasTote = false;
	
	public static boolean test1 = false;
	public static boolean test2 = false;
	public static boolean test3 = false;
	public static boolean gyroDisabled = false;
	public static boolean gasMode = false;
	public static boolean trojan = false;
	public static boolean scorpionModeTest = false; //Joystick 3 Button 1 Error is true
	public static boolean liftGasMode = false;
	
	public static Drivetrain drivetrain;
	public static Getters getters;
	public static Forklift forklift;
	public static Pusher pusher;
	public static Arm arm;
	public static Grabber grabber;
	
	public static AnalogIn[] BBBAnalogs;
	
	public static boolean autonRun = false;
	
	Command auton;
	
	public static boolean teleopZeroedNavX = false;
	
	public static SendableChooser autonRadioBoxes;
	
	public enum RobotMode
	{
		DISABLED, AUTONOMOUS, TELEOP, TEST;
	}
	
	public static RobotMode robotMode = RobotMode.DISABLED;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	BBBAnalogs = new AnalogIn[6];
    	
    	Diagnostics.initialize();
        RobotMap.init();
        
        //RobotMap.grabberEncoder.reset();
        
        (new Thread(new AnalogInputCollector())).start();
        
        drivetrain = new Drivetrain();
        getters = new Getters();
        forklift = new Forklift();
        pusher = new Pusher();
        arm = new Arm();
        grabber = new Grabber();
        
        oi = new OI();
        
        auton = new ZeroNavX(0);
        
        autonRadioBoxes = new SendableChooser();
        autonRadioBoxes.addDefault("Do Nothing Forwards", new ZeroNavX(0));
        autonRadioBoxes.addObject("Do Nothing Left", new ZeroNavX(90));
        autonRadioBoxes.addObject("Do Nothing Backwards", new ZeroNavX(180));
        autonRadioBoxes.addObject("Do Nothing Right", new ZeroNavX(-90));
        autonRadioBoxes.addObject("Test Autonomous", new AutoTest());
        autonRadioBoxes.addObject("Mine Landfill", new AutoMineLandfill());
        autonRadioBoxes.addObject("Get all totes", new AutoGetAllTotes());
        autonRadioBoxes.addObject("20 Points", new Auto20Points());
        SmartDashboard.putData("Auto Mode Chooser", autonRadioBoxes);
        
        
        if(test1)
        {
            SmartDashboard.putNumber("speedSetpoint", 0);
            SmartDashboard.putNumber("angleSetpoint", 0);
            SmartDashboard.putBoolean("speedOn", true);
            SmartDashboard.putBoolean("angleOn", true);
            SmartDashboard.putBoolean("unwind", false);
            SmartDashboard.putNumber("speed", 0);
            SmartDashboard.putNumber("angle", 0);
            SmartDashboard.putNumber("K_DELTA", RobotMap.K_MODULE_ANGLE_DELTA);
            SmartDashboard.putNumber("K_TWIST", RobotMap.K_MODULE_ANGLE_TWIST);
            SmartDashboard.putNumber("K_REVERSE", RobotMap.K_MODULE_ANGLE_REVERSE);
        }
        if(test2)
        {
            SmartDashboard.putNumber("speedP", RobotMap.WHEEL_kP);
            SmartDashboard.putNumber("speedI", RobotMap.WHEEL_kI);
            SmartDashboard.putNumber("speedD", RobotMap.WHEEL_kD);
            SmartDashboard.putNumber("speedF", RobotMap.WHEEL_kF);
            SmartDashboard.putNumber("backModuleSpeed", 0);
            SmartDashboard.putNumber("leftModuleSpeed", 0);
            SmartDashboard.putNumber("rightModuleSpeed", 0);
            SmartDashboard.putNumber("backModuleSpeedSetpoint", 0);
            SmartDashboard.putNumber("leftModuleSpeedSetpoint", 0);
            SmartDashboard.putNumber("rightModuleSpeedSetpoint", 0);
            SmartDashboard.putNumber("spinRate", 0);
        }
        if(test3)
        {
        	SmartDashboard.putNumber("absoluteTwistP", 0);
        	SmartDashboard.putNumber("absoluteTwistI", 0);
        	SmartDashboard.putNumber("absoluteTwistD", 0);
        }
        
        SmartDashboard.putBoolean("motorKilled", false);
        SmartDashboard.putBoolean("field-centric", true);
        SmartDashboard.putNumber("ARM_SHOULDER_MANUAL_SPEED", 5);
        SmartDashboard.putNumber("ARM_ELBOW_MANUAL_SPEED", 5);
        SmartDashboard.putNumber("ARM_WRIST_MANUAL_SPEED", 5);
        SmartDashboard.putNumber("startHeading", 0);
        
        /*
        SmartDashboard.putNumber("crabP", 0);
        SmartDashboard.putNumber("crabI", 0);
        SmartDashboard.putNumber("crabD", 0);
        */
        
        SmartDashboard.putData(Scheduler.getInstance());
    }
    
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
    	robotMode = RobotMode.DISABLED;
    	drivetrain.PIDOn(false);
    }
    
    boolean wasConnected = false;
	public void disabledPeriodic() {
		
		allPeriodic();
		
		if(DriverStation.getInstance().isDSAttached() && !wasConnected)
		{
			System.out.print("Connected to driver station");
			UdpAlertService.initialize("MSilver-PC.local", 5801);
			wasConnected = true;
		}

		Scheduler.getInstance().run();
	}
	
    public void autonomousInit() {
    	if(!autonRun)
    	{
    		autonRun = true;
	    	robotMode = RobotMode.AUTONOMOUS;
	    	RobotMap.grabberEncoder.reset();
	    	RobotMap.grabberMotor.set(0);
	    	auton = (Command) autonRadioBoxes.getSelected();
	    	auton.start();
    	}
    	else UdpAlertService.sendAlert(new AlertMessage("THE FIELD BROKE OUR AUTONOMOUS!").playSound("uhoh.wav").severity(Severity.FATAL));
    	/* CHANGE NAVX HEADING IF PUTTING IN AUTONOMOUS
    	drivetrain.PIDOn(true);
    	new DeadReckoningDrive(new Vector2D(false, 1, -90)) {
			
    		@Override
    		protected void initialize() {
    			super.initialize();
    			setTimeout(6.5);
    		}
			@Override
			protected boolean isFinished() {
				return isTimedOut();
			}
		}.start();
		*/
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	allPeriodic();
    	
    	SmartDashboard.putNumber("backWheelEncoderDistance", drivetrain.backModule.getWheelDistance());
    	SmartDashboard.putNumber("leftWheelEncoderDistance", drivetrain.leftModule.getWheelDistance());
    	SmartDashboard.putNumber("rightWheelEncoderDistance", drivetrain.rightModule.getWheelDistance());
    	SmartDashboard.putNumber("odometryDistance", drivetrain.odometry.getFieldCentricLinearDisplacement().getMagnitude());
    	SmartDashboard.putNumber("odometryAngle", drivetrain.odometry.getFieldCentricLinearDisplacement().getAngle());
    	
    	//drivetrain.absoluteCrabPID.setPID(SmartDashboard.getNumber("crabP"), SmartDashboard.getNumber("crabI"), SmartDashboard.getNumber("crabD"));
    	
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	robotMode = RobotMode.TELEOP;
    	auton.cancel();
    	RobotMap.grabberMotor.set(0);
    	Robot.drivetrain.setMaxSpeed(RobotMap.SLOW_MAX_CRAB_SPEED, RobotMap.SLOW_MAX_SPIN_SPEED);
    	drivetrain.PIDOn(true);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	allPeriodic();
    	
        if(test2)
        {
            SmartDashboard.putNumber("backModuleSpeed", drivetrain.backModule.getWheelSpeed());
            SmartDashboard.putNumber("leftModuleSpeed", drivetrain.leftModule.getWheelSpeed());
            SmartDashboard.putNumber("rightModuleSpeed", drivetrain.rightModule.getWheelSpeed());
            SmartDashboard.putNumber("backModuleSpeedSetpoint", drivetrain.backModule.getSpeedSetpoint());
            SmartDashboard.putNumber("leftModuleSpeedSetpoint", drivetrain.leftModule.getSpeedSetpoint());
            SmartDashboard.putNumber("rightModuleSpeedSetpoint", drivetrain.rightModule.getSpeedSetpoint());
        }
        if(test3)
        {
        	
        }
        if(test1)
        {
            SwerveModule mod = drivetrain.backModule;
            
            if(SmartDashboard.getBoolean("unwind", false))
            {
               mod.unwind();
            }
            else
            {
                if(SmartDashboard.getBoolean("speedOn", false))
                {
                    mod.setWheelSpeed(SmartDashboard.getNumber("speedSetpoint", 0));
                }
                else
                {
                    mod.speedPIDOn(false);
                }

                if(SmartDashboard.getBoolean("angleOn", false))
                {
                    mod.setAngle(SmartDashboard.getNumber("angleSetpoint", 0));
                }
                else
                {
                    mod.anglePIDOn(false);
                }
            }
            
            SmartDashboard.putNumber("speed", mod.getWheelSpeed());
            SmartDashboard.putNumber("angle", mod.getModuleAngle());
        }
        else
        {
            //the drivetrain will automatically unwind each of the 4 modules when the robot is not moving
            if(drivetrain.isMoving())
            {
                drivetrain.stopUnwinding();
            }
            else
            {
            	if(!drivetrain.swerves[0].unwinding) UdpAlertService.sendAlert(new AlertMessage("Unwinding"));
                drivetrain.unwind();
            }
            
            if(!teleopZeroedNavX && DriverStation.getInstance().getMatchTime() > 130)
            {
            	if(oi.driver.getUp().get()) RobotMap.navX.zeroYaw(0);
            	if(oi.driver.getLeft().get()) RobotMap.navX.zeroYaw(-90);
            	if(oi.driver.getDown().get()) RobotMap.navX.zeroYaw(180);
            	if(oi.driver.getRight().get()) RobotMap.navX.zeroYaw(90);
            }
            if(!trojan)
            {
	            liftWasDown = RobotMap.liftPot.get() < LiftSetpoints.GROUND.getValue();
            }
            
            SmartDashboard.putBoolean("hasTote?", getters.hasTote());
            
            Scheduler.getInstance().run();
        }
    }
    
    boolean liftWasDown = false;
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	robotMode = RobotMode.TEST;
    	allPeriodic();
    	
        LiveWindow.run();
    }
    
    public void allPeriodic()
    {
    	
        //This is a safety to prevent any of the modules from rotating too far and overtwisting the wires. 
        //If any module angle surpasses RobotMap.UNSAFE_MODULE_ANGLE, the motor controlling it will be automatically shut off
    	for(int i=0; i<drivetrain.swerves.length; i++)
    	{
    		if(Math.abs(drivetrain.swerves[i].getModuleAngle()) > RobotMap.UNSAFE_MODULE_ANGLE)
            {
                if(!SmartDashboard.getBoolean("motorKilled")) UdpAlertService.sendAlert(new AlertMessage("Steering Motor Killed").playSound("malfunction.wav"));
                ((Victor246)drivetrain.swerves[i].moduleMotor).overridingSet(0);
                SmartDashboard.putBoolean("motorKilled", true);
            }
    	}

        //allows the operator to manually return control of all modules to their respective PIDcontrollers
        if(!SmartDashboard.getBoolean("motorKilled", true))
        {
            for(int i=0; i<drivetrain.swerves.length; i++)
            {
            	((Victor246)drivetrain.swerves[i].moduleMotor).returnControl();
            }
            ((Victor246)RobotMap.grabberMotor).returnControl();
        }
        
        SmartDashboard.putNumber("Heading", RobotMap.navX.getYaw());
        
        if(DriverStation.getInstance().isBrownedOut()) UdpAlertService.sendAlert(new AlertMessage("Brownout").playSound("low_power.wav"));
        
        //gyroDisabled = !SmartDashboard.getBoolean("field-centric", true);
        
        hasTote = getters.hasTote();
        
        SmartDashboard.putBoolean("haveTote", hasTote);
    }
    
    public class AnalogInputCollector implements Runnable {
		
    	DatagramSocket AIs;
    	
    	public void run() {
    		try{
    			AIs = new DatagramSocket(5800);
				//AIs.setSoTimeout(10000);
				AIs.setReuseAddress(true);
			} catch (SocketException e) {
				e.printStackTrace();
			}
    		System.out.println("AIs initialized");
			while(true)
			{
//				System.out.println("loopin'");
				byte[] data = new byte[1000];
		        DatagramPacket packet = new DatagramPacket(data, data.length);
		        try {
					AIs.receive(packet);
				} catch (Exception e) {
					e.printStackTrace();
				}
//		        System.out.println("0: " + Integer.toHexString(data[0]&0xff));
//		        System.out.println("1: " + Integer.toHexString(data[1]&0xff));
//		        System.out.println("2: " + Integer.toHexString(data[2]&0xff));
//		        System.out.println("3: " + Integer.toHexString(data[3]&0xff));
		        for(int i = 1; i <= data[0]*3; i += 3)
		        {
		        	try
		        	{
		        		BBBAnalogs[data[i]].updateVal(getShort(data, i + 1));
		        	}
		        	catch(NullPointerException e) {}
		        }
//		        System.out.println("Grabber Pot value" + getShort(data, 2));
//		        System.out.println("Grabber Pot value hex" + Integer.toHexString(getShort(data, 2)&0xffff));
		        //BBBAnalogs.get(1).updateVal(getShort(data, 2));
			}
		}
	}

    public static int getShort(byte[] input, int offset)
    {
    	ByteBuffer buff = ByteBuffer.wrap(input);
    	buff.order(ByteOrder.LITTLE_ENDIAN);
    	return buff.getShort(offset);
    }
    
    public static int littleEndianConcatenation(byte byte1, byte byte2)
	{
    	
    	
    	return 0;
    	
    	/*
		byte[] arr1 = {byte1};
		BitSet bits1 = BitSet.valueOf(arr1);
		byte[] arr2 = {byte2};
		BitSet bits2 = BitSet.valueOf(arr2);
		BitSet resultBits = bits1;
		for(int i = 0; i < bits2.size(); i++)
		{
			bits1.set(bits1.size(), bits2.get(i));
		}
		int result = 0;
		for(int i = 0; i < resultBits.size(); i++)
		{
			if(resultBits.get(i))
			{
				result += Math.pow(2, i);
			}
		}
		return result;
		*/
	}
}
