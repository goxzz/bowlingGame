package com.bowling.game;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import com.bowling.interfaces.Loadable;
import com.bowling.interfaces.Playable;
import com.bowling.interfaces.Player;

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
		
		Loadable gl = new GameLoader();
		Map<String, Player> playersMap = gl.loadPlayersData("");
		
		if(playersMap.isEmpty()) {
			System.out.println("The system coulnd't load any players.");
		} else {
			checkIfAllPlayersFinished(playersMap.values());
			calculateScore(playersMap);
			showResults(playersMap);
			endMatch();
		}
	}

	public void calculateScore(Map<String, Player> playersMap) {
		Player currentPlayer;
		Integer[][] currentFrame;
		int frameScore = 0;
		
		for(Entry<String, Player> entry : playersMap.entrySet()) {
			currentPlayer = entry.getValue();
			currentFrame = currentPlayer.getScoreboard().getFrame();
			
			for(int i = 0; i <= 9; i++) {
				// Calculates the first 9 frames score
				if(i < 9) {
					frameScore = getFirstFramesScore(currentPlayer, currentFrame, i);
				} else {
					// Calculates the 10th frame score
					frameScore = get10thFrameScore(currentFrame);
				}				
				currentPlayer.getScoreboard().setFrameScore(frameScore, i);
				currentPlayer.addScore(frameScore);
			}
			playersMap.put(currentPlayer.getName(), currentPlayer);
		}
	}

	public void showResults(Map<String, Player> playersMap) {
		String frame = "Frame		1		2		3		4		5		"
						+ "6		7		8		9		10";
		String frameDivider = "\n---------------------------------"
				+ "-----------------------------------------------"
				+ "-----------------------------------------------"
				+ "-----------------------------------------------------";
		AtomicReference<String> display = new AtomicReference<>("");
		
		playersMap.values().stream().forEach((p) -> {
			display.set(display.get() + frameDivider + "\n" + p.getName() + frameDivider + "\nPinfalls  ");
			Integer[][] currentPlayerFrames = p.getScoreboard().getFrame();
			Integer[] framescore = p.getScoreboard().getFrameScore();
			String score = "Score";
			for(int i = 0; i <= 9; i ++) {
				for(int j = 0; j < 2; j++ ) {
				
					if (j == 0 && currentPlayerFrames[i][j] == 10 && i < 9) {
						display.set(display.get() + "		X");
						break;
					} else if(j == 0 && currentPlayerFrames[i][j] == 10 && i == 9) {
						display.set(display.get() + "	X");
					} else if (j == 1 && currentPlayerFrames[i][j] == 10 && i == 9 && currentPlayerFrames[i][j-1] == 10) {
						display.set(display.get() + "	X");
					} else if(j == 0
							&& currentPlayerFrames[i][j] + currentPlayerFrames[i][j+1] == 10) {
						display.set(display.get() + "	" + currentPlayerFrames[i][j] + "	/");
						break;
					} else {
						display.set(display.get() + "	" + currentPlayerFrames[i][j]);
					}	
				}
				
				if (i == 9 && currentPlayerFrames[i][2] != null) {
					if (currentPlayerFrames[i][2] == 10) {
						display.set(display.get() + "	X");
					} else if (currentPlayerFrames[i][0] + currentPlayerFrames[i][1] != 20
							&& currentPlayerFrames[i][1] + currentPlayerFrames[i][2] == 10 ) {
						display.set(display.get() +  "	/");
					} else {
						display.set(display.get() + "	" + currentPlayerFrames[i][2]);
					}
					
				}	
				score +=  "		" + framescore[i];
			}
			display.set(display.get() + "\n" + score);
		});
		
		display.set(frame + display.get());
		System.out.println(display);
	}

	public void endMatch() {
		System.out.println("\nThanks for playing!");
	}
	
	public int getFirstFramesScore(final Player currentPlayer
			, final Integer[][] currentFrame, final int i) {
		
		if(currentFrame[i][0] == 10) {
			if (currentPlayer.getScoreboard().getFrame()[i+1][0] == 10) {
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
	
	public int get10thFrameScore(final Integer[][] currentFrame) {
		
		if(currentFrame[9][0] == 10) {
			return 10 + currentFrame[9][1] + currentFrame[9][2];
		} else if (currentFrame[9][0] + currentFrame[9][1] == 10) {
			return 10 + currentFrame[9][2];
		} else {
			return currentFrame[9][0] + currentFrame[9][1];
		}
	}
	
	public void checkIfAllPlayersFinished(Collection<Player> playersSet) throws Exception {
		if (playersSet.stream().filter(p -> p.getRoundsPlayed() < 9).count() > 0) {
			throw new Exception("All the players need to finish the match!");
		}
	}

}
