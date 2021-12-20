package com.revature.data;

import com.revature.beans.Person;

public interface PersonDAO extends GenericDAO<Person> {
	public Person getByUsername(String username);
}