package com.revature.data.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.revature.beans.Person;
import com.revature.beans.Car;
import com.revature.data.PersonDAO;
import com.revature.utils.ConnectionUtil;

public class PersonPostgres implements PersonDAO {
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public int create(Person dataToAdd) {
		int generatedId = 0;
		
		try (Connection conn = connUtil.getConnection()) {
			// when you run DML statements, you want to manage the TCL
			conn.setAutoCommit(false);
			
			String sql = "insert into person (id,fullname,username,passwd) " + "values (default, ?, ?, ?)";
			String[] keys = {"id"}; 
		
			PreparedStatement pStmt = conn.prepareStatement(sql, keys);
			pStmt.setString(1, dataToAdd.getFullName()); 
			pStmt.setString(2, dataToAdd.getUsername());
			pStmt.setString(3, dataToAdd.getPassword());
			
			pStmt.executeUpdate();
			ResultSet resultSet = pStmt.getGeneratedKeys();
			
			if (resultSet.next()) { 
				generatedId = resultSet.getInt("id");
				conn.commit(); 
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return generatedId;
	}

	@Override
	public Person getById(int id) {
		Person person = null;
		
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from person where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			if (resultSet.next()) {
				person = new Person();
				person.setId(id);
				person.setFullName(resultSet.getString("fullname"));
				person.setUsername(resultSet.getString("username"));
				person.setPassword(resultSet.getString("passwd"));
				
				person.setCars(getCarsByOwner(conn, person.getId()));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return person;
	}

	@Override
	public Set<Person> getAll() {
		Set<Person> allPeople = new HashSet<>();
		
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from person";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()) {

				Person person = new Person();

				person.setId(resultSet.getInt("id"));
				person.setFullName(resultSet.getString("fullname"));
				person.setUsername(resultSet.getString("username"));
				person.setPassword(resultSet.getString("passwd"));

				person.setCars(getCarsByOwner(conn, person.getId()));
				
				allPeople.add(person);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allPeople;
	}

	@Override
	public void update(Person dataToUpdate) {
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "update person set " + "fullname=?,username=?,passwd=? " + "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, dataToUpdate.getFullName());
			pStmt.setString(2, dataToUpdate.getUsername());
			pStmt.setString(3, dataToUpdate.getPassword());
			pStmt.setInt(5, dataToUpdate.getId());
			
			int rowsAffected = pStmt.executeUpdate();
			
			boolean carsUpdated = addNewOwnedCars(conn, dataToUpdate);
			
			if (rowsAffected<=1 && carsUpdated) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Person dataToDelete) {
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "delete from person "
					+ "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, dataToDelete.getId());
			
			int rowsAffected = pStmt.executeUpdate();
			
			if (rowsAffected==1) {
				sql="delete from car_owner where owner_id=?";
				PreparedStatement pStmt2 = conn.prepareStatement(sql);
				pStmt2.setInt(1, dataToDelete.getId());
				rowsAffected = pStmt2.executeUpdate();
				
				if (rowsAffected==dataToDelete.getCars().size()) {
					conn.commit();
				} else {
					conn.rollback();
				}
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Person getByUsername(String username) {
		Person person = null;
		
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from person where username=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, username);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			if (resultSet.next()) {
				person = new Person();
				person.setId(resultSet.getInt("id"));
				person.setFullName(resultSet.getString("fullname"));
				person.setUsername(resultSet.getString("username"));
				person.setPassword(resultSet.getString("passwd"));

				person.setCars(getCarsByOwner(conn, person.getId()));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return person;
	}

	private List<Car> getCarsByOwner(Connection conn, int personId) throws SQLException {
		List<Car> cars = new LinkedList<>();
		
		String sql = "select * from car join car_owner on car.id=car_owner.car_id where owner_id=?";
		PreparedStatement pStmt = conn.prepareStatement(sql);
		pStmt.setInt(1, personId);
		
		ResultSet resultSet = pStmt.executeQuery();
		
		while (resultSet.next()) {
			Car car = new Car();
			car.setId(resultSet.getInt("id"));
			car.setMake(resultSet.getString("make"));
			car.setModel(resultSet.getString("model"));
			car.setYear(resultSet.getInt("year"));
			car.setStatus(resultSet.getString("status"));
			
			cars.add(car);
		}
		
		return cars;
	}
	
	private boolean addNewOwnedCars(Connection conn, Person person) throws SQLException {
		String sql = "insert into car_owner (car_id,owner_id) values ";
		for (int i = 0; i < person.getCars().size(); i++) {
			sql += "(?,?)";
			if (i!=person.getCars().size()-1) sql+= ",";
		}
		
		PreparedStatement pStmt = conn.prepareStatement(sql);
		
		int parameterIndex = 1;
		for (Car car : person.getCars()) {
			pStmt.setInt(parameterIndex++, car.getId());
			pStmt.setInt(parameterIndex++, person.getId());
		}
		
		int rowsAffected = pStmt.executeUpdate();
		if (rowsAffected==person.getCars().size()) {
			return true;
		}
		return false;
	}
}
