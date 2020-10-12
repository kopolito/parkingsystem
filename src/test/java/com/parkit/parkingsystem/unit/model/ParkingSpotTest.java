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
		parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
	}

	@Test
	void getParkingType() {
		assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
	}

	@Test
	void setAvailable() {
		parkingSpot.setAvailable(true);
		assertEquals(true, parkingSpot.isAvailable());
	}

	@Test
	void getParkingType_whenSetParkingType() {
		parkingSpot.setParkingType(ParkingType.BIKE);
		assertEquals(ParkingType.BIKE, parkingSpot.getParkingType());
	}

	@Test
	void getHashCode() {
		assertEquals(2, parkingSpot.hashCode());
	}

	@Test
	void getId_whenSetId() {
		parkingSpot.setId(3);
		assertEquals(3, parkingSpot.getId());
	}

	@Test
	void isEqual_whenEqual() {
		assertTrue(parkingSpot.equals(parkingSpot));
	}

	@Test
	void notEqual_whenNull() {
		assertFalse(parkingSpot.equals(null));
	}

	@Test
	void notEqual_whenNotSameClass() {
		assertFalse(parkingSpot.equals(new Object()));
	}

	@Test
	void notEqual_whenNotSameId() {
		assertFalse(parkingSpot.equals(new ParkingSpot(3, ParkingType.CAR, true)));
	}

	@Test
	void isEqual_whenSameId() {
		assertTrue(parkingSpot.equals(new ParkingSpot(2, ParkingType.CAR, true)));
	}

}
