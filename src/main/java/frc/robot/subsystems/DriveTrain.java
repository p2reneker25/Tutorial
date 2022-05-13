// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  private MotorController left1;
  private MotorController left2;
  private MotorController right1;
  private MotorController right2;
  private MotorControllerGroup left;
  private MotorControllerGroup right;
  private DifferentialDrive dd_drive;
  /** Creates a new ExampleSubsystem. */
  public DriveTrain() {
    left1 = new VictorSP(Constants.DriveTrain.DRIVE_PWM_LEFT1);
    left2 = new VictorSP(Constants.DriveTrain.DRIVE_PWM_LEFT2);
    right1 = new VictorSP(Constants.DriveTrain.DRIVE_PWM_RIGHT1);
    right2 = new VictorSP(Constants.DriveTrain.DRIVE_PWM_RIGHT2);
    left = new MotorControllerGroup(left1, left2);
    right = new MotorControllerGroup(right1, right2);
    dd_drive = new DifferentialDrive(left, right);
  }
  public void ArcadeDrive(double x, double y){
    dd_drive.arcadeDrive(x, y);
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
