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

package org.firstinspires.ftc.teamcode.Testing.Encoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Disabled
@Autonomous(name="TwoEncoderBlueBuilding", group = "YEET")
public class TwoEncodersBlueBuilding extends LinearOpMode {

    //taking the hardware from our Robot class with our hardware
    Robot bsgRobot = new Robot();

    //for encoders...
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;    // Neverest 40
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {

        bsgRobot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        bsgRobot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //bsgRobot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bsgRobot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //bsgRobot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bsgRobot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //bsgRobot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bsgRobot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //bsgRobot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                bsgRobot.frontLeft.getCurrentPosition(),
                //bsgRobot.backLeft.getCurrentPosition(),
                bsgRobot.frontRight.getCurrentPosition());
                //bsgRobot.backRight.getCurrentPosition());
        telemetry.update();

    //    bsgRobot.rightFoundation.setPosition(1);
     //   bsgRobot.leftFoundation.setPosition(0);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        strafeRight(800);

        encoderDrive(.5, -35.50, -36, 6); //forward 35.5 inches towards foundation

        sleep(500);

       // foundationDown(2000); //grab foundation

        encoderDrive(.6, 36.50, 36.5, 6); //drag foundation backwards 35.5 inches into build zone

        sleep(500);

      //  foundationUp(800); //let go of foundation

        strafeLeft(2250);strafeRight(800);

        encoderDrive(.5, -35.50, -36, 6); //forward 35.5 inches towards foundation

        sleep(500);

       // foundationDown(2000); //grab foundation

        encoderDrive(.6, 36.50, 36.5, 6); //drag foundation backwards 35.5 inches into build zone

        sleep(500);

      //  foundationUp(800); //let go of foundation

        strafeLeft(2250);


        telemetry.addData("Path", "Complete");
        telemetry.update();

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = bsgRobot.frontLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            //newLeftTarget = bsgRobot.backLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = bsgRobot.frontRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
           // newRightTarget = bsgRobot.backRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            bsgRobot.frontLeft.setTargetPosition(newLeftTarget);
           // bsgRobot.backLeft.setTargetPosition(newLeftTarget);
            bsgRobot.frontRight.setTargetPosition(newRightTarget);
           // bsgRobot.backRight.setTargetPosition(newRightTarget);


            // Turn On RUN_TO_POSITION
            bsgRobot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //bsgRobot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bsgRobot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           // bsgRobot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            bsgRobot.frontLeft.setPower(Math.abs(speed));
           // bsgRobot.backLeft.setPower(Math.abs(speed));
            bsgRobot.frontRight.setPower(Math.abs(speed));
           // bsgRobot.backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (bsgRobot.frontLeft.isBusy() && bsgRobot.frontRight.isBusy() /*&&
                            bsgRobot.backLeft.isBusy() && bsgRobot.backRight.isBusy()*/)) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        bsgRobot.frontLeft.getCurrentPosition(),
                        //bsgRobot.backLeft.getCurrentPosition(),
                        bsgRobot.frontRight.getCurrentPosition());
                       // bsgRobot.backRight.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            bsgRobot.stopWheels();

            // Turn off RUN_TO_POSITION
            bsgRobot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //bsgRobot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bsgRobot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           // bsgRobot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    //rotate function using IMU's
    public void rotate(int degrees, double power) {

        double leftPower, rightPower;

        //restart imu movement tracking
        bsgRobot.resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0) {   // turn left.
            leftPower = power;
            rightPower = .3;
            telemetry.addLine("left");
            telemetry.update();
        } else if (degrees > 0) {   // turn right.
            leftPower = -.3;
            rightPower = -power;
            telemetry.addLine("right");
            telemetry.update();
        } else return;

        // set power to rotate.
        bsgRobot.frontLeft.setPower(leftPower);
        bsgRobot.backLeft.setPower(leftPower);
        bsgRobot.frontRight.setPower(rightPower);
        bsgRobot.backRight.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0) //-10
        {
            // On left turn we have to get off zero first.
            while (opModeIsActive() && bsgRobot.getHeading() == 0) {
            }

            while (opModeIsActive() && bsgRobot.getHeading() < degrees) {
            }
        } else    // right turn.
            while (opModeIsActive() && bsgRobot.getHeading() > degrees) {
            }

        // turn the motors off.
        bsgRobot.frontLeft.setPower(0);
        bsgRobot.backLeft.setPower(0);
        bsgRobot.frontRight.setPower(0);
        bsgRobot.backRight.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        bsgRobot.resetAngle();

    }

  /*  public void foundationDown(int pause) {
        bsgRobot.rightFoundation.setPosition(.2);
        bsgRobot.leftFoundation.setPosition(.8);
        sleep(pause);
    }

    public void foundationUp(int pause) {
        bsgRobot.rightFoundation.setPosition(1);
        bsgRobot.leftFoundation.setPosition(0);
        sleep(pause);
    }*/

    public void strafeLeft(long time) {
        bsgRobot.frontRight.setPower(.8);
        bsgRobot.backRight.setPower(-.8);
        bsgRobot.frontLeft.setPower(-.8);
        bsgRobot.backLeft.setPower(.8);
        sleep(time);
    }

    public void strafeRight(long time) {
        bsgRobot.frontRight.setPower(-.8);
        bsgRobot.backRight.setPower(.8);
        bsgRobot.frontLeft.setPower(.8);
        bsgRobot.backLeft.setPower(-.8);
        sleep(time);
    }
}