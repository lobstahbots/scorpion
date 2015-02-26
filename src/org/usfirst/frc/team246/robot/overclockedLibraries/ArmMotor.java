package org.usfirst.frc.team246.robot.overclockedLibraries;

import org.usfirst.frc.team246.robot.Robot;
import org.usfirst.frc.team246.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class ArmMotor extends VictorSP246{
	
	public enum ArmJoint
	{
		SHOULDER(RobotMap.armShoulderPot, Robot.arm.shoulder), ELBOW(RobotMap.armElbowPot, Robot.arm.elbow), WRIST(RobotMap.armWristPot, Robot.arm.wrist);
	
		public AnalogPot pot;
		public PIDController pid;
		
		private ArmJoint(AnalogPot pot, PIDController pid)
		{
			this.pot = pot;
			this.pid = pid;
		}
	}
	ArmJoint joint;
	double gas = 0;
	
	public ArmMotor(int channel, int pdpPort, PowerDistributionPanel pdp, ArmJoint joint)
	{
		super(channel, pdpPort, pdp);
		this.joint = joint;
	}
	
	public void addGas(double gas)
	{
		this.gas = gas;
	}
	
	double lastPosition = joint.pot.get();
	double lastGas = 0;
	@Override
	public void pidWrite(double output) {
		
		ArmJoint.SHOULDER.pid = Robot.arm.shoulder;
		ArmJoint.ELBOW.pid = Robot.arm.shoulder;
		ArmJoint.WRIST.pid = Robot.arm.shoulder;
		
		double nextS = 0;
		double nextE = 0;
		double nextW = 0;
		
		if(joint == ArmJoint.SHOULDER)
		{
			nextE = ArmJoint.ELBOW.pot.get();
			nextW = ArmJoint.WRIST.pot.get();
			
			nextS = joint.pot.get() + (joint.pot.get() - lastPosition)*(gas/lastGas);
		}
		if(joint == ArmJoint.ELBOW)
		{
			nextS = ArmJoint.SHOULDER.pot.get();
			nextW = ArmJoint.WRIST.pot.get();
			
			nextE = joint.pot.get() + (joint.pot.get() - lastPosition)*(gas/lastGas);
		}
		if(joint == ArmJoint.WRIST)
		{
			nextS = ArmJoint.SHOULDER.pot.get();
			nextE = ArmJoint.ELBOW.pot.get();
			
			nextW = joint.pot.get() + (joint.pot.get() - lastPosition)*(gas/lastGas);
		}
		
		if(Robot.arm.checkConstraintsOnCurrentPosition() && Robot.arm.checkConstraints(nextS, nextE, nextW))
		{
			super.pidWrite(output + gas);
			if(gas != 0)
			{
				joint.pid.setSetpoint(joint.pot.get());
			}
			lastGas = gas;
		}
		else
		{
			lastGas = 0;
			super.pidWrite(output);
		}
		lastPosition = joint.pot.get();
	}
}
