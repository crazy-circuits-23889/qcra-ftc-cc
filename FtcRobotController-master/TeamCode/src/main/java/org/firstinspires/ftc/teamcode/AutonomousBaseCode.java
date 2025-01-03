
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
    public class AutonomousBaseCode extends LinearOpMode {
    static final double inc   = 0.05;
    static final int    Cycle    =   50;
    static final double maxPos     =  1.0;
    static final double minPos     =  0.0;

    double  position = (maxPos - minPos) / 2;
    boolean rampUp = true;
    public Servo intake = null;
    public Servo claw = null;
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor Arm = null;
    public DcMotor foreArm = null;

    @Override
    public void runOpMode() {
        intake = hardwareMap.get(Servo.class, "intake");
        claw = hardwareMap.get(Servo.class, "claw");
        leftDrive = hardwareMap.get(DcMotor.class, "leftwheels");
        rightDrive = hardwareMap.get(DcMotor.class, "rightwheels");
        Arm = hardwareMap.get(DcMotor.class,"arm");
        foreArm = hardwareMap.get(DcMotor.class,"forearm");

        Arm.setDirection(DcMotorSimple.Direction.FORWARD);
        foreArm.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            basePos();
            Stop(100);
            startIntake();
            Stop(100);
            basePos();
            Stop(100);
            openClaw(1);
            Stop(100);
            closeClaw(1);
            Stop(100);
            armUp(1, 3000);
            Stop(100);
            armDown(1, 3000);
            Stop(100);
            forearmUp(1, 3000);
            Stop(100);
            forearmDown(1, 3000);
            Stop(100);
            forward(2,5000);
            Stop(100);
            backwards(2, 5000);
            Stop(100);
            turnLeft( 1, 3000);
            Stop(100);
            turnRight(1, 5000);
            Stop(10000);


        }

    }


    public void basePos (){
        intake.setPosition(0);
    }
    public void startIntake() {
        intake.setPosition(1);

    }

    public void openClaw(double position) {
        claw.setPosition(-position);
    }
    public void closeClaw(double position) {
        claw.setPosition(position);
    }

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


