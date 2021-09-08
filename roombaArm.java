package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "roombaArm", group = "")
public class roombaArm extends LinearOpMode {

  private DcMotor rightFront;
  private DcMotor leftFront;
  private DcMotor rightBack;
  private DcMotor leftBack;
  private DcMotor shoulder;
  private DcMotor elbow;
  private double rotation = 0;
  private double directionA = 1;
  private double directionB = 0;
  
  @Override
  public void runOpMode() {
    rightFront = hardwareMap.dcMotor.get("rightFront");
    leftFront = hardwareMap.dcMotor.get("leftFront");
    rightBack = hardwareMap.dcMotor.get("rightBack");
    leftBack = hardwareMap.dcMotor.get("leftBack");
    shoulder = hardwareMap.dcMotor.get("shoulder");
    elbow = hardwareMap .dcMotor.get("elbow");
    
    leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
    leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    //shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    //elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        
        //defining directions from sin graph
        directionA = Math.sin((rotation + 90)/57.3);
        directionB = Math.sin((rotation + 180)/57.3) * 1;
        rotation += gamepad1.right_stick_x/55.5;
        
        //keeps rotation between -360 and 360 so big values dont slow it down
        if (rotation > 360) {
          rotation *= -1;
        } else if (rotation < -360) {
          rotation *= -1;
        }
        
        //resets direction, press y when leftBack wheel is facing you
        if (gamepad1.y == true) {
          rotation = 0;
          directionA = 1;
          directionB = 0;
        }
        
        leftFront.setPower((gamepad1.left_stick_y * directionA) - (gamepad1.left_stick_x * directionB) - gamepad1.right_stick_x/2);
        rightBack.setPower((gamepad1.left_stick_y * directionA) - (gamepad1.left_stick_x * directionB) + gamepad1.right_stick_x/2);
        leftBack.setPower((gamepad1.left_stick_y * directionB) + (gamepad1.left_stick_x * directionA) - gamepad1.right_stick_x/2);
        rightFront.setPower((gamepad1.left_stick_y * directionB) + (gamepad1.left_stick_x * directionA) + gamepad1.right_stick_x/2);
        
        //instantly stops the motor when sticks are released, instead of coasting to stop with out it
        if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0 && gamepad1.right_stick_x == 0) {
                leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
              }
        if(gamepad1.dpad_up)
        {
          elbow.setPower(.8);
        }else{
          elbow.setPower(0);
        }
         
         if(gamepad1.a)
        {
          elbow.setPower(-.8);
        }else{
          elbow.setPower(0);
        }
        
        if(gamepad1.x)
        {
          shoulder.setPower(1);
        }else{
          shoulder.setPower(0);
        }
         if(gamepad1.b)
        {
          shoulder.setPower(-.8);
        }else{
          shoulder.setPower(0);
        }
        
        
        //putting variable data on the phones telemetry
        telemetry.addData("Stick Y", gamepad1.left_stick_y);
        telemetry.addData("DirectionA", directionA);
        telemetry.addData("DirectionB", directionB);
        telemetry.addData("Stick X", gamepad1.left_stick_x);
        telemetry.addData("Rotation", rotation);
        telemetry.update();
      }
    }
  }
}
