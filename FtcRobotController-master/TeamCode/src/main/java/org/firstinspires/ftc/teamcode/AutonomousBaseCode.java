
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm. robotcore. hardware. Servo. Direction;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
    public class AutonomousBaseCode extends LinearOpMode {

    public CRServo intake = null;
    public Servo claw = null;
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor Arm = null;
    public DcMotor foreArm = null;

    @Override
    public void runOpMode() {
        intake = hardwareMap.get(CRServo.class, "intake");
        claw = hardwareMap.get(Servo.class, "claw");
        leftDrive = hardwareMap.get(DcMotor.class, "leftwheels");
        rightDrive = hardwareMap.get(DcMotor.class, "rightwheels");
        Arm = hardwareMap.get(DcMotor.class,"arm");
        foreArm = hardwareMap.get(DcMotor.class,"forearm");

        Arm.setDirection(DcMotorSimple.Direction.FORWARD);
        foreArm.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            Stop(3000);
            startIntake(3, 10000);
            Stop(10000);
            reverseIntake(3, 10000);
            Stop(10000);
            openClaw();
            armUp(1, 3000);
            Stop(10000);
            armDown(1, 3000);
            Stop(10000);
            forearmUp(1, 3000);
            Stop(10000);
            forearmDown(1, 3000);
            Stop(10000);
            forward(2,5000);
            Stop(10000);
            backwards(2, 5000);
            Stop(10000);
            turnLeft( 1, 3000);
            Stop(10000);
            turnRight(1, 5000);
            Stop(10000);

        }

    }

    public void startIntake(double power, long time) {
        intake.setPower(power);
    }
    public void reverseIntake(double power, long time) {
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setPower(power);
    }

    public void openClaw() {

        //I am testing so once we remove he comment slashes add the double position into the parinthesis of the line above.

      //  claw.setPosition(-position);
        telemetry.addData("Position", claw.getPosition());
        telemetry.update();
    }
   // public void closeClaw(double position) {
     //   claw.setPosition(position);
    //}

    public void armUp(double power, long time)  {
        Arm.setPower(power);
    }

    public void armDown(double power, long time)  {
        Arm.setPower(-power);
    }
    public void forearmUp(double power, long time) {
        foreArm.setPower(power);
    }

    public void forearmDown(double power, long time) {
        foreArm.setPower(power);
    }

    public void forward (double power, long time) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

    public void turnLeft (double power, long time) {
        rightDrive.setPower(power);
        leftDrive.setPower(-power);
    }
    public void turnRight (double power, long time) {
        leftDrive.setPower(power);
        rightDrive.setPower(-power);
    }
    public void backwards (double power, long time) {
        rightDrive.setPower(-power);
        leftDrive.setPower(-power);
    }
    public void Stop (long time){
        rightDrive.setPower(0);
        leftDrive.setPower(0);
    }
}


