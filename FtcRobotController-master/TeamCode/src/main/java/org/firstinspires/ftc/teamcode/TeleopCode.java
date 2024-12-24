package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TeleopCode extends LinearOpMode {
    public Servo servo = null;
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

        // Define and Initialize Motors
        leftDrive = hardwareMap.get(DcMotor.class, "leftwheels");
        rightDrive = hardwareMap.get(DcMotor.class, "rightwheels");
        forearm = hardwareMap.get(DcMotor.class, "forearm");
        servo = hardwareMap.get(Servo.class, "intake");
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
           //start wheel code
            drive = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            // Combine drive and turn for blended motion.
            left = drive + turn;
            right = drive - turn;

            // Normalize the values so neither exceed +/- 1.0
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }


            // Output the safe vales to the motor drives.
            leftDrive.setPower(left);
            rightDrive.setPower(right);
            //end wheel code

            //start forearm code
            if (gamepad2.dpad_up) {
                forearm.setPower(0.5); // Move arm up
            } else if (gamepad2.dpad_down) {
                forearm.setPower(-0.5); // Move arm down
            } else if (gamepad2.dpad_left) {
                forearm.setPower(0); // Stop arm
            }
            //end forearm code

                //start intake code
                if (gamepad2.left_bumper) {
                    // move to 0 degrees.
                    servo.setPosition(0);
                } else if (gamepad2.b) {
                    // move to 90 degrees.
                    servo.setPosition(0.5);
                } else if (gamepad2.a) {
                    // move to 180 degrees.
                    servo.setPosition(1);
                }
                telemetry.addData("Servo Position", servo.getPosition());
                // telemetry.addData("Motor Power", CCOpModeServo.getPower());
                telemetry.addData("Status", "Running");
                telemetry.update();
                //end intake code

                //start arm code
                if (gamepad2.right_trigger == 1) {
                    arm.setPower(0.5); // Move arm up
                } else if (gamepad2.left_trigger == 1) {
                    arm.setPower(-0.5); // Move arm down
                } else if(gamepad2.right_bumper) {
                    arm.setPower(0); // Stop arm
                }
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
