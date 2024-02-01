package com.meyer.elevator.model;

/**
 * the ElevatorState enum defines the "states" that the elevator can be in
 * 
 * INIT_STATE = we're on the ground floor, empty, waiting to go somewhere
 * 
 * NUM_PASSENGERS = we're waiting for a group of passengers to tell us how many they are
 * 
 * PASSENGER_WEIGHTS = we're waiting for the weights of the passengers, as we dont want to overload
 * 
 * DESIRED_FLOOR = We're waiting for the passengers to tell us where they want to go
 * 
 * ELEVATOR_IN_USE = the elevator is moving between floors in the building
 * 
 * @author Colby
 *
 */
public enum ElevatorState {
	
	INIT_STATE
	, NUM_PASSENGERS
	, PASSENGER_WEIGHTS
	, DESIRED_FLOOR
	, ELEVATOR_IN_USE
	
}
