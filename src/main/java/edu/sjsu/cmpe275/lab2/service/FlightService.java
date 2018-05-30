package edu.sjsu.cmpe275.lab2.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.FlightDAO;
//import edu.sjsu.cmpe275.lab2.dao.FlightDAO_GHOST;
import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Plane;

@Service
public class FlightService {
	@Autowired
	private FlightDAO flightDAO;
	
	public String UpdatePUT(String flightNumber,int price,String origin,String to,String departureTime,String arrivalTime,String description,Plane plane) throws JsonProcessingException, JSONException {
		Flight flyig = flightDAO.findOne(flightNumber);
		ObjectMapper mapperObj10 = new ObjectMapper();
		String str = mapperObj10.writeValueAsString(flyig);
		
		JSONObject innrerj3 = new JSONObject();
		JSONObject outerj3 = new JSONObject();
		innrerj3.put("code", "404");
		innrerj3.put("msg", "Sorry the requested Flight with number " +flightNumber+" does not exist");
		outerj3.put("Bad Request", innrerj3);
		if (str.equals("null")){

			return outerj3.toString();
			

		}
		
		
		
		flyig.setArrivalTime(arrivalTime);
		flyig.setDepartureTime(departureTime);
		flyig.setDescription(description);
		flyig.setFrom(origin);
		flyig.setTo(to);
		flyig.setFlightNumber(flightNumber);
		flyig.setPlane(plane);
		flyig.setPrice(price);
	
		flightDAO.save(flyig);
		ObjectMapper mapperObj = new ObjectMapper();
		String sri = mapperObj.writeValueAsString(flyig);
		
		int part1End =sri.indexOf("passengers");
		String str1 = sri.substring(0, part1End+("passengers").length()+2);
		
		int part2Start = sri.indexOf("[",part1End);
		String str2 = sri.substring(part2Start)+"}";
		String addition = "{\"passenger\":";
		String newStr = str1+addition+ str2;
		JSONObject j = new JSONObject("{\"flight\":"+newStr+"}");
		return j.toString();
	}
	
	
	
	
	
	
	
	public String insert(Flight flight) throws JsonProcessingException, JSONException {
		flightDAO.save(flight);
		
		ObjectMapper mapperObj = new ObjectMapper();
		String str = mapperObj.writeValueAsString(flight);
		
		int pend1 =str.indexOf("passengers");
		String str1 = str.substring(0, pend1+("passengers").length()+2);
		int pend2 = str.indexOf("[",pend1);
		String str2 = str.substring(pend2)+"}";
		String addition = "{\"passenger\":";
		String news = str1+addition+ str2;
		
		JSONObject j3 = new JSONObject("{\"flight\":"+news+"}");
		return j3.toString();
	}
	
	public String deleteone(String id) throws JSONException, JsonProcessingException {
		Flight flyigg = flightDAO.findOne(id);
		ObjectMapper mapperObj10 = new ObjectMapper();
		String str = mapperObj10.writeValueAsString(flyigg);
		
		JSONObject innrerj3 = new JSONObject();
		JSONObject outerj3 = new JSONObject();
		innrerj3.put("code", "404");
		innrerj3.put("msg", "Sorry the requested Flight with number " +id+" does not exist");
		outerj3.put("Bad Request", innrerj3);
		if (str.equals("null")){

			return outerj3.toString();
			

		}
		
		
		flightDAO.delete(id);
		
		
		return "delete successful";
	}

	public String getflights(String id) throws JsonProcessingException, JSONException {
		
			
		
		ObjectMapper mapperObj = new ObjectMapper();
		String str = mapperObj.writeValueAsString(flightDAO.findOne(id));
		
		JSONObject innrerj3 = new JSONObject();
		JSONObject outerj3 = new JSONObject();
		innrerj3.put("code", "404");
		innrerj3.put("msg", "Sorry the requested Flight with number " +id+" does not exist");
		outerj3.put("Bad Request", innrerj3);
		if (str.equals("null")){

			return outerj3.toString();
			

		}
		
		int pend1 =str.indexOf("passengers");
		String str1 = str.substring(0, pend1+("passengers").length()+2);
		
		int pend2 = str.indexOf("[",pend1);
		String sri1 = str.substring(pend2)+"}";
		String addition = "{\"passenger\":";
		String newStr = str1+addition+ sri1;
		
		JSONObject j = new JSONObject("{\"flight\":"+newStr+"}");
			return j.toString();
		
		
	}
	
	public String getFlight(String id,boolean xml) throws JsonProcessingException, JSONException {
		
		
		if (xml){
		ObjectMapper mapperObj = new ObjectMapper();
		String str = mapperObj.writeValueAsString(flightDAO.findOne(id));
		
		JSONObject innrerj3 = new JSONObject();
		JSONObject outerj3 = new JSONObject();
		innrerj3.put("code", "404");
		innrerj3.put("msg", "Sorry the requested Flight with number " +id+" does not exist");
		outerj3.put("Bad Request", innrerj3);
		if (str.equals("null")){

			return outerj3.toString();
			

		}
		
		
		int part1End =str.indexOf("passengers");
		/*if(str==null) {
			String error = "{\"Bad Request\":";

			
		}*/
		String str1 = str.substring(0, part1End+("passengers").length()+2);
		
		int part2Start = str.indexOf("[",part1End);
		String str2 = str.substring(part2Start)+"}";
		String addition = "{\"passenger\":";
		String newStr = str1+addition+ str2;
		
		JSONObject j = new JSONObject("{\"flight\":"+newStr+"}");
			
		
			return XML.toString(j);
		}
		return null;
		
	}
	

}
