package com.bowling.exception;

public class PlayerTurnHasNotEndedExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerTurnHasNotEndedExeption(String errorMessage) {
        super(errorMessage);
    }
}
