package com.revature.services;

import java.util.Set;

import com.revature.beans.Car;
import com.revature.beans.Person;


public interface UserService {
	
	public Person register(Person newUser);
	public Person logIn(String username, String password);
	public Person updateUser(Person userToUpdate);
	public Person purchaseCar(int carId, Person newOwner);
	public Set<Car> viewAvailableCars();
	public Set<Car> searchAvailableCarsByModel(String model);
}
