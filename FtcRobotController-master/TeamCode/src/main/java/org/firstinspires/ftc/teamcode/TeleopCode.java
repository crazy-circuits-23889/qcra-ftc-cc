package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class TeleopCode extends LinearOpMode {
    public CRServo intake = null;
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor forearm = null;
    public DcMotor arm = null;
    public ServoImplEx ClawServo = null;

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
        ClawServo = hardwareMap.get(ServoImplEx.class, "claw");
        ClawServo.setPwmRange(new PwmControl.PwmRange(500, 2500));
        ClawServo.scaleRange(0.9, 1);


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

            if (gamepad2.left_bumper) {
                intake.setPower(0);
            } else if (gamepad2.b) {
                intake.setPower(-1);
            } else if (gamepad2.a) {
                intake.setPower(1);
            }

            //start forearm code
            if (gamepad2.dpad_up) {
                forearm.setPower(0.5); // Move arm up
            } else if (gamepad2.dpad_down) {
                forearm.setPower(-0.5); // Move arm down
            } else if (gamepad2.dpad_left) {
                forearm.setPower(0); // Stop arm
            }
            forearm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            telemetry.addData("forearm position", forearm.getCurrentPosition());
            //end forearm code

            if (gamepad1.y) {

                FullStop(5000);
            }


            //start intake code

            telemetry.addData("Servo Position", intake.getPower());
            // telemetry.addData("Motor Power", CCOpModeServo.getPower());
            telemetry.addData("Status", "Running");
            //end intake code

            //start arm code
            if (gamepad2.right_trigger == 1) {
                arm.setPower(0.5); // Move arm up
            } else if (gamepad2.left_trigger == 1) {
                arm.setPower(-0.5); // Move arm down
            } else if (gamepad2.right_bumper) {
                arm.setPower(0); // Stop arm
            }
            if (arm.getCurrentPosition()>2815) {
                arm.setPower(0);
                arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            telemetry.addData("arm position", arm.getCurrentPosition());
                //end arm code

                //start claw code
            if(gamepad2.dpad_right) {
                // move to 0 degrees.
                //ClawServo.setPosition(0);
            } else if (gamepad2.y) {
                // move to 45 degrees.
                ClawServo.setPosition(1);
            } else if (gamepad2.x) {
                // move to 180 degrees.
                ClawServo.setPosition(0);
            }
            telemetry.addData("claw Position", ClawServo.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();


        }
    }
    public void FullStop (long time) {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        arm.setPower(0);
        forearm.setPower(0);
        intake.setPower(0);
    }

}
