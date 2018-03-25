package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

public class encPosB {

	String gameData = "LRL";
	
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
	
	DoubleSolenoid lPiston;
	DoubleSolenoid rPiston;

	double CPI = 4096/18.84;
	
	boolean turn = false;
	boolean timerStart = false;

	public encPosB(TalonSRX rTal, TalonSRX lTal, VictorSPX rVic1, 
			VictorSPX rVic2, VictorSPX lVic1, VictorSPX lVic2, Timer timer, TalonSRX armTal, VictorSPX armVic, TalonSRX intakeTal, VictorSPX intakeVic, DoubleSolenoid lPiston, DoubleSolenoid rPiston){
		
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
		this.lPiston = lPiston;
		this.rPiston = rPiston;
		
	
	}
	
	public void start(){
			
		//If we own the left side of the switch
		if (gameData.charAt(0) == 'L'){
			
			//Go straight for 60 inches
			if (rTalN.getSelectedSensorPosition(0) < (CPI * 40)){
				
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
			//Turn left for 18.64 inches
			}else if (rTalN.getSelectedSensorPosition(0) >= (CPI * 40) && rTalN.getSelectedSensorPosition(0) < (CPI * 63)){
				
				lTalN.set(ControlMode.PercentOutput, -.35);
				rTalN.set(ControlMode.PercentOutput, -.35);
			
			//Go forward for about 81.5 inches
			}else if (rTalN.getSelectedSensorPosition(0) >= (CPI * 63) && rTalN.getSelectedSensorPosition(0) < (CPI * 130) && turn == false){
				
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
			//Begin to turn right and raise the arm
			}else if (lTalN.getSelectedSensorPosition(0) < (CPI * 113)){
				
				turn = true;
				
				lTalN.set(ControlMode.PercentOutput, .35);
				rTalN.set(ControlMode.PercentOutput, .35);
				armTalN.set(ControlMode.Velocity, -10);

				
			//Go forward for about 79.5 inches
			}else if (lTalN.getSelectedSensorPosition(0) < (CPI * 175)){
				
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -2);
				//lPiston.set(DoubleSolenoid.Value.kForward);
				//rPiston.set(DoubleSolenoid.Value.kForward);
				
				
			}else if (lTalN.getSelectedSensorPosition(0) >= (CPI *175) && timerN.get()<1.5){
				
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				intakeTalN.set(ControlMode.PercentOutput, -.25);
				
				if(timerStart == false){
				timerN.start();
				}
				
				timerStart = true;

			}else if (lTalN.getSelectedSensorPosition(0) >= (CPI*175) && timerN.get()>1.5){
				
				intakeTalN.set(ControlMode.PercentOutput, 0);
				armTalN.setIntegralAccumulator(0, 0, 0);
				
				
			}
			
			if(DriverStation.getInstance().getMatchTime()<7){
				
				intakeTalN.set(ControlMode.PercentOutput, -.25);
				
			}
			
			
			
			
//	  -------------------------------------------------------------			
			
		//If we own the right side of the switch
		}else if (gameData.charAt(0) == 'R'){
			
			//Go forward for 60 inches
			if (lTalN.getSelectedSensorPosition(0) < (CPI * 50)){
				
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
			//Turn for 18.5 inches (to the right)
			}else if (lTalN.getSelectedSensorPosition(0) >= (CPI * 50) && lTalN.getSelectedSensorPosition(0) < (CPI * 73)){
				
				lTalN.set(ControlMode.PercentOutput, .35);
				rTalN.set(ControlMode.PercentOutput, .35);
				
			//Go forward for 35.5 inches
			}else if (lTalN.getSelectedSensorPosition(0) >= (CPI * 73) && lTalN.getSelectedSensorPosition(0) < (CPI * 109) && turn == false){
				
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				
			//Turn for 18.5 inches (to the left)
			}else if (rTalN.getSelectedSensorPosition(0) < (CPI * 91)){
				
				turn = true;
				
				lTalN.set(ControlMode.PercentOutput, -.3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -10);
				//lPiston.set(DoubleSolenoid.Value.kForward);
				//rPiston.set(DoubleSolenoid.Value.kForward);
				
				
			//Go forward for about 81.5 inches
			}else if (rTalN.getSelectedSensorPosition(0) < (CPI * 160)){
				
				turn = false;
				lTalN.set(ControlMode.PercentOutput, .3);
				rTalN.set(ControlMode.PercentOutput, -.3);
				armTalN.set(ControlMode.Velocity, -2);
			
			//Stop once at switch and start the intake
			}else if (rTalN.getSelectedSensorPosition(0) >= (CPI * 160) && timerN.get() < 1.5){
				
				lTalN.set(ControlMode.PercentOutput, 0);
				rTalN.set(ControlMode.PercentOutput, 0);
				intakeTalN.set(ControlMode.PercentOutput, -.25);
				
				if (timerStart == false){
					timerN.start();
				}
				
				timerStart = true;
				
			}else if (rTalN.getSelectedSensorPosition(0) >= (CPI * 160) && timerN.get()> 1.5){
				
				intakeTalN.set(ControlMode.PercentOutput, 0);
				armTalN.setIntegralAccumulator(0, 0, 0);
				
			}
			
			if(DriverStation.getInstance().getMatchTime()<7){
				
				intakeTalN.set(ControlMode.PercentOutput, -.25);
				
			}
			
			
			
		}//End of gameData if statements
		
		
		
		
	}//End of start()
	
}//End of class
