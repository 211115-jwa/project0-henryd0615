package com.revature.data;

import org.junit.jupiter.api.Test;

import com.revature.beans.Car;
import com.revature.data.postgres.CarPostgres;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class CarDAOTest {
	private CarDAO carDao = new CarPostgres();
	
	// made by synergy w/ small edit by sierra
	@Test // Test 
	public void getByIdWhenIdExists() {
		// setup
		int idInput = 1;
		// call the method we're testing
		Car idOutput = carDao.getById(idInput);
		// assert that it did what we expected
		assertEquals(1, idOutput.getId());
	}
	
	// made by amplifire
	@Test
	public void getByIdWhenIdDoesNotExist() {
		int idInput = -1;
		Car carOutput = carDao.getById(idInput);
		assertNull(carOutput);
	}
	
	// made by amplifire with edits by sierra
	@Test
	public void getAll() {
		Set<Car> givenOutput = carDao.getAll();
		assertNotNull(givenOutput);
	}
	
	@Test
	public void addNewCar() {
		Car newCar = new Car();
		System.out.println(newCar);
		
		int generatedId = carDao.create(newCar);
		
		assertNotEquals(1, generatedId);
		System.out.println(generatedId);
	}
}
