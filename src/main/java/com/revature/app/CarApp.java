package com.revature.app;

import io.javalin.Javalin;
import io.javalin.http.HttpCode;

// this static import is for the path and get/post/put methods
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.Set;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.beans.Car;
import com.revature.beans.Person;

public class CarApp {
	private static UserService userServ = new UserServiceImpl();
	private static EmployeeService empServ = new EmployeeServiceImpl();

	public static void main(String[] args) {
		Javalin app = Javalin.create();
		
		app.start();
		
		
		app.routes(() -> {
			path("/cars", () -> {
				get(ctx -> {
					String modelSearch = ctx.queryParam("model");
		
					if (modelSearch != null && !"".equals(modelSearch)) {
						Set<Car> carsFound = userServ.searchAvailableCarsByModel(modelSearch);
						ctx.json(carsFound);

						
					} else {
						Set<Car> availableCars = userServ.viewAvailableCars();
						ctx.json(availableCars);
					}
				});
				post(ctx -> {
					Car newCar = ctx.bodyAsClass(Car.class);
					if (newCar !=null) {
						empServ.addNewCar(newCar);
						ctx.status(HttpStatus.CREATED_201);
					} else {
						ctx.status(HttpStatus.BAD_REQUEST_400);
					}
				});
				
				path("/purchase/{id}", () -> {
					put(ctx -> {
						try {
							int carId = Integer.parseInt(ctx.pathParam("id")); // 
							Person newOwner = ctx.bodyAsClass(Person.class);
							newOwner = userServ.purchaseCar(carId, newOwner);
							ctx.json(newOwner);
						} catch (NumberFormatException e) {
							ctx.status(400);
							ctx.result("Car ID must be a numeric value");
						}
					});
				});
				
				path("/{id}", () -> {
					
					get(ctx -> {
						try {
							int carId = Integer.parseInt(ctx.pathParam("id"));
							Car car = empServ.getCarById(carId);
							if (car != null)
								ctx.json(car);
							else
								ctx.status(404);
						} catch (NumberFormatException e) {
							ctx.status(400);
							ctx.result("Car ID must be a numeric value");
						}
					});
					
					put(ctx -> {
						try {
							int carId = Integer.parseInt(ctx.pathParam("id")); 
							Car carToEdit = ctx.bodyAsClass(Car.class);
							if (carToEdit != null && carToEdit.getId() == carId) {
								carToEdit = empServ.editCar(carToEdit);
								if (carToEdit != null)
									ctx.json(carToEdit);
								else
									ctx.status(404);
							} else {
								ctx.status(HttpCode.CONFLICT);
							}
						} catch (NumberFormatException e) {
							ctx.status(400);
							ctx.result("Car ID must be a numeric value");
						}
					});
					
				});
			});
		});
	}
	
}
