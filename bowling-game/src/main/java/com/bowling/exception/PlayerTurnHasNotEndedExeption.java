package com.bowling.exception;

/**
 * The Class PlayerTurnHasNotEndedExeption.
 */
public class PlayerTurnHasNotEndedExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerTurnHasNotEndedExeption(String errorMessage) {
        super(errorMessage);
    }
}
