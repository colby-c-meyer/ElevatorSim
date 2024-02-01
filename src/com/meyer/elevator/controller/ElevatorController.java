package com.meyer.elevator.controller;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.meyer.elevator.model.Building;
import com.meyer.elevator.model.Car;
import com.meyer.elevator.model.ElevatorState;
import com.meyer.elevator.model.Operator;
import com.meyer.elevator.model.Passenger;
import com.meyer.elevator.view.UserInterface;

/**
 * ElevatorController contains the business logic to operate the elevator properly
 * and coordinates the communication between the model objects which represent the state of the application
 * and the user interface component, which in this case is the console.
 * 
 * @author Colby
 *
 */
public class ElevatorController {

	private Building building;
	private Car car;
	
	private Operator operator;
	
	private UserInterface userInterface;
	
	private ElevatorState activeState;
	
	
	private Pattern p = Pattern.compile("(\\p{Digit}+,?)+");
			
	private static final Integer PASSENGER_LIMIT = 11;
	private static final Integer INDIVIDUAL_WEIGHT_LIMIT = 250;
	
	// Enhancement - pull all of the following from a properties file
	private String BUILDING_NAME = "The Empire State Building";
	private String OPERATOR_NAME = "Jimmy Smits";
	private String OPERATOR_GLOVE_COLOR = "White";
	private Integer OPERATOR_WEIGHT = 175;
	
	/**
	 * ElevatorController is the brains of the application
	 * all of the logic, state decisions and such are handled here
	 * this application is somewhat of lame version of an MVC application
	 */
	public ElevatorController() {
		
		this.building = new Building();
		this.building.setBuildingOpen(true);
		this.building.setNumberOfFloors(10);
		this.building.setName(this.BUILDING_NAME);
		
		this.car = new Car();
		this.car.setCapacity(PASSENGER_LIMIT);
		this.car.setWeightLimit( this.car.getCapacity() * INDIVIDUAL_WEIGHT_LIMIT);
		
		// create the elevator operator, this is a high class joint
		this.operator = new Operator();
		this.operator.setName(this.OPERATOR_NAME);
		this.operator.setWeight(this.OPERATOR_WEIGHT);
		this.operator.setGloveColor(this.OPERATOR_GLOVE_COLOR);
		
		// the operator is a person too!
		this.car.addPerson(this.operator);
		
		this.activeState = ElevatorState.INIT_STATE;
		
		this.userInterface = new UserInterface();
		this.userInterface.setController(this);
		// Things are set up load up the first set of instructions to the user
		this.userInterface.issueUserGreeting();
	}
	
	/**
	 * the elevator is considered active if the building is open for business
	 */
	public Boolean isElevatorActive() {
		
		return building.isBuildingOpen();
	}
	
	public String getBuildingName() {
		return this.building.getName();
	}
	
	public String getOperatorName() {
		return this.operator.getName();
	}
	
	public String getOperatorGloveColor() {
		return this.operator.getGloveColor();
	}
	
	/**
	 * acceptUserInput is the "callback" method from the UserInterface
	 * any input is processed through here and depending on our state 
	 * we make the appropriate decision
	 * @param userInput
	 */
	public void acceptUserInput(String userInput) {
		
		switch ( this.activeState ) {
			case INIT_STATE:
				// We're gonna go for a ride , Maybe
				switch( userInput) {
				case "exit":
					this.userInterface.sayGoodbye();
					this.building.setBuildingOpen(false);
					break;
				case "elevator":
					this.activeState = ElevatorState.NUM_PASSENGERS;
					this.userInterface.getNumPassengers();
					break;
				default:
					this.userInterface.unknownInput();
					break;
				}
				break;
			case NUM_PASSENGERS:
				// we asked how many passengers they want to ride the elevator
				// validate that user provided an integer, if not return error
				try {
					Integer numPassengers = Integer.valueOf(userInput);
					if ( numPassengers > this.car.getCapacity()) {
						String message = String.format("The car only holds %s passengers, of which I am one, please tell %s folks to wait in the lobby"
													, String.valueOf(car.getCapacity())
													, String.valueOf( (numPassengers - 1) - car.getCapacity()));
						
						this.userInterface.getNumPassengers(message);
					} else {
						
						this.car.setCurrentCapacity(numPassengers);
						this.activeState = ElevatorState.PASSENGER_WEIGHTS;
						this.userInterface.getPassengerWeights(this.car.getCurrentCapacity());
					}
					
				} catch ( NumberFormatException nfex) {
					this.userInterface.unknownInput();
				}
				break;
			case PASSENGER_WEIGHTS:
				// we should be looking at a user defined comma-separated list of weights
				Matcher testIt =  p.matcher(userInput);
				
				if(testIt.matches()) {
					String[] tempWeight = userInput.split(",");
					if ( tempWeight.length == this.car.getCurrentCapacity()) {
						
						for ( String w : tempWeight) {
							Passenger p = new Passenger();
							p.setWeight(Integer.valueOf(w));
							// and put the passenger in the car
							this.car.addPerson(p);;
						}
						
						// Now that everybody is on board, validate you're not overweight
						if ( this.car.isOverweight()) {
							this.activeState = ElevatorState.NUM_PASSENGERS;
							// everybody out ...
							car.getPassengers().clear();
							// except the operator
							car.addPerson(this.operator);
							
							String message = String.format("I'm sorry, we've exceeded the weight limit, I weigh %d pounds and the elevator " +
														"can only hold %d pounds.\n Please re-organize your group so that the " +
														"number and weight limit is met", this.operator.getWeight(), this.car.getWeightLimit());
							this.userInterface.getNumPassengers(message);
							
						} else {
							this.activeState = ElevatorState.DESIRED_FLOOR;
							this.userInterface.getDesiredFloor();
						}
					} else {
						String message = "You provided an incorrect amount of weight values";
						this.userInterface.getPassengerWeights(this.car.getCurrentCapacity(), message);
						
					}
				} else {
					this.userInterface.unknownInput();
				}
				break;
			case DESIRED_FLOOR:
				// we've captured the desired floor, we can now activate the elevator
				try {
					this.car.setDesiredFloor(Integer.valueOf(userInput));
					this.activeState = ElevatorState.ELEVATOR_IN_USE;
					moveElevatorCar();
					
				} catch (NumberFormatException nfex){
					this.userInterface.unknownInput();
				} catch (Exception ex) {
					this.userInterface.evacuateTheBuilding();
					this.building.setBuildingOpen(false);
				}
				
				break;
			case ELEVATOR_IN_USE:
				// here we are on a floor in the building, user has elected to go to another floor
				try {
					this.car.setDesiredFloor(Integer.valueOf(userInput));
					if ( this.car.getDesiredFloor() == 1) {
						this.activeState = ElevatorState.INIT_STATE;
					} else {
						this.activeState = ElevatorState.ELEVATOR_IN_USE;
					}
					moveElevatorCar();
					if ( this.car.getDesiredFloor() == 1) {
						
						//reset the elevator to empty
						this.car.getPassengers().clear();
						this.car.setCurrentCapacity(0);
						this.car.setCurrentWeight(0);
						// adding a person sets the weight and capacity correctly
						this.car.addPerson(this.operator);
						
						this.userInterface.issueUserGreeting();
					}
					
				} catch (NumberFormatException nfex){
					this.userInterface.unknownInput();
				} catch (Exception ex) {
					this.userInterface.evacuateTheBuilding();
					this.building.setBuildingOpen(false);
				}
				
				break;
		}
	}
	
	/**
	 * moveElevatorCar is called from a couple of places, so its a method
	 * this makes sure that we can go where we want to go and then calls the display
	 * method which will show us the elevator moving
	 * @throws Exception
	 */
	private void moveElevatorCar() throws Exception {
		
		if (this.car.getDesiredFloor() > this.building.getNumberOfFloors()) {
			String message = String.format("I'm sorry, this building only has %d floors, Please enter a valid floor number", this.building.getNumberOfFloors());
			this.userInterface.getDesiredFloor(message);
		} 
		else {
			int currentFloor = this.car.getCurrentFloor();
			this.car.setCurrentFloor(this.car.getDesiredFloor());
			this.userInterface.elevatorInUse(currentFloor, this.car.getDesiredFloor() );
		}
	}
	
	
}
