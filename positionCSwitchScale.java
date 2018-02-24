package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class positionCSwitchScale {

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
	//Timer
	Timer Time;
	
	public positionCSwitchScale(TalonSRX rTal, TalonSRX lTal, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal,
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
		Time = timer;
		
	}
	
	public void posCStart(){
		
		
		System.out.println(gameData);
		
		if (gameData.substring(0, 1).equalsIgnoreCase("R")) {
			
			if (Time.get()==0){
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
				}
			//Takes a 90 degree turn
			if (Time.get()==5) {
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, -.4);
			}
			//Drives Straight
			if (Time.get()>=6 && Time.get()<=7) {
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			}
			//program stops for driving
			if (Time.get()>=7 && Time.get()<=8) {
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
			}
			//Move arm up
			if (Time.get()>=0 && Time.get()<=1) {
				//TalonArmMotor.set(ControlMode.PercentOutput, -.1);
			}
			//Stops arm
			if (Time.get()>2) {
				//TalonArmMotor.set(ControlMode.PercentOutput, 0);
			}
			//intake spits out cube
			if (Time.get()>=1 && Time.get()<=2) {
				TalonIntakeMotor.set(ControlMode.PercentOutput, .5);
			}
			//stops intake
			if (Time.get()>=2) {
				//TalonArmMotor.set(ControlMode.PercentOutput, 0);
			
			}
			
		}
		
			
		//Receives letter L from message
		else if (gameData.substring(0, 1).equalsIgnoreCase("L")) {

			if (Time.get()<=10) {
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, .4);
			}
			//takes a 90 degree turn
			if (Time.get()>10 && Time.get()<=11) {	
				rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				leftTalonMotor.set(ControlMode.PercentOutput, -.4);
			}
			//Motor stop
			if (Time.get()>11) {
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
					}
				}
			
		}
		
		//drives straight for 10 seconds
		
		
	}

