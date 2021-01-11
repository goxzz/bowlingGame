package com.bowling.game;

import java.util.Map;
import java.util.Map.Entry;

public class Bowling implements Playable {
	
	public void startMatch() throws Exception {
		System.out.println("Welcome to the bowling game!");
		System.out.println("             .-.\n" + 
				"            /   \\\n" + 
				"        ____\\___/\n" + 
				"        \\   /\\\n" + 
				"           /  \\____\n" + 
				"          |\\\n" + 
				"          | \\                                         __       ((()\n" + 
				"         /   /                                  -=   /  \\      ///\\\n" + 
				"________/___/________________________________-=______\\__/______\\\\\\/\n" + 
				"");
		
		GameLoader gl = new GameLoader();
		Map<String, Player> playersMap = gl.loadPlayersData("");
		
		if(playersMap.isEmpty()) {
			System.out.println("The system coulnd't load any players.");
		} else {
			calculateScore(playersMap);
		}
	}

	public void calculateScore(Map<String, Player> playersMap) {
		Player currentPlayer;
		int[][] currentFrame;
		int frameScore = 0;
		
		for(Entry<String, Player> entry : playersMap.entrySet()) {
			currentPlayer = entry.getValue();
			currentFrame = currentPlayer.getPinfall().getFrame();
			
			for(int i = 0; i <= 9; i++) {
				// Calculates the first 9 frames score
				if(i < 9) {
					frameScore = getFirstFramesScore(currentPlayer, currentFrame, i);
				} else {
					// Calculates the 10th frame score
					frameScore = get10thFrameScore(currentFrame);
				}				
				currentPlayer.setScore(currentPlayer.getScore() + frameScore);
			}
			playersMap.put(currentPlayer.getName(), currentPlayer);
		}
		
		playersMap.values().forEach(p -> System.out.println(p.getName() + " has scored " + p.getScore() + " points!"));
	}

	public void showResults(Map<String, Player> playersMap) {
		
	}

	public void endMatch() {
		
	}
	
	public int getFirstFramesScore(final Player currentPlayer
			, final int[][] currentFrame, final int i) {
		
		if(currentFrame[i][0] == 10) {
			if (currentPlayer.getPinfall().getFrame()[i+1][0] == 10) {
				if(i < 8) {
					return 20 + currentFrame[i+2][0];
				} else {
					return  20 + currentFrame[i+1][0];
				}
			} else {
				return 10 + currentFrame[i+1][0] + currentFrame[i+1][1];
			}
		} else if(currentFrame[i][0] + currentFrame[i][1] == 10){
			return currentFrame[i][0] + currentFrame[i][1] + currentFrame[i+1][0];
		} else {
			return currentFrame[i][0] + currentFrame[i][1];
		}
	}
	
	public int get10thFrameScore(final int[][] currentFrame) {
		
		if(currentFrame[9][0] == 10) {
			return 10 + currentFrame[9][1] + currentFrame[9][2];
		} else if (currentFrame[9][0] + currentFrame[9][1] == 10) {
			return 10 + currentFrame[9][2];
		} else {
			return currentFrame[9][0] + currentFrame[9][1];
		}
	}

}
