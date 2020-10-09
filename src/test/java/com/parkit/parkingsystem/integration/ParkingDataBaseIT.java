package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
@Tag("IntegrationTests")
@DisplayName("Parking DataBase Integration Tests")
public class ParkingDataBaseIT {

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
			when(inputReaderUtil.readSelection()).thenReturn(1);
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

	// check that a ticket is actually saved in DB and Parking table is updated with availability
	@Test
	public void ticketAndParkingSpotAvailableStatusSavedInDB_whenParkingLotEnter() {
		// GIVEN
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		Ticket ticket = ticketDAO.getTicket(vehicleRegistrationNumber);
		// THEN
		assertNotEquals(null, ticket);
		assertFalse(ticket.getParkingSpot().isAvailable());
	}

	// check that the fare generated and out time are populated correctly in the database
	@Test
	public void ticketFareAndOutTimeCorrectlySavedInDB_whenParkingLotExit() {
		// compare same date format
		SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		Ticket ticket = ticketDAO.getTicket(vehicleRegistrationNumber);
		// withdraw 1.hour to inTime
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticketDAO.saveTicket(ticket);

		parkingService.processExitingVehicle();
		ticket = ticketDAO.getTicket(vehicleRegistrationNumber);

		// THEN
		assertNotEquals(null, ticket);
		assertEquals(Fare.CAR_RATE_PER_HOUR, ticket.getPrice());

		Duration duration = Duration.between(ticket.getInTime().toInstant(), ticket.getOutTime().toInstant());
		assertEquals(60, duration.getSeconds() / 60);

	}

	// check if parking is full
	@Test
	public void noNextAvailableSlot_whenParkinkLotIsFull() throws Exception {
		// GIVEN
		when(inputReaderUtil.readVehicleRegistrationNumber())
				.thenReturn("ABCDEA")
				.thenReturn("ABCDEB")
				.thenReturn("ABCDEC")
				.thenReturn("ABCDEE");
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		parkingService.processIncomingVehicle();
		parkingService.processIncomingVehicle();
		parkingService.processIncomingVehicle();

		assertEquals(0, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));

		assertEquals(null, parkingService.getNextParkingNumberIfAvailable());
	}
}
