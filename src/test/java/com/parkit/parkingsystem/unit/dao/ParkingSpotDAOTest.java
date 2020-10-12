package com.parkit.parkingsystem.unit.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

@Tag("UnitTests")
@DisplayName("ParkingSpotDAO Unit Tests")
class ParkingSpotDAOTest {

	private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;

	@BeforeEach
	private void setUpPerTest() {
		// GIVEN
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
	}

	@Test
	public void getNextAvailableSlot_whenParkingTypeIsNull() {
		// THEN
		assertEquals(-1, parkingSpotDAO.getNextAvailableSlot(null));
	}

	@Test
	public void updateParking_whenParkingTypeIsNull() {
		// THEN
		assertEquals(false, parkingSpotDAO.updateParking(null));
	}

}
