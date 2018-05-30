package edu.sjsu.cmpe275.lab2.serializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Passenger;

public class FlightSerializer extends StdSerializer<Flight>{

	/**
	 * 
	 */
	
	public FlightSerializer() {
		super(Flight.class);
	}
	
	
	private static final long serialVersionUID = 1L;
	
	public FlightSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
		// TODO Auto-generated constructor stub
	}

	public FlightSerializer(JavaType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public FlightSerializer(StdSerializer<?> src) {
		super(src);
		// TODO Auto-generated constructor stub
	}

	protected FlightSerializer(Class<Flight> t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serialize(Flight value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		gen.writeStartObject();
		gen.writeStringField("number", value.getFlightNumber());
		gen.writeNumberField("price", value.getPrice());
		gen.writeStringField("origin", value.getFrom());
		gen.writeStringField("to", value.getTo());
		gen.writeStringField("departureTimes", value.getDepartureTime());
		gen.writeStringField("arrivalTimes", value.getArrivalTime());
		gen.writeStringField("description", value.getDescription());
		gen.writeNumberField("seatsLeft", value.getSeatsLeft());
		gen.writeObjectField("plane", value.getPlane());
		gen.writeArrayFieldStart("passengers");
		for(Passenger p: value.getPassengers()) {
			p.setReservations(null);
			gen.writeObject(p);
		}
		gen.writeEndArray();
		gen.writeEndObject();
	}

}
