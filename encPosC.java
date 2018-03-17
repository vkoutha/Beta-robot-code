<<<<<<< HEAD
package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class encPosC {

	
	String gameData;
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
	
	boolean intakeStop = false;
	boolean armRaising = false;
	
	public encPosC(TalonSRX rTal, TalonSRX lTal, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal,
			VictorSPX intakeVic, VictorSPX rVic1, VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer, DigitalInput upSwitch){
		
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
		
	}
	
	public void start(){
		
		//If we own the right side of the switch (go to the switch)
		if(gameData.charAt(0) == 'R' && gameData.charAt(1)=='L'){
			
			//Monitor the right talon motor for ease of turning
			switch (rightTalonMotor.getSelectedSensorPosition(0)){
			
			case 0:
				
				//Go all the way forward to side of the switch
				leftTalonMotor.set(ControlMode.PercentOutput, .5);
				rightTalonMotor.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Go forward for 9216 counts of encoder
			case 9216:
				
				//Begin raising the arm and turning; Turn right so arm is facing the switch
				leftTalonMotor.set(ControlMode.PercentOutput, -.3);
				rightTalonMotor.set(ControlMode.PercentOutput, -.3);
				TalonArmMotor.set(ControlMode.Velocity, -6.5);
				
				break;
			
			//Turn for about 800 counts (needs to be tested/changed)
			case 10000:
				
				//Go forward a small amount so robot hits switch
				leftTalonMotor.set(ControlMode.PercentOutput, .3);
				rightTalonMotor.set(ControlMode.PercentOutput, -.3);
				TalonArmMotor.set(ControlMode.Velocity, -3);
				
				break;
				
			//Go forward for 1000 counts (needs to be tested/changed)
			case 11000:
				
				//Stop wheels and start intake
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -.5);
				
				break;
				
				default: 
					
					break;
					
					
					}//End of switch statement
			
			//Starting timer for intake to run
			if (TalonIntakeMotor.getMotorOutputPercent() == -.5 && intakeStop == false){
				time.reset();
				intakeStop = true;
			}
			
			//Stopping the intake 
			if (time.get() > 1.5 && intakeStop == true){
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
//      ------------------------------------------------------------------------------
				
			//If the switch is not on our side but the scale is (go to the scale)
			}else if (gameData.charAt(1) == 'R'){
				
				//Monitor the left talon motor for ease of turning (since we turn the robot's back towards the scale)
				switch(leftTalonMotor.getSelectedSensorPosition(0)){
				
				case 0:
					
					//Go forward all the way to the side of the scale
					leftTalonMotor.set(ControlMode.PercentOutput, .5);
					rightTalonMotor.set(ControlMode.PercentOutput, -.5);
					
					break;
				
				//Go forward for 17152 counts 
				case 17152:
					
					//Turn so that back of the robot is facing scale; Begin raising arm
					leftTalonMotor.set(ControlMode.PercentOutput, .3);
					rightTalonMotor.set(ControlMode.PercentOutput, .3);
					armRaising = true;
					
					break;
				
				//Turn for about 850 counts
				case 18000:
					
					//Stop moving, stop the arm, and start the intake
					leftTalonMotor.set(ControlMode.PercentOutput, 0);
					rightTalonMotor.set(ControlMode.PercentOutput, 0);
					
					break;
					
					default:
						
						break;
						
				}//End of switch statement
				
				//Raising the arm for the scale
				if(upSwitch.get()==true && armRaising == true){
					
					TalonArmMotor.set(ControlMode.Velocity, -10);
					
				}else if (upSwitch.get()==false && armRaising == true){
						
					TalonArmMotor.set(ControlMode.Velocity, 0);
					TalonArmMotor.setIntegralAccumulator(0, 0, 0);
					armRaising = false;
						
					}
				
				//Starting the intake once arm raise is complete
				
				if (armRaising == false){
					TalonIntakeMotor.set(ControlMode.PercentOutput, -.65);
				}
				//Starting timer for intake to run
				if (TalonIntakeMotor.getMotorOutputPercent() == -.65 && intakeStop == false){
					time.reset();
					intakeStop = true;
				}
				
				//Stopping the intake 
				if (time.get() > 1.5 && intakeStop == true){
					TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
				
			}
			
//		----------------------------------------------------------------------------
				
		//If neither the switch nor the scale is on our side, the right side (just cross the autoline)
		}else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L'){
			
			//Monitor left talon motor (side does not matter)
			switch (leftTalonMotor.getSelectedSensorPosition(0)){
			
			case 0:
				
				leftTalonMotor.set(ControlMode.PercentOutput, .5);
				rightTalonMotor.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Go forward for 6656 counts
			case 6656:
				
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				
				break;
				
				default:
					
					break;
			
			}
			
		} // End of gameData if statements
		
	}
	
}
=======
package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class encPosC {

	
	String gameData;
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
	
	boolean intakeStop = false;
	boolean armRaising = false;
	
	public encPosC(TalonSRX rTal, TalonSRX lTal, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal,
			VictorSPX intakeVic, VictorSPX rVic1, VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer, DigitalInput upSwitch){
		
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
		
	}
	
	public void start(){
		
		//If we own the right side of the switch (go to the switch)
		if(gameData.charAt(0) == 'R' && gameData.charAt(1)=='L'){
			
			//Monitor the right talon motor for ease of turning
			switch (rightTalonMotor.getSelectedSensorPosition(0)){
			
			case 0:
				
				//Go all the way forward to side of the switch
				leftTalonMotor.set(ControlMode.PercentOutput, .5);
				rightTalonMotor.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Go forward for 9216 counts of encoder
			case 9216:
				
				//Begin raising the arm and turning; Turn right so arm is facing the switch
				leftTalonMotor.set(ControlMode.PercentOutput, -.3);
				rightTalonMotor.set(ControlMode.PercentOutput, -.3);
				TalonArmMotor.set(ControlMode.Velocity, -6.5);
				
				break;
			
			//Turn for about 800 counts (needs to be tested/changed)
			case 10000:
				
				//Go forward a small amount so robot hits switch
				leftTalonMotor.set(ControlMode.PercentOutput, .3);
				rightTalonMotor.set(ControlMode.PercentOutput, -.3);
				TalonArmMotor.set(ControlMode.Velocity, -3);
				
				break;
				
			//Go forward for 1000 counts (needs to be tested/changed)
			case 11000:
				
				//Stop wheels and start intake
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				TalonIntakeMotor.set(ControlMode.PercentOutput, -.5);
				
				break;
				
				default: 
					
					break;
					
					
					}//End of switch statement
			
			//Starting timer for intake to run
			if (TalonIntakeMotor.getMotorOutputPercent() == -.5 && intakeStop == false){
				time.reset();
				intakeStop = true;
			}
			
			//Stopping the intake 
			if (time.get() > 1.5 && intakeStop == true){
				TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
			}
//      ------------------------------------------------------------------------------
				
			//If the switch is not on our side but the scale is (go to the scale)
			}else if (gameData.charAt(1) == 'R'){
				
				//Monitor the left talon motor for ease of turning (since we turn the robot's back towards the scale)
				switch(leftTalonMotor.getSelectedSensorPosition(0)){
				
				case 0:
					
					//Go forward all the way to the side of the scale
					leftTalonMotor.set(ControlMode.PercentOutput, .5);
					rightTalonMotor.set(ControlMode.PercentOutput, -.5);
					
					break;
				
				//Go forward for 17152 counts 
				case 17152:
					
					//Turn so that back of the robot is facing scale; Begin raising arm
					leftTalonMotor.set(ControlMode.PercentOutput, .3);
					rightTalonMotor.set(ControlMode.PercentOutput, .3);
					armRaising = true;
					
					break;
				
				//Turn for about 850 counts
				case 18000:
					
					//Stop moving, stop the arm, and start the intake
					leftTalonMotor.set(ControlMode.PercentOutput, 0);
					rightTalonMotor.set(ControlMode.PercentOutput, 0);
					
					break;
					
					default:
						
						break;
						
				}//End of switch statement
				
				//Raising the arm for the scale
				if(upSwitch.get()==true && armRaising == true){
					
					TalonArmMotor.set(ControlMode.Velocity, -10);
					
				}else if (upSwitch.get()==false && armRaising == true){
						
					TalonArmMotor.set(ControlMode.Velocity, 0);
					TalonArmMotor.setIntegralAccumulator(0, 0, 0);
					armRaising = false;
						
					}
				
				//Starting the intake once arm raise is complete
				
				if (armRaising == false){
					TalonIntakeMotor.set(ControlMode.PercentOutput, -.65);
				}
				//Starting timer for intake to run
				if (TalonIntakeMotor.getMotorOutputPercent() == -.65 && intakeStop == false){
					time.reset();
					intakeStop = true;
				}
				
				//Stopping the intake 
				if (time.get() > 1.5 && intakeStop == true){
					TalonIntakeMotor.set(ControlMode.PercentOutput, 0);
				
			}
			
//		----------------------------------------------------------------------------
				
		//If neither the switch nor the scale is on our side, the right side (just cross the autoline)
		}else if (gameData.charAt(0) == 'L' && gameData.charAt(1) == 'L'){
			
			//Monitor left talon motor (side does not matter)
			switch (leftTalonMotor.getSelectedSensorPosition(0)){
			
			case 0:
				
				leftTalonMotor.set(ControlMode.PercentOutput, .5);
				rightTalonMotor.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Go forward for 6656 counts
			case 6656:
				
				leftTalonMotor.set(ControlMode.PercentOutput, 0);
				rightTalonMotor.set(ControlMode.PercentOutput, 0);
				
				break;
				
				default:
					
					break;
			
			}
			
		} // End of gameData if statements
		
	}
	
}
>>>>>>> 077c490c5d99920b1983dec83b1b07790ed7abc6
