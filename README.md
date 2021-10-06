## About

This challenge solves the IOOF's recruitment challenge involving a robot simulation. The complete details of the challenge is [laid out in full here.](https://github.com/ioof-holdings/recruitment/wiki/Robot-Challenge) This program solves the extension to the problem, which allows multiple robots to roam around on the table. The following discussion will provide a high-level overview of the architecture, followed by a more detailed breakdown of classes and interfaces, followed by the testing methodology. 

This program is written in a Java 11 environment, however is completely compatible with Java 8+.

## How to Run

### Running With Docker

Running with Docker is the recommend way to run this application. Simply make sure that you have [Docker](https://docs.docker.com/get-docker/) installed and run the following command:

```
docker run -it --rm jamesrmenzies/robots
```
This will put you straight into the interactive prompt to run the simulation. The image is approximately 90 MB in size.

### From Source
If you prefer to not use Docker, or would like to run the test suite, it is also possible to run the program from source. Instructions are (virtually) identical between Windows (Powershell), Mac OS, and Linux (Bash). A basic knowledge of terminal commands is required. 

> This program uses Gradle as a build tool, however it is *not* required to be installed in order to run the program, as it will be bootstrapped for you. Note that there is some input lag when running the application in comparison to the Dockerised version. This is due to the presence of Gradle when running the application.

### Steps
1. Make sure that Git and either your choice of JDK 8 or JDK 11 are installed. *NB: JRE 8 will not work with this program.*
2. Navigate to your desired directory via the terminal.
3. Clone the repository `git clone https://github.com/james-menzies/robot-challenge`
4. Navigate to newly created directory `cd robot-challenge`
5. If on Mac / Linux, run this command `chmod +x gradlew`
6. To run the program, run `./gradlew run --quiet --console=plain`
7. To run the tests, run `./gradlew test --rerun-tasks`

> On Windows, substitute `./gradlew` with `.\gradlew.bat`



### Troubleshooting
#### Getting a 'Permission Denied' Error
Make sure that you have run the command in step 5 to make the script executable.
#### 'Cannot find tools.jar'
Make sure that you are using JDK 8 and not JRE 8.
#### Corrupted graphical output
Make sure that you include the `--console-plain` and `--quiet` flags in the `run` command.

#### I don't have Gradle installed.

That's OK! The wrapper downloads it for you and places it in the project directory. When you're done with the program, delete the project folder and the Gradle installation will disappear too.


## Program Architecture

The key feature of this demonstration is the intent to separate the concerns between the **View** (the user interface), the **Controller** (what happens when a user issues a command) and the **Model** (the underlying data structures). This is roughly equivalent to the standard MVC architecture, but with one key exception:

> Rather than the **View** be directly updated from the **Model**, the View will arbitrarily request an update from the **Controller** via the `REPORT` command. This is purely to stay within the parameters of the challenge. 
>
>If desired however, it's entirely possible that a traditional MVC solution could be deployed, likely via attaching a callback listener on the **Model.**

To round out the application, a `Main` class is implemented to bootstrap the program.

## The Model

There are 2 primary components in this program, the `Robot` and the `Table`. However, for context it is important to first discuss some support objects.

### Support Objects
* `Coordinate` - An object to represent a location on the `Table`. It is an immutable, read-only object that has two instance variables `x` and `y`, as well as getters for those variables. Both variables must be equal to or greater than 0.
* `Orientation` - An `enum` that represents the 4 cardinal directions, `NORTH`, `SOUTH`, `EAST` and `WEST`. Used to describe the direction the `Robot` is facing.
* `Direction` - An `enum` that represents the direction the `Robot` can rotate, `LEFT` and `RIGHT`. 

### Robot

The `Robot` class represents instances of the physical robots that roam on the table. 
#### Instance Variables
* `Orientation orientation` - The direction the `Robot` is facing, must be provided on initialization.
#### Methods
* `Orientation getOrientation()` 
* `void turn(Direction direction)` - This will analyze the direction provided and update the `orientation` variable.
* `Coordinate getIntent(Coordinate currentPosition)` - This will calculate where this object would move based on the given `Coordinate`

### Table

The table represents the surface the `Robot` instances traverse, and are ultimately responsible for validating their movement. To avoid tight coupling between the `Robot` and `Table`, the `Robot` will be referred to via an interface reference, `TableEntity` (described below). This has multiple benefits, including:
* Better isolation for unit testing.
* The ability to create different types of objects that roam on the table which exhibit different behaviour. For example, the simulation might implement a `Frog` object that jumps 3 spaces at a time!
#### Instance Variables
* `Coordinate tableLimit` - The maximum permissible `Coordinate` on the table. In the challenge this is hard-coded to a 5x5 grid, therefore this value will always be (4,4) given a zero-based index. However, defining it as a variable allows flexibility for future iterations.
* `Map<Integer, PositionedEntity> registeredEntities` - This stores a reference to all the entities on the table and is used extensively in the methods described below. Also see `PositionedEntity` for a detailed explanation.
#### Methods
* `boolean move(int reference)` - Given an integer reference, this method will analyze the `PositionedEntity` for the given reference, and establish whether the move is permissible based on the position of the other objects on, and the range of, the table. If an invalid reference is given, an Exception will occur.
* `int registerEntity(TableEntity tableEntity, Coordinate coordinate)` - This will attempt to place the entity in the `registeredEntities` variable. An exception will be thrown if the `Coordinate` value is out of range of the `Table` or the position is occupied. It will return an integer reference to be used for subsequent method calls.
* `Map<Integer, Coordinate> getPositions()` - This will return a map of all entity references to their position on the table.


### TableEntity

The `TableEntity` is a functional interface, and any object that would exist on the `Table` needs to implement it. It contains the `getIntent()` method as described by the `Robot` class.

#### Methods
* `Coordinate getIntent(Coordinate currentPosition)`

### PositionedEntity

This is an inner class contained in the `Table` class, and is simply a way to fuse together a `Coordinate` variable with a `TableEntity` variable.

#### Variables
* `final TableEntity entity`
* `Coordinate coordinate`
#### Methods
* `getEntity()`
* `getCoordinate()`
* `setCoordinate(Coordinate coordinate)`

## The Controller

Since the program is very small, the entire controller will be contained in a single class; `Controller`. However, care has been taken so that if the program were to increase in complexity, the functionality of the controller could be conveniently refactored into multiple classes if desired. 

### Functionality 
The controller is essentially the backbone of the application and needs to perform the following functions:
* Maintain a reference to the robots on the table, the table itself, and the active robot, in other words, maintain the 'application state'
* Map the commands from the user interface to handler methods, which can manipulate the application state.
* Accept a callback function that allows the application state to be printed to the screen.

### Singleton Pattern

This is enforced on the Controller class to ensure that there can't be multiple versions of application state in the program. Additionally, since it is accessed statically, references for the controller don't need to be passed around to every interested party. 

The controller is **_strongly encapsulated_** to ensure the application can't be put into an invalid state arbitrarily. 

`Controller` has been implemented this way to allow for many different controller and view classes to be created in the future without a labyrinth of references being passed around.

### Initialization
The only thing required to initialize the application state is a `Table` object, which is provided by the `Main` class.


### Methods and Variables

#### Variables

* `private int activeRobot` - The integer reference of the selected robot.
* `private Map<Integer, Robot> robots` - A mapping of all the robots on the table.
* `private Table table` - The `Table` instance being used in the 
application.
* `private Consumer<List<RobotDescription>> onReportCallback` - Callback that gets called when `onReport()` is called.
* `private Controller self` - A reference to the single running instance of the `Controller` class.

#### Methods

* `static void initialize(Table t)` - Required to initialize the application state. Failing to call this function before calling `onPlace` will result in an exception being thrown.
* `static Controller getInstance()` - Get the single running instance of the `Controller` class.
* `void onPlace(Coordinate c, Orientation o)` - This will place a `Robot` on the table. This method will perform no function if the `Coordinate` argument is beyond the range of the table. This method must also be called before any of the other handlers, which will perform no function until that point.
* `void onMove()` - Inspects the `activeRobot` variable and calls the `move()` method on the `Table`.
* `void onLeft()` - Inspects the `activeRobot` variable and calls the `turn()` function on the selected robot passing in `Direction.LEFT`.
* `void onRight()` - Same as `onLeft()` except with `Direction.RIGHT`.
* `void onReport()` - Generates a list of `RobotDescription` objects (see below), then calls the callback provided by `static setOnReportCallback`. If unset, no function is performed.
* `void onRobot(int reference)` - Change `activeRobot` to given reference. Perform no function if reference is invalid.
* `void setOnReportCallback(Consumer<List<RobotDescription>>)` - This registers a callback that gets called when the `onReport` handle is called. If unset `onReport()` will perform no function.

### RobotDescription Class
This class is introduced to address the need to combine information together about the position (stored in the `Table`) and orientation (stored in the `Robot`) so that it can be output as per the requirements in the challenge description. It in an inner class of `Controller` and has 3 read-only variables:
* `String name`
* `Coordinate coordinate`
* `Orientation orientation`

## The View
The **View** is very similar to the **Controller** in that it is contained in a single class and is accessed statically. It will statically call the `setOnReportCallback` method in the loading of the class, so no initialization method is required in this instance.


### Methods
* `static void dispatch(String command)` - Takes a string, and calls the appropriate handler based on the command. Performs no action if the command or its parameters are invalid.
* `static void run()` - This will run an infinite loop that accepts standard input and dispatches it.

## Testing

Testing involves the use of unit as well as integration testing. For each class, the desired behaviour has been outlined. No tests will be specifically created for `Direction`, `Orientation`, `RobotDescription` and `PositionedEntity` as they are too basic to contain anything meaningful to test.

### Orientation
* Ensure negative indices cannot be created on either variable.
* Ensure that equality works as expected.

### Robot
* Ensure that the robot turns correctly, e.g. a robot turning left when facing `NORTH` should then be facing `WEST`.
* Ensure that the robot's intent functions correctly, e.g. a robot facing `NORTH` when given a `Coordinate` of (1,1) should return (1,2).
* Ensure graceful handling of invalid coordinates, e.g. A robot facing `SOUTH` when given a `Coordinate` of (0,0) should return (0,0) to avoid throwing an exception.

### Table

Since the `Table` refers to the `Robots` via an interface, it can be mocked when testing.

Desired behaviour:
* Out of range register throws exception.
* Occupied position register throws exception.
* `getPositions` correctly returns positions of robots on initial placement. `getPositions` will be passively tested on following tests as well.
* Legal moves of entity causes its position to change.
* Out of range movement causes no move to occur.
* Movement that would cause a collision causes no movement to occur.

### Display

The Display has separated the dispatch logic from the reading of standard input. This means commands can be programmatically passed to the display in order to be tested. The other part of this process involves mocking out the `Controller` class, again via an interface. 

This interface contains all of the `Controller` class methods minus the `initialize()` and `getInstance()` methods. It adds the functionality of exposing the last called handler to ensure that the correct handler is being called by the display.

To ensure that information is displayed correctly to the user, manual tests have been performed.

Desired behaviour:
* All zero-argument commands (`REPORT`, `LEFT`, `RIGHT` and `MOVE`) get correctly called.
* All non-zero-argument command (`ROBOT` and `PLACE`) do **not** get called when no arguments are provided.
* Verify invalid and valid argument cases for both `ROBOT` and `PLACE`.

### Controller

The logic of the `Controller` is tested via integration tests, rather than by mocking out classes. It uses the example cases mentioned in the challenge description, as well as a more complex example involving multiple robots. 
