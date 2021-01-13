package com.bowling.exception;

/**
 * The Class PlayerTurnHasEndedExeption.
 */
public class PlayerTurnHasEndedExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerTurnHasEndedExeption(String errorMessage) {
        super(errorMessage);
    }
}
