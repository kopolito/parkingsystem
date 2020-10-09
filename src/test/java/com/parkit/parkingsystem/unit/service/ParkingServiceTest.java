package com.parkit.parkingsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;

	@BeforeEach
	private void setUpPerTest() {
		try {
			lenient().when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

			ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
			Ticket ticket = new Ticket();
			ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
			ticket.setParkingSpot(parkingSpot);
			ticket.setVehicleRegNumber("ABCDEF");
			lenient().when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
			lenient().when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

			lenient().when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	@Test
	void getVehiculeType_whenValidInputProvided() {
		when(inputReaderUtil.readSelection())
				.thenReturn(1)
				.thenReturn(2);

		ParkingType getVehichleType = parkingService.getVehicleType();
		assertEquals(ParkingType.CAR, getVehichleType);

		getVehichleType = parkingService.getVehicleType();
		assertEquals(ParkingType.BIKE, getVehichleType);

		verify(inputReaderUtil, Mockito.times(2)).readSelection();
	}

	@Test
	void getVehiculeType_whenBadInputProvided() {
		when(inputReaderUtil.readSelection()).thenReturn(3);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> parkingService.getVehicleType());
		assertEquals("Entered input is invalid", exception.getMessage());
	}

	@Test
	void getNextParkingNumberIfAvailable_whenBadInputProvided() {
		when(inputReaderUtil.readSelection()).thenReturn(3);

		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();

		assertNull(parkingSpot);
	}

	@Test
	public void processIncomingVehicle() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

		parkingService.processIncomingVehicle();

		assertEquals("ABCDEF", parkingService.getVehicleRegNumber());

		verify(inputReaderUtil, Mockito.times(1)).readSelection();
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
		verify(ticketDAO, Mockito.times(1)).saveTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void processIncomingVehicle_WhenParkingFull() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(-1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

		parkingService.processIncomingVehicle();

		assertEquals("ABCDEF", parkingService.getVehicleRegNumber());

		verify(inputReaderUtil, Mockito.times(1)).readSelection();
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
		verify(ticketDAO, Mockito.times(0)).saveTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void processIncomingVehicle_WhenBadRegistrationNumber() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(any(ParkingType.class))).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber())
				.thenThrow(new IllegalArgumentException("Invalid input provided"));

		parkingService.processIncomingVehicle();

		verify(inputReaderUtil, Mockito.times(1)).readSelection();
		verify(parkingSpotDAO, Mockito.times(1)).getNextAvailableSlot(any(ParkingType.class));
		verify(ticketDAO, Mockito.times(0)).saveTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void processExitingVehicle() {
		parkingService.processExitingVehicle();
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
		verify(ticketDAO, Mockito.times(1)).updateTicket(any(Ticket.class));
		verify(ticketDAO, Mockito.times(1)).hasBrothers(any(Ticket.class));
	}

	@Test
	public void processExitingVehicle_whenBadRegistrationNumber() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber())
				.thenThrow(new IllegalArgumentException("Invalid input provided"));

		parkingService.processExitingVehicle();
		verify(ticketDAO, Mockito.times(0)).getTicket(any(String.class));
		verify(ticketDAO, Mockito.times(0)).hasBrothers(any(Ticket.class));
		verify(ticketDAO, Mockito.times(0)).updateTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
	}

}
