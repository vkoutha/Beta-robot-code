package org.usfirst.frc.team193.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

//Code written by Nick
public class startingPositionPickerAuton {

		Preferences position = Preferences.getInstance();
		String startingPosition = position.getString("startingPosition", null); //Get robot starting position
	    
	    TalonSRX leftTalon;
	    TalonSRX rightTalon;
	    
	    VictorSPX leftVictor1;
	    VictorSPX leftVictor2;
	    
	    VictorSPX rightVictor1;
	    VictorSPX rightVictor2;
	    
	    TalonSRX armTalon;
	    VictorSPX armVictor;
	    
	    TalonSRX intakeTalon;
	    VictorSPX intakeVictor;
	    
	    Timer autonTimer;
	    
	    String gameDataN = DriverStation.getInstance().getGameSpecificMessage();
	
	public startingPositionPickerAuton(TalonSRX lTal, TalonSRX rTal, VictorSPX lVic1, VictorSPX lVic2, VictorSPX rVic1, VictorSPX rVic2, TalonSRX armTal
			, VictorSPX armVic, TalonSRX intakeTal, VictorSPX intakeVic, Timer timer, Preferences position, String startingPosition){
				
		leftTalon = lTal;
		rightTalon = rTal;
		leftVictor1 = lVic1;
		leftVictor2 = lVic2;
		rightVictor1 = rVic1;
		rightVictor2 = rVic2;
		armTalon = armTal;
		armVictor = armVic;
		intakeTalon = intakeTal;
		intakeVictor = intakeVic;
		autonTimer = timer;
		
	}
	public void positionPickerAuton(){
		
		  System.out.println("starting auto");
		  System.out.println(startingPosition);
		  System.out.println(gameDataN);
          
          if(startingPosition.equalsIgnoreCase("a")) { //Check if it is in position a
              
              System.out.println("Got to here part a");
              
              if(autonTimer.get() < 3.5) { //Go forwards for 3.5 seconds
              
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);

              }
              
              else if(autonTimer.get() >= 3.5 && autonTimer.get() < 4) { //Turn for 0.5s
                  
                  rightTalon.set(ControlMode.PercentOutput, 0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);
                  
                  
              }
              
              else if(autonTimer.get() >= 4 && autonTimer.get() < 4.8) { // Go forwards for 0.8 seconds
                  
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);
                  
              }
              
              else if(autonTimer.get() >= 4.8 && autonTimer.get() < 5.5) {
                  
                  rightTalon.set(ControlMode.PercentOutput, 0); // Stop
                  leftTalon.set(ControlMode.PercentOutput, 0);
              
                      
                  if(gameDataN.charAt(0) == 'L'){ //Check if it is on the left
                  
                      intakeTalon.set(ControlMode.PercentOutput, -0.3); //Push out cube
                      
                      
                  }
              }
              
              else {
                  
                  rightTalon.set(ControlMode.PercentOutput, 0);//stop
                  leftTalon.set(ControlMode.PercentOutput, 0);
                  
                  intakeTalon.set(ControlMode.PercentOutput, 0);//stop
                  
              }
                  
              }
          
          else if(startingPosition.equalsIgnoreCase("b")){ // check if starting position is B
        	  
        	  System.out.println("Got to part b");
        	  
              if(autonTimer.get() < 3) { //Move forwards for 3 seconds
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);
                  
              }
              
              /*else if(autonTimer.get() >= 3.5 && autonTimer.get() < 4) {
                  
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, -0.2);
                  
              }
              
              else if(autonTimer.get() >= 4 && autonTimer.get() < 4.8) {
                  
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);
              
              }*/
              
              else if(autonTimer.get() >= 3 && autonTimer.get() < 3.7) { 
                  
                  rightTalon.set(ControlMode.PercentOutput, 0); //Stop
                  leftTalon.set(ControlMode.PercentOutput, 0);
                  
                  if(gameDataN.charAt(0) == 'R'){ //Check if switch is on right
                  
                      intakeTalon.set(ControlMode.PercentOutput, -0.3); //push out cube
                      
                  }
                  
              }else{
                  
                  rightTalon.set(ControlMode.PercentOutput, 0);//stop
                  leftTalon.set(ControlMode.PercentOutput, 0);
                  
                  intakeTalon.set(ControlMode.PercentOutput, 0);//stop
                  
              }
          }
	}
	
}
