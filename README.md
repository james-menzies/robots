## About

This challenge attempts to solve the IOOF's recruitment challenge involving a robot simulation. The complete details of the challenge is [laid out in full here.](https://github.com/ioof-holdings/recruitment/wiki/Robot-Challenge) This program solves the extension to the problem, which allows multiple robots to roam around on the table. The following discussion will provide a high-level overview of the architecture, followed by a more detailed breakdown of classes and interfaces, followed by the testing methodology. 

This program is written in Java 11, however is completely backward-compatible with Java 8.

## How to Run
(Coming Soon)

## Program Architecture

The key feature of this demonstration is the intent to separate the concerns between the **View** (the user interface), the **Controller** (what happens when a user issues a command) and the **Model** (the underlying data structures). This is roughly equivalent to the standard MVC architecture, but with one key exception:

> Rather than the **View** be directly updated from the **Model**, the View will arbitrarily request an update from the **Controller** via the `REPORT` command. This is purely to stay within the parameters of the challenge. 
>
>If desired however, it's entirely possible that a traditional MVC solution could be deployed, likely via attaching a callback listener on the **Model.**

To round out the application, a `Main` class is implemented to bootstrap the program.

## The Model

There are 2 primary components in this program, the **ROBOT** and the **TABLE.** However, for context it is important to first discuss some support objects

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
* `void turn(Direction d)` - This will analyze the direction provided and update the `orientation` variable.
* `Coordinate getIntent(Coordinate c)` - This will calculate where this object would move based on the given `Coordinate`

### Table

The table represents the surface the `Robot` instances traverse, and are ultimately responsible for validating their movement. To avoid tight coupling between the `Robot` and `Table`, the `Robot` will be referred to via an interface reference, `TableEntity` (described below). This has multiple benefits, including:
* Better isolation for unit testing.
* The ability to create different types of objects that roam on the table which exhibit different behaviour. For example, the simulation might implement a `Frog` object that jumps 3 spaces at a time!
#### Instance Variables
* `Coordinate tableLimit` - The maximum permissible `Coordinate` on the table. In the challenge this is hard-coded to a 5x5 grid, therefore this value will always be (4,4) given a zero-based index. However, defining it as a variable allows flexibility for future iterations.
* `Map<Integer, PositionedEntity> registeredEntities` - This stores a reference to all the entities on the table and is used extensively in the methods described below. Also see `PositionedEntity` for a detailed explanation.
#### Methods
* `boolean move(int reference)` - Given an integer reference, this method will analyze the `PositionedEntity` for the given reference, and establish whether the move is permissible based on the position of the other objects on, and the range of, the table. If an invalid reference is given, an Exception will occur.
* `int registerEntity(TableEntity t, Coordinate c)` - This will attempt to place the entity in the `registeredEntities` variable. An exception will be thrown if the `Coordinate` value is out of range of the `Table`. It will return an integer reference to be used for subsequent method calls.
* `Map<Integer, Coordinate> getPositions()` - This will return a map of all entity references to their position on the table.


### TableEntity

The `TableEntity` is a functional interface, and any object that would exist on the `Table` needs to implement it. It contains the `getIntent()` method as described by the `Robot` class.

#### Methods
* `Coordinate getIntent(Coordinate c)`

### PositionedEntity

This is an inner class contained in the `Table` class, and is simply a way to fuse together a `Coordinate` variable with a `TableEntity` variable.

#### Variables
* `final TableEntity entity`
* `Coordinate coordinate`
#### Methods
* `getEntity()`
* `getCoordinate()`
* `setCoordinate(Coordinate c)`


## The Controller Implementation

The controller needs to maintain a reference to the Table, a map of the IDs of the robots to their object references, and the reference of the currently selected robot. There is already one critical concern here:

> The controller needs to have direct references to `Robot` instances, however that will expose the `move()` method, which should remain encapsulated from everything other than the `Table` class.

Therefore, the controller should interact with the `Robot` class via an interface, similar to how the `Table` does. 

### The "Turnable" Interface

The behavior that the controller requires is:

- `turn()`: So that it can handle the `LEFT` and `RIGHT` commands.
- `getPosition()`: So it can help render `REPORT` information to the scree
- `getOrientation()`: As above

Therefore, the `Robot` class will implement both the `Turnable` and `TableEntity` interfaces.

### Instance Variables

- `active`: The ID of the selected `Robot`. Integer type.
- `robots`: A reference to all the robots on the `Table`. It is a hash map of Integer IDs to `Turnable` objects.
- `table`: A reference to the table object.
- `displayCallback`: The function that gets called when a report is requested

### Initialization

The controller requires a `Table` object to be initialized and nothing else.

### Behavior

The controller should map all of the user commands on a 1:1 ratio. It should also accept a callback function so that the `REPORT` function can correctly update the display. Functions are as follows:

- `setDisplayCallback()`: Sets the function to call when a report is requested.
- `onLeft()`: Look at active robot, then calls the `turn()` method on the active robot. Ignores if active robot isn't set.
- `onRight()`: Same as above, with a different argument
- `onRobot()`: Change the active robot variable, ignore if ID isn't present in hash map.
- `onMove()`: Call the table's `turn()` method, providing the ID of the active robot.
- `onPlace()`: Call the table's `register()` method, and appends the returned ID to the `robots` map. Does nothing if `table` returns an error.
- `onReport()`: Calls the `displayCallback()` function. If unset, an exception is thrown.

## Display Implementation

## Testing
