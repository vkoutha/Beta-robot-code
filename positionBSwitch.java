package org.usfirst.frc.team193.robot;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

//CODE WRITTEN BY VEDH
public class positionBSwitch {

	String gameData = DriverStation.getInstance().getGameSpecificMessage();
	
	double AUTONSPEEDN;
	double NAUTONSPEEDN;
	//leftTalon and rightTalon
	TalonSRX lTalN;
	TalonSRX rTalN;
	
	//leftEncoder and rightEncoder
		
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
	
	//Code written by Vedh
	public positionBSwitch(double AUTONSPEED, double NAUTONSPEED, TalonSRX lTal, TalonSRX rTal, VictorSPX rVic1, 
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
		
		
	
	//String for switch/scale orientation
		
		
		
	//If switch is on our right side, we just go straight to the switch
	public void auton3posBSwitch(){
		
		
		if(gameData.substring(0, 1).equalsIgnoreCase("R")){
			
			if(timerN .get()>0&&timerN.get()<.4){
				//Go forward for small bit
			lTalN.set(ControlMode.PercentOutput, .35);	
			rTalN.set(ControlMode.PercentOutput, -.35);
			
			}else if(timerN.get()<1.9&&timerN.get()>.4){
				//Turn right
			lTalN.set(ControlMode.PercentOutput, .165);
			rTalN.set(ControlMode.PercentOutput, .165);
			
			}else if(timerN.get()>1.9&&timerN.get()<2.9){
				//Go forward
				lTalN.set(ControlMode.PercentOutput, .325 );
				rTalN.set(ControlMode.PercentOutput, -.325);
				
			}else if(timerN.get()>2.9&&timerN.get()<4.4){
				//Turn left
				lTalN.set(ControlMode.PercentOutput, -.15);
				rTalN.set(ControlMode.PercentOutput, -.15);
			}else if(timerN.get()>4.4&&timerN.get()<6){
				//Go forward
				lTalN.set(ControlMode.PercentOutput, .26);
				rTalN.set(ControlMode.PercentOutput, -.26);
			}else if(timerN.get()>5.8&&timerN.get()<7){
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				armTalN.set(ControlMode.PercentOutput, .1);
			}else if(timerN.get()>7&&timerN.get()<8){
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				armTalN.set(ControlMode.PercentOutput, 0);
				intakeTalN.set(ControlMode.PercentOutput, -.5);
			}else if(timerN.get()>8){
				intakeTalN.set(ControlMode.PercentOutput, 0);
			}
				
		//If switch is on our left side: 
			//We go straight, take a left, go straight, take a right, go straight again
		}else if(gameData.substring(0, 1).equalsIgnoreCase("L")){
			
			if(timerN.get()<2){
				//Go straight
				lTalN.set(ControlMode.PercentOutput, .15);
				rTalN.set(ControlMode.PercentOutput, -.15);
				
			}else if(timerN.get()>2 && timerN.get()<3.5){
				//Turn left
				lTalN.set(ControlMode.PercentOutput, -.175);
				rTalN.set(ControlMode.PercentOutput, -.175);
				
			}else if(timerN.get()>3.5&&timerN.get()<5.5){
				//Go straight
				lTalN.set(ControlMode.PercentOutput, .325);
				rTalN.set(ControlMode.PercentOutput, -.325);
				
			}else if(timerN.get()>5.5&&timerN.get()<7.4){
				//Turn right
				lTalN.set(ControlMode.PercentOutput, .175);
				rTalN.set(ControlMode.PercentOutput, .175);
				
			}else if(timerN.get()>7.4&&timerN.get()<9.4){
				//Go straight to switch
				lTalN.set(ControlMode.PercentOutput, .23);
				rTalN.set(ControlMode.PercentOutput, -.23);
			}else if (timerN.get()>9.4 && timerN.get()<11.4){
				//Stops once at switch
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				//armTalN.set(ControlMode.PercentOutput, .1);
			}else if (timerN.get()>11.4 && timerN.get()<13.4){
				//armTalN.set(ControlMode.PercentOutput, 0);
				intakeTalN.set(ControlMode.PercentOutput, -.5);
			}else if(timerN.get()>13.4){
				intakeTalN.set(ControlMode.PercentOutput, 0);
			}
		}
	}
}



	
