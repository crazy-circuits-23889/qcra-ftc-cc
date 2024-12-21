package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
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

        CCOpModeServo  = hardwareMap.get(Servo.class, "intake");

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.");    //
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                if(gamepad1.y) {
                    CCOpModeServo.setPosition(0);
                } else if (gamepad1.x || gamepad1.b) {
                    CCOpModeServo.setPosition(0.5);
                } else if (gamepad1.a) {
                    CCOpModeServo.setPosition(1);
                }
                telemetry.addData("Servo Position", CCOpModeServo.getPosition());
                telemetry.addData("Status", "Running");
                telemetry.update();

            }
        }
    }


}
