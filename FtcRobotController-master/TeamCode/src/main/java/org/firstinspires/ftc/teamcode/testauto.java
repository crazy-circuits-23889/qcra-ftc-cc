package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="testauto", preselectTeleOp="CCOpModeServo")
public class testauto extends LinearOpMode {
    public Servo servo = null;

    @Override
    public void runOpMode() {


        servo  = hardwareMap.get(Servo.class, "intake");

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
                // move to 0 degrees.
                servo.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                servo.setPosition(0.5);
            } else if (gamepad1.a) {
                // move to 180 degrees.
                servo.setPosition(1);
            }
            telemetry.addData("Servo Position", servo.getPosition());
            telemetry.addData("Target Power", tgtPower);
            // telemetry.addData("Motor Power", CCOpModeServo.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}

