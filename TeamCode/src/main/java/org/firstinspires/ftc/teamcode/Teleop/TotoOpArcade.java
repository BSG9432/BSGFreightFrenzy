//bro like half of this im referencing from tylaop lmfao
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware.Robot;


@TeleOp @Disabled
public class TotoOpArcade extends OpMode {
    Robot bsgRobot = new Robot();


    @Override
    public void init() {
        bsgRobot.init(hardwareMap);;

        bsgRobot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bsgRobot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void loop() {

        telemetry.addData("Left Stick X: ", gamepad1.left_stick_x);
        telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
        telemetry.addData("Left Stick Y:", gamepad1.left_stick_y);
        telemetry.addData("Right Stick Y:", gamepad1.right_stick_y);


        telemetry.update();


        if (Math.abs(gamepad1.left_stick_y) > .1 && Math.abs(gamepad1.left_stick_y) < .3) {
            bsgRobot.frontRight.setPower(-gamepad1.left_stick_y);
            bsgRobot.backRight.setPower(-gamepad1.left_stick_y);
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_y);
            bsgRobot.backLeft.setPower(-gamepad1.left_stick_y);
        } else {
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
        }
        if (gamepad1.left_stick_x < -.1 && Math.abs(gamepad1.left_stick_y) < .3) {
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.left_stick_x);
            bsgRobot.frontRight.setPower(gamepad1.left_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.left_stick_x);
        }
        else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }


        //Right strafe when left_stick_x is positive (right)
        if (gamepad1.left_stick_x > .1 && Math.abs(gamepad1.left_stick_y) < .3) {
            bsgRobot.frontLeft.setPower(-gamepad1.left_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.left_stick_x);
            bsgRobot.frontRight.setPower(gamepad1.left_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.left_stick_x);
        }
        else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }

        //Rotate counterclockwise (pivot turn left) when right stick is pressed to the left
        if (gamepad1.right_stick_x < -.1){
            bsgRobot.frontLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.frontRight.setPower(-gamepad1.right_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.right_stick_x);
        }
        else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }

        //Rotate clockwise (pivot turn right) when right stick is pressed to the right
        if (gamepad1.right_stick_x > .1){
            bsgRobot.frontLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.backLeft.setPower(gamepad1.right_stick_x);
            bsgRobot.frontRight.setPower(-gamepad1.right_stick_x);
            bsgRobot.backRight.setPower(-gamepad1.right_stick_x);
        }
        else {
            bsgRobot.frontLeft.setPower(0);
            bsgRobot.backLeft.setPower(0);
            bsgRobot.frontRight.setPower(0);
            bsgRobot.backRight.setPower(0);
        }
        if (gamepad1.left_bumper) {
            bsgRobot.carousel.setPower(-0.5);
        } else {
            bsgRobot.carousel.setPower(0);
        }
        if (gamepad1.right_bumper) {
            bsgRobot.carousel.setPower(0.5);
        } else {
            bsgRobot.carousel.setPower(0);
        }
        telemetry.addData("Front Right Value: ", bsgRobot.frontRight.getPower());
        telemetry.addData("Back Right Value: ", bsgRobot.backRight.getPower());
        telemetry.addData("Front Left Value: ", bsgRobot.frontLeft.getPower());
        telemetry.addData("Back  Left Value: ", bsgRobot.backLeft.getPower());
        telemetry.update();

        /*if (gamepad1.a)
            bsgRobot.clamp.setPosition(50);
        else if(gamepad1.b){
            bsgRobot.clamp.setPosition(-50);
        }
        else {
            bsgRobot.clamp.setPosition(0);
        }*/
        //moving the pulley up/down
        if (gamepad1.dpad_up) {
            bsgRobot.lift.setPower(1);
        } else if (gamepad1.dpad_down) {
            bsgRobot.lift.setPower(-1);
        }
        else{
            bsgRobot.lift.setPower(0);
        }
        //moving claw up and down
        if (gamepad1.x){
            bsgRobot.motion.setPower(1.0);
        }
        else{
            bsgRobot.motion.setPower(0);
        }
    }
}


