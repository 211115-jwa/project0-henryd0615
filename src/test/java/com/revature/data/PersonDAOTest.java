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

//@TestMethodOrder(Alphanumeric.class)
//@TestMethodOrder(OrderAnnotation.class)
public class PersonDAOTest {
	private PersonDAO personDao = new PersonPostgres();
	
	// made by vanquish
	@Test
	public void getAllNotNull() {
		Set<Person> actual = personDao.getAll();
		assertNotEquals(null, actual);
	}
	
	// made by vanquish w/ some edits by sierra
	@Test
	public void getValidPersonById()
	{
		String expectedUsername = "sierra";
		Person actual = personDao.getById(1);
		assertEquals(expectedUsername, actual.getUsername());
	}

	// made by alchemy
	@Test
	public void testUpdate() {
		Person personUp = personDao.getById(1);
		personUp.setFullName("ricky");
		personDao.update(personUp);
		assertEquals("Matt",personDao.getById(1).getFullName());	
	}
	
	// made by alchemy with edit by sierra
	@Test
	public void testGetIDNoID() {
		Person personOutput= personDao.getById(10000);
		assertNull(personOutput);
	}
	
	// made by synergy
	@Test
	public void createTest() {
		Person create = new Person();
		assertNotEquals(0, personDao.create(create));
		// use person dao to test that create method is not null
	}
	
	//@Order(1)
	@Test
	public void getByUsernameWhenUsernameExists() {
		// setup
		String usernameInput = "Henry";
		// call the method we're testing
		Person personOutput = personDao.getByUsername(usernameInput);
		// assert that it did what we expected
		assertEquals("Henry", personOutput.getUsername());
	}
	
	//@Order(2)
	@Test
	public void getByUsernameButUsernameDoesNotExist() {
		String usernameInput = "qwertyuiop";
		Person personOutput = personDao.getByUsername(usernameInput);
		assertNull(personOutput); // assertEquals(null, personOutput)
	}
	
	// JUnit tests will be annotated with @Test
	// they are public, void, and have no parameters
	@Test
	public void basicTest() {
		// setup
		StringBuilder input = new StringBuilder("hello");
		// call the method we're testing
		input.reverse();
		// check for the expected behavior
		Assertions.assertEquals("olleh", input.toString());
	}
	
	@Test
	public void basicTest2() {
		// setup
		StringBuilder input = new StringBuilder("hello");
		// call the method we're testing
		String output = input.substring(0,1);
		// check for the expected behavior
		Assertions.assertEquals("h", output);
	}
	
	@BeforeAll // this method will run ONCE before any of our tests run
	public static void setup() {
		System.out.println("this happens before any tests");
	}
	
	@BeforeEach // this method runs before each test
	public void beforeEachTest() {
		System.out.println("this happens before each test");
	}
	
	@AfterEach // this method runs after each test
	public void afterEachTest() {
		System.out.println("this happens after each test");
	}
	
	@AfterAll // this method will run ONCE after all tests have completed
	public static void tearDown() {
		System.out.println("this happens after all the tests have completed");
	}
}
