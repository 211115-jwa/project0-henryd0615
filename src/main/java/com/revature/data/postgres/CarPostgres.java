package com.revature.data.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Car;
import com.revature.data.CarDAO;
import com.revature.utils.ConnectionUtil;

public class CarPostgres implements CarDAO {
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public int create(Car dataToAdd) {
		int generatedId = 0;
		
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "insert into car (id,make,model,year,status) "
					+ "values (default, ?, ?, ?, ?)";
			String[] keys = {"id"}; 
			PreparedStatement pStmt = conn.prepareStatement(sql, keys);
			
			pStmt.setString(1, dataToAdd.getMake()); 
			pStmt.setString(2, dataToAdd.getModel());
			pStmt.setInt(3, dataToAdd.getYear());
			pStmt.setString(4, dataToAdd.getStatus());
			
			//running the statements
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
	public Car getById(int id) {
		Car car = null;
		
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from car where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			
			if (resultSet.next()) {
				car = new Car();
				car.setId(id);
				car.setMake(resultSet.getString("make"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("year"));
				car.setStatus(resultSet.getString("status"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return car;
	}

	@Override
	public Set<Car> getAll() {
		Set<Car> allCars = new HashSet<>();
		
		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from car";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			
			while (resultSet.next()) {
				// creating Object
				Car car = new Car();
		
				car.setId(resultSet.getInt("id"));
				car.setMake(resultSet.getString("make"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("year"));
				car.setStatus(resultSet.getString("status"));
				
				allCars.add(car);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allCars;
	}

	@Override
	public void update(Car dataToUpdate) {
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "update car set " + "make=?,model=?,year=?,status=? " + "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, dataToUpdate.getMake());
			pStmt.setString(2, dataToUpdate.getModel());
			pStmt.setInt(3, dataToUpdate.getYear());
			pStmt.setString(4, dataToUpdate.getStatus());
			pStmt.setInt(5, dataToUpdate.getId());
			
			int rowsAffected = pStmt.executeUpdate();
			
			if (rowsAffected==1) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Car dataToDelete) {
		try (Connection conn = connUtil.getConnection()) {
			conn.setAutoCommit(false);

			String sql = "delete from car " + "where id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, dataToDelete.getId());

			int rowsAffected = pStmt.executeUpdate();

			if (rowsAffected==1) {
				sql="delete from car_owner where car_id=?";
				PreparedStatement pStmt2 = conn.prepareStatement(sql);
				pStmt2.setInt(1, dataToDelete.getId());
				rowsAffected = pStmt2.executeUpdate();
				
				if (rowsAffected<=1) {
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
	public Set<Car> getByStatus(String status) {
		Set<Car> allCars = new HashSet<>();

		try (Connection conn = connUtil.getConnection()) {
			String sql = "select * from car where status=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, status);
	
			ResultSet resultSet = pStmt.executeQuery();

			while (resultSet.next()) {

				Car car = new Car();
				car.setId(resultSet.getInt("id"));
				car.setMake(resultSet.getString("make"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("year"));
				car.setStatus(resultSet.getString("status"));

				allCars.add(car);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allCars;
	}

}