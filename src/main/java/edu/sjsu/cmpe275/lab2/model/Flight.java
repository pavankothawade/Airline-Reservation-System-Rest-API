package edu.sjsu.cmpe275.lab2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.sjsu.cmpe275.lab2.serializers.FlightSerializer;



@Entity
@Table(name="Flight")
@JsonSerialize(using = FlightSerializer.class)
public class Flight {
	
	@Id
	@Column(name="FLIGHT_NUMBER")
	private String flightNumber;
	
	@Column(name="PRICE")
    private int price;
	
	@Column(name="ORIGIN")
    private String origin;
	
	@Column(name="DEST")
    private String to; 
	
	@Column(name="DEPARTURETIME")
    private String departureTime;     
	
	@Column(name="ARRIVALTIME")
    private String arrivalTime;
    
	@Column(name="SEATSLEFT")
    private int seatsLeft; 
    
	@Column(name="DESCRIPTION")
    private String description;
    
	@Embedded
    private Plane plane;  
    
	@ManyToMany()
	@JoinTable(name = "PASSENGERS_FLIGHT", 
			joinColumns={@JoinColumn(name="FLIGHT_NUMBER", referencedColumnName="FLIGHT_NUMBER")}, 
			   inverseJoinColumns = { @JoinColumn(name = "PASSENGER_ID", referencedColumnName="PASSENGER_ID") })
	
    private List<Passenger> passengers = new ArrayList<Passenger>();
	
    
   
	
	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Flight(){}
	
	public Flight(String flightNumber, int price, String origin, String to, String departureTime, String arrivalTime,
			String description, Plane plane, List<Passenger> passengers) {
		super();
		this.flightNumber = flightNumber;
		this.price = price;
		this.origin = origin;
		this.to = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsLeft = plane.getCapacity();
		this.description = description;
//		Plane plane = new Plane(capacity, model, manufacturer,yearOfManufacture);
		this.plane = plane;
		this.passengers = passengers;
	}
	
	public Flight(String flightNumber, int price, String from, String to, String departureTime, String arrivalTime,
			String description, Plane plane) {
		super();
		//int capacity, String model, String manufacturer, int yearOfManufacture
		this.flightNumber = flightNumber;
		this.price = price;
		this.origin = from;
		this.to = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsLeft = plane.getCapacity();
		this.description = description;
		this.plane = plane;
		
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFrom() {
		return origin;
	}
	public void setFrom(String from) {
		this.origin = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getSeatsLeft() {
		return seatsLeft;
	}
	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
    
    
    
    

}
