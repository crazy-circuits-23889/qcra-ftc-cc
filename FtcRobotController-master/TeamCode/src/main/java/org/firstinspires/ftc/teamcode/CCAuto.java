
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="CCAuto", preselectTeleOp="CCOpModeServo")
public class CCAuto extends LinearOpMode {
    static final double INCREMENT   = 0.05;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;

    public Servo intakeServo = null;
    public Servo clawServo = null;
    public DcMotor leftWheelsMotor = null;
    public DcMotor rightWheelsMotor = null;
    public DcMotor armMotor = null;
    public DcMotor forearmMotor = null;

    @Override
    public void runOpMode() {
        intakeServo = hardwareMap.get(Servo.class, "intake");
        clawServo = hardwareMap.get(Servo.class, "claw");
        leftWheelsMotor = hardwareMap.get(DcMotor.class, "leftwheels");
        rightWheelsMotor = hardwareMap.get(DcMotor.class, "rightwheels");
        armMotor = hardwareMap.get(DcMotor.class,"arm");
        forearmMotor = hardwareMap.get(DcMotor.class,"forearm");

        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        forearmMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            takeSample(); //intake
            sleep(1000);
            leaveSample(); //intake
            sleep(1000);
            moveClaw(-0.5); // open claw
            sleep(1000);
            moveClaw(0.5); // close claw
            sleep(1000);
            moveArm(0.5); // move arm up
            sleep(1000);
            moveArm(0); // stop earm
            sleep(1000);
            moveArm(-0.5); // move earm down
            sleep(1000);
            moveForearm(0.5); // move forearm up
            sleep(1000);
            moveForearm(0); // stop forearm
            sleep(1000);
            moveForearm(-0.5); // move forearm down
            sleep(1000);

            forward(.5, 5000);
            Stop();
            sleep(1000);
            turnLeft(.5, 5000);
            Stop();
            sleep(1000);
            turnRight(.5, 5000);
            Stop();
            sleep(1000);
            backwards(.5, 5000);
        }
    }

    public void takeSample() {
        telemetry.addData("takesample",rampUp);
        telemetry.addData("takesample",intakeServo.getPosition());
        telemetry.update();
        if (rampUp) {
            // Keep stepping up until we hit the max value.
            position += INCREMENT ;
            if (position >= MAX_POS ) {
                position = MAX_POS;
                rampUp = !rampUp;   // Switch ramp direction
            }
        }

        intakeServo.setPosition(position);
        sleep(CYCLE_MS);
        idle();
        telemetry.addData("takesample",intakeServo.getPosition());
        telemetry.addData("takesample",rampUp);
        telemetry.update();
    }

    public void leaveSample()  {
        telemetry.addData("leavesample",rampUp);
        telemetry.addData("leavesample",intakeServo.getPosition());
        telemetry.update();
        if (!rampUp) {
            // Keep stepping up until we hit the max value.
            position -= INCREMENT ;
            if (position >= MAX_POS ) {
                position = MAX_POS;
                rampUp = !rampUp;   // Switch ramp direction
            }
        }

        intakeServo.setPosition(position);
        sleep(CYCLE_MS);
        idle();
        telemetry.addData("takesample",rampUp);
        telemetry.addData("leavesample",intakeServo.getPosition());
        telemetry.update();
    }

    public void moveClaw(double position)  {
        clawServo.setPosition(position);
    }

    public void moveArm(double power)  {
        armMotor.setPower(power);
    }
    public void moveForearm(double power) {
        forearmMotor.setPower(power);
    }

    public void forward (double power, long time) {
        leftWheelsMotor.setPower(power);
        rightWheelsMotor.setPower(power);
    }

    public void turnLeft (double power, long time) {
        rightWheelsMotor.setPower(power);
        leftWheelsMotor.setPower(-power);
    }
    public void turnRight (double power, long time) {
        leftWheelsMotor.setPower(power);
        rightWheelsMotor.setPower(-power);
    }
    public void backwards (double power, long time) {
        leftWheelsMotor.setPower(-power);
        rightWheelsMotor.setPower(-power);
    }
    public void Stop (){
        leftWheelsMotor.setPower(0);
        rightWheelsMotor.setPower(0);
    }
}


