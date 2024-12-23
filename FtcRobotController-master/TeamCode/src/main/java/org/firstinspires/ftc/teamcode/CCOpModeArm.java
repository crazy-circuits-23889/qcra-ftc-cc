package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
    public class CCOpModeArm extends LinearOpMode {
        public DcMotor leftarm = null;
public DcMotor rightarm=null;

        @Override
        public void runOpMode() {
            double left;
            double right;
            double drive;
            double turn;
            double max;

            // Define and Initialize Motors
            leftarm = hardwareMap.get(DcMotor.class, "arm");
//rightarm= hardwareMap.get(DcMotor.class, "arm");

            //arm.setDirection(DcMotor.Direction.REVERSE);
            leftarm.setDirection(DcMotor.Direction.FORWARD);
//rightarm.setDirection(DcMotorSimple.Direction.REVERSE);
            // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
            // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // rightDrive.  ```setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            // Send telemetry message to signify robot waiting;
            telemetry.addData(">", "Robot Ready.  Press START.");    //
            telemetry.update();

            // Wait for the game to start (driver presses START)
            waitForStart();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {

                // Run wheels in POV mode (note: The joystick goes negative when pushed forward, so negate it)
                // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
                // Ths way it's also easy to just drive straight, or just turn.
               // drive = gamepad1.left_trigger;
                //leftarm.setPower(drive);
                //turn = gamepad1.right_trigger;
                //leftarm.setPower(turn);

                if (gamepad1.a) {
                    leftarm.setPower(0.5); // Move arm up
                } else if (gamepad1.b) {
                    leftarm.setPower(-0.5); // Move arm down
                } else if(gamepad1.x) {
                    leftarm.setPower(0); // Stop arm
                }
            }
        }
    }

