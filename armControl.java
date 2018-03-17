package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;

//CODE WRITTEN BY VINAY
public class armControl {

	//Game controller
	Joystick GControl;
	
	//Arm talon
	TalonSRX MControl1;
	//Arm victor
	VictorSPX MControl2;
	
	//Limit switches
	DigitalInput LSwitch1;
	DigitalInput LSwitch2;
	
	public armControl(Joystick gControl, TalonSRX armTal, VictorSPX armVic, DigitalInput upSwitch, DigitalInput downSwitch){
		GControl = gControl;
		MControl1 = armTal;
		MControl2 = armVic;
		LSwitch1 = upSwitch; //upSwitch 
		LSwitch2 = downSwitch; //downSwitch
	}
	
	//Code written by Vinay
	public void armStart(){
		
		MControl1.set(ControlMode.Velocity, GControl.getY()*-10);
		if(LSwitch1.get()==false){
			//Switch 1 is Pressed
			if(GControl.getY()>0){
				MControl1.set(ControlMode.Velocity, GControl.getY()*-10);
				//Switch 1 is Pressed, but it doesn't matter because the controller is going in the other direction
			}else if(GControl.getY()<=0){
				MControl1.set(ControlMode.Velocity, 0);
				//Switch 1 is pressed, so the motor speed is set to 0
			}
		}else if(LSwitch2.get()==false){
			//Switch 2 is pressed
			if(GControl.getY()>=0){
				MControl1.set(ControlMode.Velocity, 0);
				//Switch 2 is pressed, so the motor speed is set to 0
			}else if(GControl.getY()<0){
				MControl1.set(ControlMode.Velocity, GControl.getY()*-10);
				//Switch 2 is pressed, but it doesn't matter because the controller is going in the other direction
			}
		}else if(LSwitch1.get()==true && LSwitch2.get()==true){
			MControl1.set(ControlMode.Velocity, GControl.getY()*-10);
			//Switch 1 and Switch 2 are not pressed
		}else if(LSwitch1.get()==false && LSwitch2.get()==false){
			//Both Switch 1 and switch 2 are pressed
			//This should never happen! If this happens, then the motors should be set to 0 to avoid further damage
			MControl1.set(ControlMode.Velocity, 0);
		}else{
			//This is if switch 1 or switch 2 is not pressed
			MControl1.set(ControlMode.Velocity, GControl.getY()*-10);
		}
	}
	
}
