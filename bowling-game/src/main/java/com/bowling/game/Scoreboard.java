package com.bowling.game;

import com.bowling.interfaces.Scoreable;

/**
 * The Class Scoreboard.
 */
public class Scoreboard implements Scoreable{
	
	private Integer[][] frame;
	private Integer[] frameScore;
	
	/**
	 * Instantiates a new scoreboard.
	 */
	public Scoreboard() {
		this.frame = new Integer[10][3];
		this.frameScore = new Integer[10];
	}

	public Integer[][] getFrame() {
		return frame;
	}

	public void setFrame(final Integer[][] frame) {
		this.frame = frame;
	}

	public Integer[] getFrameScore() {
		return frameScore;
	}

	/**
	 * Sets the frame score.
	 *
	 * @param frameScore the frame score
	 * @param index the index
	 */
	public void setFrameScore(final Integer frameScore, final int index) {
		if(index > 0 && index < 10) {
			this.frameScore[index] = this.frameScore[index-1] + frameScore;
		} else {
			this.frameScore[index] = frameScore;
		}
	}
	
	/**
	 * Load frame.
	 *
	 * @param tryNumber the try number
	 * @param roundNumber the round number
	 * @param score the score
	 */
	public void loadFrame(final int tryNumber,final int roundNumber, final int score) {
		if (roundNumber < 9 && score == 10 && tryNumber == 0) {
			this.frame[roundNumber][0] = score;
			this.frame[roundNumber][1] = 0;
		} else {
			this.frame[roundNumber][tryNumber] = score;
		}
	}
	
}
