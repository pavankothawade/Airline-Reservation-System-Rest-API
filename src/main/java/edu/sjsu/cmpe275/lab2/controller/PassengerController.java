package edu.sjsu.cmpe275.lab2.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.PassengerDAO;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.service.PassengerService;

@RestController
public class PassengerController {
	
	
	
	
	@GetMapping("/passenger/{id}")
	@ResponseBody
	public String getPassJSON(@PathVariable long id)throws Exception{
		try{	
			return PassS.getPassinJSON(id);
			
		
		} catch (RuntimeException e){ throw e;}
	}
	
	
	@Autowired
	private PassengerService PassS;
	@Autowired
	private PassengerDAO passengerDAO;
	@RequestMapping(method = RequestMethod.GET, value="/passenger/{id}",params="xml", produces="application/xml")
	public String getPassXML(@PathVariable long id, @RequestParam boolean xml)throws Exception{
		try{
			return PassS.getPassinXML(id, xml);
		}
		catch (RuntimeException e){ e.printStackTrace();}
		return null;
		
	} 
	

	
	
	//PUT
	@PutMapping("/passenger/{id}")
	public String updatePass(@PathVariable long id, @RequestParam String firstname,@RequestParam String lastname, 
			   @RequestParam int age, @RequestParam String gender,
			   @RequestParam String phone ) throws JsonProcessingException, JSONException
	{
		return PassS.updateinJSON(id, firstname, lastname, age, gender, phone);}
	
	
	
	//DELETE
	@DeleteMapping("/passenger/{id}")
	public String deletePass(@PathVariable long id) throws JSONException, JsonProcessingException
	{
		return PassS.del(id);
		
	}

	@PostMapping("/passenger")
	public Passenger addPassJSON(@RequestParam String firstname,@RequestParam String lastname, 
												   @RequestParam int age, @RequestParam String gender,
												   @RequestParam String phone)
	{
		Passenger passen = new Passenger(firstname, lastname, age, gender, phone);
		return PassS.createinJSON(passen);
		
	}
	
	
}
