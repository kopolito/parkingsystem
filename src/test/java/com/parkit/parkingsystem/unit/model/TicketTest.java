package com.parkit.parkingsystem.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

@Tag("UnitTests")
@DisplayName("Ticket Unit Tests")
class TicketTest {

	private Ticket ticket;

	@BeforeEach
	void setUp() {
		// GIVEN
		ticket = new Ticket();
	}

	@Test
	void setId() {
		// GIVEN
		ticket.setId(2);
		// THEN
		assertEquals(2, ticket.getId());
	}

	@Test
	void setParkingSpot() {
		// GIVEN
		ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		assertEquals(parkingSpot, ticket.getParkingSpot());
	}

	@Test
	void setVehicleRegNumber() {
		// GIVEN
		ticket.setVehicleRegNumber("ABCDEF");
		// THEN
		assertEquals("ABCDEF", ticket.getVehicleRegNumber());
	}

	@Test
	void setPrice() {
		// GIVEN
		ticket.setPrice(2.5);
		// THEN
		assertEquals(2.5, ticket.getPrice());
	}

	@Test
	void setInTime() {
		// GIVEN
		Date date = new Date();
		ticket.setInTime(date);
		// THEN
		assertEquals(date, ticket.getInTime());
	}

	@Test
	void setOutTime() {
		// GIVEN
		Date date = new Date();
		ticket.setOutTime(date);
		// THEN
		assertEquals(date, ticket.getOutTime());
	}

}
