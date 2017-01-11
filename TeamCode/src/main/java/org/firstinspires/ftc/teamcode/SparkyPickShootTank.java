/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 * <p>
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * <p>
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "SparkyTank", group = "Pushbot")
//@Disabled
public class SparkyPickShootTank extends OpMode {

    /* Declare OpMode members. */
    HardwarePushbot robot = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    double position = 0.02;
    double INCREMENT = 0.01;     // amount to slew servo each cycle
    double MAX_POS = 0.26;     // Maximum rotational position
    double MIN_POS = 0.02;     // Minimum rotational position

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("", "G O  S P A R K Y !");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override


    public void loop() {
        double left;
        double right;
        boolean pickerIn;
        boolean pickerOut;
        double leftShoot;
        double rightShoot;
        boolean guiderPos;
        //double guiderDown
        boolean shooter;


        //boolean rampUp = true;
        // Rheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        //Driver

        //driver
        left = -gamepad2.left_stick_y;
        right = gamepad2.right_stick_y;
        //picker
        pickerOut = gamepad2.left_bumper;
        pickerIn = gamepad2.right_bumper;
        //shooter
        shooter = gamepad1.y;
        //guider
        guiderPos = gamepad1.b;
        //guiderDown=gamepad1.a
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);
        if (pickerIn)
            robot.pickMotor.setPower(-1);
        else if (pickerOut)
            robot.pickMotor.setPower(1);
        else
            robot.pickMotor.setPower(0);
        if (gamepad1.x)
            robot.pickMotor.setPower(0);
        if (shooter) {
            robot.leftShoot.setPower(1);
            robot.rightShoot.setPower(-1);
        } else {
            robot.leftShoot.setPower(0);
            robot.rightShoot.setPower(0);
        }
        //take the guider up to shoot the particle
        if (gamepad1.b) {
            position -= INCREMENT;
            position = Range.clip(position, MIN_POS, MAX_POS);
            robot.guider.setPosition(position);
        }

//bring down the guider
        if (gamepad1.a) {
            // Keep stepping up until we hit the max value.
            position += INCREMENT;
            position = Range.clip(position, MIN_POS, MAX_POS);
            robot.guider.setPosition(position);
        }


        //Picker
        // Send telemetry message to signify robot running;
        // telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("shooter", shooter);
        telemetry.addData("guider", guiderPos);
        telemetry.addData("Servo Position", "%5.2f", position);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
