package com.parkit.parkingsystem.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.util.InputReaderUtil;

public class InteractiveShell {

	private static final Logger logger = LogManager.getLogger("InteractiveShell");
	private InputReaderUtil inputReaderUtil;
	private ParkingService parkingService;

	public InteractiveShell(InputReaderUtil inputReaderUtil, ParkingService parkingService) {
		this.inputReaderUtil = inputReaderUtil;
		this.parkingService = parkingService;
	}

	public void loadInterface() {
		logger.info("App initialized!!!");
		System.out.println("Welcome to Parking System!");

		boolean continueApp = true;

		while (continueApp) {
			loadMenu();
			int option = inputReaderUtil.readSelection();
			switch (option) {
			case 1: {
				parkingService.processIncomingVehicle();
				break;
			}
			case 2: {
				parkingService.processExitingVehicle();
				break;
			}
			case 3: {
				System.out.println("Exiting from the system!");
				continueApp = false;
				break;
			}
			default:
				System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
			}
		}
	}

	private static void loadMenu() {
		System.out.println("Please select an option. Simply enter the number to choose an action");
		System.out.println("1 New Vehicle Entering - Allocate Parking Space");
		System.out.println("2 Vehicle Exiting - Generate Ticket Price");
		System.out.println("3 Shutdown System");
	}

}
