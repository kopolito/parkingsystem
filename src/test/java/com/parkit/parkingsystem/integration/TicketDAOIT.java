package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

@ExtendWith(MockitoExtension.class)
@Tag("IntegrationTests")
@DisplayName("TicketDAOIT Integration Tests")
public class TicketDAOIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
	private static final String vehicleRegNumber = "ABCDEF";
	private static Ticket ticket;
	private static ParkingSpot parkingSpot;

	@BeforeAll
	private static void setUp() throws Exception {
		System.out.println("TICKETDAO DEBUT");
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();

		ParkingType parkingType = ParkingType.CAR;
		int parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
		if (parkingNumber > 0) {
			parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
		} else {
			throw new Exception("Error fetching parking number from DB. Parking slots might be full");
		}
	}

	@BeforeEach
	private void setUpEach() {
		ticket = new Ticket();
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber(vehicleRegNumber);
	}

	@AfterEach
	private void cleanAfter() {
		System.out.println("TICKETDAO clean");
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	void test_hasBrothers() {
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		System.out.println("Before save");
		ticketDAO.saveTicket(ticket);

		ticket = new Ticket();
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticket.setInTime(new Date(System.currentTimeMillis()));
		assertTrue(ticketDAO.hasBrothers(ticket));
	}

	@Test
	void test_hasNoBrothers() {
		ticket.setInTime(new Date(System.currentTimeMillis()));
		assertFalse(ticketDAO.hasBrothers(ticket));
	}

}
