package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Teleoptest extends LinearOpMode {
    public CRServo intake = null;
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor forearm = null;
    public DcMotor arm = null;
    public Servo ClawServo = null;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;
        double leftFast;
        double rightFast;
        double driveFast;
        double turnFast;
        // Define and Initialize Motors
        leftDrive = hardwareMap.get(DcMotor.class, "leftwheels");
        rightDrive = hardwareMap.get(DcMotor.class, "rightwheels");
        forearm = hardwareMap.get(DcMotor.class, "forearm");
        intake = hardwareMap.get(CRServo.class, "intake1");
        arm = hardwareMap.get(DcMotor.class, "arm");
        ClawServo = hardwareMap.get(Servo.class, "claw");


        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        forearm.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.left_trigger > 0) {
                drive = -gamepad1.left_stick_y/1.5;
                turn  =  gamepad1.right_stick_x/1.5;

                left  = drive + turn;
                right = drive - turn;

                max = Math.max(Math.abs(left), Math.abs(right));
                if (max > 1.0)
                {
                    left /= max;
                    right /= max;
                }

                // Output the safe vales to the motor drives.
                leftDrive.setPower(left);
                rightDrive.setPower(right);
            }else if (gamepad1.right_trigger > 0){
                driveFast = -gamepad1.left_stick_y;
                turnFast  =  gamepad1.right_stick_x;

                leftFast  = driveFast + turnFast;
                rightFast = driveFast - turnFast;

                max = Math.max(Math.abs(leftFast), Math.abs(rightFast));
                if (max > 1.0)
                {
                    leftFast /= max;
                    rightFast /= max;
                }

                // Output the safe vales to the motor drives.
                leftDrive.setPower(leftFast);
                rightDrive.setPower(rightFast);
            }else {
                leftDrive.setPower(0);
                rightDrive.setPower(0);
            }


            //start forearm code
            if (gamepad2.dpad_up) {
                forearm.setPower(.35); // Move arm up
            } else if (gamepad2.dpad_down) {
                forearm.setPower(-.35); // Move arm down
            } else if (gamepad2.dpad_left) {
                forearm.setPower(0); // Stop arm
            } else {
                forearm.setPower(0);
            }
            //end forearm code

            //start intake code
            if (gamepad2.left_bumper) {
                intake.setPower(0);
            } else if (gamepad2.b) {
                intake.setPower(-2);
            } else if (gamepad2.a) {
                intake.setPower(2);
            } else {
                intake.setPower(0);
            }
            telemetry.addData("Servo Position", intake.getPower());
            // telemetry.addData("Motor Power", CCOpModeServo.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
            //end intake code

            //start arm code
            if (gamepad2.right_trigger > 0) {
                arm.setPower(.25); // Move arm up
            } else if (gamepad2.left_trigger > 0) {
                arm.setPower(-.25); // Move arm down
            } else if(gamepad2.right_bumper) {
                arm.setPower(0); // Stop arm
            } else {
                arm.setPower(0);
            }
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm.setPower(0);
            //end arm code

            //start claw code
            if(gamepad2.dpad_right) {
                // move to 0 degrees.
                ClawServo.setPosition(0);
            } else if (gamepad2.y) {
                // move to 45 degrees.
                ClawServo.setPosition(0.25);
            } else if (gamepad2.x) {
                // move to 180 degrees.
                ClawServo.setPosition(1);
            }
            telemetry.addData("Servo Position", ClawServo.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();


        }
    }
}
