package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class encPosB {

	String gameData;
	
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
	public encPosB(TalonSRX rTal, TalonSRX lTal, VictorSPX rVic1, 
			VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal, VictorSPX intakeVic){
		
		lTalN = lTal;
		rTalN = rTal;
		rVic1N = rVic1;
		rVic2N = rVic2;
		lVic1N = lVic1;
		lVic2N = lVic2;
		timerN = timer;
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
			
			//Go forward for 3000 counts
			case 0:
				
				//Go straight
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
				
			//Turn for 800 counts
			case 3000: 
				
				//Turn left
				lTalN.set(ControlMode.PercentOutput, -.3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
				break;
			
			//Left encoder counts go to 2200 and right encoder counts go to 3800
				
			//Go forward for 5100 counts
			case 3800:
				
				//Go straight (facing left)
				lTalN.set(ControlMode.PercentOutput, .5);	
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Left encoder counts at 8100
				
			//Turn for 800 counts (encoder counts will reverse as well)
			case 8900:
				
				//Turn right to the switch
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, .3);
				
				break;
			
			//Go forward for 4610
			case 8100:
				
				//Making sure that it will only go forward after it turns
				if(lTalN.getSelectedSensorPosition(0) >= 8100){
				//Stop to raise the arm
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -6.5);
				
				}
				
				break;
			
			//Stops arm from moving
			case 10000: 
				
				//Stop the arm at a certain point while still moving
				armTalN.set(ControlMode.Velocity, -3);
				
				break;
				
			case 12710: 
				
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
			
			//Go forward for 3000 counts
			case 0:
				
				//Go straight for a little bit
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
			
			//Turn for 800 counts
			case 3000:
				
				//Turn to the right
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, .5);
				
				break;
			
			//Right encoder goes to 2200 counts

			//Go forward for 1200 counts
			case 3800:
				
				//Go straight (facing the right)
				lTalN.set(ControlMode.PercentOutput, .5);
				rTalN.set(ControlMode.PercentOutput, -.5);
				
				break;
				
			//Right encoder goes to 3400 counts	
			
			//Turn for 800 counts (encoder counts will reverse due to turning direction)
			case 5000:
				
				//Turn left to face the switch
				lTalN.set(ControlMode.PercentOutput, -.3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
				break;
			
			//Right encoder goes to 4200 counts
				
			//Go forward for 4610 counts
			case 4200:
				
				if(rTalN.getSelectedSensorPosition(0)==4200){
				//Begin raising arm and going towards the switch
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -6.5);
				}
				
				break; 
			
			//Stop arm at certain point from moving
			case 6500:
				
				//Stop the arm from moving
				armTalN.set(ControlMode.Velocity, -3);
				
				break;
			
			//Stop moving and spit cube out
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
