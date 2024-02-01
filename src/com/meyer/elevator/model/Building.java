package com.meyer.elevator.model;

/**
 * the building object, number of floors is a big one if you're talking elevators
 * @author Colby
 *
 */
public class Building {

	private String name;
	
	private Integer numberOfFloors;
	
	private Boolean buildingOpen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(Integer numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public Boolean isBuildingOpen() {
		return buildingOpen;
	}

	public void setBuildingOpen(Boolean buildingOpen) {
		this.buildingOpen = buildingOpen;
	}
	
	
	
}
