package com.bowling.interfaces;

public interface Player {

	public String getName();

	public void setName(final String name);

	public Scoreable setScoreboard();

	public void getScoreboard(final Scoreable scoreboard);

	public int getScore();

	public void setScore(final int score);
	
	public void addScore(final int score);
	
	public int getTryNumber();

	public void setTryNumber(int tryNumber);

	public int getRoundsPlayed();

	public void setRoundsPlayed(int roundsPlayed);

	public int getRemainingShots();

	public void setRemainingShots(int remainingShots);

	public Boolean getIsActive();

	public void setIsActive(Boolean isActive);
}
