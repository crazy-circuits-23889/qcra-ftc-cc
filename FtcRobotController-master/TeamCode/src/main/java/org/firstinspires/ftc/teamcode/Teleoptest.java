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

    // Speed variables
    double slowSpeed = 0.8;  // Slow speed factor like as in math factors
    double fastSpeed = 1.2;  // Fast speed factor like as in math factors
    double currentSpeed = slowSpeed; // Start at slow speed
    double armFast = -.7;
    double armSlow = .45;
    double armSpeed = armSlow;
    double stopSlow = 0;
    double stopFast = -.7;
    double stopCurrent = stopSlow;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;

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
        telemetry.addData(">", "Robot Ready.  Press START.");
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.left_trigger > 0) {
                currentSpeed = slowSpeed;  // Set to slow mode
                telemetry.addData("Speed Mode", "Slow");
            } else if (gamepad1.right_trigger > 0) {
                currentSpeed = fastSpeed;  // Set to fast mode
                telemetry.addData("Speed Mode", "Fast");
            }

            drive = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            left = drive + turn;
            right = drive - turn;

            // Apply current speed setting
            left *= currentSpeed;
            right *= currentSpeed;

            // Ensure values do not exceed -1 to 1 range
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }

            // Set motor powers
            leftDrive.setPower(left);
            rightDrive.setPower(right);

            // Start forearm code
            if (gamepad2.dpad_up) {
                forearm.setPower(0.35); // Move arm up
            } else if (gamepad2.dpad_down) {
                forearm.setPower(-0.35); // Move arm down
            } else {
                forearm.setPower(0); // Stop arm
            }

            // Start intake code

            if (gamepad2.b) {
                intake.setPower(-2);
            } else if (gamepad2.a) {
                intake.setPower(2);
            } else {
                intake.setPower(0);
            }

            telemetry.addData("Servo Position", intake.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

            // Start arm code
            if (gamepad2.right_trigger > 0) {
                arm.setPower(armSpeed); // Move arm up
            } else if (gamepad2.right_trigger == 1) {
                arm.setPower(armSpeed);
            } else if (gamepad2.left_trigger > 0) {
                arm.setPower(-armSpeed); // Move arm down
            } else if (gamepad2.left_trigger == 1) {
                arm.setPower(-armSpeed);
            } else if (gamepad2.right_bumper) {
                arm.setPower(0);
                armSpeed = armFast;
                stopCurrent = stopFast;
            } else if (gamepad2.left_bumper) {
                arm.setPower(0);
                armSpeed = armSlow;
                stopCurrent = stopSlow;
            } else {
                arm.setPower(stopCurrent); // Stop arm
            }




            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Start claw code
            if (gamepad2.dpad_right) {
                ClawServo.setPosition(0); // move to 0 degrees
            } else if (gamepad2.y) {
                ClawServo.setPosition(0.25); // move to 45 degrees
            } else if (gamepad2.x) {
                ClawServo.setPosition(1); // move to 180 degrees
            }

            telemetry.addData("Servo Position", ClawServo.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}
