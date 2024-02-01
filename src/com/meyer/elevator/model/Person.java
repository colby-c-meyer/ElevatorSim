package com.meyer.elevator.model;

/**
 * a Person is either a passenger or an operator of the elevator
 * whats important here is the Person has a mass, which affects the elevator
 * they also have a name, cause they're not a faceless automation
 * 
 * @author Colby
 *
 */
public abstract class Person {
	
	private String name;

	private Integer weight;

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
