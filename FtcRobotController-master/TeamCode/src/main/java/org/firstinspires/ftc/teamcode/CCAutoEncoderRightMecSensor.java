/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name="CCAutoRightEncoder", group="Robot")
public class CCAutoRightEncoder extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor fL = null;
    public DcMotor bL = null;
    public DcMotor fR = null;
    public DcMotor bR = null;
    public DcMotor armMotor = null;
    public ServoImplEx clawServo = null;
    public DcMotor forearmMotor = null;
    public CRServo intakeServo = null;
    private ElapsedTime  runtime = new ElapsedTime();
    private ColorSensor colorSensor;

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 30.24 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.543 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double ARMSPEED    = 0.75;
    static final double FOREARMSPEED = 0.75;

    @Override
    public void runOpMode() {
        // Initialize the drive system variables.
        fL = hardwareMap.get(DcMotor.class, "fL");
        bL = hardwareMap.get(DcMotor.class, "bL");
        bR = hardwareMap.get(DcMotor.class, "rB");
        fR = hardwareMap.get(DcMotor.class, "fB");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        armMotor = hardwareMap.get(DcMotor.class, "arm");
        clawServo = hardwareMap.get(ServoImplEx.class, "claw");
        clawServo.setPwmRange(new PwmControl.PwmRange(500, 2500));
        clawServo.scaleRange(0, 1);
        forearmMotor = hardwareMap.get(DcMotor.class, "forearm");
        intakeServo = hardwareMap.get(CRServo.class, "intake1");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        fL.setDirection(DcMotor.Direction.FORWARD);
        bL.setDirection(DcMotor.Direction.REVERSE);
        bR.setDirection(DcMotor.Direction.REVERSE);
        fR.setDirection(DcMotor.Direction.REVERSE);
        
        fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        forearmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        forearmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at",  "%7d :%7d",
                //leftWheelsMotor.getCurrentPosition(),
                //rightWheelsMotor.getCurrentPosition());
        armMotor.getCurrentPosition();
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        if (opModeIsActive()){
            int red = colorSensor.red();
            int green = colorSensor.green();
            int blue = colorSensor.blue();


            //telemetry.addData("White", white);
            //telemetry.update();

            //if white is detected
            if (red > 189 && green > 189 && blue > 189) {
                telemetry.addLine("White Detected- Stopping");
                lfMotor.setPower(0);
                rfMotor.setPower(0);
                rbMotor.setPower(0);
                lbMotor.setPower(0);
                sleep (1000);
                continue
                break;
                //code: how to go around and also code auto
            }

            closeclaw();
            encoderDriveForArm(ARMSPEED, 2815, 2); //moving the arm up to 2815
// Step through each leg of the path,
            // Note: Reverse movement is obtained by setting a negative distance (not speed)
            encoderDrive(DRIVE_SPEED,  12.3,  12.3 , 2.0);  // move forward for 12 inches
            encoderDrive(TURN_SPEED,   -7.5, 7.5, 2.0);  //move right to 7.5 inches - 90 degrees
            encoderDrive(DRIVE_SPEED, 5, 5, 2.0);  //  //move forward for 5 inches
            encoderDrive(TURN_SPEED,   7.5, -7.5, 2.0); // move left to 90 degrees to straighten
            encoderDrive(DRIVE_SPEED, 5, 4.2, 2.0); // move forward toward the rung
            encoderDriveForArm(ARMSPEED, 1900, 2); // moving the arm down to 1682
            openclaw(); // open the claw to move from the specimen
            encoderDrive(DRIVE_SPEED,  -6,  -6 , 2.0); // move backward by 6 inches
            encoderDrive(TURN_SPEED,  7.5,  -7.5 , 2.0); // move left by 90 degrees
            encoderDrive(DRIVE_SPEED,  19 ,  19 , 2.0); // move forward by 17 inches
            encoderDrive(TURN_SPEED,  -7.5,  7.5 , 2.0);// move right by 90 degrees
            encoderDrive(DRIVE_SPEED,  18.5,  18.5, 2.0); // move forward by 10.5 inches
            encoderDrive(TURN_SPEED,  7.5,  -7.5 , 2.0); // move left 90 degrees
            encoderDrive(DRIVE_SPEED,  4,  4, 2.0); // move forward by 1 inches
            encoderDrive(TURN_SPEED, 7.5, -7.5, 2.0);
            encoderDrive(DRIVE_SPEED, 30, 30, 2.0);




            runtime.reset();
//           encoderDriveForForearm(FOREARMSPEED, 1500, 2); //move forearm up to original position
//           encoderDrive(TURN_SPEED,   5, -5, 2.0); //turn right to reach the basket
//           encoderDriveForArm(ARMSPEED, 1682, 2); // extend the arm up to put in basket
//           encoderDriveForForearm(FOREARMSPEED, 2800, 2); //move forearm up to put in basket
//           encoderDrive(DRIVE_SPEED,   10, 10, 2.0); //move forward to reach the basket
//           while (runtime.seconds() < 2) {
//               intakeServo.setPower(-1);
//           }
//           encoderDriveForForearm(FOREARMSPEED, 126, 2); //move forearm up to original position
//           encoderDrive(TURN_SPEED,   -5, 5, 2.0); //turn left to straighten

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);  // pause to display final telemetry message.*/
        }
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // encoderDrive(DRIVE_SPEED,  12,  12 , 2.0);  // S1: Forward 47 Inches with 5 Sec timeout
        //  encoderDrive(TURN_SPEED,   7.5, -7.5, 2.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //    encoderDrive(DRIVE_SPEED, 5, 5, 2.0);  // S3: Reverse 24 Inches with 4 Sec timeout
        //      encoderDrive(TURN_SPEED,   -7.5, 7.5, 2.0);
//        encoderDrive(DRIVE_SPEED, 5, 5, 2.0);

//        runtime.reset();

        //telemetry.addData("Path", "Complete");
        //telemetry.update();
        // sleep(1000);  // pause to display final telemetry message.
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void encoderDrive(double speed, double fLInches, double bLInches, double fRInches, double bRInches double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftWheelsMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = rightWheelsMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            leftWheelsMotor.setTargetPosition(newLeftTarget);
            rightWheelsMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftWheelsMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightWheelsMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftWheelsMotor.setPower(Math.abs(speed));
            rightWheelsMotor.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftWheelsMotor.isBusy() && rightWheelsMotor.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        leftWheelsMotor.getCurrentPosition(), rightWheelsMotor.getCurrentPosition());
                telemetry.update();
            }
            // Stop all motion;
            leftWheelsMotor.setPower(0);
            rightWheelsMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            leftWheelsMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightWheelsMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
    // put new code right here not in the cubLy brckets MAINLY FOR ANISH SPECIFICALLY

    public void encoderDriveForArm(double speed,
                                   int targetPosition,
                                   double timeoutS) {
        int newArmTarget;
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            // newArmTarget = armMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            armMotor.setTargetPosition(targetPosition);

            // Turn On RUN_TO_POSITION
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            armMotor.setPower(Math.abs(0.75));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (armMotor.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Running to",  " %7d", targetPosition);
                telemetry.addData("Currently at",  " at %7d",
                        armMotor.getCurrentPosition());
                telemetry.update();
            }
            if (armMotor.getCurrentPosition()>2815) {
                armMotor.setPower(0);
                armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            // Stop all motion;
            armMotor.setPower(0);
            //armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Turn off RUN_TO_POSITION
            // armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }

    public void encoderDriveForForeArm(double speed, int targetPosition, double timeoutS) {
        int newArmTarget;
        telemetry.addData("Currently at", " at %7d",
                forearmMotor.getCurrentPosition());
        //    forearmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            // newArmTarget = armMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            telemetry.addData("Currently at", " at %7d",
                    forearmMotor.getCurrentPosition());
            forearmMotor.setTargetPosition(targetPosition);
            telemetry.addData("Currently at", " at %7d",
                    forearmMotor.getCurrentPosition());
            // Turn On RUN_TO_POSITION
            forearmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            forearmMotor.setPower(Math.abs(0.75));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (forearmMotor.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Running to", " %7d", targetPosition);
                telemetry.addData("Currently at", " at %7d",
                        forearmMotor.getCurrentPosition());
                telemetry.update();
            }
            if (forearmMotor.getCurrentPosition() >  150) {
                forearmMotor.setPower(0);
                forearmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            // Stop all motion;
            forearmMotor.setPower(0);
            //armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // Turn off RUN_TO_POSITION
            // armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
    public void openclaw()
    {
        clawServo.setPosition(0);
    }
    public void closeclaw()
    {
        clawServo.setPosition(1);
    }
}
/*
 * This OpMode illustrates the concept of driving a path based on encoder counts.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: RobotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forward, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backward for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This method assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
