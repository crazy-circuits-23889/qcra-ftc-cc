package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

// This program will have the code for mecanum wheels and also sensors

@TeleOp
public class TeleopCodeMecanumSensor extends LinearOpMode {
    public CRServo intake = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor frontRight = null;
    public DcMotor backLeft = null;
    public DcMotor forearm = null;
    public DcMotor arm = null;
    public ServoImplEx ClawServo = null;

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
        double leftBack;
        double rightBack;
        double rightFront;
        double leftFront;
        double drive;
        double turn;
        double max;

        // Define and Initialize Motors
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        forearm = hardwareMap.get(DcMotor.class, "forearm");
        intake = hardwareMap.get(CRServo.class, "intake1");
        arm = hardwareMap.get(DcMotor.class, "arm");
        ClawServo = hardwareMap.get(ServoImplEx.class, "claw");
        ClawServo.setPwmRange(new PwmControl.PwmRange(500, 2500));
        ClawServo.scaleRange(0.2, 1);


        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        forearm.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);



        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");
        //telemetry.update();

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

            double y = -gamepad1.left_stick_y; //forward
            double x = gamepad1.left_stick_x; //strafe
            double rx = gamepad1.right_stick_x; //turn

            frontLeft.setPower(-x);
            frontRight.setPower(x);
            backLeft.setPower(x);
            backRight.setPower(-x);

            rightFront = y + x + rx;
            leftFront = y - x - rx;
            rightBack = y + x - rx;
            leftBack = y - x + rx;

            // Apply current speed setting
            leftBack *= currentSpeed;
            rightBack *= currentSpeed;
            leftFront *= currentSpeed;
            rightFront *= currentSpeed;

            if(Math.abs(leftBack) > 1){
                leftBack = leftBack / Math.abs(leftBack) ;
            }
            if(Math.abs(rightBack) > 1){
                rightBack = rightBack / Math.abs(rightBack) ;
            }
            if(Math.abs(leftFront) > 1){
                leftFront = leftFront / Math.abs(leftFront) ;
            }
            if(Math.abs(rightFront) > 1){
                rightFront = rightFront / Math.abs(rightFront) ;
            }
            // Ensure values do not exceed -1 to 1 range

            // Set motor powers
            backRight.setPower(rightBack);
            frontRight.setPower(rightFront);
            backLeft.setPower(leftBack);
            frontLeft.setPower(leftFront);

            // Start forearm code
            if (gamepad2.dpad_up) {                            //swap up for down i think
                forearm.setPower(0.25); // Move arm up
            } else if (gamepad2.dpad_down) {
                forearm.setPower(-3.5); // Move arm down
            } else {
                forearm.setPower(0); // Stop arm
            }
            forearm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

