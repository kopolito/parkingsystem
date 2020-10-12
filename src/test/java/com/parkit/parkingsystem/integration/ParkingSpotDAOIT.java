package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

@Tag("IntegrationTests")
@DisplayName("ParkingSpotDAO Integration Tests")
class ParkingSpotDAOIT {

	private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;

	@BeforeEach
	private void setUpPerTest() {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
	}

	@Test
	public void getNextAvailableSlot_whenParkingTypeIsNull() {

		assertEquals(-1, parkingSpotDAO.getNextAvailableSlot(null));
	}

	@Test
	public void updateParking_whenParkingTypeIsNull() {

		assertEquals(false, parkingSpotDAO.updateParking(null));
	}

}
