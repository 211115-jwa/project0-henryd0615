package com.revature.services;

import java.util.Set;
import java.util.stream.Collectors;

import com.revature.beans.Car;
import com.revature.beans.Person;
import com.revature.data.CarDAO;
import com.revature.data.PersonDAO;
import com.revature.data.postgres.PersonPostgres;
import com.revature.data.postgres.CarPostgres;

public class UserServiceImpl implements UserService {
	private PersonDAO personDao = new PersonPostgres();
	private CarDAO carDao = new CarPostgres();

	@Override
	public Person register(Person newUser) {
		int newId = personDao.create(newUser);
		if (newId != 0) {
			newUser.setId(newId);
			return newUser;
		}
		return null;
	}

	@Override
	public Person logIn(String username, String password) {
		Person personFromDatabase = personDao.getByUsername(username);
		if (personFromDatabase != null && personFromDatabase.getPassword().equals(password)) {
			return personFromDatabase;
		}
		return null;
	}

	@Override
	public Person updateUser(Person userToUpdate) {
		if (personDao.getById(userToUpdate.getId()) != null) {
			personDao.update(userToUpdate);
			userToUpdate = personDao.getById(userToUpdate.getId());
			return userToUpdate;
		}
		return null;
	}

	@Override
	public Person purchaseCar(int carId, Person newOwner) {
		Car carToPurchase = carDao.getById(carId);
		if (carToPurchase.getStatus().equals("Available")) {
			carToPurchase.setStatus("Purchased");
			newOwner.getCars().add(carToPurchase);

			carDao.update(carToPurchase);
			personDao.update(newOwner);
			return newOwner;
		}
		return null;
	}

	@Override
	public Set<Car> viewAvailableCars() {
		return carDao.getByStatus("Available");
	}

	@Override
	public Set<Car> searchAvailableCarsByModel(String model) {
		Set<Car> availableCars = carDao.getByStatus("Available");

		availableCars = availableCars.stream().filter(car -> car.getModel().toLowerCase().contains(model.toLowerCase()))
				.collect(Collectors.toSet());

		return availableCars;
	}

}