package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class mecanumWheelsTest1 extends LinearOpMode {
    public CRServo intake = null;
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;

    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;

    public DcMotor forearm = null;
    public DcMotor arm = null;
    public ServoImplEx ClawServo = null;

    // Speed variables
    double normalMode;
    double strafeMode;
    double currentMode = normalMode; //Letting us know what mode that we start with
    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;
        double vertical = 0;
        double horizontal = 0;
        double pivot = 0;

        // Define and Initialize Motors
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftfrontwheels");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftbackwheels");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightfrontwheels");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightbackwheels");
        forearm = hardwareMap.get(DcMotor.class, "forearm");
        intake = hardwareMap.get(CRServo.class, "intake1");
        arm = hardwareMap.get(DcMotor.class, "arm");
        ClawServo = hardwareMap.get(ServoImplEx.class, "claw");
        ClawServo.setPwmRange(new PwmControl.PwmRange(500, 2500));
        ClawServo.scaleRange(0.8, 1);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        forearm.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");
        //telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (gamepad1.left_bumper){
                currentMode = normalMode;
            } else if (gamepad1.right_bumper) {
                currentMode = strafeMode;
            }

            if (currentMode == normalMode){
                drive = -gamepad1.left_stick_y*.8;
                turn = gamepad1.right_stick_x*.8;

                left = drive + turn;
                right = drive - turn;

                // Ensure values do not exceed -1 to 1 range
                max = Math.max(Math.abs(left), Math.abs(right));
                if (max > 1.0) {
                    left /= max;
                    right /= max;
                }

                // Set motor powers
                leftFrontDrive.setPower(left);
                leftBackDrive.setPower(left);
                rightFrontDrive.setPower(right);
                rightBackDrive.setPower(right);

            }

            if (currentMode == strafeMode) {
                vertical = -gamepad1.left_stick_y;
                horizontal = gamepad1.left_stick_x;
                pivot = gamepad1.right_stick_x;


                rightFrontDrive.setPower(pivot + (-vertical + horizontal));
                rightBackDrive.setPower(pivot + (-vertical - horizontal));
                leftFrontDrive.setPower(pivot + (-vertical - horizontal));
                leftFrontDrive.setPower(pivot + (-vertical + horizontal));

            }

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
            // telemetry.update();

            // Start arm code
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




            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //start claw code
            if(gamepad2.dpad_right) {
                // move to 0 degrees.
                //ClawServo.setPosition(0);
            } else if (gamepad2.y) {
                // move to 45 degrees.
                ClawServo.setPosition(0);
            } else if (gamepad2.x) {
                // move to 180 degrees.
                ClawServo.setPosition(1);
            }
            telemetry.addData("claw Position", ClawServo.getPosition());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}