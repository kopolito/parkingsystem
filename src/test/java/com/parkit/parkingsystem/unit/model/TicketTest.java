package com.parkit.parkingsystem.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

class TicketTest {

	private static Ticket ticket;

	@BeforeEach
	void setUp() {
		ticket = new Ticket();
	}

	@Test
	void setId() {
		ticket.setId(2);
		assertEquals(2, ticket.getId());
	}

	@Test
	void setParkingSpot() {
		ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
		ticket.setParkingSpot(parkingSpot);
		assertEquals(parkingSpot, ticket.getParkingSpot());
	}

	@Test
	void setVehicleRegNumber() {
		ticket.setVehicleRegNumber("ABCDEF");
		assertEquals("ABCDEF", ticket.getVehicleRegNumber());
	}

	@Test
	void setPrice() {
		ticket.setPrice(2.5);
		assertEquals(2.5, ticket.getPrice());
	}

	@Test
	void setInTime() {
		Date date = new Date();
		ticket.setInTime(date);
		assertEquals(date, ticket.getInTime());
	}

	@Test
	void setOutTime() {
		Date date = new Date();
		ticket.setOutTime(date);
		assertEquals(date, ticket.getOutTime());
	}

}
