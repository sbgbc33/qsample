package com.gaesample.main.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Employee {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private final String firstName;

	@Persistent
	private final String lastName;

	@Persistent
	private final Date hireDate;

	public Employee(String firstName, String lastName, Date hireDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.hireDate = hireDate;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getHireDate() {
		return hireDate;
	}

}