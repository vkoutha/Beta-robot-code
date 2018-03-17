<<<<<<< HEAD
package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Timer;

//CODE WRITTEN BY MADHUR AND KEVIN
public class crossAutoLine {


	TalonSRX rightTalon; //rTal
	TalonSRX leftTalon; //lTal

	
	VictorSPX vic1right; //rVic1
	VictorSPX vic2left; //lVic1
	VictorSPX vic3right; //rVic2
	VictorSPX vic4left; //lVic2
	Timer autoTime; //timer
	
	public crossAutoLine(TalonSRX rTal, TalonSRX lTal, VictorSPX rVic1, VictorSPX lVic1, VictorSPX rVic2, VictorSPX lVic2, Timer timer){
		
		rightTalon = rTal;
		leftTalon = lTal;
		vic1right = rVic1;
		vic2left = lVic1;
		vic3right = rVic2;
		vic4left = lVic2;
		autoTime = timer;
	}
	
	public void cAL(){
		
		if(autoTime.get()<4.1) {
			//Drive forward for 4 seconds
			rightTalon.set(ControlMode.PercentOutput,-.2);
			leftTalon.set(ControlMode.PercentOutput,.2);
			
		}else if(autoTime.get()>=4.1) {
			//After 4 seconds, stop moving
			rightTalon.set(ControlMode.PercentOutput, 0);
			leftTalon.set(ControlMode.PercentOutput, 0);
		}
		
	}
	
}
=======
package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Timer;

//CODE WRITTEN BY MADHUR AND KEVIN
public class crossAutoLine {


	TalonSRX rightTalon; //rTal
	TalonSRX leftTalon; //lTal

	
	VictorSPX vic1right; //rVic1
	VictorSPX vic2left; //lVic1
	VictorSPX vic3right; //rVic2
	VictorSPX vic4left; //lVic2
	Timer autoTime; //timer
	
	public crossAutoLine(TalonSRX rTal, TalonSRX lTal, VictorSPX rVic1, VictorSPX lVic1, VictorSPX rVic2, VictorSPX lVic2, Timer timer){
		
		rightTalon = rTal;
		leftTalon = lTal;
		vic1right = rVic1;
		vic2left = lVic1;
		vic3right = rVic2;
		vic4left = lVic2;
		autoTime = timer;
	}
	
	public void cAL(){
		
		if(autoTime.get()<4.1) {
			//Drive forward for 4 seconds
			rightTalon.set(ControlMode.PercentOutput,-.2);
			leftTalon.set(ControlMode.PercentOutput,.2);
			
		}else if(autoTime.get()>=4.1) {
			//After 4 seconds, stop moving
			rightTalon.set(ControlMode.PercentOutput, 0);
			leftTalon.set(ControlMode.PercentOutput, 0);
		}
		
	}
	
}
>>>>>>> parent of a7f3e99... Merge branch 'master' of https://github.com/vkoutha/robot
