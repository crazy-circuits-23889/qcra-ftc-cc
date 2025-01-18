package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp
public class Test1 extends OpMode {

    DcMotor motorL, motorR;
    //This is a variable it is stating that motorL and motorR are 2 motors
    //motor1 is the left side wheels
    //motor2 is the right side wheels

    @Override
    public void init() {
        motorL = hardwareMap.get (DcMotor.class, "leftwheels");
        motorR = hardwareMap.get (DcMotor.class, "rightwheels");
//This is making the control hub understand what motorL and motorR are
    }

    @Override
    public void loop() {

        float xL = gamepad1.left_stick_x;
        if (gamepad1.left_stick_x > 0) {
            motorL.setDirection(DcMotorSimple.Direction.FORWARD);
            motorL.setPower(xL);

//Test: x is horizontal so move the left stick to the right to make the motor move
//This controls the left side motor
        }
        float yL = gamepad1.left_stick_y;
        if (gamepad1.left_stick_y > 0) {
            motorL.setDirection(DcMotorSimple.Direction.REVERSE);
            motorL.setPower(yL);

            telemetry.addData("Status", "Initialized");
            telemetry.update();
            // Wait for the game to start (driver presses PLAY)

             // run until t
                telemetry.addData("Status", "Running");
                telemetry.update();


//This should do the same thing as the code above this one but make it work for
//the vertical movement of the joystick and it should make the motor go the opposite
//direction of the code above
            }

        }
    }
