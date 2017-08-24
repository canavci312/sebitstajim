package com.canavci.library.dom;

public class Person {
	int personID;
	String personName;
	 
	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Override
	public String toString() {
		return personID +" "+ personName;
	}

}
