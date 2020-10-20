package com.parkit.parkingsystem.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

@Tag("UnitTests")
@DisplayName("ParkingSpot Unit Tests")
class ParkingSpotTest {
	private static ParkingSpot parkingSpot;

	@BeforeEach
	void setUp() {
		// GIVEN
		parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
	}

	@Test
	void getParkingType() {
		// THEN
		assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
	}

	@Test
	void setAvailable() {
		// GIVEN
		parkingSpot.setAvailable(true);
		// THEN
		assertEquals(true, parkingSpot.isAvailable());
	}

	@Test
	void getParkingType_whenSetParkingType() {
		// GIVEN
		parkingSpot.setParkingType(ParkingType.BIKE);
		// THEN
		assertEquals(ParkingType.BIKE, parkingSpot.getParkingType());
	}

	@Test
	void getHashCode() {
		// THEN
		assertEquals(2, parkingSpot.hashCode());
	}

	@Test
	void getId_whenSetId() {
		// GIVEN
		parkingSpot.setId(3);
		// THEN
		assertEquals(3, parkingSpot.getId());
	}

	@Test
	void isEqual_whenEqual() {
		// THEN
		assertTrue(parkingSpot.equals(parkingSpot));
	}

	@Test
	void notEqual_whenNull() {
		// THEN
		assertFalse(parkingSpot.equals(null));
	}

	@Test
	void notEqual_whenNotSameClass() {
		// THEN
		assertFalse(parkingSpot.equals(new Object()));
	}

	@Test
	void notEqual_whenNotSameId() {
		// THEN
		assertFalse(parkingSpot.equals(new ParkingSpot(3, ParkingType.CAR, true)));
	}

	@Test
	void isEqual_whenSameId() {
		// THEN
		assertTrue(parkingSpot.equals(new ParkingSpot(2, ParkingType.CAR, true)));
	}

}
