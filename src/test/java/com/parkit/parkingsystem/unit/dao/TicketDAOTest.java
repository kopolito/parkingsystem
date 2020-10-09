package com.parkit.parkingsystem.unit.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

class TicketDAOTest {

	private static final TicketDAO ticketDAO = new TicketDAO();

	@Test
	public void saveTicket_returnsFalse_whenTicketIsNull() {

		assertFalse(ticketDAO.saveTicket(null));
	}

	@Test
	public void saveTicket_returnsFalse_whenParkingSpotIsNull() {
		Ticket ticket = new Ticket();
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setParkingSpot(null);

		assertFalse(ticketDAO.saveTicket(ticket));
	}

	@Test
	public void updateTicket_returnsFalse_whenTicketIsNull() {

		assertFalse(ticketDAO.updateTicket(null));
	}

	@Test
	public void getTicket_returnsNull_whenVehicleRegNumberIsNull() {

		assertNull(ticketDAO.getTicket(null));
	}

	@Test
	public void hasBrothers_returnsFalse_whenTicketIsNull() {

		assertFalse(ticketDAO.hasBrothers(null));
	}

}
