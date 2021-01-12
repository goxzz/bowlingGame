package com.bowling.exception;

public class InvalidFileFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
