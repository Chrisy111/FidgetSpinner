/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3863.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */




public class Robot extends IterativeRobot {
	WPI_TalonSRX LeftTalonA = new WPI_TalonSRX(13);
	WPI_TalonSRX RightTalonA = new WPI_TalonSRX(2);
	WPI_TalonSRX LeftTalonB = new WPI_TalonSRX(12);
	WPI_TalonSRX RightTalonB = new WPI_TalonSRX(3);
	
	Joystick joy1 = new Joystick(1);
	
	DoubleSolenoid transmission = new DoubleSolenoid(4 , 5);
	
	private static final String kDefaultAuto = "Default";
	
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	private void motorspeed(double left, double right) {
		LeftTalonA.set(left);
		LeftTalonB.set(left);
		RightTalonA.set(right * -1);
		RightTalonB.set(right * -1);
		
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
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
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
		motorspeed(1, -1);
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		double forward = joy1.getRawAxis(1)* -1;
		if(Math.abs(forward)< 0.05) {
			forward = 0;
		}
		System.out.println(forward);
		
		
		double lr = joy1.getRawAxis(2)* -1;
		if(Math.abs(forward)< 0.05) {
			forward = 0;}
		
		
		double leftwheels = forward + lr * -1;
		double rightwheels = forward + lr;
		motorspeed(leftwheels, rightwheels);
		
		if(joy1.getRawButton(3)) {
			transmission.set(DoubleSolenoid.Value.kForward);
			
		
		}
		
		else {
			
		
		transmission.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
		
		motorspeed(-1, -1);
	}
}
