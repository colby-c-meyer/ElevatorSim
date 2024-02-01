# ElevatorSim

Simple command line java application to provide a very basic example of how I try to organize my code and logic

### MVC pattern

I think the MVC pattern makes a lot of sense, much simpler to read and maintain.
also demonstrates the SOLID philosophy of software engineering

### Model Package
contains the objects that are used in the simulation, they hold the state of various things that are at play

### Controller Package
contains the class which implements the business logic for the application
as this is a very simple example, only 1 class exits

### View Package
contains the user interface component(s) for the application. Being that this is a command line simulator
only very basic UI components are defined, in a single file
#### HOWEVEVR
If you wanted to get fancy you could very easily implement JavaFX or Swing components and put them in here
and the logic itself wouldn't necessarily have to change in the controller.
you would just rewrite the UserInterface class to be a delegate which would then call / display the
relevant information in your chosen UI.
