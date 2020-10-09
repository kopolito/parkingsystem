package com.parkit.parkingsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
class InteractiveShellTest {

	private static InteractiveShell interactiveShell;
	private static PrintStream outOriginal;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;
	@Mock
	private static TicketDAO ticketDAO;
	@Mock
	private static ParkingService parkingService;

	@BeforeEach
	private void setUpPerTest() {
		outOriginal = System.out;
		interactiveShell = new InteractiveShell(inputReaderUtil, parkingService);
	}

	@AfterEach
	private void tearDown() {
		System.setOut(outOriginal);
	}

	@Test
	void loadInterface_exits_whenInputThree() {
		when(inputReaderUtil.readSelection()).thenReturn(3);

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		interactiveShell.loadInterface();

		assertTrue(out.toString()
				.contains("Exiting from the system!"));
	}

	@Test
	void loadInterface_callsParkingServiceprocessIncomingVehicle_whenInputOne() {
		when(inputReaderUtil.readSelection())
				.thenReturn(1)
				.thenReturn(3);

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));

		interactiveShell.loadInterface();

		verify(parkingService, Mockito.times(1)).processIncomingVehicle();
	}

}
