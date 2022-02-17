// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.subsystems.HoodProtype;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final HoodProtype protoHypeSub = new HoodProtype();
  private VideoSource[] enumerateSources;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    initializeCamera();
  }
//Creates and establishes camera streams for the shuffleboard ~Ben
  private void initializeCamera() {
    CameraServer.startAutomaticCapture();
    enumerateSources = VideoSource.enumerateSources();

    System.out.println("            Number of Sources:" + enumerateSources.length);
    System.out.println("            Name of Source" + enumerateSources[0].getName());

    if (enumerateSources.length > 0 && enumerateSources[0].getName().contains("USB")) {
      Shuffleboard.getTab("Camera Test Tab").add("Camera 1", enumerateSources[0]).withPosition(0, 0).withSize(4, 4)
          .withWidget(BuiltInWidgets.kCameraStream);
    }
    HttpCamera limelight = new HttpCamera("Limelight", "http://10.43.29.11:5800");
    CameraServer.startAutomaticCapture(limelight);

    Shuffleboard.getTab("Camera Test Tab").add("Limelight Baby", limelight).withPosition(4, 0).withSize(2, 2)
        .withWidget(BuiltInWidgets.kCameraStream);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }
}
