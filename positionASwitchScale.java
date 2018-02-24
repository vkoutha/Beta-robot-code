package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class positionASwitchScale {

	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	// Right Talon Motor- Drive
	TalonSRX rightTalonMotor;
	// Left Talon Motor- Drive
	TalonSRX leftTalonMotor;
	//Talon Arm Motor
	TalonSRX TalonArmMotor;
	//Talon Intake Motor
	TalonSRX TalonIntakeMotor;
	//Right Victor Motor- Drive
	VictorSPX rightVictorMotor;
	//Right Victor Motor- Drive
	VictorSPX rightVictor1Motor;
	//Left Victor Motor- Drive
	VictorSPX leftVictorMotor;
	//Left Victor Motor- Drive
	VictorSPX leftVictor1Motor;
	//Victor Arm Motor
	VictorSPX VictorArmMotor;
	//Victor Intake Motor
	VictorSPX VictorIntakeMotor;
	//timer
	Timer time;
	
	public positionASwitchScale(TalonSRX rTal, TalonSRX lTal, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal,
			VictorSPX intakeVic, VictorSPX rVic1, VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer){
		
		rightTalonMotor = rTal;
		leftTalonMotor = lTal;
		TalonArmMotor = armTal;
		TalonIntakeMotor = intakeTal;
		rightVictorMotor = rVic1;
		rightVictor1Motor = rVic2;
		leftVictorMotor = lVic1;
		leftVictor1Motor = lVic2;
		VictorArmMotor = armVic;
		VictorIntakeMotor = intakeVic;
		time = timer;
		
	}
	
	public void posAStart(){
		
		//Receives Message from game
		//Receives Message from game
		System.out.println(gameData);
		//Receives first letter of message from game
		gameData.substring(0, 1);
		//Follow code if first letter from game is L for position A
		if (gameData.substring(0, 1).equalsIgnoreCase("L")) {
			if (time.get()==0)
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			
			//It take a 90 degree turn 
			if (time.get()>=5) {
				rightTalonMotor.set(ControlMode.PercentOutput, .4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			}
			//Drives straight
			if (time.get()>=6 && time.get()<=7) {
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			}
			//program stops for driving
			if (time.get()>=7 && time.get()<=8) {
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
			}
			//Move arm up	
			if (time.get()>=0 && time.get()<=1) {
				//TalonArmMotor.set(ControlMode.PercentOutput, -.1);
			}
			//Stops arm
			if (time.get()>2) {
				//TalonArmMotor.set(ControlMode.PercentOutput, 0);
				//VictorArmMotor.set(ControlMode.PercentOutput, 0);
			}
			//Intake spits out cube
			if (time.get()>=1 && time.get()<=2) {
				TalonIntakeMotor.set(ControlMode.PercentOutput, .5);
			}
			//Stops Intake
			if (time.get()>=2) {
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
		}
		//Drives straight 
		
		//Receives Letter R from message
		else if (gameData.substring(0, 1).equalsIgnoreCase("R")) {
			
			if (time.get()<=10) {
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			}
			//takes a 90 degree turn
			if (time.get()>10 && time.get()<=11) {	
				rightTalonMotor.set(ControlMode.PercentOutput, .4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			}
			//Motor stop
			if (time.get()>11) {
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
			}
			
		}
		//drives straight for 10 seconds
		

	}
	
}
