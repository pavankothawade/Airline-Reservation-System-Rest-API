package edu.sjsu.cmpe275.lab2.service;

import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.PassengerDAO;
import edu.sjsu.cmpe275.lab2.dao.ReservationDAO;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.model.Reservation;
import javassist.NotFoundException;

@Service
public class PassengerService {
	
	@Autowired
	PassengerDAO passDao;
	
	@Autowired
	ReservationDAO reservationDAO;
	public String getPassinXML(long id, boolean xml) throws JsonProcessingException, JSONException, NotFoundException {
		try{
			if (xml){
		
			
    	
    		ObjectMapper objmapped = new ObjectMapper();
    		String jso = objmapped.writeValueAsString(passDao.findOne(id));
    		System.out.println(jso);
    		JSONObject innrerj = new JSONObject();
			JSONObject outerj = new JSONObject();
			innrerj.put("code", "404");
			innrerj.put("msg", "Sorry the requested passenger with id " +id+" does not exist");
			outerj.put("Bad", innrerj);
    		if (jso.equals("null")){

    			return XML.toString(outerj);
    			
    
    		}
    		else {
       		System.out.println("string" + jso.length());
       		JSONObject j = new JSONObject("{\"passenger\":"+jso+"}");
    	
    		return XML.toString(j);}
		}
		else {
			JSONObject innrerj = new JSONObject();
			JSONObject outerj = new JSONObject();
			innrerj.put("code", "404");
			innrerj.put("msg", "Sorry the requested passenger with id " +id+" does not exist");
			outerj.put("Bad", innrerj);
			return outerj.toString();
			
		}   	
		
		

		
	}
	
	catch (RuntimeException e){ throw e;}
}
	public String del(long id) throws JSONException, JsonProcessingException {
		ObjectMapper objmapped = new ObjectMapper();		
		String jso = objmapped.writeValueAsString(passDao.findOne(id));
		System.out.println(jso);
		JSONObject innrerj = new JSONObject();
		JSONObject outerj = new JSONObject();
		innrerj.put("code", "404");
		innrerj.put("msg", "Sorry the requested passenger with id " +id+" does not exist");
		outerj.put("Bad", innrerj);
		if (jso.equals("null")){

			return outerj.toString();
			

		}
		passDao.delete(id);
		
		JSONObject innrerj1 = new JSONObject();
		JSONObject outerj1 = new JSONObject();
		innrerj1.put("code", "200");
		innrerj1.put("msg", "Passenger with id " +id+" is deleted successfully");
		outerj1.put("Response", innrerj1);
		return XML.toString(outerj1);
	}

	
	
	
	public Passenger createinJSON(Passenger passenger) {
		
		try {
		passDao.save(passenger);
		return passenger;
	}
catch(RuntimeException e) {
	
	throw e;
}
	}
	
	public String getPassinJSON(long id) throws JSONException, JsonProcessingException {
		try{	System.out.println("Im inside");
    		
    		ObjectMapper objmapped = new ObjectMapper();
    		String jso = objmapped.writeValueAsString(passDao.findOne(id));
    		JSONObject innrerj = new JSONObject();
			JSONObject outerj = new JSONObject();
			innrerj.put("code", "404");
			innrerj.put("msg", "Sorry the requested passenger with id " +id+" does not exist");
			outerj.put("Bad Request", innrerj);
			if (jso.equals("null")){

    			return outerj.toString();
    			
    
    		}
			else {
       		List<Reservation> passre = reservationDAO.findByPassenger(passDao.findOne(id)); 
    		JSONObject j = new JSONObject("{\"passenger\":"+jso+"}");
    		
        	return j.toString();
    		}
   	} catch (RuntimeException e){ throw e;}

}
	
	
	
	public String updateinJSON(long id, String firstname, String lastname, int age, String gender, String phone) throws JsonProcessingException, JSONException {
		Passenger passedup = passDao.findOne(id);
		System.out.println("------------"+passedup);
		ObjectMapper objmapped = new ObjectMapper();
		JSONObject innrerj = new JSONObject();
		JSONObject outerj = new JSONObject();
		innrerj.put("code", "404");
		innrerj.put("msg", "Sorry the requested passenger with id " +id+" does not exist");
		outerj.put("Bad", innrerj);
		String jso1 = objmapped.writeValueAsString(passedup);
		System.out.println("{{{{{{{{{"+jso1);
		System.out.println(jso1.length());

		if (jso1.equals("null")){
			System.out.println("ds");
		return outerj.toString();

	}
		
		passedup.setFirstname(firstname);
		passedup.setLastname(lastname);
		passedup.setAge(age);
		passedup.setGender(gender);
		passedup.setPhone(phone);
		passDao.save(passedup);
		String jso = objmapped.writeValueAsString(passedup);
		
		
		
		
		
		JSONObject j = new JSONObject("{\"passenger\":"+jso+"}");
		return j.toString();
		
	}

	

	
}
