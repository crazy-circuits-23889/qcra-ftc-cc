package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class CCServo extends OpMode {
    public Servo servo;
    @Override
    public void init() {
        servo  = hardwareMap.get(Servo.class, "intake");
    }

    @Override
    public void loop() {
        if(gamepad1.a){;
            servo.setPosition(0);
        }
        if(gamepad1.b){
            servo.setPosition(1);
        }
    }
}