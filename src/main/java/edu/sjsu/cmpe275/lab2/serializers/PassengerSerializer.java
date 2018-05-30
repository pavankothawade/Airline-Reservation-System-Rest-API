package edu.sjsu.cmpe275.lab2.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.sjsu.cmpe275.lab2.model.Flight;
import edu.sjsu.cmpe275.lab2.model.Passenger;
import edu.sjsu.cmpe275.lab2.model.Reservation;

public class PassengerSerializer extends StdSerializer<Passenger> {
/*
	public PassengerSerializer() {
		super(Passenger.class);
	}
	*/
	
	private static final long serialVersionUID = 1L;
	
	public PassengerSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
		// TODO Auto-generated constructor stub
	}

	public PassengerSerializer(JavaType type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public PassengerSerializer(StdSerializer<?> src) {
		super(src);
		// TODO Auto-generated constructor stub
	}

	protected PassengerSerializer(Class<Passenger> t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public PassengerSerializer() {
		super(Passenger.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serialize(Passenger value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		// TODO Auto-generated method stub
		
		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("firstname", value.getFirstname());
		gen.writeStringField("lastname", value.getLastname());
		gen.writeNumberField("age", value.getAge());
		gen.writeStringField("gender", value.getGender());
		gen.writeStringField("phone", value.getPhone());
		gen.writeObjectField("reservations", value.getReservations());
		for(Reservation r: value.getReservations()) {
			gen.writeObjectField("flights", r.getFlights());

			for(Flight f: r.getFlights()) {
				f.setPassengers(null);
				gen.writeObject(f);

			}
			gen.writeEndArray();

			gen.writeObject(r);
		}
		gen.writeEndArray();
		gen.writeEndObject();
	}

}

