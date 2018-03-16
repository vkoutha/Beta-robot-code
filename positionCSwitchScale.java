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
		
		
		System.out.println("Orientation of switch and scale: " + gameData);
		
		if (gameData.substring(0, 1).equalsIgnoreCase("R")) {
			
			if (Time.get()>0 && Time.get()<5){
				//Go straight
				rightTalonMotor.set(ControlMode.PercentOutput, -.15);
				leftTalonMotor.set(ControlMode.PercentOutput, .15);
			
			}else if (Time.get()> 5 && Time.get()<6.5) {
				//It take a 90 degree turn to the right
				rightTalonMotor.set(ControlMode.PercentOutput, -.165);
				leftTalonMotor.set(ControlMode.PercentOutput, -.165);
				
			}else if (Time.get()>6.5 && Time.get()<7) {
				//Go straight to switch for a small bit
				rightTalonMotor.set(ControlMode.PercentOutput, -.125);
				leftTalonMotor.set(ControlMode.PercentOutput, .125);
				
			}else if (Time.get()>7 && Time.get()<=8) {
				//Stop drive train and start moving arm up
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonArmMotor.set(ControlMode.Velocity, 10);
				
			}else if (Time.get()>8 && Time.get()<10){
				//Stop the arm from going up and push out cubes
				TalonArmMotor.set(ControlMode.Velocity, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -1);
			}else if (Time.get()>10){
				//Stop intake motor
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
			
		}
		
			
		//Receives letter L from message
		else if (gameData.substring(1, 2).equalsIgnoreCase("R") && gameData.substring(0, 1).equals("L")) {

			if (Time.get()>0 && Time.get()<=6) {
				//Go forward for 6 seconds to the scale
				rightTalonMotor.set(ControlMode.PercentOutput, -.15);
				leftTalonMotor.set(ControlMode.PercentOutput, .15);
				
			}else if (Time.get()>6 && Time.get()<7.5) {	
				//Turn right to face the scale
				rightTalonMotor.set(ControlMode.PercentOutput, -.175);
				leftTalonMotor.set(ControlMode.PercentOutput, -.175);
				
			}else if (Time.get()>7.5 && Time.get()<10) {
				//Stop motors
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonArmMotor.set(ControlMode.Velocity, 10);
				
			}else if (Time.get()>10 && Time.get()<12.5){
				
				//Starts the intake to spit out cubes
				TalonArmMotor.set(ControlMode.Velocity, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -1);
				
			}else if (Time.get()>12.5){
				//Stops the intake
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
			
		}else if (gameData.substring(0,1).equals("L") && gameData.substring(1,2).equals("L")){
			
			if(Time.get()<4.1) {
				//Drive forward for 4 seconds
				rightTalonMotor.set(ControlMode.PercentOutput,-.2);
				leftTalonMotor.set(ControlMode.PercentOutput,.2);
				
			} else if(Time.get()>=4.1) {
				//After 4 seconds, stop moving
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
			}
			
			
		}
		
	}
		//drives straight for 10 seconds
		
		
	}

