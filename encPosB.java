package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class encPosB {

	String gameData;
	
	double AUTONSPEEDN;
	double NAUTONSPEEDN;
	//leftTalon and rightTalon
	TalonSRX lTalN;
	TalonSRX rTalN;
			
	//vic1 and vic2 follow rightTalon (they are its slaves)
	VictorSPX rVic1N;
	VictorSPX rVic2N;
	
	//vic3 and vic4 follow leftTalon (they are its slaves)
	VictorSPX lVic1N;	
	VictorSPX lVic2N;
	
	TalonSRX armTalN;
	VictorSPX armVicN;
		
	TalonSRX intakeTalN;
	VictorSPX intakeVicN;
	
	Timer timerN;
	
	boolean intakeStop = false;
	
	//Code written by Vedh
	public encPosB(double AUTONSPEED, double NAUTONSPEED, TalonSRX lTal, TalonSRX rTal, VictorSPX rVic1, 
			VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal, VictorSPX intakeVic){
		
		lTalN = lTal;
		rTalN = rTal;
		rVic1N = rVic1;
		rVic2N = rVic2;
		lVic1N = lVic1;
		lVic2N = lVic2;
		timerN = timer;
		AUTONSPEEDN = AUTONSPEED;
		NAUTONSPEEDN = NAUTONSPEED;
		armTalN = armTal;
		armVicN = armVic;
		intakeTalN = intakeTal;
		intakeVicN = intakeVic;
		
	
	}
	
	public void start(){
			
		//If we own the left side of the switch
		if (gameData.charAt(0) == 'L'){
			
			//7610 total counts to get to the front of the switch (not including turns)
			
			//Monitor right talon for ease of turning
			switch (rTalN.getSelectedSensorPosition(0)){
			
			case 0:
				
				//Go straight
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
				
			//Go forward for 3000 counts
			case 3000: 
				
				//Turn left
				lTalN.set(ControlMode.PercentOutput, -.3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
				break;
			
			//Left encoder counts go to 2200 and right encoder counts go to 3800
			//Turn for 800 counts
			case 3800:
				
				//Go straight (facing left)
				lTalN.set(ControlMode.PercentOutput, .5);	
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Left encoder counts at 4400
			//Go forward for 2200 counts
			case 8900:
				
				//Turn right to the switch
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, .3);
				
				break;
			
			//Turn for 800 counts (since wheels are moving backwards, encoder counts will go backwards as well)
			case 9800:
				
				//Making sure that it will only go forward after it turns
				if(lTalN.getSelectedSensorPosition(0) == 9800){
				//Stop to raise the arm
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -6.5);
				
				}
				
				break;
				
			case 11000: 
				
				//Stop the arm at a certain point while still moving
				armTalN.set(ControlMode.Velocity, -3);
				
				break;
				
			case 14420: 
				
				//Stop once at switch and spit out the cube
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				intakeTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
				
				
				default: 
					
					
					break;
			
			
			}//End of switch statement
			
			
			//Starting timer for intake to run
			if (intakeTalN.getMotorOutputPercent() == -.5 && intakeStop == false){
				timerN.reset();
				intakeStop = true;
			}
			
			//Stopping the intake 
			if (timerN.get() > 1.5 && intakeStop == true){
				intakeTalN.set(ControlMode.PercentOutput, 0);
			}
			
//	  -------------------------------------------------------------			
			
		//If we own the right side of the switch
		}else if (gameData.charAt(0) == 'R'){
			
			
			switch (lTalN.getSelectedSensorPosition(0)){
			
			case 0:
				
				//Go straight for a little bit
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Go forward for 3000 counts
			case 3000:
				
				//Turn to the right
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, .5);
				
				break;
			
			//Left encoder goes to 2200 counts
			//Turn for 800 counts
			case 3800:
				
				//Go straight (facing the right)
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
				
			//Go forward for 1200 counts
			case 5000:
				
				//Turn left to face the switch
				lTalN.set(ControlMode.PercentOutput, -.3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
				break;
				
			case 4200:
				
				if(rTalN.getSelectedSensorPosition(0)==4200){
				//Begin raising arm and going towards the switch
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -6.5);
				}
				
				break; 
				
			case 6500:
				
				//Stop the arm from moving
				armTalN.set(ControlMode.Velocity, -3);
				
				break;
				
			case 8810:
				
				//Stop driving and start the intake
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				intakeTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
				
				default:
					
					
					break;
			
			}//End of switch statement
			
			//Starting timer for intake to run
			if (intakeTalN.getMotorOutputPercent() == -.5 && intakeStop == false){
				timerN.reset();
				intakeStop = true;
			}
			
			//Stopping the intake
			if (timerN.get() > 1.5 && intakeStop == true){
				intakeTalN.set(ControlMode.PercentOutput, 0);
			}
			
		} //End of gameData if statement
		
		
		
		
	}//End of start()
	
}//End of class
