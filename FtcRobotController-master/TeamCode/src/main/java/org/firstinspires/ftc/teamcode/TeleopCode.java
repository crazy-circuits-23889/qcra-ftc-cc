package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TeleopCode extends LinearOpMode{
    public Servo servo = null;
    public DcMotor leftdrive = null;
    public DcMotor rightdrive = null;
    private DcMotor motortest;

    @Override

