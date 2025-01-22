package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class CCOpModeServo extends LinearOpMode {
    public CRServo servo = null;

    @Override
    public void runOpMode() {


        servo  = hardwareMap.get(CRServo.class, "intake");

        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();


        // Wait for the game to start (driver presses START)
        waitForStart();

            // run until the end of the match (driver presses STOP)
            double tgtPower;
            while (opModeIsActive()) {
                tgtPower = this.gamepad1.left_stick_y;
                //CCOpModeServo.setPower(tgtPower);
                // check to see if we need to move the servo.
                if(gamepad1.y) {

                    servo.setPower(0);
                } else if (gamepad1.x || gamepad1.b) {

                    servo.setPower(-1);
                } else if (gamepad1.a) {

                    servo.setPower(1);
                }
                telemetry.addData("Servo Position", servo.getPower());
                telemetry.addData("Target Power", tgtPower);
               // telemetry.addData("Motor Power", CCOpModeServo.getPower());
                telemetry.addData("Status", "Running");
                telemetry.update();

            }
        }
    }

