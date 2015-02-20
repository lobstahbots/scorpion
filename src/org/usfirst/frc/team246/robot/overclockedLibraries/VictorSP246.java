package org.usfirst.frc.team246.robot.overclockedLibraries;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.VictorSP;

/**
 *
 * @author Paul
 */
public class VictorSP246 extends VictorSP implements SpeedController246{
    
    boolean overridden = false;
    int pdpPort;
    PowerDistributionPanel pdp;
    
    public VictorSP246(final int channel, int pdpPort, PowerDistributionPanel pdp)
    {
    	super(channel);
    	this.pdpPort = pdpPort;
    	this.pdp = pdp;
    }
    
    public double getCurrent()
    {
    	return pdp.getCurrent(pdpPort);
    }
    
    public void set(double speed)
    {
        if(!overridden) super.set(speed);
    }
    
    /**
     * sets the speed of the motor, and disables the set command
     * @param speed the speed which the motor should move at
     */
    public void overridingSet(double speed)
    {
        overridden = true;
        super.set(speed);
    }
    
    /**
     * enables the set command after overridingSet has been called
     */
    public void returnControl()
    {
        overridden = false;
    }
}
