package edu.sjsu.cmpe275.lab2.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.FlightDAO;
//import edu.sjsu.cmpe275.lab2.dao.FlightDAO_GHOST;
import edu.sjsu.cmpe275.lab2.dao.PassengerDAO;
import edu.sjsu.cmpe275.lab2.dao.ReservationDAO;
import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.model.Plane;
import edu.sjsu.cmpe275.lab2.model.Reservation;

@Service
public class ReservationService {

	@Autowired
	private PassengerDAO passengerDAO;
	
	@Autowired 
	private ReservationDAO reservationDAO;
	
	@Autowired
	private FlightDAO flightDAO;
	
	class Break{
		public Date stdate;
		public Date eddate;
		
	}

	private boolean checkOverlapping(String [] flightList) throws ParseException{
		boolean checkoverlp = false;
		Break[] sch = new Break[flightList.length];
		for(int i=0;i<flightList.length;i++){
			Flight flight = new Flight();
			flight = flightDAO.findOne(flightList[i]);
			
			
			
			
			
			
			
			
			
			
			Date stdateTime = (Date) new SimpleDateFormat("yyyy-MM-dd-HH").parse(flight.getDepartureTime());
			Date eddateTime = (Date) new SimpleDateFormat("yyyy-MM-dd-HH").parse(flight.getArrivalTime());
			Break Break = new Break();
			Break.stdate = stdateTime;
			Break.eddate = eddateTime;
			sch[i]=Break;
			
		}
		Date[] dateArr = new Date[2];
		
		Arrays.sort(sch, new Comparator<Break>(){
			public int compare(Break a, Break b){
				return a.eddate.compareTo(b.stdate);
				
			}
			
		});
		
		
		for (int i=1;i<sch.length;i++){
			if (sch[i].stdate.before(sch[i-1].eddate)){
				return true;
			}
		}
		
		return checkoverlp;
	}
	
	
public String delR(long number) throws JSONException, JsonProcessingException {
		
		Reservation reserv = new Reservation();
		reserv = reservationDAO.findOne(number);
		
		
		
		ObjectMapper objmapped3 = new ObjectMapper();		
		String jso = objmapped3.writeValueAsString(reserv);
		JSONObject innrerj3 = new JSONObject();
		JSONObject outerj3 = new JSONObject();
		innrerj3.put("code", "404");
		innrerj3.put("msg", "Reservation number " +number+" does not exist");
		outerj3.put("Bad", innrerj3);
		if (jso.equals("null")){

			return outerj3.toString();
			

		}
		
		
		
		
		
		
		List<Flight> flights = reserv.getFlights();
		for(int i=0;i<flights.size();i++){
			flights.get(i).setSeatsLeft(flights.get(i).getSeatsLeft()+1);
			List<Passenger> allPassengers = flights.get(i).getPassengers();
					for(Iterator<Passenger> iter = allPassengers.listIterator();iter.hasNext();){
				Passenger passenger = iter.next();
				if(passenger.equals(reserv.getPassenger())){
					iter.remove();
				}
			}
		}
		
		reservationDAO.delete(number);
		JSONObject innj = new JSONObject();
		JSONObject outj = new JSONObject();
		innj.put("code", "200");
		innj.put("msg", "Reservation with number " +number+" is cancelled successfully");
		outj.put("Response", innj);
		System.out.println(outj.toString());
		return XML.toString(outj);

		
		
	}



public String doReser(long passengerId, String [] flightList) throws ParseException, JsonProcessingException, JSONException {
	Reservation reserv = new Reservation();
	List<JSONObject> flJList = new ArrayList<JSONObject>();
	List<Flight> flightLists = new ArrayList<Flight>();
	int tPric = 0;
	Passenger passenger = passengerDAO.findOne(passengerId);
	
	ObjectMapper objmapped = new ObjectMapper();		
	String jso = objmapped.writeValueAsString(passenger);
	JSONObject innrerj = new JSONObject();
	JSONObject outerj = new JSONObject();
	innrerj.put("code", "404");
	innrerj.put("msg", "Sorry the requested passenger with id " +passengerId+" does not exist");
	outerj.put("Bad", innrerj);
	if (jso.equals("null")){

		return outerj.toString();
		

	}
	
	
	boolean checkoverlp= checkOverlapping(flightList);
	if (!checkoverlp){
		ObjectMapper objmapped1 = new ObjectMapper();		

		
		for(int i=0;i<flightList.length;i++)
		{
			
			Flight flight = new Flight();
			flight = flightDAO.findOne(flightList[i]);
			System.out.println(objmapped1.writeValueAsString(flightDAO.findOne(flightList[i])));
			
			if(objmapped1.writeValueAsString(flightDAO.findOne(flightList[i])).equals("null")) {
				JSONObject innrerj1 = new JSONObject();
				JSONObject outerj1 = new JSONObject();
				innrerj1.put("code", "404");
				innrerj1.put("msg", "Sorry the requested flight with id " +flightList[i]+" does not exist");
				outerj1.put("Bad", innrerj);
				return outerj1.toString();
			}
			
			
			
			
			
			
			if(flight.getSeatsLeft() > 0)
			{
			tPric += flight.getPrice();
			flight.setSeatsLeft(flight.getSeatsLeft()-1);
			JSONObject flyJ = new JSONObject();
			flyJ.put("number", flight.getFlightNumber());
			flyJ.put("price", flight.getPrice());
			flyJ.put("from", flight.getFrom());
			flyJ.put("to", flight.getTo());
			flyJ.put("departureTime", flight.getDepartureTime());
			flyJ.put("arrivalTime", flight.getArrivalTime());
			flyJ.put("seatsLeft", flight.getSeatsLeft());
			flyJ.put("description", flight.getDescription());
			Plane JP = new Plane();
			JP = flight.getPlane();
			ObjectMapper mapperObj = new ObjectMapper();
    		String jp = mapperObj.writeValueAsString(JP);
    		JSONObject jsonObject = new JSONObject(jp);	
			flyJ.put("plane", jsonObject);
			flJList.add(flyJ);
			flightLists.add(flight);
			flight.getPassengers().add(passenger);
			
			flightDAO.save(flight);
			
			}
		}
		
		reserv.setPrice(tPric);
		reserv.setFlights(flightLists);
		reserv.setPassenger(passenger);
		reservationDAO.save(reserv);
		JSONObject Jn = new JSONObject();
		Jn.put("orderNumber", reserv.getreservationNumber());
		Jn.put("price", reserv.getPrice());
		Passenger jPassenger =new Passenger();
		jPassenger = reserv.getPassenger();
		ObjectMapper mapperObj = new ObjectMapper();
		String jp = mapperObj.writeValueAsString(jPassenger);
		
		JSONObject jsonObject = new JSONObject(jp);
		
		Jn.put("passenger", jsonObject);
		JSONObject flightJ = new JSONObject();
		flightJ.put("flight", flJList);
		Jn.put("flights", flightJ);
		JSONObject returnVal = new JSONObject("{\"reservation\":"+Jn+"}");
		return XML.toString(returnVal);
		
	}
	else{
		System.out.println("<<<<<OVERLAPPING>>>>>>>>>>");
	}
	return null;
}
	
	
	public String updateF(long number, String[] flightsAdd, String[] flightsRem) throws JSONException, JsonProcessingException, ParseException {
	
		Reservation reserv = new Reservation();
		reserv = reservationDAO.findOne(number);
		
		ObjectMapper objmapped2 = new ObjectMapper();		
		String jso = objmapped2.writeValueAsString(reserv);
		JSONObject innrerj3 = new JSONObject();
		JSONObject outerj3 = new JSONObject();
		innrerj3.put("code", "404");
		innrerj3.put("msg", "Reservation number " +number+" does not exist");
		outerj3.put("Bad", innrerj3);
		if (jso.equals("null")){

			return outerj3.toString();
			

		}
		
		
		
		
		
		
		
		
		HashSet<Flight> flightsToRemove = new HashSet<Flight>();
		for(int i=0;i<flightsRem.length ;i++){
			flightsToRemove.add(flightDAO.findOne(flightsRem[i]));
			
		}
		for(int i=0;i<flightsAdd.length ;i++){
			reserv.getFlights().add(flightDAO.findOne(flightsAdd[i]));
			
		}
		
		for(Iterator<Flight> iter = reserv.getFlights().listIterator();iter.hasNext();){
			Flight flight = iter.next();
			if(flightsToRemove.contains(flight)){
				iter.remove();
			}
			
		}
		
		
		Passenger passenger = reserv.getPassenger();
		List<Flight> flightL= reserv.getFlights();
		String[] flightList = new String[flightL.size()];
		for (int i=0;i<flightL.size();i++){
			flightList[i] = flightL.get(i).getFlightNumber();
		}
		
		
		
		List<JSONObject> flJList = new ArrayList<JSONObject>();
		List<Flight> flightLists = new ArrayList<Flight>();
		int tPric = 0;
		
		boolean checkoverlp= checkOverlapping(flightList);
		if (!checkoverlp){
			for(int i=0;i<flightList.length;i++)
			{
				
				Flight flight = new Flight();
				flight = flightDAO.findOne(flightList[i]);
				
				if(flight.getSeatsLeft() > 0)
				{
				tPric += flight.getPrice();
				
				JSONObject flyJ = new JSONObject();
				flyJ.put("number", flight.getFlightNumber());
				flyJ.put("price", flight.getPrice());
				flyJ.put("from", flight.getFrom());
				flyJ.put("to", flight.getTo());
				flyJ.put("departureTime", flight.getDepartureTime());
				flyJ.put("arrivalTime", flight.getArrivalTime());
				flyJ.put("seatsLeft", flight.getSeatsLeft());
				flyJ.put("description", flight.getDescription());
				
				Plane JP = new Plane();
				JP = flight.getPlane();
				ObjectMapper mapperObj = new ObjectMapper();
	    		String jp = mapperObj.writeValueAsString(JP);
	    		
	    		JSONObject jsonObject = new JSONObject(jp);
				
				flyJ.put("plane", jsonObject);
				flJList.add(flyJ);
				flightLists.add(flight);
			
				
				}
			}
			
			reserv.setPrice(tPric);
			reserv.setFlights(flightLists);
			reserv.setPassenger(passenger);
			JSONObject Jn = new JSONObject();
			Jn.put("orderNumber", reserv.getreservationNumber());
			Jn.put("price", reserv.getPrice());
			Passenger jPassenger =new Passenger();
			jPassenger = reserv.getPassenger();
			ObjectMapper mapperObj = new ObjectMapper();
    		String jp = mapperObj.writeValueAsString(jPassenger);
    		
    		JSONObject jsonObject = new JSONObject(jp);
			
			Jn.put("passenger", jsonObject);
			JSONObject flightJ = new JSONObject();
			flightJ.put("flight", flJList);
			Jn.put("flights", flightJ);
			
			JSONObject retv = new JSONObject("{\"reservation\":"+Jn.toString()+"}");
			
			
    					return retv.toString();
		
		
		
		
	}
		return null;
}
	
	
	public String getresinJson(long orderNumber) throws JsonProcessingException, JSONException, ParseException {
		Reservation reserv = reservationDAO.findOne(orderNumber);
		
		ObjectMapper objmapped = new ObjectMapper();		
		String jso = objmapped.writeValueAsString(reserv);
		System.out.println(jso);
		JSONObject innrerj = new JSONObject();
		JSONObject outerj = new JSONObject();
		innrerj.put("code", "404");
		innrerj.put("msg", "Reservation with number " +orderNumber+" does not exist");
		outerj.put("Bad Request", innrerj);
		if (jso.equals("null")){

			return outerj.toString();

		}
		
		
		
		Passenger passenger = reserv.getPassenger();
		
		
		List<Flight> flightL= reserv.getFlights();
		
		String[] flightList = new String[flightL.size()];
		for (int i=0;i<flightL.size();i++){
			flightList[i] = flightL.get(i).getFlightNumber();
		}
		
		List<JSONObject> flJList = new ArrayList<JSONObject>();
		List<Flight> flightLists = new ArrayList<Flight>();
		int tPric = 0;
		boolean checkoverlp= checkOverlapping(flightList);
		if (!checkoverlp){
			for(int i=0;i<flightList.length;i++)
			{
				
				Flight flight = new Flight();
				flight = flightDAO.findOne(flightList[i]);
				
				if(flight.getSeatsLeft() > 0)
				{
				tPric += flight.getPrice();
				
				JSONObject flyJ = new JSONObject();
				flyJ.put("number", flight.getFlightNumber());
				flyJ.put("price", flight.getPrice());
				flyJ.put("from", flight.getFrom());
				flyJ.put("to", flight.getTo());
				flyJ.put("departureTime", flight.getDepartureTime());
				flyJ.put("arrivalTime", flight.getArrivalTime());
				flyJ.put("seatsLeft", flight.getSeatsLeft());
				flyJ.put("description", flight.getDescription());
				
				Plane JP = new Plane();
				JP = flight.getPlane();
				ObjectMapper mapperObj = new ObjectMapper();
	    		String jp = mapperObj.writeValueAsString(JP);
	    		
	    		JSONObject jsonObject = new JSONObject(jp);
				
				flyJ.put("plane", jsonObject);
				flJList.add(flyJ);
				flightLists.add(flight);
				
				
				}
			}
			
			reserv.setPrice(tPric);
			reserv.setFlights(flightLists);
			JSONObject Jn = new JSONObject();
			Jn.put("orderNumber", reserv.getreservationNumber());
			Jn.put("price", reserv.getPrice());
			Passenger jPassenger =new Passenger();
			jPassenger = reserv.getPassenger();
			ObjectMapper mapperObj = new ObjectMapper();
    		String jp = mapperObj.writeValueAsString(jPassenger);
    		
    		JSONObject jsonObject = new JSONObject(jp);
			
			Jn.put("passenger", jsonObject);
			JSONObject flightJ = new JSONObject();
			flightJ.put("flight", flJList);
			Jn.put("flights", flightJ);
			
			JSONObject returnVal = new JSONObject("{\"reservation\":"+Jn.toString()+"}");
			System.out.println(returnVal);
			return returnVal.toString();
		}
		return null;
	
	}
}
