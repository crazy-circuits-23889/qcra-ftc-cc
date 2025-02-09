package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class mecanumWheelsTest1 extends LinearOpMode {
    public DcMotor leftFrontDrive = null;
    public DcMotor leftBackDrive = null;

    public DcMotor rightFrontDrive = null;
    public DcMotor rightBackDrive = null;


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
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
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

            }}}}