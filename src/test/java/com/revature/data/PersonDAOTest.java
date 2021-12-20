package com.revature.data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.beans.Person;
import com.revature.data.postgres.PersonPostgres;

// this imports the static methods from Assertions so that
// we can type "assertEquals" rather than "Assertions.assertEquals"
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;


public class PersonDAOTest {
	private PersonDAO personDao = new PersonPostgres();
	
	@Test
	public void getAllNotNull() {
		Set<Person> actual = personDao.getAll();
		assertNotEquals(null, actual);
	}
	
	@Test
	public void getValidPersonById()
	{
		String expectedUsername = "sierra";
		Person actual = personDao.getById(1);
		assertEquals(expectedUsername, actual.getUsername());
	}

	@Test
	public void testUpdate() {
		Person personUp = personDao.getById(1);
		personUp.setFullName("ricky");
		personDao.update(personUp);
		assertEquals("Matt",personDao.getById(1).getFullName());	
	}
	
	@Test
	public void testGetIDNoID() {
		Person personOutput= personDao.getById(10000);
		assertNull(personOutput);
	}
	
	@Test
	public void createTest() {
		Person create = new Person();
		assertNotEquals(0, personDao.create(create));
	}
	
	@Test
	public void getByUsernameWhenUsernameExists() {
		String usernameInput = "Henry";
		Person personOutput = personDao.getByUsername(usernameInput);
		assertEquals("Henry", personOutput.getUsername());
	}
	
	@Test
	public void getByUsernameButUsernameDoesNotExist() {
		String usernameInput = "qwertyuiop";
		Person personOutput = personDao.getByUsername(usernameInput);
		assertNull(personOutput); // assertEquals(null, personOutput)
	}
	
	@Test
	public void basicTest() {
		StringBuilder input = new StringBuilder("hello");
		input.reverse();
		Assertions.assertEquals("olleh", input.toString());
	}
	
	@Test
	public void basicTest2() {
		StringBuilder input = new StringBuilder("hello");
		String output = input.substring(0,1);
		Assertions.assertEquals("h", output);
	}
	
	@BeforeAll 
	public static void setup() {
		System.out.println("this happens before any tests");
	}
	
	@BeforeEach 
	public void beforeEachTest() {
		System.out.println("this happens before each test");
	}
	
	@AfterEach 
	public void afterEachTest() {
		System.out.println("this happens after each test");
	}
	
	@AfterAll 
	public static void tearDown() {
		System.out.println("this happens after all the tests have completed");
	}
}
