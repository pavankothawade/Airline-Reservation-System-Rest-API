package edu.sjsu.cmpe275.lab2.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.sjsu.cmpe275.lab2.model.Flight;

public class FlightDeserializer extends StdDeserializer<Flight>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected FlightDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Flight deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		JsonNode flightNode = p.getCodec().readTree(p);
		Flight flight = new Flight();
		flight.setFlightNumber(flightNode.get("number").textValue());
		return null;
	}

}
