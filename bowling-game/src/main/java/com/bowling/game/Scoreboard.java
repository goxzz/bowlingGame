package com.bowling.game;

public class Scoreboard {
	
	private int[][] frame;
	private int[] frameScore;
	
	public Scoreboard() {
		this.frame = new int[10][3];
		this.frameScore = new int[10];
	}

	public int[][] getFrame() {
		return frame;
	}

	public void setFrame(final int[][] frame) {
		this.frame = frame;
	}

	public int[] getFrameScore() {
		return frameScore;
	}

	public void setFrameScore(final int[] frameScore) {
		this.frameScore = frameScore;
	}
	
	public void loadFrame(final int tryNumber,final int roundNumber, final int score) {
		if (roundNumber < 9 && score == 10 && tryNumber == 0) {
			frame[roundNumber][0] = score;
			frame[roundNumber][1] = 0;
		} else {
			frame[roundNumber][tryNumber] = score;
		}
	}
	
}
