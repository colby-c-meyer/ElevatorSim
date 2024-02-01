package com.meyer.elevator;

import com.meyer.elevator.controller.ElevatorController;

/**
 * the ElevatorSim is very basic elevator simulator, shows some very basic examples of
 * abstracting out the model (data)
 *  presentation layer - the UserInterface
 *  and the business logic - Controller
 *  
 * @author Colby
 *
 */
public class ElevatorSim {

	ElevatorController controller;
	
	public static void main(String[] args) {
		
		ElevatorSim sim = new ElevatorSim();
		
		sim.initialize();
		while( sim.isActive()) {
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
				System.err.println(" Apparently there has been a natural disaster that has shut the building down!!!");
				System.exit(-1);
			}
		}
		
		System.exit(0);
	}

	// start the application up
	public void initialize() {
		controller = new ElevatorController();
	}
	
	// check if we are still running
	public boolean isActive() {
		if( controller.isElevatorActive()) {
			return true;
		}
		return false;
	}
	
}
