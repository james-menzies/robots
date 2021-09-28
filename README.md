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

There are 2 primary components in this program, the **ROBOT** and the **TABLE.**

### Robot

The `Robot` class represents instances of the physical robots that roam on the table. 
#### Instance Variables
* `orientation` - The direction the `Robot` is facing, must be provided on initialization.
#### Methods
* `Orientation getOrientation()` 
* `void turn(Direction d)` - This will analyze the direction provided and update the `orientation` variable.
* `Coordinate getIntent(Coordinate c)` - This will calculate where this object would move based on the given `Coordinate`

### Table

The table cares about its dimensions, and what positions are occupied. Therefore it needs to maintain a reference of every `Robot` that exists on it. This is fine, however it would be nice to prevent tight coupling between `Robot` and `Table`, so we could allow different objects to roam on our table. Perhaps we may have a `Frog` ****object in the future that moves 2 spaces at a time! 

To solve this, the `Robot` ****object will implement an interface `TableEntity` that only exposes the parts of `Robot` that `Table` is interested in (see below).

To be initialized, the table needs initial demonstrations. Whilst the demo states the dimensions are always 5x5, it would mean less refactoring going forward if the table was able to accept different dimensions upon initialization.

The behavior that TABLE exposes is as follows:

- A register method, which takes a `TableEntity` so that it can track its movements. If the `TableEntity` has an invalid position, an exception will be thrown. It returns a numerical identifier for reference
- A move method, which takes an identifier and moves the entity (robot) if it is legal to do so.

### TableEntity

The `TableEntity` interface should expose the following:

- `getPosition`: Get coordinates of entity
- `getIntent`: Get coordinates of where entity would move
- `move`: Direct entity to move.


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
