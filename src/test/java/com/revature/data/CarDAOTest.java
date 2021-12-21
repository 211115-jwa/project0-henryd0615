package com.revature.data;

import org.junit.jupiter.api.Test;

import com.revature.beans.Car;
import com.revature.data.postgres.CarPostgres;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class CarDAOTest {
	private CarDAO carDao = new CarPostgres();

	@Test
	public void getByIdWhenIdExists() {

		int idInput = 1;

		Car idOutput = carDao.getById(idInput);

		assertEquals(1, idOutput.getId());
	}

	@Test
	public void getByIdWhenIdDoesNotExist() {

		int idInput = -1;

		Car carOutput = carDao.getById(idInput);

		assertNull(carOutput);
	}

	@Test
	public void getAll() {

		Set<Car> givenOutput = carDao.getAll();

		assertNotNull(givenOutput);
	}

	@Test
	public void addNewCar() {

		Car newCar = new Car();

		int generatedId = carDao.create(newCar);

		assertNotEquals(1, generatedId);

	}

	@Test
	public void getByModelWhenModelExists() {

		String modelInput = "X5";

		int amountOfX5 = 1;

		Set<Car> carOutput = carDao.getByModel(modelInput);

		assertEquals(amountOfX5, carOutput.size());

	}

	@Test
	public void getByMakeWhenMakeExists() {

		String makeInput = "Honda";

		int amountOfHonda = 1;

		Set<Car> carOutput = carDao.getByMake(makeInput);

		assertEquals(amountOfHonda, carOutput.size());

	}

	@Test
	public void getByStatusWhenStatusExists() {

		String statusInput = "Purchased";

		int amountOfStatus = 0;

		Set<Car> carOutput = carDao.getByMake(statusInput);

		assertEquals(amountOfStatus, carOutput.size());

	}

	@Test
	public void getByMakeWhenMakeDoesNotExist() {

		String makeInput = "fewcejdsn";

		Set<Car> carOutput = carDao.getByMake(makeInput);

		assertTrue(carOutput.isEmpty());

	}

	@Test
	public void getByModelWhenModelDoesNotExist() {

		String makeInput = "ehbrv";

		Set<Car> carOutput = carDao.getByModel(makeInput);

		assertTrue(carOutput.isEmpty());
	}

	@Test
	public void getByStatusWhenStatusDoesNotExist() {

		String statusInput = "Unavailable";

		Set<Car> carOutput = carDao.getByModel(statusInput);

		assertTrue(carOutput.isEmpty());
	}

}
