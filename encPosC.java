
package org.usfirst.frc.team193.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class encPosC {

	
	String gameData = "RLR";
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
	
	DigitalInput upSwitch;
	
	DoubleSolenoid lPiston;
	DoubleSolenoid rPiston;
	
	//Counts per inch
	double CPI = 4096/18.84;
	//One turn = 18.6 inches/4000 counts
		
	boolean intakeStop = false;
	boolean armRaising = false;
	boolean timerStart = false;
	
	public encPosC(TalonSRX rTal, TalonSRX lTal, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal,
			VictorSPX intakeVic, VictorSPX rVic1, VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer, DigitalInput upSwitch, DoubleSolenoid lPiston, DoubleSolenoid rPiston){
		
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
		this.upSwitch = upSwitch;
		this.lPiston = lPiston;
		this.rPiston = rPiston;
		
	}
	
	public void start(){
		
		
		//If the switch is on the left side (go to the switch)
		if(gameData.charAt(0) == 'R' && gameData.charAt(1)=='L'){
			
			
			//Go forward for 168 inches
			if (rightTalonMotor.getSelectedSensorPosition(0) >= 0 && rightTalonMotor.getSelectedSensorPosition(0) < (CPI * 162) ){
				
				leftTalonMotor.set(ControlMode.PercentOutput, .3);
				rightTalonMotor.set(ControlMode.PercentOutput, -.3);
				
			//Turn for 27 inches
			}else if (rightTalonMotor.getSelectedSensorPosition(0) >= (CPI*162) && rightTalonMotor.getSelectedSensorPosition(0) < (CPI * 188) ){
				
				leftTalonMotor.set(ControlMode.PercentOutput, -.22);
				rightTalonMotor.set(ControlMode.PercentOutput, -.22);
				TalonArmMotor.set(ControlMode.Velocity, -7.5);

			//Go forward for about 22 inches
			}else if (rightTalonMotor.getSelectedSensorPosition(0) >= (CPI * 188) && rightTalonMotor.getSelectedSensorPosition(0) < (CPI*210)){
				
				leftTalonMotor.set(ControlMode.PercentOutput, .3);
				rightTalonMotor.set(ControlMode.PercentOutput, -.3);
				TalonArmMotor.set(ControlMode.Velocity, -2);
				
				
			//Start intake once at the switch
			}else if (rightTalonMotor.getSelectedSensorPosition(0) >= (CPI*210) && time.get()<1.5){
				
				
				
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -.25);
			
				if (timerStart == false){
					
					time.start();
					
				}
				
				timerStart = true;
				
			}else if (rightTalonMotor.getSelectedSensorPosition(0) > (CPI * 210) && time.get() > 1.5){
				
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
				
			}
			
			if(DriverStation.getInstance().getMatchTime() < 8){
				
				TalonIntakeMotor.set(ControlMode.PercentOutput, -.25);
				
			}
			
		
				
			
//      ------------------------------------------------------------------------------
				
			//If the switch is not on the left side but the scale is (Going to the scale)
			}else if (gameData.charAt(1) == 'R'){
				
				//Go forward for 314 inches
				if (leftTalonMotor.getSelectedSensorPosition(0) < (CPI * 285)){
					
					leftTalonMotor.set(ControlMode.PercentOutput, .4);
					rightTalonMotor.set(ControlMode.PercentOutput, -.4);
				
				//Turn for 18.5 inches
				}else if (leftTalonMotor.getSelectedSensorPosition(0) >= (CPI * 285) && leftTalonMotor.getSelectedSensorPosition(0) < (CPI * 322)){
					
					leftTalonMotor.set(ControlMode.PercentOutput, .35);
					rightTalonMotor.set(ControlMode.PercentOutput, .35);
				
				//Go forward for little bit
				}else if (leftTalonMotor.getSelectedSensorPosition(0) >= (CPI*322) && leftTalonMotor.getSelectedSensorPosition(0) < (CPI * 326)){
					
					leftTalonMotor.set(ControlMode.PercentOutput, .15);
					rightTalonMotor.set(ControlMode.PercentOutput, -.15);
					armRaising = true;

				}else if (leftTalonMotor.getSelectedSensorPosition(0) >= (CPI * 326)){
					
					leftTalonMotor.set(ControlMode.PercentOutput, 0);
					rightTalonMotor.set(ControlMode.PercentOutput, 0);
					
				}
				
				//Raising the arm for the scale
				if(upSwitch.get()==true && armRaising == true){
					
					TalonArmMotor.set(ControlMode.Velocity, -8);
				
				//Start the intake once arm is fully raised
				}else if (upSwitch.get()==false && time.get() < 1.5){
						
					TalonArmMotor.set(ControlMode.Velocity, 0);
					TalonArmMotor.setIntegralAccumulator(0, 0, 0);
					TalonIntakeMotor.set(ControlMode.PercentOutput, -.3);
					
					//Start the timer for intake
					if(timerStart == false){
						
						time.start();
						
					}
					
					timerStart = true;
				
				//Stop intake after 1.5 seconds
				}else if (time.get()>1.5){
					
					TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
					
				}
			
//	 ----------------------------------------------------------------------------
		
		//If neither the switch nor the scale is on our left side (Just cross the autoline)
		}else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L'){
			
			
			if (leftTalonMotor.getSelectedSensorPosition(0) >= 0 && leftTalonMotor.getSelectedSensorPosition(0) < (CPI * 125)){
				
				leftTalonMotor.set(ControlMode.PercentOutput, .15);
				rightTalonMotor.set(ControlMode.PercentOutput, -.15);
				
			}else if (leftTalonMotor.getSelectedSensorPosition(0) >= (CPI * 125)){
				
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				
			}
					
					
			
		} // End of gameData if statements
		
	}
	
}

