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
		System.out.println("Orientation of switches and scale: " + gameData);
		
		if (gameData.substring(0, 1).equalsIgnoreCase("L")) {
			//Follow code if first letter from game is L for position A
			
			if (time.get()>0 && time.get()<5){
				//Go straight
				rightTalonMotor.set(ControlMode.PercentOutput, -.15);
				leftTalonMotor.set(ControlMode.PercentOutput, .15);
			
			}else if (time.get()> 5 && time.get()<6.5) {
				//It take a 90 degree turn to the right
				rightTalonMotor.set(ControlMode.PercentOutput, .165);
				leftTalonMotor.set(ControlMode.PercentOutput, .165);
				
			}else if (time.get()>6.5 && time.get()<7) {
				//Go straight to switch for a small bit
				rightTalonMotor.set(ControlMode.PercentOutput, -.125);
				leftTalonMotor.set(ControlMode.PercentOutput, .125);
				
			}else if (time.get()>7 && time.get()<=8) {
				//Stop drive train and start moving arm up
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonArmMotor.set(ControlMode.Velocity, 10);
				
			}else if (time.get()>8 && time.get()<10){
				//Stop the arm from going up and push out cubes
				TalonArmMotor.set(ControlMode.Velocity, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -1);
			}else if (time.get()>10){
				//Stop intake motor
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
			
			
			//If scale is on our left side but switch is on our right
		}else if (gameData.substring(1, 2).equalsIgnoreCase("L") && gameData.substring(0,1).equalsIgnoreCase("R")) {
			
			if (time.get()>0 && time.get()<=6) {
				//Go forward for 6 seconds to the scale
				rightTalonMotor.set(ControlMode.PercentOutput, -.15);
				leftTalonMotor.set(ControlMode.PercentOutput, .15);
				
			}else if (time.get()>6 && time.get()<7.5) {	
				//Turn right to face the scale
				rightTalonMotor.set(ControlMode.PercentOutput, .175);
				leftTalonMotor.set(ControlMode.PercentOutput, .175);
				
			}else if (time.get()>7.5 && time.get()<10) {
				//Stop motors
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonArmMotor.set(ControlMode.Velocity, 10);
				
			}else if (time.get()>10 && time.get()<12.5){
				TalonArmMotor.set(ControlMode.Velocity, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -1);
				
			}else if (time.get()>12.5){
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
			
		}else if (gameData.substring(0, 1).equalsIgnoreCase("R") && gameData.substring(1, 2).equalsIgnoreCase("R")){
			//If we don't own the switch or the scale on the left side, just cross the autoline
			
			if(time.get()<4.2) {
				
				//Drive forward for 4 seconds
				rightTalonMotor.set(ControlMode.PercentOutput,-.2);
				leftTalonMotor.set(ControlMode.PercentOutput,.2);
				
			}else if(time.get()>=4.2) {
				
				//After 4 seconds, stop moving
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				
			}
			
		}		

	}
	
}
