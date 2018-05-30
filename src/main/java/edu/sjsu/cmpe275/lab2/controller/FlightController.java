package edu.sjsu.cmpe275.lab2.controller;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Plane;
import edu.sjsu.cmpe275.lab2.service.FlightService;

@RestController
public class FlightController {
	@Autowired
	FlightService flights;
	@PostMapping("/flight/{flight_number}")
	public String createor(@PathVariable(value = "flight_number") String flightNumber, @RequestParam(value="price") int price, @RequestParam(value="origin") String origin, @RequestParam(value="to") String to, @RequestParam(value="departureTime") String departureTime, @RequestParam(value="arrivalTime") String arrivalTime,@RequestParam(value="description") String description,@RequestParam(value="capacity") int capacity,@RequestParam(value="model") String model,@RequestParam(value="manufacturer") String manufacturer,@RequestParam(value="year") int yearOfManufacture) throws ParseException, JsonProcessingException, JSONException{	
	try{
				
    	    Plane plane = new Plane(capacity,model,manufacturer,yearOfManufacture);
		    Flight flight= new Flight(flightNumber,price,origin,to,departureTime,arrivalTime,description, plane);
		    return flights.insert(flight);
		    	
		} catch (RuntimeException e){ throw e;}
    }  
	
	
	
	@PutMapping("/flight/{flight_number}")
	public String UpdateFlight(@PathVariable(value = "flight_number") String flightNumber, @RequestParam(value="price") int price, @RequestParam(value="origin") String origin, @RequestParam(value="to") String to, @RequestParam(value="departureTime") String departureTime, @RequestParam(value="arrivalTime") String arrivalTime,@RequestParam(value="description") String description,@RequestParam(value="capacity") int capacity,@RequestParam(value="model") String model,@RequestParam(value="manufacturer") String manufacturer,@RequestParam(value="year") int yearOfManufacture) throws ParseException, JsonProcessingException, JSONException{	
	try{
    		  	
    			Plane plane = new Plane(capacity,model,manufacturer,yearOfManufacture);
		    	return flights.UpdatePUT(flightNumber,price,origin,to,departureTime,arrivalTime,description, plane);
		    	
		    	
    		} catch (RuntimeException e){ throw e;}
    }
	

	@RequestMapping(method = RequestMethod.GET, value="/flight/{id}", produces="application/json")
	public String getflightinJson(@PathVariable String id) throws JsonProcessingException, JSONException{
		return flights.getflights(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/flight/{id}", params = "xml", produces="application/xml")
	public String getflightinXml(@PathVariable String id, @RequestParam boolean xml) throws JsonProcessingException, JSONException{
		
		return flights.getFlight(id, xml);
	}
	
	@DeleteMapping("/airline/{id}")
	public String deleteflight(@PathVariable String id) throws JsonProcessingException, JSONException
	{
		return flights.deleteone(id);
		
	}
}
