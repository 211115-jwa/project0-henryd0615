package com.revature.data;

import java.util.Set;

import com.revature.beans.Car;

public interface CarDAO extends GenericDAO<Car> {
	public Set<Car> getByStatus(String status);
	public Set<Car> getByModel(String model);
	public Set<Car> getByMake(String make);
}

