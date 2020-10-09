package com.parkit.parkingsystem;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

public class App {
	private static final Logger logger = LogManager.getLogger("App");

	public static void main(String args[]) {
		logger.info("Initializing Parking System");
		Scanner scan = new Scanner(System.in);
		InputReaderUtil inputReaderUtil = new InputReaderUtil(scan);
		ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
		TicketDAO ticketDAO = new TicketDAO();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		InteractiveShell interactiveShell = new InteractiveShell(inputReaderUtil, parkingService);
		interactiveShell.loadInterface();
	}
}
