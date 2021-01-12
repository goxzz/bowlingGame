package com.bowling.game;

public class ScoreLimitReachedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ScoreLimitReachedException(String errorMessage) {
        super(errorMessage);
    }
}
