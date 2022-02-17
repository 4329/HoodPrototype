//Written by Ben "hecking" Durbin, 2022

package frc.robot.subsystems;

import java.util.Collections;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
// import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
// import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.wpilibj.motorcontrol.Spark;
// import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class HoodProtype extends SubsystemBase {
  private PIDController hoodPID;
  private NetworkTableEntry sparkPosition;
  private CANSparkMax hoodwheel;
  private NetworkTableEntry falconOutput;
  private NetworkTableEntry hoodSetpoint;
  // private NetworkTableEntry falconOutput14;
  private NetworkTableEntry sparkOutput;
  private TalonSRX falcon13;
  private TalonSRX falcon14;
  // private NetworkTableEntry setpointNetworkTable;
  private RelativeEncoder hoodEncoder;

  public HoodProtype() {
    falcon13 = new TalonSRX(13);
    falcon14 = new TalonSRX(14);
    falcon14.follow(falcon13);
    hoodwheel = new CANSparkMax(1, MotorType.kBrushless);

    hoodEncoder = hoodwheel.getEncoder();

    hoodPID = new PIDController(1.25, 0, 0);
    hoodwheel.setIdleMode(IdleMode.kBrake);
    // hoodPID.setTolerance(1);

    falconOutput = Shuffleboard.getTab("Hood Data").add("Falcon Output Percent", 0)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Collections.singletonMap("Block Increment", 0.01)).withPosition(4, 0).withSize(2, 1).getEntry();

    // falconOutput14 = Shuffleboard.getTab("Hood Data").add("Output Percent",
    // 0).withWidget(BuiltInWidgets.kNumberSlider)
    // .withProperties(Collections.singletonMap("Block Increment",
    // 0.01)).withPosition(6, 0).withSize(2, 1).getEntry();

    sparkOutput = Shuffleboard.getTab("Hood Data").add("Spark Output Percent", 0)
        .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Collections.singletonMap("Block Increment", .01)).withPosition(2, 2).getEntry();

    sparkPosition = Shuffleboard.getTab("Hood Data").add("Position", hoodEncoder.getPosition()).withWidget(BuiltInWidgets.kTextView)
    .withPosition(0, 4).withSize(2, 1).getEntry();

    hoodSetpoint = Shuffleboard.getTab("Hood Data").add("Set Point", hoodEncoder.getPosition()).withWidget(BuiltInWidgets.kTextView)
    .withPosition(3, 3).withSize(1, 1).getEntry();
    // Hardcodes Motor IDs and Properties
  }


  public void HoodPeriodic() {
    double hoodposition = hoodEncoder.getPosition();
    sparkPosition.setDouble(hoodposition);

    double output = hoodPID.calculate(hoodposition, hoodSetpoint.getDouble(0));
    output =  output / 29;
    // 0.15 is the feed forward value. I just didn't assign it a network table
    // entry.
    // if (output < 0) {
    //   output = output - 0.05;
    // } else {
    //   output = output + .05;
    // }

    falcon13.set(TalonSRXControlMode.PercentOutput, falconOutput.getDouble(0));
    // falcon14.set(TalonSRXControlMode.PercentOutput, falconOutput14.getDouble(0));

    hoodwheel.set(output);

    // double setpointCalculated = hoodPID.calculate(hoodposition,
    // hoodSetpoint.getDouble(0));
    // set point network table needs to go in the control mode of whatever, or if i
    // cant set the double calculated to the network table then i need to figure
    // some stuff out.

  }
}