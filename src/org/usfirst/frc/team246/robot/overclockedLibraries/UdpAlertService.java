/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team246.robot.overclockedLibraries;

import java.io.IOException;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

/**
 *
 * @author Dave
 */
public class UdpAlertService {

    public static void initialize(String hostname, int port) throws Exception
    {
        alertServerAddress = InetAddress.getByName(hostname);
        alertServicePort = port;
        transmitSocket = new DatagramSocket();
        transmitSocket.setBroadcast(false);
        transmitSocket.setReuseAddress(true);
    }
    
    private static InetAddress alertServerAddress;
    private static int alertServicePort;
    private static DatagramSocket transmitSocket;
    
    public static void sendAlert(AlertMessage alertMessage)
    {
        String xmlString = alertMessage.toXml();
        DatagramPacket packet = new DatagramPacket(xmlString.getBytes(), xmlString.length(), alertServerAddress, alertServicePort);
        try {
			transmitSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void close()
    {
        transmitSocket.close();
    }
}
