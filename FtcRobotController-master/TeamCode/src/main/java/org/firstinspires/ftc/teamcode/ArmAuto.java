package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class ArmAuto extends LinearOpMode {
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
