
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
		
		  //System.out.println("Starting auto");
		  //System.out.println(startingPosition);
		  //System.out.println(gameDataN);
          
          if(startingPosition.equalsIgnoreCase("a")) { //Check if it is in position a
              
              //System.out.println("Got to here part a");
              
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
                  armTalon.set(ControlMode.Velocity, 10);
                      
                  if(gameDataN.charAt(0) == 'L'){ //Check if it is on the left
                  
                	  armTalon.set(ControlMode.Velocity, 0);
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
        	  
        	  //System.out.println("Got to part b");
        	  
        	  if(gameDataN.substring(0, 1).equalsIgnoreCase("R")){
      			
      			if(autonTimer .get()>0&&autonTimer.get()<.4){
      				//Go forward for small bit
      			leftTalon.set(ControlMode.PercentOutput, .35);	
      			rightTalon.set(ControlMode.PercentOutput, -.35);
      			
      			}else if(autonTimer.get()<1.9&&autonTimer.get()>.4){
      				//Turn right
      			leftTalon.set(ControlMode.PercentOutput, .165);
      			rightTalon.set(ControlMode.PercentOutput, .165);
      			
      			}else if(autonTimer.get()>1.9&&autonTimer.get()<2.9){
      				//Go forward
      				leftTalon.set(ControlMode.PercentOutput, .325 );
      				rightTalon.set(ControlMode.PercentOutput, -.325);
      				
      			}else if(autonTimer.get()>2.9&&autonTimer.get()<4.4){
      				//Turn left
      				leftTalon.set(ControlMode.PercentOutput, -.15);
      				rightTalon.set(ControlMode.PercentOutput, -.15);
      			}else if(autonTimer.get()>4.4&&autonTimer.get()<6){
      				//Go forward
      				leftTalon.set(ControlMode.PercentOutput, .26);
      				rightTalon.set(ControlMode.PercentOutput, -.26);
      				
      			}else if(autonTimer.get()>6&&autonTimer.get()<7){
      				//Stop moivng at switch and raise the arm
      				leftTalon.set(ControlMode.PercentOutput, 0);
      				rightTalon.set(ControlMode.PercentOutput, 0);
      				armTalon.set(ControlMode.PercentOutput, 10);
      				
      			}else if(autonTimer.get()>7&&autonTimer.get()<8){
      				//Start the intake to spit cubes out
      				leftTalon.set(ControlMode.PercentOutput, 0);
      				rightTalon.set(ControlMode.PercentOutput, 0);
      				intakeTalon.set(ControlMode.PercentOutput, -.5);
      			}else if(autonTimer.get()>8){
      				//Stop intake
      				intakeTalon.set(ControlMode.PercentOutput, 0);
      			}
      				
      		//If switch is on our left side: 
      			//We go straight, take a left, go straight, take a right, go straight again
      		}else if(gameDataN.substring(0, 1).equalsIgnoreCase("L")){
      			
      			if(autonTimer.get()<2){
      				//Go straight
      				leftTalon.set(ControlMode.PercentOutput, .15);
      				rightTalon.set(ControlMode.PercentOutput, -.15);
      				
      			}else if(autonTimer.get()>2 && autonTimer.get()<3.5){
      				//Turn left
      				leftTalon.set(ControlMode.PercentOutput, -.175);
      				rightTalon.set(ControlMode.PercentOutput, -.175);
      				
      			}else if(autonTimer.get()>3.5&&autonTimer.get()<5.5){
      				//Go straight
      				leftTalon.set(ControlMode.PercentOutput, .325);
      				rightTalon.set(ControlMode.PercentOutput, -.325);
      				
      			}else if(autonTimer.get()>5.5&&autonTimer.get()<7.4){
      				//Turn right
      				leftTalon.set(ControlMode.PercentOutput, .175);
      				rightTalon.set(ControlMode.PercentOutput, .175);
      				
      			}else if(autonTimer.get()>7.4&&autonTimer.get()<9.4){
      				//Go straight to switch
      				leftTalon.set(ControlMode.PercentOutput, .23);
      				rightTalon.set(ControlMode.PercentOutput, -.23);
      			}else if (autonTimer.get()>9.4 && autonTimer.get()<11.4){
      				//Stops once at switch
      				leftTalon.set(ControlMode.PercentOutput, 0);
      				rightTalon.set(ControlMode.PercentOutput, 0);
      				//armTalN.set(ControlMode.PercentOutput, .1);
      			}else if (autonTimer.get()>11.4 && autonTimer.get()<13.4){
      				//armTalN.set(ControlMode.PercentOutput, 0);
      				intakeTalon.set(ControlMode.PercentOutput, -.5);
      			}else if(autonTimer.get()>13.4){
      				intakeTalon.set(ControlMode.PercentOutput, 0);
      			}
      		}
        	  
              /*if(autonTimer.get() < 3) { //Move forwards for 3 seconds
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);
                  
              }else if(autonTimer.get() >= 3 && autonTimer.get() < 3.5){
            	  
            	  //armTalon.set(ControlMode.Velocity, 30);
            	  
              }
              
              //else if(autonTimer.get() >= 3.5 && autonTimer.get() < 4) {
                  
                  //rightTalon.set(ControlMode.PercentOutput, -0.2);
                  //leftTalon.set(ControlMode.PercentOutput, -0.2);
                  
              }
              
              else if(autonTimer.get() >= 4 && autonTimer.get() < 4.8) {
                  
                  rightTalon.set(ControlMode.PercentOutput, -0.2);
                  leftTalon.set(ControlMode.PercentOutput, 0.2);
              
              }
              
              else if(autonTimer.get() >= 3.5 && autonTimer.get() < 4.2) { 
                  
                  rightTalon.set(ControlMode.PercentOutput, 0); //Stop
                  leftTalon.set(ControlMode.PercentOutput, 0);
                  
                  if(gameDataN.charAt(0) == 'R'){ //Check if switch is on right
                  
                      intakeTalon.set(ControlMode.PercentOutput, -0.3); //push out cube
                      
                  }
                  
              }
              
              else{
                  
                  rightTalon.set(ControlMode.PercentOutput, 0);//stop
                  leftTalon.set(ControlMode.PercentOutput, 0);
                  
                  intakeTalon.set(ControlMode.PercentOutput, 0);//stop
                  
              }
              
              */
          }
          
          
	}
	
}
