package com.bowling.game;

import com.bowling.interfaces.Player;
import com.bowling.interfaces.Scoreable;

/**
 * The Class BowlingPlayer.
 */
public class BowlingPlayer implements Player{
	
	private String name;
	private Scoreable scoreboard;
	private int score;
	private int tryNumber;
	private int remainingShots;
	private int roundsPlayed;
	private Boolean isActive;
	
	/**
	 * Instantiates a new bowling player.
	 *
	 * @param name the name
	 */
	public BowlingPlayer(final String name) {
		this.name = name;
		this.scoreboard = new Scoreboard();
		this.score = 0;
		this.tryNumber = 0;
		this.roundsPlayed = 0;
		this.remainingShots = 1;
		this.isActive = true;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Scoreable getScoreboard() {
		return scoreboard;
	}

	public void setScoreboard(final Scoreable scoreboard) {
		this.scoreboard = scoreboard;
	}

	public int getScore() {
		return score;
	}

	public void setScore(final int score) {
		this.score = score;
	}
	
	public void addScore(final int score) {
		this.score += score;
	}

	public int getTryNumber() {
		return tryNumber;
	}

	public void setTryNumber(int tryNumber) {
		this.tryNumber = tryNumber;
	}

	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	public int getRemainingShots() {
		return remainingShots;
	}

	public void setRemainingShots(int remainingShots) {
		this.remainingShots = remainingShots;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
