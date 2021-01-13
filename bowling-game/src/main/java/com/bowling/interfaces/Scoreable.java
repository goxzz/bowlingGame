package com.bowling.interfaces;

/**
 * The Interface Scoreable.
 */
public interface Scoreable {

	public Integer[][] getFrame();

	public void setFrame(final Integer[][] frame);

	public Integer[] getFrameScore();

	public void setFrameScore(final Integer frameScore, final int index);
	
	public void loadFrame(final int tryNumber,final int roundNumber, final int score);
}
