package com.parkit.parkingsystem.unit.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

@Tag("UnitTests")
@DisplayName("TicketDAO Unit Tests")
class TicketDAOTest {

	private static final TicketDAO ticketDAO = new TicketDAO();

	@Test
	public void saveTicket_returnsFalse_whenTicketIsNull() {
		// THEN
		assertFalse(ticketDAO.saveTicket(null));
	}

	@Test
	public void saveTicket_returnsFalse_whenParkingSpotIsNull() {
		// GIVEN
		Ticket ticket = new Ticket();
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setParkingSpot(null);
		// THEN
		assertFalse(ticketDAO.saveTicket(ticket));
	}

	@Test
	public void updateTicket_returnsFalse_whenTicketIsNull() {
		// THEN
		assertFalse(ticketDAO.updateTicket(null));
	}

	@Test
	public void getTicket_returnsNull_whenVehicleRegNumberIsNull() {
		// THEN
		assertNull(ticketDAO.getTicket(null));
	}

	@Test
	public void hasBrothers_returnsFalse_whenTicketIsNull() {
		// THEN
		assertFalse(ticketDAO.hasBrothers(null));
	}

}
