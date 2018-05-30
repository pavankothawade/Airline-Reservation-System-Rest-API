package edu.sjsu.cmpe275.lab2.controller;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.service.ReservationService;

@RestController
public class ReservationController {
	
	@Autowired
	ReservationService resS;
	
	@PostMapping("/reservation")
	public String makeR(@RequestParam long passengerId, @RequestParam(value="flightLists") String flightList) throws ParseException, JsonProcessingException, JSONException{
		String [] flLis = flightList.split(","); 
		return resS.doReser(passengerId, flLis);
	}
	@RequestMapping(method = RequestMethod.GET, value="/reservation/{number}", produces="application/json")
	public String getflightinJson(@PathVariable long number) throws JsonProcessingException, JSONException, ParseException{
		
		return resS.getresinJson(number);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/reservation", produces="application/json")
	public String getSpecificFlightJSON(@RequestParam(value="passengerId") long passengerId, @RequestParam(value="origin") String origin, @RequestParam(value="to") String to, @RequestParam(value="flightNumber") String flightNumber) throws JsonProcessingException, JSONException, ParseException{
		System.out.println(passengerId);
		System.out.println(origin);
		System.out.println(to);
		System.out.println(flightNumber);
		return null;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/reservation/{number}", produces="application/json")
	public String updatefJSON(@PathVariable long number, 
			                        @RequestParam (value="flightsAdded", defaultValue="null") String flightsAdd, 
			                        @RequestParam (value="flightsRemoved", defaultValue="null") String flightsRem) throws JsonProcessingException, JSONException, ParseException{
		
		String[] fl_a = flightsAdd.split(",");
		String[] fl_r = flightsRem.split(",");
		return resS.updateF(number,fl_a,fl_r);
	}
	
	@DeleteMapping("/reservation/{number}")
	public String delF(@PathVariable long number) throws JSONException, JsonProcessingException{
		return resS.delR(number);
	}

}
