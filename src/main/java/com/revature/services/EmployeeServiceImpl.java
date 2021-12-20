package com.revature.services;

import com.revature.beans.Car;
import com.revature.data.CarDAO;
import com.revature.data.postgres.CarPostgres;

public class EmployeeServiceImpl implements EmployeeService {
	private CarDAO carDao = new CarPostgres();

	@Override
	public int addNewCar(Car newCar) {
		return carDao.create(newCar);
	}

	@Override
	public Car editCar(Car carToEdit) {
		Car carFromDatabase = carDao.getById(carToEdit.getId());
		if (carFromDatabase != null) {
			carDao.update(carToEdit);
			return carDao.getById(carToEdit.getId());
		}
		return null;
	}

	@Override
	public Car getCarById(int id) {
		return carDao.getById(id);
	}

}
