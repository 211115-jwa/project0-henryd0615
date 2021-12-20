package com.revature.services;

import com.revature.beans.Car;

public interface EmployeeService {
	public int addNewCar(Car newCar);
	public Car editCar(Car carToEdit);
	public Car getCarById(int id);
}
