package com.bowling.interfaces;

public interface Scoreable {

	public int[][] getFrame();

	public void setFrame(final int[][] frame);

	public int[] getFrameScore();

	public void setFrameScore(final int[] frameScore);
	
	public void loadFrame(final int tryNumber,final int roundNumber, final int score);
}
