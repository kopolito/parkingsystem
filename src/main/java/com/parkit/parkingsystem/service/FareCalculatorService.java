package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public static double calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		double price;
		double fare;
		long durationMs = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
		double durationHour = durationMs / (double) (60 * 60 * 1000);
		durationHour = Math.round(durationHour * 100.0) / 100.0;

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			fare = Fare.CAR_RATE_PER_HOUR;
			break;
		}
		case BIKE: {
			fare = Fare.BIKE_RATE_PER_HOUR;
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		price = durationHour * fare;
		return price;
	}
}
