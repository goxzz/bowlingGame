package com.bowling.exception;

/**
 * The Class InvalidFileFormatException.
 */
public class InvalidFileFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
