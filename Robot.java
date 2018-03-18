/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team193.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	private static final String auton1 = "Cross autoline"; //Written by Madhur and Kevin
	private static final String auton2 = "Position A (L)"; //Written by Gavin and Moesha
	private static final String auton3 = "Position B (M)";  //Written by Vedh
	private static final String auton4 = "Position C (R)"; //Written by Gavin and Moesha
	private static final String auton5	= "Do nothing"; //Written by Nick	

	private String autonSelected;
	private SendableChooser<String> autonChooser = new SendableChooser<>();

	//String for switch/scale orientation
	String gameData;
	Preferences position;
	String startingPosition;
	
	final double AUTONSPEED = .4;
	final double NAUTONSPEED = -.4;
	
	boolean finalAuton = true;

	    
	//vic1 and vic2 follow rightTalon (they are its slaves)
	TalonSRX rTal = new TalonSRX(4);
	VictorSPX rVic1 = new VictorSPX(2);
	VictorSPX rVic2 = new VictorSPX(6);
	
	
	//LEFT TALON IS POSTIVE, RIGHT TALON IS NEGATIVE
	
	//vic3 and vic4 follow leftTalon (they are its slaves)
	TalonSRX lTal = new TalonSRX(5);
	VictorSPX lVic1 = new VictorSPX(3);	
	VictorSPX lVic2 = new VictorSPX(7);
	
	TalonSRX armTal = new TalonSRX(10);
	VictorSPX armVic = new VictorSPX(11);
	
	TalonSRX intakeTal = new TalonSRX(20);
	VictorSPX intakeVic = new VictorSPX(21);
	
	Joystick lJoy = new Joystick(0);
	Joystick rJoy = new Joystick(1);
	Joystick gControl = new Joystick(2);
	
	DigitalInput upSwitch = new DigitalInput(0); //lSwitch
	DigitalInput downSwitch = new DigitalInput(1); //rSwitch
	DigitalInput intakeSwitch = new DigitalInput(2);
	
	Timer timer = new Timer();
	
	armControl armC = new armControl(gControl, armTal, armVic, upSwitch, downSwitch);
	
	positionBSwitch posBSwitch = new positionBSwitch(AUTONSPEED, NAUTONSPEED, lTal, rTal, rVic1, rVic2, lVic1, lVic2, timer, armTal, armVic,
			intakeTal, intakeVic, gameData);
	
	//Agrons code
	driveTrainIntake dTI = new driveTrainIntake(lJoy, rJoy, lTal, rTal, lVic1, lVic2, rVic1, rVic2
				, gControl, intakeTal, intakeVic, intakeSwitch);
	
	crossAutoLine crossAL = new crossAutoLine(rTal, lTal, rVic1, lVic1, rVic2, lVic2, timer);
	
	camera cam = new camera();
	
	startingPositionPickerAuton startingPositionPicker = new startingPositionPickerAuton(lTal, rTal, lVic1, lVic2, rVic1, rVic2, armTal
			, armVic, intakeTal, intakeVic, timer, position, startingPosition, gameData);
	
	positionASwitchScale posASwitchScale = new positionASwitchScale(rTal, lTal, armTal, armVic, intakeTal,
			intakeVic, rVic1, rVic2, lVic1, lVic2, timer, gameData);
	
	positionCSwitchScale posCSwitchScale = new positionCSwitchScale(rTal, lTal, armTal, armVic, intakeTal,
			intakeVic, rVic1, rVic2, lVic1, lVic2, timer, gameData);
	
	
	//Encoder autons

	
	encAL eAL = new encAL(lTal, rTal);
	
	encPosA ePA = new encPosA(rTal, lTal, armTal, armVic, intakeTal, intakeVic, rVic1, rVic2, lVic1, lVic2, timer, upSwitch);
	
	encPosB ePB= new encPosB(rTal, lTal, rVic1, rVic2, lVic1, lVic2, timer, armTal, armVic, intakeTal, intakeVic);
	
	encPosC ePC = new encPosC(rTal, lTal, armTal, armVic, intakeTal, intakeVic, rVic1, rVic2, lVic1, lVic2, timer, upSwitch);
	
	

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		
		//Make sure editable mode is disabled on SmartDashboard
		autonChooser.addDefault("Cross autoline", auton1); //14 Characters
		autonChooser.addObject("Position A (L)", auton2); //15 Characters
		autonChooser.addObject("Position B (M)", auton3); //15 Characters
		autonChooser.addObject("Position C (R)", auton4); //15 Characters
		autonChooser.addObject("Do nothing", auton5); //10 Characters
		
		SmartDashboard.putData("Auton Modes", autonChooser); 
		 
		
		//Starts the robot's camera
		cam.startCam();
		
		//Right victors following rightTalonSRX
		rVic1.follow(rTal);
		rVic2.follow(rTal);
		
		//Left victors following leftTalonSRX
		lVic1.follow(lTal);
		lVic2.follow(lTal);
		
		//Arm victor following Arm talon
		armTal.setInverted(true);
		armTal.setSensorPhase(true);
		
		armVic.follow(armTal);
		
		intakeVic.setInverted(true);
		//Setting Intake victor opposite of intake talon
		
		timer.start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		
		intakeVic.follow(intakeTal);

		autonSelected = autonChooser.getSelected(); //Gets selected auton mode from the SmartDashboard
	
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		
		System.out.println("Autonomous mode selected: " + autonSelected);
		
		//Getting gameData from field
		gameData = DriverStation.getInstance().getGameSpecificMessage(); //Getting Switch-Scale orientation from DriverStation/Field
		
		posBSwitch.gameData = this.gameData; //Transferring gameData to auton classes
		posASwitchScale.gameData = this.gameData; //Transferring gameData to auton classes
		posCSwitchScale.gameData = this.gameData; //Transferring gameData to auton classes
		
		ePA.gameData = this.gameData;
		ePB.gameData = this.gameData;
		ePC.gameData = this.gameData;
		
		System.out.println("Orientation of switch and scale: " + gameData);

		timer.reset();
		
		//Resetting encoder positions for auton mode
		lTal.setSelectedSensorPosition(0, 0, 0);
		rTal.setSelectedSensorPosition(0, 0, 0);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
				
		if (finalAuton == true){
		autonSelected = autonChooser.getSelected();
		System.out.println("AutonPeriodic selected: " + autonSelected);
		}
		
		finalAuton = false;
		
		switch (autonSelected) {
		

			case auton1:
				
				//Cross autoline
				eAL.start();
		
				break;
				
			case auton2:
				
				//Position A (Left)
				ePA.start();

				break;
			case auton3:
				
				//Position B (Middle)
				ePB.start();

				break;
				
			case auton4:
				
				//Position C (Right)
				ePC.start();

				break;
			case auton5:
				
				//Do nothing
				
			default: 
				
				
				break;
		
				
		}
	
	
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	
	public void teleopPeriodic() {
		
		
		
		intakeVic.valueUpdated(); //Unfollowing TalonSRX from auton mode
		
		armC.armStart();
		dTI.dTIntake();
	
		if(gControl.getRawButton(2) == true){
			
			System.out.println("Left encoder counts: " + lTal.getSelectedSensorPosition(0));
			
		}else if (gControl.getRawButton(3)==true){
			
			System.out.println("Right encoder counts: " + rTal.getSelectedSensorPosition(0));
			
		}else{
			
			System.out.println("Left encoder counts: " + lTal.getSelectedSensorPosition(0));
			System.out.println("Right encoder counts: " + rTal.getSelectedSensorPosition(0));
		}
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
