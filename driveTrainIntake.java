package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

//CODE WRITTEN BY AGRON, EMRE, AND DAN
public class driveTrainIntake {

		//Left joystick
		Joystick joy1; 
		//Right joystick
		Joystick joy2; 
		
		//Left talonSRX
		TalonSRX talonSRX1; 
		//Right talonSRX
		TalonSRX talonSRX2;
		
		//Left victorSPX #1
		VictorSPX leftDrive1A;
		//Left victorSPX #2
		VictorSPX leftDrive1B;
		//Right victorSPX#1
		VictorSPX rightDrive1A;
		//Right victorSPX#2
		VictorSPX rightDrive1B; 
		
		//Game controller
		Joystick joy3; 
		
		//Intake victor
		TalonSRX realIntake;
		//Intake victor
		VictorSPX invertedIntake; 
		
		DigitalInput intakeLim;
			
		public driveTrainIntake(Joystick lJoy, Joystick rJoy, TalonSRX lTal, TalonSRX rTal, VictorSPX lVic1, VictorSPX lVic2, VictorSPX rVic1, VictorSPX rVic2
				, Joystick gControl, TalonSRX intakeTal, VictorSPX intakeVic, DigitalInput intakeSwitch){
			
			
			joy1 = lJoy;
			joy2 = rJoy;
			talonSRX1 = lTal;
			talonSRX2 = rTal;
			leftDrive1A = lVic1;
			leftDrive1B = lVic2;
			rightDrive1A = rVic1;
			rightDrive1B = rVic2;
			joy3 = gControl;
			realIntake = intakeTal;
			invertedIntake = intakeVic;
			intakeLim = intakeSwitch;
			
		}
	
		//Code written by Agron and Emre and Dan
	public void dTIntake(){
		
		//Drive train code
		
		
		
		 talonSRX1.set(ControlMode.PercentOutput, joy1.getY()*-1); 
		 talonSRX2.set(ControlMode.PercentOutput, joy2.getY());
		
	
			//If right button is pressed, suck cubes in
			if  (joy3.getRawButtonPressed(6) == true && intakeLim.get()==true) {
				
				realIntake.set(ControlMode.PercentOutput, -1);
			//If right button is not pressed, turn off the intake
				
			}else if(joy3.getRawButtonReleased(6)==true && intakeLim.get()==true) {
				
				realIntake.set(ControlMode.PercentOutput, 0);
				
			}else if (joy3.getRawButtonPressed(6)==true && intakeLim.get()==false){
				
				//If limit switch is pressed and cubes are being sucked in, stop the motor
				realIntake.set(ControlMode.PercentOutput, 0);
				invertedIntake.set(ControlMode.PercentOutput, 0);
			}
			
			//if left button is pressed, suck cubes in
			if (joy3.getRawButtonPressed(5) == true && intakeLim.get()==true) {
				
				invertedIntake.set(ControlMode.PercentOutput, -1);
			//If left button is not pressed, turn off the intake
				
			}else if(joy3.getRawButtonReleased(5)==true && intakeLim.get()==true) {
				
				invertedIntake.set(ControlMode.PercentOutput, 0);
				
			}else if (joy3.getRawButtonPressed(5)==true && intakeLim.get()==false){
				//If limit switch is pressed and cubes are being sucked in, stop the motor
				realIntake.set(ControlMode.PercentOutput, 0);
				invertedIntake.set(ControlMode.PercentOutput, 0);
			}
			
			//Controlling one intake side at a time
			if (joy3.getRawButtonPressed(7)==true){
				
				//If left trigger is pressed, push cubes out
				invertedIntake.set(ControlMode.PercentOutput, 1);
				
			}else if(joy3.getRawButtonReleased(7)==true){
				
				//Stop motor if trigger is released
				invertedIntake.set(ControlMode.PercentOutput, 0);
				
			}
			

			
			if (joy3.getRawButtonPressed(8)==true){
				
				//If right trigger is pressed, push cubes out
				realIntake.set(ControlMode.PercentOutput, 1);
				
			}else if (joy3.getRawButtonReleased(8)==true){
				
				//Stop motor if trigger is released
				realIntake.set(ControlMode.PercentOutput, 0);
				
			}
			
			
			
	}
	
}
