package de.truschin.helloworldpaas;

import org.springframework.data.annotation.Id;

import net.vz.mongodb.jackson.ObjectId;

public class Person {

	@Id 
	public String _id;

	String firstName;

	String secondName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Person(String firstName, String secondName) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	@Override
	public String toString() {
		return "Person [First Name = " + this.firstName + " Second Name = " + this.secondName + "]";
	}

}
