package com.bowling.game;

public class PlayerTurnHasNotEndedExeption extends Exception {

	private static final long serialVersionUID = 1L;

	public PlayerTurnHasNotEndedExeption(String errorMessage) {
        getMessage();
    }
}
