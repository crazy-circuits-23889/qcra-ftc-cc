package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
@TeleOp
public class CCOpModeServo extends LinearOpMode {
    public Servo CCOpModeServo = null;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double max;

        CCOpModeServo  = hardwareMap.get(Servo.class, "intake");

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
