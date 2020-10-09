package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public static double calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			if (ticket.getOutTime() != null) {
				throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
			} else {
				throw new IllegalArgumentException("Out time provided is incorrect: NULL");
			}
		}

		long durationMs = ticket.getOutTime().getTime() - ticket.getInTime().getTime();
		double durationHour = durationMs / (double) (60 * 60 * 1000);
		durationHour = Math.round(durationHour * 100.0) / 100.0;

		// free 30 minutes
		if (durationHour <= 0.5) {
			return 0.0;
		}

		double price;
		double fare;
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
		if (!ticket.getIsRecurrent()) {
			price = durationHour * fare;
		} else {
			price = durationHour * fare * (1 - Fare.RECURRENT_DISCOUNT);
		}
		return price;
	}
}
