package com.parkit.parkingsystem.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

@Tag("UnitTests")
@DisplayName("FareCalculatorService Unit Tests")
public class FareCalculatorServiceTest {

	private Ticket ticket;

	@BeforeEach
	private void setUpPerTest() {
		// GIVEN
		ticket = new Ticket();
	}

	@Test
	public void calculateFare_whenCar() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals(Fare.CAR_RATE_PER_HOUR, price);
	}

	@Test
	public void calculateFare_whenBike() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals(Fare.BIKE_RATE_PER_HOUR, price);
	}

	@Test
	public void calculateFare_whenUnkownType() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		assertThrows(NullPointerException.class, () -> FareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFare_whenFutureInTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		assertThrows(IllegalArgumentException.class, () -> FareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFareBike_whenLessThanHalfAnOneHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));
		// 30 minutes parking time should give free parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals(0.0, price);
	}

	@Test
	public void calculateFareCar_whenLessThanHalfAnOneHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (30 * 60 * 1000));
		// 30 minutes parking time should give free parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals(0.0, price);
	}

	@Test
	public void calculateFareBike_whenLessThanOneHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), price);
	}

	@Test
	public void calculateFareCar_whenLessThanOneHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR), price);
	}

	@Test
	public void calculateFareCar_whenMoreThanADayParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals((24 * Fare.CAR_RATE_PER_HOUR), price);
	}

	@Test
	public void calculateFareCar_whenRecurrent() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (2 * 60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		ticket.setIsReccurent(true);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals((2 * Fare.CAR_RATE_PER_HOUR) * (1 - Fare.RECURRENT_DISCOUNT), price);
	}

	@Test
	public void calculateFareBike_whenRecurrent() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (2 * 60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		ticket.setIsReccurent(true);
		// THEN
		double price = FareCalculatorService.calculateFare(ticket);
		assertEquals((2 * Fare.BIKE_RATE_PER_HOUR) * (1 - Fare.RECURRENT_DISCOUNT), price);
	}

	@Test
	public void calculateFare_throwsException_WhenNullOutTime() {
		// GIVEN
		ticket.setInTime(new Date());
		ticket.setOutTime(null);
		ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
		// THEN
		assertThrows(IllegalArgumentException.class, () -> FareCalculatorService.calculateFare(ticket));
	}

	@Test
	public void calculateFare_throwsException_WhenOutTimeBeforeInTime() {
		// GIVEN
		Date inTime = new Date();

		Date outTime = new Date();
		outTime.setTime(System.currentTimeMillis() - (2 * 60 * 60 * 1000));

		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);

		ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
		// THEN
		assertThrows(IllegalArgumentException.class, () -> FareCalculatorService.calculateFare(ticket));
	}
}
