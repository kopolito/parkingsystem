package com.parkit.parkingsystem.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class InputReaderUtilTest {

	private static InputReaderUtil inputReaderUtil;

	private InputReaderUtil getInputReaderForString(String input) {
		return new InputReaderUtil(new Scanner(new ByteArrayInputStream(input.getBytes())));
	}

	@Test
	void whenValidInput_thenReadSelectionReturnsInt() {
		inputReaderUtil = getInputReaderForString("1");

		int result = inputReaderUtil.readSelection();
		assertEquals(1, result);
	}

	@Test
	void whenInvalidInput_thenReadSelectionReturnInvalid() {
		inputReaderUtil = getInputReaderForString("abcd");

		int result = inputReaderUtil.readSelection();
		assertEquals(-1, result);
	}

	@Test
	void whenValidInput_thenReadVehicleRegReturnsValid() throws Exception {
		inputReaderUtil = getInputReaderForString("ABCDEF");

		String result = inputReaderUtil.readVehicleRegistrationNumber();
		assertEquals("ABCDEF", result);
	}

	@Test
	void whenInvalidInput_thenReadVehicleRegThrowsException() {
		inputReaderUtil = getInputReaderForString(" ");

		assertThrows(Exception.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
	}

}
