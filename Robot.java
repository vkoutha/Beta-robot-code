/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team193.robot;



import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	private static final String auton5 = "Do nothing";
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
	
	DigitalInput encALim = new DigitalInput(5);
	DigitalInput encBLim = new DigitalInput(6);
	DigitalInput encCLim = new DigitalInput(7);
	DigitalInput encAutoLine = new DigitalInput(8);
	DigitalInput doNothing = new DigitalInput(9);

	
	//Encoder lEnc = new Encoder(5, 0);
	//Encoder rEnc = new Encoder(4, 0);
	
	Timer timer = new Timer();
	
	Compressor comp = new Compressor(0);
	
	DoubleSolenoid rPiston = new DoubleSolenoid(0, 4, 5);
	DoubleSolenoid lPiston = new DoubleSolenoid(0, 6, 7);
	
	//Vinays code
	armControl armC = new armControl(gControl, armTal, armVic, upSwitch, downSwitch);
	
	//Vedhs code
	positionBSwitch posBSwitch = new positionBSwitch(AUTONSPEED, NAUTONSPEED, lTal, rTal, rVic1, rVic2, lVic1, lVic2, timer, armTal, armVic,
			intakeTal, intakeVic, gameData);
	/*pBS is NOT for PBS kids channel where you can watch Cyberchase or whatever that show with the giant talking computer giving out
	missions was called*/
	
	//Agrons code
	driveTrainIntake dTI = new driveTrainIntake(lJoy, rJoy, lTal, rTal, lVic1, lVic2, rVic1, rVic2
				, gControl, intakeTal, intakeVic, intakeSwitch);
	
	//Madhur and Kevin's code
	crossAutoLine crossAL = new crossAutoLine(rTal, lTal, rVic1, lVic1, rVic2, lVic2, timer);
	
	//Madhur and Kevin's code
	camera cam = new camera();
	
	//Nick's code
	startingPositionPickerAuton startingPositionPicker = new startingPositionPickerAuton(lTal, rTal, lVic1, lVic2, rVic1, rVic2, armTal
			, armVic, intakeTal, intakeVic, timer, position, startingPosition, gameData);
	
	//Gavin and Moesha's code
	positionASwitchScale posASwitchScale = new positionASwitchScale(rTal, lTal, armTal, armVic, intakeTal,
			intakeVic, rVic1, rVic2, lVic1, lVic2, timer, gameData);
	
	//Gavin and Moesha's code
	positionCSwitchScale posCSwitchScale = new positionCSwitchScale(rTal, lTal, armTal, armVic, intakeTal,
			intakeVic, rVic1, rVic2, lVic1, lVic2, timer, gameData);
	
	pneumatics pneumatics = new pneumatics(rPiston, lPiston, gControl);
	
	encAL eAL = new encAL(lTal, rTal);
	
	encPosA ePA = new encPosA(rTal, lTal, armTal, armVic, intakeTal, intakeVic, rVic1, rVic2, lVic1, lVic2, timer, upSwitch, lPiston, rPiston);
	
	encPosB ePB= new encPosB(rTal, lTal, rVic1, rVic2, lVic1, lVic2, timer, armTal, armVic, intakeTal, intakeVic, lPiston, rPiston);
	
	encPosC ePC = new encPosC(rTal, lTal, armTal, armVic, intakeTal, intakeVic, rVic1, rVic2, lVic1, lVic2, timer, upSwitch, lPiston, rPiston);
	

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		
		//Make sure editable mode is disabled on SmartDashboard
		autonChooser.addDefault("Cross autoline", auton1); //Madhur and Kevin ---- 14 Characters
		autonChooser.addObject("Position A (L)", auton2); //Gavin and Moesha ---- 15 Characters
		autonChooser.addObject("Position B (M)", auton3); //Vedh ---- 15 Characters
		autonChooser.addObject("Position C (R)", auton4); //Gavin and Moesha ---- 15 Characters
		autonChooser.addObject("Do nothing", auton5); // ---- 10 Characters
		
		SmartDashboard.putData("Auton Modes", autonChooser); 
		
		comp.start();
		
		//Starts the robot's camera
		cam.startCam();
		
		//Right victors following rightTalonSRX
		rVic1.follow(rTal);
		rVic2.follow(rTal);
		rTal.setSensorPhase(false);
		
		//Left victors following leftTalonSRX
		lVic1.follow(lTal);
		lVic2.follow(lTal);
		lTal.setSensorPhase(true);

		//Arm victor following Arm talon
		armTal.setInverted(true);
		armTal.setSensorPhase(true);
		
		armVic.follow(armTal);
		
		intakeVic.setInverted(true);
		//Setting Intake victor opposite of intake talon
				
		lTal.setSelectedSensorPosition(0, 0, 0);
		rTal.setSelectedSensorPosition(0, 0, 0);
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
		
		lPiston.set(DoubleSolenoid.Value.kForward);
		rPiston.set(DoubleSolenoid.Value.kForward);
		
		intakeVic.follow(intakeTal);

		autonSelected = autonChooser.getSelected(); //Gets selected auton mode from the SmartDashboard
	
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		
		System.out.println("Autonomous mode selected: " + autonSelected);
		
		gameData = DriverStation.getInstance().getGameSpecificMessage(); //Getting Switch-Scale orientation from DriverStation/Field
		
		//ENCODER AUTON CLASSES
		ePA.gameData = this.gameData;
		ePB.gameData = this.gameData;
		ePC.gameData = this.gameData;
		
		System.out.println("Orientation of switch and scale: " + gameData);
		
		lTal.setSelectedSensorPosition(0, 0, 0);
		rTal.setSelectedSensorPosition(0, 0, 0);

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
				
		
		if(encALim.get() == false){
			
			ePA.start();
			
		}else if (encBLim.get() == false){
			
			ePB.start();
			
		}else if (encCLim.get() == false){
			
			ePC.start();
			
		}else if (encAutoLine.get() == false){
			
			eAL.start();
			
		}else if (doNothing.get() == false){
			
			//Do nothing
			
		}else{
			
		eAL.start();
			
		}
		
		
		/*System.out.println(rTal.getSelectedSensorPosition(0));

		
		if (finalAuton == true){
		autonSelected = autonChooser.getSelected();
		System.out.println("AutonPeriodic selected: " + autonSelected);
		}
		
		finalAuton = false;
		
		switch (autonSelected) {
		

			case auton1:
				
				//Autoline auton
				eAL.start();
				
				break;
				
			case auton2:
				
				//Position A auton
			//	ePA.start();
				eAL.start();

				break;
			case auton3:
				
				//Position B auton
				//ePB.start();

				eAL.start();

				break;
				
			case auton4:
			
				//Position C auton
				//ePC.start();

				eAL.start();

				break;
				
			case auton5:
				
				eAL.start();
				
			default: 
				
				
				break;
		
				
		}*/
	
	
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	 
	public void teleopPeriodic() {
		
		
		
		intakeVic.valueUpdated(); //Unfollowing TalonSRX from auton mode
		
		pneumatics.start();
		armC.armStart();
		dTI.dTIntake();
		//System.out.println("Left encoder: " + lTal.getSelectedSensorPosition(0));
		//System.out.println("Right encoder: " + rTal.getSelectedSensorPosition(0));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		

	}
}
