package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class ArmTest2 extends OpMode {

    DcMotor arm;
    //This is a variable it is stating that motorL and motorR are 2 motors
    //motor1 is the left side wheels
    //motor2 is the right side wheels

    @Override
    public void init() {
        arm = hardwareMap.get(DcMotor.class, "arm");
    }

    @Override
    public void loop() {

        if (gamepad2.right_trigger > 0 & gamepad2.left_trigger == 0) {
            arm.setDirection(DcMotorSimple.Direction.FORWARD);
            arm.setPower(1);
        } else if (gamepad2.left_trigger > 0 & gamepad2.right_trigger == 0) {
            arm.setDirection(DcMotorSimple.Direction.REVERSE);
            arm.setPower(1);
        } else {
        arm.setTargetPosition(arm.getCurrentPosition());
        arm.setPower(.02);
    }


    }

}