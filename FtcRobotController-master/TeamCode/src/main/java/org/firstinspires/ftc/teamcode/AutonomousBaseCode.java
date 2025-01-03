package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//test git bash
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {
    public DcMotor leftDrive   = null;
    public DcMotor  rightDrive  = null;


    @Override
    public void runOpMode() throws InterruptedException {
        leftDrive  = hardwareMap.get(DcMotor.class, "leftwheels");
        rightDrive = hardwareMap.get(DcMotor.class, "rightwheels");

        waitForStart();

        while (opModeIsActive()) {
            //Notes: The time is in milliseconds btw 1000 milliseconds is 1 second
            //the power is power LOL
            //when editing just saying you have to use stop after each one
            forward(.5, 5000);
            Stop();
            turnLeft(.5, 5000);
            Stop();
            turnRight(.5, 5000);
            Stop();
            backwards(.5, 5000);

        }
    }
    public void forward (double power, long time) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }
    public void turnLeft (double power, long time) {
        rightDrive.setPower(power);
        leftDrive.setPower(-power);
    }
    public void turnRight (double power, long time) {
        leftDrive.setPower(power);
        rightDrive.setPower(-power);
    }
    public void backwards (double power, long time) {
        leftDrive.setPower(-power);
        rightDrive.setPower(-power);
    }
    public void Stop (){
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }
    //DONT EDIT THE CAPITAL S

    @com.qualcomm.robotcore.eventloop.opmode.Autonomous
    public static class ArmAuto extends LinearOpMode {
        public DcMotor Arm = null;
        //sigma

        @Override
        public void runOpMode() throws InterruptedException {
            Arm = hardwareMap.get(DcMotor.class, "arm");


            waitForStart();


            while (opModeIsActive()) {
    //Note: we just edit this part to the right timings for the ring.
                Up(.5, 5000);
                Stop();
                Down(.5, 5000);
                Stop ();
            }
        }
            public void Up (double power, long time){

                Arm.setPower(power);

            }

            public void Down (double power, long time){

                Arm.setPower(-power);

            }

            public void  Stop (){

                Arm.setPower(0);

            }





    }
}
