package com.parkit.parkingsystem.unit.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.util.InputReaderUtil;

@Tag("UnitTests")
@DisplayName("InputReaderUtil Unit Tests")
class InputReaderUtilTest {

	private static InputReaderUtil inputReaderUtil;

	private InputReaderUtil getInputReaderForString(String input) {
		return new InputReaderUtil(new Scanner(new ByteArrayInputStream(input.getBytes())));
	}

	@Test
	void readSelection_returnsInt_whenValidInput() {
		inputReaderUtil = getInputReaderForString("1");

		int result = inputReaderUtil.readSelection();
		assertEquals(1, result);
	}

	@Test
	void readSelection_returnsInvalid_whenInvalidInput() {
		inputReaderUtil = getInputReaderForString("abcd");

		int result = inputReaderUtil.readSelection();
		assertEquals(-1, result);
	}

	@Test
	void readVehicleReg_returnsValid_whenValidInput() throws Exception {
		inputReaderUtil = getInputReaderForString("ABCDEF");

		String result = inputReaderUtil.readVehicleRegistrationNumber();
		assertEquals("ABCDEF", result);
	}

	@Test
	void readVehicleReg_throwsException_whenInvalidInput() {
		inputReaderUtil = getInputReaderForString(" ");

		assertThrows(Exception.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
	}

}
