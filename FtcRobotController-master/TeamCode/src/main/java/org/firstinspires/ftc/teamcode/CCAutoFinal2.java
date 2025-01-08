
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="CCAutoFinal2", preselectTeleOp="CCOpModeServo")
public class CCAutoFinal2 extends LinearOpMode {
    static final double INCREMENT   = 0.05;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position

    // Define class members
    double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    boolean rampUp = true;

    public CRServo intakeCRServo = null;
    public Servo clawServo = null;
    public DcMotor leftWheelsMotor = null;
    public DcMotor rightWheelsMotor = null;
    public DcMotor armMotor = null;
    public DcMotor forearmMotor = null;
    private ElapsedTime runtime = new ElapsedTime();

    static final double     forward = 0.6;
    static final double     turn    = 0.5;

    @Override
    public void runOpMode() {
        intakeCRServo = hardwareMap.get(CRServo.class, "intake1");
        clawServo = hardwareMap.get(Servo.class, "claw");
        leftWheelsMotor = hardwareMap.get(DcMotor.class, "leftwheels");
        rightWheelsMotor = hardwareMap.get(DcMotor.class, "rightwheels");
        armMotor = hardwareMap.get(DcMotor.class, "arm");
        forearmMotor = hardwareMap.get(DcMotor.class, "forearm");

        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        forearmMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            //MOVE FORWARD FOR 3 SECONDS
            while (runtime.seconds() < 2.0) {
                turnLeft(.5);
            }
            runtime.reset();
            while (runtime.seconds() < 2) {
                turnLeft(.5);
            }
            runtime.reset();
            while (runtime.seconds() < 3) {
                forwards(.5);
            }
            runtime.reset();
            while (runtime.seconds() < 1) {
                moveArm(-0.5);
                moveClaw(1);
            }
            runtime.reset();
          /*  while (runtime.seconds() < 1.0) {
                backward(.5);
            }
            runtime.reset();
            while (runtime.seconds() < 3.0) {
                turnLeft(.5);
            }
            runtime.reset();
            while (runtime.seconds() < 1.0) {
                turnRight(0.5);
            }
            runtime.reset();
            while (runtime.seconds() < 0.5) {
                moveArm(0.5);
            }
            runtime.reset();
            while (runtime.seconds() < 1) {
                moveForearm(0.5);
            }
            runtime.reset();
            while (runtime.seconds() < 2.0) {
                takeSampleCRServo();
            }
            runtime.reset();
            while (runtime.seconds() < 2.0) {
                backward(1);
            }
            runtime.reset();
            while (runtime.seconds() < 1.0) {
                turnLeft( 1);
            }
            while (runtime.seconds() < 2.0) {
                moveArm(1);
            }
            runtime.reset();
            while (runtime.seconds() < 2.0) {
                leaveSampleCRServo();
            }
            runtime.reset();*/
        }
    }

    public void takeSampleCRServo() {
        intakeCRServo.setPower(1);
    }

    public void leaveSampleCRServo() {
        intakeCRServo.setPower(-1);
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

    public void turnRight (double power) {
        leftWheelsMotor.setPower(power);
        rightWheelsMotor.setPower(power);
    }

    public void backward (double power) {
        leftWheelsMotor.setDirection((DcMotorSimple.Direction.REVERSE));
//        rightWheelsMotor.setPower(power);
//        leftWheelsMotor.setPower(-power);
    }
    public void forwards(double power) {
        leftWheelsMotor.setDirection((DcMotorSimple.Direction.FORWARD));
//        leftWheelsMotor.setPower(-power);
//        rightWheelsMotor.setPower(power);
    }
    public void turnLeft (double power) {
        leftWheelsMotor.setPower(-power);
        rightWheelsMotor.setPower(-power);
    }
    public void Stop (){
        leftWheelsMotor.setPower(0);
        rightWheelsMotor.setPower(0);
    }
}


