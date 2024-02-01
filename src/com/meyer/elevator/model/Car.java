package com.meyer.elevator.model;

import java.util.ArrayList;
import java.util.List;

public class Car {

	private Integer capacity;
	
	private Integer currentCapacity;
	
	private Integer weightLimit;
	
	private Integer currentWeight;

	private List<Person> passengers = null;
	
	private Integer currentFloor;
	
	private Integer desiredFloor;
	
	/**
	 * object that represents the elevator car
	 * holds a number of passengers
	 * but it only holds so many
	 * and they can only be so fat
	 * the elevator also exists on a floor
	 * and it may desired to be sent to another floor
	 */
	public Car() {
		this.passengers = new ArrayList<Person>();
		this.currentFloor = 1;
		this.currentWeight = 0;
		this.currentCapacity = 0;
	}
	
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Integer weightLimit) {
		this.weightLimit = weightLimit;
	}

	public List<Person> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Person> passengers) {
		this.passengers = passengers;
	}
	
	public Integer getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(Integer currentFloor) {
		this.currentFloor = currentFloor;
	}
	
	public Integer getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(Integer currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public Integer getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(Integer currentWeight) {
		this.currentWeight = currentWeight;
	}

	public Integer getDesiredFloor() {
		return desiredFloor;
	}

	public void setDesiredFloor(Integer desiredFloor) {
		this.desiredFloor = desiredFloor;
	}

	/**
	 * addPerson adds the person (either Passenger or Operator)
	 * to the elevator and adjust the capacity and weight accordingly
	 * @param p
	 */
	public void addPerson(Person p) {
		
		this.currentWeight += p.getWeight();
		this.currentCapacity++;
		this.passengers.add(p);
		
	}

	/**
	 * isOverweight sums the weight of all passengers and ensure it does not exceed
	 * weightLimit
	 * @return
	 */
	public Boolean isOverweight() {
		
		int currentPayload = 0;
		for( Person p : this.passengers) {
			currentPayload += p.getWeight();
		}
		if ( currentPayload > this.weightLimit) {
			return true;
		}
		
		return false;
	}
	
}
