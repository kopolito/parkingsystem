package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
@Tag("IntegrationTests")
@DisplayName("Parking Service Integration Tests")
public class ParkingServiceIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
	private static ParkingService parkingService;
	private static final String vehicleRegistrationNumber = "ABCDEF";

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		try {
			lenient().when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(vehicleRegistrationNumber);
			dataBasePrepareService.clearDataBaseEntries();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@AfterAll
	private static void tearDown() {

	}

	@Test
	public void getNextParkingNumberIfAvailable_returnsParkingSpot() throws Exception {
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(1);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingSpot spot = parkingService.getNextParkingNumberIfAvailable();
		// THEN
		assertNotEquals(null, spot);
		assertEquals(ParkingType.CAR, spot.getParkingType());
	}

	@Test
	public void getNextParkingNumberIfAvailable_returnsNull_whenWrongInput() {
		// GIVEN
		when(inputReaderUtil.readSelection()).thenReturn(3);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		ParkingSpot spot = parkingService.getNextParkingNumberIfAvailable();
		// THEN
		assertEquals(null, spot);
	}

}
