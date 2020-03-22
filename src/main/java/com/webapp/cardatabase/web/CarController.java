package com.webapp.cardatabase.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.webapp.cardatabase.domain.Car;
import com.webapp.cardatabase.domain.CarRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", 
				method = { RequestMethod.GET, 
						   RequestMethod.POST, 
						   RequestMethod.PUT, 
						   RequestMethod.DELETE} )

public class CarController {

	private Logger log = LoggerFactory.getLogger(CarController.class);

	@Autowired
	private CarRepository carRepo;

	@RequestMapping(value = "/cars", method = RequestMethod.GET)
	public List<Car> getCars() {
		log.info("getCars() - START");

		List<Car> carsFetchedDb = new ArrayList<Car>();

		Iterable<Car> iterableCar = carRepo.findAll();
		Iterator<Car> iterarorCar = iterableCar.iterator();
		StringBuilder items = new StringBuilder("");
		if (iterarorCar.hasNext()) {
			Car item = null;
			items.append("\t\t   items: [\t\n");
			while (iterarorCar.hasNext()) {
				item = iterarorCar.next();
				carsFetchedDb.add(item);
				items.append("\t\t\t\t" + item + ",\n");
			}
			items.append("\t\t\t   ]");
		}
		else 
			items.append("empty");
		

		log.info("getcars() - RETURNED: \n\n\t\t{\n  " + items + "\n\t\t}\n");
		log.info("getCars() - END");
		return carsFetchedDb;
	}

	@RequestMapping(value = "/car/{id}", method = RequestMethod.GET)
	public Car getCar(@PathVariable Long id) {
		log.info("getCar(@PathVariable Long id) - START");
		log.info("getCar(@PathVariable Long id) - DEBUG: \n\n\tparam: {\n\t\t  id: " + id + "\n\t\t}\n");

		Car car = carRepo.findById(id).get();

		log.info("getcars() - RETURNED: \n\n\titem: {\n\t\t  " + car + "\n\t\t}\n");
		log.info("getCar() - END");
		return car;

	}
}
