package org.usfirst.frc.team193.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

//CODE WRITTEN BY MADHUR AND KEVIN
public class camera {
	
	//Sends camera information back to Driver's station SmartDashboard
	public void startCam(){
		
	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
	camera.setResolution(1280, 720);
	camera.setFPS(40);
	}

	
}
