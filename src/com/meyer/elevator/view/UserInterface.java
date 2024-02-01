package com.meyer.elevator.view;

import java.util.Scanner;

import com.meyer.elevator.controller.ElevatorController;

/**
 * This is a simple console based UI, different messages are displayed to the user based on how they are interacting
 * the decision to call different messages is made in the controller class.
 * The UI has a reference to the controller so that we can pass information back to it for action
 * 
 * @author Colby
 *
 */
public class UserInterface {
	
	Scanner scanner;
	// reference to the controller so that we can pass information back to it, effectively send it events
	ElevatorController controller;
	
	public UserInterface() {
		scanner = new Scanner(System.in);
	}
	
	public void setController( ElevatorController inController) {
		this.controller = inController;
	}
	
	public void issueUserGreeting() {
		
		System.out.println(String.format("Welcome to the %s ", this.controller.getBuildingName()));
		System.out.println(String.format("I am %s, the building elevator operator, don't you like my beautiful %s gloves?"
					, this.controller.getOperatorName()
					, this.controller.getOperatorGloveColor()));
		System.out.println(("If you would like to use the elevator, please type elevator, if you are done visiting our building, please type exit"));
		this.controller.acceptUserInput(scanner.next());
		return;
	}
	

	public void unknownInput() {
		System.out.println("Unknown user input, please try again...");
		this.controller.acceptUserInput(scanner.next());
	}
	
	public void getNumPassengers() {
		System.out.println(" Please tell me how many passengers wish to ride the elevator.");
		this.controller.acceptUserInput(scanner.next());
	}
	
	public void getNumPassengers(String message) {
		System.out.println(String.format(" There was a problem , %s", message));
		System.out.println(" Please tell me how many passengers wish to ride the elevator.");
		this.controller.acceptUserInput(scanner.next());
	}
	
	
	public void sayGoodbye() {
		System.out.println( String.format("Thank you for visiting the %s , Have a great day, Goodbye", this.controller.getBuildingName()));
	}
	
	public void getPassengerWeights(Integer numPassengers) {
		System.out.println(String.format(" Please provide me a comma seprated list of %d passenger weights, just commas and numbers please.",numPassengers));
		this.controller.acceptUserInput(scanner.next());
	}
	
	public void getPassengerWeights(Integer numPassengers, String message) {
		System.out.println(message);
		System.out.println(String.format(" Please provide me a comma seprated list of %d passenger weights, just commas and numbers please.",numPassengers));
		this.controller.acceptUserInput(scanner.next());
	}
	
	/**
	 * getDesiredFloor - prompts user for target floor
	 */
	public void getDesiredFloor() {
		System.out.println("What a svelte group you are...");
		System.out.println("What floor would you like please?");
		this.controller.acceptUserInput(scanner.next());
	}
	
	/**
	 * prompt the user for the floor they are targeting, but also display a message
	 * this is used if the user did something silly
	 * @param message
	 */
	public void getDesiredFloor(String message) {
		System.out.println(message);
		System.out.println("What floor would you like please?");
		this.controller.acceptUserInput(scanner.next());
	}
	
	/**
	 * elevatorInUse is moving the elevator between floors, show current floor info 
	 * @param currentFloor
	 * @param desiredFloor
	 * @throws Exception
	 */
	public void elevatorInUse(Integer currentFloor)  {
		
		System.out.println(String.format(" Passing floor %d", currentFloor));
		System.out.println("...");
		
	}
	
	/**
	 * elevator has arrived at a target floor, passengers do their thing
	 * @param currentFloor
	 */
	public void elevatorWaiting(Integer currentFloor) {
		System.out.println(String.format(" Here we are, floor number %d", currentFloor));
		System.out.println("I'll wait here for you, when you're done, come back and tell me what floor you would like next");
		this.controller.acceptUserInput(scanner.next());
	}
	
	public void evacuateTheBuilding() {
		System.out.println("Oh my something terrible has happened! Please take the stairs and evacuate");
	}
	
}
