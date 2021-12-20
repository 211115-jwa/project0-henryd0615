package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.beans.Person;
import com.revature.beans.Car;
import com.revature.data.PersonDAO;
import com.revature.data.CarDAO;

// tell JUnit that we're using Mockito
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	// tell Mockito which classes/interfaces that we'll be mocking
	@Mock
	private CarDAO carDao;
	
	@Mock
	private PersonDAO personDao;
	
	// tell Mockito to override the regular DAOs with our mock DAOs
	@InjectMocks
	private UserService userServ = new UserServiceImpl();
	
	private static Set<Car> mockAvailableCars;
	
	@BeforeAll
	public static void mockAvailableCarsSetup() {
		mockAvailableCars = new HashSet<>();
		
		for (int i=1; i<=5; i++) {
			Car car = new Car();
			car.setId(i);
			if (i<3)
				car.setModel("BMW");
			mockAvailableCars.add(car);
		}
	}
	
	@Test
	public void logInSuccessfully() {
		// input setup
		String username="BookJean";
		String password="BookJeanpass";
		
		// set up the mocking
		Person mockPerson = new Person();
		mockPerson.setUsername(username);
		mockPerson.setPassword(password);
		when(personDao.getByUsername(username)).thenReturn(mockPerson);
		
		// call the method we're testing
		Person actualPerson = userServ.logIn(username, password);
		
		// assert the expected behavior/output
		assertEquals(mockPerson,actualPerson);
	}
	
	@Test
	public void logInIncorrectPassword() {
		String username="BookJean";
		String password="1111";
		
		Person mockPerson = new Person();
		mockPerson.setUsername(username);
		mockPerson.setPassword("BookJeanpass");
		when(personDao.getByUsername(username)).thenReturn(mockPerson);
		
		Person actualPerson = userServ.logIn(username, password);
		assertNull(actualPerson);
	}
	
	@Test
	public void logInUsernameDoesNotExist() {
		String username="juanito";
		String password="BokJeanpass";
		
		when(personDao.getByUsername(username)).thenReturn(null);
		
		Person actualPerson = userServ.logIn(username, password);
		assertNull(actualPerson);
	}
	
	@Test
	public void registerPersonSuccessfully() {
		Person person = new Person();
		
		when(personDao.create(person)).thenReturn(2017);
		
		Person actualPerson = userServ.register(person);
		assertEquals(2017, actualPerson.getId());
	}
	
	@Test
	public void registerPersonSomethingWrong() {
		Person person = new Person();
		when(personDao.create(person)).thenReturn(0);
		Person actualPerson = userServ.register(person);
		assertNull(actualPerson);
	}
	
	@Test
	public void searchBySpeciesExists() {
		String model = "BMW";
		
		when(carDao.getByStatus("Available")).thenReturn(mockAvailableCars);
		
		Set<Car> actualBMW = userServ.searchAvailableCarsByModel(model);
		boolean onlyBMW = true;
		for (Car car : actualBMW) {
			if (!car.getModel().equals(model))
				onlyBMW = false;
		}
		
		assertTrue(onlyBMW);
	}
	
	@Test
	public void searchBySpeciesDoesNotExist() {
		String model = "fdcv";
		
		when(carDao.getByStatus("Available")).thenReturn(mockAvailableCars);
		
		Set<Car> actualCars = userServ.searchAvailableCarsByModel(model);
		assertTrue(actualCars.isEmpty());
	}
	
	@Test
	public void purchaseCarSuccessfully() {
		int carId = 1;
		Person person = new Person();
		
		Car mockCar = new Car();
		mockCar.setId(1);
		when(carDao.getById(carId)).thenReturn(mockCar);
		
		// mock will do nothing when "update" gets called with any pet or person
		doNothing().when(carDao).update(Mockito.any(Car.class));
		doNothing().when(personDao).update(Mockito.any(Person.class));
		
		Person newPerson = userServ.purchaseCar(carId, person);
		
		// make sure that the method returned a person that has their
		// newly adopted pet there, and that pet has the correct status
		mockCar.setStatus("Purchsed");
		assertTrue(newPerson.getCars().contains(mockCar));
	}
	
	@Test
	public void purchaseCarAlreadyPurchased() {
		int carId = 1;
		Person person = new Person();
		
		Car mockCar = new Car();
		mockCar.setId(1);
		mockCar.setStatus("Purchased");
		when(carDao.getById(carId)).thenReturn(mockCar);
		
		Person newPerson = userServ.purchaseCar(carId, person);
		
		assertNull(newPerson);
		
		// these Mockito methods will verify that neither of these
		// update methods got called
		verify(carDao, times(0)).update(Mockito.any(Car.class));
		verify(personDao, times(0)).update(Mockito.any(Person.class));
	}
	
	@Test
	public void updateSuccessfully() {
		Person mockPerson = new Person();
		mockPerson.setId(1);
		
		doNothing().when(personDao).update(Mockito.any(Person.class));
		when(personDao.getById(1)).thenReturn(mockPerson);
		
		Person person = new Person();
		person.setId(1);
		person.setUsername("fdcv");
		Person updatedPerson = userServ.updateUser(person);
		assertNotEquals(person, updatedPerson);
	}
	
	@Test
	public void updateSomethingWrong() {
		Person mockPerson = new Person();
		mockPerson.setId(1);
		
		doNothing().when(personDao).update(Mockito.any(Person.class));
		when(personDao.getById(1)).thenReturn(mockPerson);
		
		Person person = new Person();
		person.setId(1);
		person.setUsername("fdcv");
		Person updatedPerson = userServ.updateUser(person);
		assertNotEquals(person, updatedPerson);
	}
	
	@Test
	public void viewAvailableCars() {
		when(carDao.getByStatus("Available")).thenReturn(mockAvailableCars);
		
		Set<Car> actualCars = userServ.viewAvailableCars();
		
		assertEquals(mockAvailableCars, actualCars);
	}
}