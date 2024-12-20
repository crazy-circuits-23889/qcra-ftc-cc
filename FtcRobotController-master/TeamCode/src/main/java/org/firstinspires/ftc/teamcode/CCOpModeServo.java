package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
@TeleOp
public class CCOpModeServo extends LinearOpMode {
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    public Servo CCOpModeServo = null;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;

        // Define and Initialize Motors
//        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
  //      rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        CCOpModeServo  = hardwareMap.get(Servo.class, "intake");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
    //    leftDrive.setDirection(DcMotor.Direction.REVERSE);
      //  rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            drive = -gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;

            // Combine drive and turn for blended motion.
         //   left  = drive + turn;
           // right = drive - turn;

            // Normalize the values so neither exceed +/- 1.0
            //max = Math.max(Math.abs(left), Math.abs(right));
            //if (max > 1.0)
            //{
                //left /= max;
                //right /= max;
            //}

            // Output the safe vales to the motor drives.
            //leftDrive.setPower(left);
            //rightDrive.setPower(right);

            // run until the end of the match (driver presses STOP)
            double tgtPower = 0;
            while (opModeIsActive()) {
                tgtPower = -this.gamepad1.left_stick_y;
                //CCOpModeServo.setPower(tgtPower);
                // check to see if we need to move the servo.
                if(gamepad1.y) {
                    // move to 0 degrees.
                    CCOpModeServo.setPosition(0);
                } else if (gamepad1.x || gamepad1.b) {
                    // move to 90 degrees.
                    CCOpModeServo.setPosition(0.5);
                } else if (gamepad1.a) {
                    // move to 180 degrees.
                    CCOpModeServo.setPosition(1);
                }
                telemetry.addData("Servo Position", CCOpModeServo.getPosition());
                telemetry.addData("Target Power", tgtPower);
               // telemetry.addData("Motor Power", CCOpModeServo.getPower());
                telemetry.addData("Status", "Running");
                telemetry.update();

            }
        }
    }


}
