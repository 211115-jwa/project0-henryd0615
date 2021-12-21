package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.beans.Car;
import com.revature.data.CarDAO;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	@Mock
	private CarDAO carDao;
	
	@InjectMocks
	private EmployeeService empServ = new EmployeeServiceImpl();
	
	@Test
	public void addNewCarSuccessfully() {
		
		Car car = new Car();
		
		when(carDao.create(car)).thenReturn(10);
		
		int newId = empServ.addNewCar(car);
		
		assertNotEquals(0, newId);
	}
	
	@Test
	public void addNewCarSomethingWrong() {
		
		Car car = new Car();
		
		when(carDao.create(car)).thenReturn(0);
		
		int newId = empServ.addNewCar(car);
		
		assertEquals(0,newId);
	}
	
	@Test
	public void editCarSuccessfully() {
		
		Car editedCar = new Car();
		editedCar.setId(2);
		editedCar.setYear(2017);
		
		when(carDao.getById(2)).thenReturn(editedCar);
		doNothing().when(carDao).update(Mockito.any(Car.class));
		
		Car actualCar = empServ.editCar(editedCar);
		
		assertEquals(editedCar, actualCar);
	}
	
	@Test
	public void editCarSomethingWrong() {
		
		Car mockCar = new Car();
		mockCar.setId(2);
		
		when(carDao.getById(2)).thenReturn(mockCar);
		doNothing().when(carDao).update(Mockito.any(Car.class));
		
		Car editedCar = new Car();
		editedCar.setId(2);
		editedCar.setYear(2017);
		
		Car actualCar = empServ.editCar(editedCar);
		
		assertNotEquals(editedCar, actualCar);
	}
	
	@Test
	public void getByIdCarExists() {
		
		Car car = new Car();
		car.setId(2);
		
		when(carDao.getById(2)).thenReturn(car);
		
		Car actualCar = empServ.getCarById(2);
		assertEquals(car, actualCar);
	}
	
	@Test
	public void getByIdCarDoesNotExist() {
		
		when(carDao.getById(2)).thenReturn(null);
		
		Car actualCar = empServ.getCarById(2);
		
		assertNull(actualCar);
	}
}