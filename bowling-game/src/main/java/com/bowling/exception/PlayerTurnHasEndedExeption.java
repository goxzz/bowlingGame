package com.bowling.exception;

public class PlayerTurnHasEndedExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerTurnHasEndedExeption(String errorMessage) {
        super(errorMessage);
    }
}
