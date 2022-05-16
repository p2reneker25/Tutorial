# Tutorial

# Setting up your computer

1. Download the WPILib files from [here](https://github.com/wpilibsuite/allwpilib/releases/latest/). Choose the one corresponding to your computer.

2. Extract the WPILib IDE and files.

   1. Windows:

      1. Mount the ISO
      2. Run the WPILib installer file

   2. Linux (Working on making a workaround for driving, so can only program rn)

      1. tar -xf WPILib_Linux-&lt;version>.tar.gz
      2. cd WPILib_Linux-&lt;version>/ Replace &lt;version> with the version number
      3. ./WPILibInstaller 

3. Install WPILib

   1. Click start on the installer
   2. Select install everything, choose which user to install it to.
   3. Select Download VS code to computer
   4. When it is done installing, click finish

4. Create a new project

   1. Open 2022 WPILIB VS CODE (It should have been installed in the last step)
   2. Bring up the Visual Studio Code command palette with Ctrl+Shift+P. Then, type “WPILib” into the prompt. Now, select the “Create a new project” command.
   3. ![](https://lh5.googleusercontent.com/P7FAY0qes8IZzEwxwg-uzpcz67C_pO2kI9roXtd9m6rmpRNUQjLIGUUssQvUe6kfxn3Kiw3fXu1p6MuvDxArrVg64gJRQEatSVf3pF5dKrNPMNPqVju4L8PwDfTg7UcYWQmNdT-OgwVcLfSUBA)
   4. Select Template, java, Command robot, so it looks like this. The base folder should be where you want it to store the project, and the project name should be what you want to call your project. Set team number to 2035 -- this allows the code to be sent to the robot.
   5. Generate project, and open it in the current window.
   6. Trust the authors of the program (its you)
   7. Congratulations, you just created the project!




# Programming basics

We program our robot as a “command based robot.” A great explanation of it is [here](https://docs.wpilib.org/en/stable/docs/software/commandbased/what-is-command-based.html).

The [subsystems](https://docs.wpilib.org/en/stable/docs/software/commandbased/subsystems.html) are pieces of code that correspond with a physical part of the robot.

The [commands](https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html) tell the subsystems what to do.

For the autonomous portions of the competition, we usually run a [group of commands](https://docs.wpilib.org/en/stable/docs/software/commandbased/command-groups.html) that each do part of the competition’s task. When the robot runs, it runs each command. 

When the driver is driving the robot, we [bind buttons](https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html) on the joystick or other triggers to run a command. This allows the driver to control each part of the robot.

All of this [comes together](https://docs.wpilib.org/en/stable/docs/software/commandbased/structuring-command-based-project.html) to create an awesome robot!




# Programming a driving robot! (Walkthrough)

1. Take the project that you made earlier, and open it.

2. Talk to whoever is building the robot, and ask what speed controllers they are using. For our example, we will be using Victor SPs, which we have a crap ton of.

3. Since we are making a driving robot, the subsystem we will need to make is a drive train. This, for now, consists of 4 motors attached to 4 speed controllers.

   1. Navigate to src/main/java/frc/robot/subsystems in VS code and click on ExampleSubsystem.java
   2. Right click on it and duplicate it.
   3. Rename the copy DriveTrain.java, or whatever you want to call your drivetrain. It is a good idea to check [the naming convention doc](https://docs.google.com/document/d/1S1E9FI0kbV6z85cYk2ohh4TC2ApqnpK69J1cj7ug9Ew/edit), as we have had issues with naming things properly.
   4. You will notice that red squiggly lines appear over Example Subsystem in the code. Replace ExampleSubsystem with whatever you named your drive subsystem. The top of your DriveTrain subsystem should look something like

this: 

5. public class DriveTrain extends SubsystemBase {
6.  /\*\* Creates a new ExampleSubsystem. \*/
7.  public DriveTrain() {}

 

8. Now, we need to add the speed controllers. These control the motors which move the robot. 

   1. We will make 4 motor controller objects in DriveTrain, like this:

public class DriveTrain extends SubsystemBase {

 private MotorController left1;

 private MotorController left2;

 private MotorController right1;

 private MotorController right2;

 /\*\* Creates a new ExampleSubsystem. \*/

 public DriveTrain() {}

9. We need to tell these speed controllers where they will be getting their instructions from. In order to make adapting the code for wiring changes easier, we have a file named Constants.java where we store all the numbers and values that don’t change.

   1. Go to Constants.java
   2. In constants, create a static class called DriveTrain, and make 4 public static final ints (numbers) that correspond to each speed controller. Again, check the naming conventions doc for how to name them. These are each port on the roborio that the wiring people will plug the speed controllers into. 

public final class Constants {

   public static class DriveTrain{

       public static final int DRIVE_PWM_LEFT1 = 0;

       public static final int DRIVE_PWM_LEFT2 = 1;

       public static final int DRIVE_PWM_RIGHT1 = 2;

       public static final int DRIVE_PWM_RIGHT2 = 3;

   }

}

10. Go back to your drivetrain subsystem, and initialize each speed controller in DriveTrain(), with your newly made constants. Initialize them like this:

11.  public DriveTrain() {

12.    left1 = new VictorSP(Constants.DriveTrain.DRIVE_PWM_LEFT1);

    1. Remember to do this for each one, so there will be 4 of them, and each of them has a different corresponding constant.

13. We now need to group them into each gearbox, as there are 2 motors per gearbox. Create two private MotorControllerGroups in the DriveTrain class in your subsystem, like this:

public class DriveTrain extends SubsystemBase {

 private MotorController left1;

 private MotorController left2;

 private MotorController right1;

 private MotorController right2;

 private MotorControllerGroup left;

 private MotorControllerGroup right;

14. Initialize them with the two motors, so that the left group has left1 and left 2, and the right group has right1 and right2.

15.  Now, we need to create the actual driving code. For us, we will use WPILib’s existing driving function. 

    1. Create a DifferentialDrive object in class DriveTrain, under the motorcontroller groups.

 private DifferentialDrive dd_drive;

2. In DriveTrain(), initialize it with the two groups.

   1.    dd_drive = new DifferentialDrive(left, right);

3. Create a new void function called ArcadeDrive that takes in two doubles, the x axis and the y axis of the joystick..

   1. public void ArcadeDrive(double x, double y){
   2.   
   3.  }

4. Tell the differential drive to drive, taking in the two joystick inputs like so:

   1.    dd_drive.arcadeDrive(x, y);
   2. This tells the robot to rotate with the left right movement of the joystick, and go forward and backward with the up down movement of the joystick.

**WAIT! We’re not done yet! We haven’t told the robot to actually drive. For this, we need a command!**

4. Create the drive command

   1. Duplicate the example command file in the commands file. Replace examplecommand with drive, or whatever you named your drive command. Check the naming convention sheet for ideas.
   2. We need to import the drivetrain subsystem into the command. To do that, replace the examplesubsystem line with drivetrain. It should look like this.

public class Drive extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final DriveTrain m_drivetrain;

3. Next, we need to add a joystick to this command so that it can get the joystick inputs. Create a private final Joystick under the drivetrain.

  private final Joystick m_joystick;

4. Scroll down to the public drive(). We need to add the drivetrain and the joystick to the parameters the function takes in. Add a DriveTrain and a joystick to the parameters in the Drive() function. Below it, set the final Drivetrain made above in the class Drive to the one in the parameters. Do the same for the joystick. It should look like this:

public Drive(DriveTrain dt, Joystick j) {

    m_drivetrain = dt;

    m_joystick = j;

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(dt);

  }

5. Use the addRequirements to require the drivetrain, but not the other functions.
6. Great! Now, we need to have the drive command move the drivetrain. In the execute function, have call the arcade drive function in the subsystem with the joystick axes as the parameters.

 @Override

  public void execute() {

    m_drivetrain.ArcadeDrive(m_joystick.getX(), m_joystick.getY());

  }

7. Save this code, and go RobotContainer.java.


5. Tell the robot to drive when it is enabled

   1. In RobotContainer.java, replace the private final ExampleSubsystem with DriveTrain, and its initialization function too.It should look like this:

  private final DriveTrain m_drivetrain = new DriveTrain();

2. We need to add a joystick here. For organizational purposes, go to Constants.java and create a new class with a public static final int that is the joystick port. It should be 0.

public static class Controls{

        public static final int CONTROLS_USB_JOYSTICK = 0;

    }

3. Got back to robotcontainer and create a joystick object with the port from constants.

  private final Joystick j_joystick = new Joystick(Constants.Controls.CONTROLS_USB_JOYSTICK);

4. Go down to the configure button bindings function. This is where the robot is told when to do what. Take your drivetrain subsystem and set the default command to your drive command using setDefaultCommand(). Make sure to add the subsystem and the joystick. It should look like this:

private void configureButtonBindings() {

   m_drivetrain.setDefaultCommand(new Drive(m_drivetrain, j_joystick));

 }

Congratulations! You’re done! 


# Loading code to the robot

So you’ve coded your robot code. But this doesn’t do anything without a robot. So, step one is to

1. Build a robot (or have someone else build you a robot), (or even better, help your team build a robot)
2. Download and install the [FRC Game Tools](https://www.ni.com/en-us/support/downloads/drivers/download.frc-game-tools.html#) from NI.
3. Connect the roborio via ethernet cable to your Windows computer. I am currently working on making the 2022 driver station compatible with linux (ubuntu), but for now you are stuck with windows.
4. Open your code.
5. Press Shift + F5 to deploy your robot code.
6. Open the FRC driver station. Plug in a joystick. Click enable to enable the robot, and have fun driving it around!


# Programming additional devices

Now that you have a functional driving robot, you may want to program other parts of the robot. Oftentimes, these robots contain pneumatic cylinders, servos, and sensors.


### Pneumatics

1. For each new device that you add, you will need to make a subsystem for it. For example, if your robot has a shooter, you will need a shooter subsystem. For each action that your subsystem does, you will need a command. Keep this in mind.

2. We’re going to program a cylinder to go back and forth. The cylinder is connected to a double solenoid.

3. Duplicate the example subsystem, and name it cylinder or whatever. Replace the example subsystems with what you named it.

4. Cylinders are controlled by solenoids. In order for the cylinder to go both up and down, we use a double solenoid.

   1. Create a double solenoid object like this under the class Cylinder:

public class Cylinder extends SubsystemBase {

 private DoubleSolenoid s_solenoid;

2. Go into constants and create a new static class with two public static final ints inside of it.

public static class Pneumatics{

       public static final int PNEUMATICS_PCM_CYLINDER_UP = 0;

       public static final int PNEUMATICS_PCM_CYLINDER_DN = 1;

   }

3. Go back to your subsystem and initialize the double solenoid. It takes in 3 arguments - the module type, and then the two channels. Set the module type to PneumaticsModuleType.CTREPCM and the two channels to your constants. It should look like this

    s_solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.Pneumatics.PNEUMATICS_PCM_CYLINDER_UP, Constants.Pneumatics.PNEUMATICS_PCM_CYLINDER_DN);

4. We will now create three functions that control the cylinder. They will be set cylinder forward, set cylinder reverse, and disable cylinder. Create three void functions (since they will not be returning anything, they are voids) and in them set the cylinder to the position using s_solenoid.set(pos), where position is Value.kForward, kReverse, or kOff. It should look like this

public void CylinderUp(){

    s_solenoid.set(Value.kForward);

  }

  public void CylinderDown(){

    s_solenoid.set(Value.kReverse);

  }

  public void DisableCylinder(){

    s_solenoid.set(Value.kOff);

  }

5. Now, we need to create commands for each of the functions. There are a few ways of doing this, and for simplicity’s sake as well as debugging features, we will create individual commands for each position. 
6. Create two copies of examplecommand and rename them to CylinderUp and CylinderDown. Replace the example command fields with CylinderUp and CylinderDown and replace the example subsystems with your cylinder subsystem. It should look something like this:

public class CylinderDown extends CommandBase {

  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final Cylinder m_cylinder;

 

  /\*\*

   \* Creates a new ExampleCommand.

   \*

   \* @param subsystem The subsystem used by this command.

   \*/

  public CylinderDown(Cylinder c) {

    m_cylinder = c;

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(c);

  }

7. In the execute function of the command, tell m_cylinder to call the function in the subsystem that corresponds to the command name. For cylinderdown, we would use the cylinder down function like this

public void execute() {

    m_cylinder.CylinderDown();

  }

8. Do the same for the other cylinder command.
9. These commands will, when executed, run the command indefinitely because there is no stop system engaged. There are two options for this, and it depends on the use case. You can set it so that when a joystick button is held, it runs the command until it is released, or you can set an auto stop for the command and have it run when a button is pressed. I will show both ways.


#### Button pressed:

1. We set the joystick buttons in constants to make it easier to change mapping. Go to constants, and in the controls class create two new public static final ints for the two buttons. For this robot, I will be using buttons 3 and 4, which are highlighted in the image.

Your controls class should look something like this

public static class Controls{

        public static final int CONTROLS_USB_JOYSTICK = 0;

        public static final int CONTROLS_BUTTON_CYLINDER_UP = 3;

        public static final int CONTROLS_BUTTON_CYLINDER_DN = 4;

    }

2. Go to RobotContainer.java. We now need to create two joystick buttons and import the cylinder subsystem. Create a private final cylinder subsystem and initialize it. Create the joystick buttons in public class robotcontainer, but initialize them in the public robotcontainer like this. They need a joystick and the button number as an int. Use the joystick object that was created earlier and your constants for the numbers.

public class RobotContainer {

  // The robot's subsystems and commands are defined here...

  private final DriveTrain m_drivetrain = new DriveTrain();

  private final Joystick j_joystick = new Joystick(Constants.Controls.CONTROLS_USB_JOYSTICK);

  private final Cylinder m_cylinder = new Cylinder();

  private final JoystickButton b_cylinder_up;

  private final JoystickButton b_cylinder_dn;

 

  /\*\* The container for the robot. Contains subsystems, OI devices, and commands. \*/

  public RobotContainer() {

    b_cylinder_up = new JoystickButton(j_joystick, Constants.Controls.CONTROLS_BUTTON_CYLINDER_UP);

    b_cylinder_dn = new JoystickButton(j_joystick, Constants.Controls.CONTROLS_BUTTON_CYLINDER_DN);

 

    // Configure the button bindings

    configureButtonBindings();

  }

3. In the configurebuttonbindings function, have the cylinder buttons.whenHeld run a new command that is your cylinder up or cylinder down command. The configure button bindings function should look like this with the drivetrain

  private void configureButtonBindings() {

    m_drivetrain.setDefaultCommand(new Drive(m_drivetrain, j_joystick));

    b_cylinder_dn.whileHeld(new CylinderDown(m_cylinder));

    b_cylinder_up.whileHeld(new CylinderUp(m_cylinder));

  }

4. Congratulations! Your pneumatics system is fully programmed!


#### Button held:

1. In your cylinder commands, create a new private timer.

  private Timer timer;

2. Initialize the timer in the public cylinder command.

  public CylinderDown(Cylinder c) {

    m_cylinder = c;

    timer  = new Timer();

    // Use addRequirements() here to declare subsystem dependencies.

    addRequirements(c);

  }

3. In the initialize function, set the timer to reset and start itself like this

  @Override

  public void initialize() {

    timer.reset();

    timer.start();

  }

4. In the boolean isFinished at the bottom of the function, have it return whether the timer is greater than the amount of time it takes for the cylinder to cycle itself. For our purposes, it will be 2 seconds.

@Override

  public boolean isFinished() {

    return timer.get()>2;

  }

5. Follow the button pressed instructions step 1 and 2.
6. Follow step 3, except replace  “whileHeld” with “whenPressed”  It should look like this

  private void configureButtonBindings() {

    m_drivetrain.setDefaultCommand(new Drive(m_drivetrain, j_joystick));

    b_cylinder_dn.whenPressed(new CylinderDown(m_cylinder));

    b_cylinder_up.whenPressed(new CylinderUp(m_cylinder));

  }

                                        Congratulations! Your pneumatics should work!

Source code for this project: <https://github.com/p2reneker25/Tutorial>
