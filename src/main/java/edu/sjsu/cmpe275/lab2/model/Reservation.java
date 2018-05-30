package edu.sjsu.cmpe275.lab2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="Reservation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reservationNumber")
public class Reservation {
	
	@Id
	@Column(name="RESERVATION_NUMBER")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long reservationNumber;

	@Column(name="PRICE")
    private int price;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "PASSENGER_RESERVATION", 
			joinColumns = @JoinColumn(name = "RESERVATION_NUMBER", referencedColumnName="RESERVATION_NUMBER"),
			inverseJoinColumns = @JoinColumn(name = "PASSENGER_ID", referencedColumnName = "PASSENGER_ID"))
    private Passenger passenger;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch= FetchType.EAGER)
	@JoinTable(
				name="RESERVATION_FLIGHT",
				joinColumns={@JoinColumn(name="RESERVATION_NO", referencedColumnName="RESERVATION_NUMBER")},
				inverseJoinColumns={@JoinColumn(name="FLIGHT_ID", referencedColumnName="FLIGHT_NUMBER")}
			
			)
	
	private List<Flight> flights = new ArrayList<Flight>();
    
    public Reservation(){}
    
    
	public Reservation(long reservationNumber, Passenger passenger, int price, List<Flight> flights) {
		super();
		this.reservationNumber = reservationNumber;
		this.passenger = passenger;
		this.price = price;
		this.flights = flights;
	}
	
	public long getreservationNumber() {
		return reservationNumber;
	}
	public void setreservationNumber(long reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public void setPassenger(Passenger passenger) {
		System.out.println(passenger);
		this.passenger = passenger;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
    
    

}
