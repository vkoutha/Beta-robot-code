/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team193.robot;


//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Test

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	
	
	//This is a test
	
	private static final String auton1 = "Cross the autoline"; //Written by Madhur and Kevin
	private static final String auton2 = "Position A (left) Switch/Scale/Autoline"; //Written by Gavin and Moesha
	private static final String auton3 = "Position B (Middle) - Switch/Autoline"; //Written by Vedh
	private static final String auton4 = "Position C (right) - Switch/Scale/Autoline"; //Written by Gavin and Moesha
	private static final String auton5	= "Enter position A or B in preferences - To drop cube or not to drop cube"; //Written by Nick	
	private String autonSelected;
	private SendableChooser<String> autonChooser = new SendableChooser<>();

	//String for switch/scale orientation
	String gameData;
	
	final double AUTONSPEED = .4;
	final double NAUTONSPEED = -.4;

	 Preferences position;
	 String startingPosition;
	    
	PowerDistributionPanel pDP = new PowerDistributionPanel(1);
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
	
	
	Timer timer = new Timer();
	
	//Vinays code
	armControl armC = new armControl(gControl, armTal, armVic, upSwitch, downSwitch);
	
	//Vedhs code
	positionBSwitch posBSwitch = new positionBSwitch(AUTONSPEED, NAUTONSPEED, lTal, rTal, rVic1, rVic2, lVic1, lVic2, timer, armTal, armVic,
			intakeTal, intakeVic);
	/*pBS is NOT for PBS kids channel where you can watch Cyberchase or whatever that show with the giant talking computer giving out
	missions was called*/
	
	//Agrons code
	driveTrainIntake dTI = new driveTrainIntake(lJoy, rJoy, lTal, rTal, lVic1, lVic2, rVic1, rVic2
				, gControl, intakeTal, intakeVic);
	
	//Madhur and Kevin's code
	crossAutoLine crossAL = new crossAutoLine(rTal, lTal, rVic1, lVic1, rVic2, lVic2, timer);
	
	//Madhur and Kevin's code
	camera cam = new camera();
	
	//Nick's code
	startingPositionPickerAuton startingPositionPicker = new startingPositionPickerAuton(lTal, rTal, lVic1, lVic2, rVic1, rVic2, armTal
			, armVic, intakeTal, intakeVic, timer, position, startingPosition);
	
	//Gavin and Moesha's code
	positionASwitchScale posASwitchScale = new positionASwitchScale(rTal, lTal, armTal, armVic, intakeTal,
			intakeVic, rVic1, rVic2, lVic1, lVic2, timer);
	
	//Gavin and Moesha's code
	positionCSwitchScale posCSwitchScale = new positionCSwitchScale(rTal, lTal, armTal, armVic, intakeTal,
			intakeVic, rVic1, rVic2, lVic1, lVic2, timer);
	
	

	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		
		
		autonChooser.addDefault("Cross the autoline only" , auton1 ); //Madhur and Kevin
		autonChooser.addObject("Position A (Robot on the left)", auton2); //Gavin and Moesha
		autonChooser.addObject("Position B (Robot in the middle) - Switch/Scale/Autoline", auton3); //Vedh
		autonChooser.addObject("Position C (robot on the right) - Switch/Scale/Autoline", auton4); //Gavin and Moesha
		autonChooser.addObject("Enter position A or B in preferences - To drop cube or not to drop cube", auton5); //Nick
		
		SmartDashboard.putData("CHOOSE YA AUTON MODE MY DUDES!!!", autonChooser);
		
		
		//armTal.configNominalOutputForward(arg0, arg1);
		//armTal.configPeakOutputForward(arg0, arg1);
		 
		
		//Starts the robot's camera
		
		//Right victors following rightTalonSRX
		rVic1.follow(rTal);
		rVic2.follow(rTal);
		//Left victors following leftTalonSRX
		lVic1.follow(lTal);
		lVic2.follow(lTal);
		//Arm victor following Arm talon
		armTal.setInverted(true);
		armVic.follow(armTal);
		
		
		
		//Intake victor following inake talon
		intakeVic.setInverted(true);
		//Setting Intake victor opposite of intake talon
		
		//Start the timer
		
		//Prints out random stuff that you don't really need to know
		System.out.println("Temperature of PDP: " + pDP.getTemperature() + " Degrees Fahrenheit");
		System.out.println("Voltage through Power Distribution Panel: " + pDP.getVoltage() + " volts");
		System.out.println("Total current through all PDP channels: " + pDP.getTotalCurrent() + " amps");
		System.out.println("Total power through all PDP channels: " + pDP.getTotalEnergy() + " watts");
		
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

		autonSelected = autonChooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		
		System.out.println("Ya choosed this autonomous mode: " + autonSelected);
		
		//It prints the orientation of the scale and switch at the beginning of autonomous mode by giving it in three characters, either L or R. 
		//This tells you whether your side of the game piece is on the left or right, starting from the side closest to you.
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//position = Preferences.getInstance();
	   // startingPosition = position.getString("startingPosition", null); //Get robot starting position
		System.out.println(gameData);
        System.out.println("Starting position: " + startingPosition);

		timer.reset();
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autonSelected) {
		

			case auton1:
				//Written by Madhur and Kevin
				crossAL.cAL();
		
				break;
				
			case auton2:
				posASwitchScale.posAStart();
				break;
			case auton3:
				//Written by Vedh
				/*pBS is NOT for PBS kids channel where you can watch Cyberchase or whatever that show with the giant talking computer giving out
				missions was called*/
				posBSwitch.auton3posBSwitch();
				
				break;
				
			case auton4:
				
				posCSwitchScale.posCStart();
				
				break;
			case auton5:
				
				startingPositionPicker.positionPickerAuton();
				
			default: 
								
				break;
				
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	
	public void teleopPeriodic() {
		
		//Servo
		
		intakeVic.follow(intakeVic);
				
		//armTal.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0 ,0);
		armTal.setSensorPhase(true);
		System.out.println("Arm motor: " + armTal.getSelectedSensorVelocity(0));
		
		//System.out.println("Right motor: " + rTal.getSelectedSensorVelocity(0));
		//System.out.println("Left motor " +lTal.getSelectedSensorVelocity(0));
		armC.armStart();
		dTI.dTIntake();

		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
