package com.gaesample.main.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class EmployeeEMF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	@Basic
	private final String firstName;

	@Basic
	private final String lastName;

	@Basic
	private final Date hireDate;

	public EmployeeEMF(String firstName, String lastName, Date hireDate) {
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