
package org.usfirst.frc.team246.robot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.BitSet;

import org.usfirst.frc.team246.nav6.IMUAdvanced;
import org.usfirst.frc.team246.robot.overclockedLibraries.SwerveModule;
import org.usfirst.frc.team246.robot.overclockedLibraries.Victor246;
import org.usfirst.frc.team246.robot.subsystems.Arm;
import org.usfirst.frc.team246.robot.subsystems.Drivetrain;
import org.usfirst.frc.team246.robot.subsystems.Forklift;
import org.usfirst.frc.team246.robot.subsystems.Getters;
import org.usfirst.frc.team246.robot.subsystems.Grabber;
import org.usfirst.frc.team246.robot.subsystems.OTS;
import org.usfirst.frc.team246.robot.subsystems.Pusher;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	
	public static boolean beagleBoneConnected = true;
	public static boolean requestCorner = false;
	
	public static double toteCornerX = 65536;
	public static double toteCornerY = 65536;
	public static int toteAngle = 255;
	public static double toteDistance = 65536;
	public static double otsRPM = 0;
	
	public static boolean test1 = false;
	public static boolean test2 = false;
	public static boolean test3 = true;
	public static boolean gyroDisabled = false;
	public static boolean gasMode = false;
	public static boolean trojan = true;
	
	public static Drivetrain drivetrain;
	public static Getters getters;
	public static Forklift forklift;
	public static Pusher pusher;
	public static Arm arm;
	public static Grabber grabber;
	public static OTS ots;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        RobotMap.init();
        
        drivetrain = new Drivetrain();
        getters = new Getters();
        forklift = new Forklift();
        pusher = new Pusher();
        arm = new Arm();
        grabber = new Grabber();
        ots = new OTS();
        
        oi = new OI();
        
        //(new Thread(new BeagleBoneCollector())).start();
        (new Thread(new ShutDownDetector())).start();
        
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
    }
    
    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
    	drivetrain.PIDOn(false);
    }
    
	public void disabledPeriodic() {
		
		allPeriodic();
        SmartDashboard.putBoolean("encoderZeroing", false);
        
        //Zeros the module angle encoders when the driver presses the button
        if(/*RobotMap.encoderZeroing.get()*/ SmartDashboard.getBoolean("encoderZeroing", false))
        {
            drivetrain.zeroAngles();
        }
        if(RobotMap.navX.isCalibrating()) System.out.println("Calibrating NavX");
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	RobotMap.navX.zeroYaw();
    	drivetrain.PIDOn(true);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	allPeriodic();
    	
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
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
        	drivetrain.absoluteTwistPID.setPID(SmartDashboard.getNumber("absoluteTwistP"), SmartDashboard.getNumber("absoluteTwistI"), SmartDashboard.getNumber("absoluteTwistD"));
        	
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
                drivetrain.unwind();
            }
            
            SmartDashboard.putBoolean("Forklift On Target", forklift.onTarget());
            
            Scheduler.getInstance().run();
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
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
                System.out.println("Stopping " + drivetrain.swerves[i].name);
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
        }
        
        SmartDashboard.putNumber("Heading", RobotMap.navX.getYaw());
        
        gyroDisabled = !SmartDashboard.getBoolean("field-centric", true);
        
        if(isEnabled())
        {
        	//Tote possession indicator
        	SmartDashboard.putBoolean("haveTote", toteDistance <= RobotMap.OTS_FULL_TOTE_DISTANCE);
        }
    }
    
    public class BeagleBoneCollector implements Runnable {
		
    	public DatagramSocket beagleBone;
    	
    	public void run() {
			try {
				beagleBone = new DatagramSocket(5800);
			} catch (SocketException e) {
				beagleBoneConnected = false;
				e.printStackTrace();
			}
			while(isEnabled())
			{
				byte[] data = new byte[1];
		        InetAddress address = null;
		        int port = 0;
		        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		        try {
					beagleBone.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
		     
		        data = new byte[9];
		        packet = new DatagramPacket(data, data.length);
		        try {
					beagleBone.receive(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
		        toteCornerX = littleEndianConcatenation(data[0], data [1])/25.4;
		        toteCornerY = littleEndianConcatenation(data[2], data[3])/25.4;
		        toteAngle = data[4];
		        toteDistance = littleEndianConcatenation(data[5], data[6])/25.4;
		        otsRPM = littleEndianConcatenation(data[7], data[8]);
			}
			beagleBone.close();
		}
    	
    	public int littleEndianConcatenation(byte byte1, byte byte2)
    	{
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
    	}
	}
    
    class ShutDownDetector implements Runnable
    {
    	double lastTime = 0;
    	
		@Override
		public void run() {
			double time = Timer.getFPGATimestamp();
			
			DriverStation ds = DriverStation.getInstance();
			
			if(time - lastTime > .01)
			{
				System.out.println(time - lastTime);
				System.out.println(ds.getBatteryVoltage());
				System.out.println(ds.isEnabled());
				System.out.println(ds.isSysActive());
				System.out.println(ds.isBrownedOut());
				System.out.println(ds.isDSAttached());
				
			}
			lastTime = time;
			Timer.delay(.001);
		}
    	
    }
}
