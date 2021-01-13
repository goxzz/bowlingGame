package com.bowling.game;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import com.bowling.exception.PlayerTurnHasNotEndedExeption;
import com.bowling.interfaces.Loadable;
import com.bowling.interfaces.Playable;
import com.bowling.interfaces.Player;

/**
 * The Class Bowling.
 * 
 * Implements the game logic.
 */
public class Bowling implements Playable {
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger("com.bowling.game.Bowling");
	
	/**
	 * Start match.
	 * 
	 * Gives the welcome message, tries to load the game data
	 * and coordinates the beginning of the other game functions.
	 *
	 * @throws Exception the exception
	 */
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
		} else if(checkIfAllPlayersFinished(playersMap.values())) {
			calculateScore(playersMap);
			showResults(playersMap);
			endMatch();
		}
	}

	/**
	 * Calculate score.
	 * 
	 * Calculates the score obtained for every player.
	 *
	 * @param playersMap the players map
	 */
	public void calculateScore(Map<String, Player> playersMap) {
		Player currentPlayer;
		Integer[][] currentFrame;
		int frameScore = 0;
		
		for(Entry<String, Player> entry : playersMap.entrySet()) {
			currentPlayer = entry.getValue();
			currentFrame = currentPlayer.getScoreboard().getFrame();
			
			for(int i = 0; i <= 9; i++) {
				if(i < 9) {
					frameScore = getFirstFramesScore(currentPlayer, currentFrame, i);
				} else {
					frameScore = get10thFrameScore(currentFrame);
				}				
				currentPlayer.getScoreboard().setFrameScore(frameScore, i);
				currentPlayer.addScore(frameScore);
			}
			playersMap.put(currentPlayer.getName(), currentPlayer);
		}
	}

	/**
	 * Show results.
	 *
	 * @param playersMap the players map
	 */
	public void showResults(Map<String, Player> playersMap) {
		System.out.println(generateScoreboard(playersMap).get());
	}

	/**
	 * End match.
	 */
	public void endMatch() {
		System.out.println("\nThanks for playing!");
	}
	
	/**
	 * Gets the first frames score.
	 *
	 * @param currentPlayer the current player
	 * @param currentFrame the current frame
	 * @param i the i
	 * @return the first frames score
	 */
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
	
	/**
	 * Gets the 10 th frame score.
	 *
	 * @param currentFrame the current frame
	 * @return the 10 th frame score
	 */
	public int get10thFrameScore(final Integer[][] currentFrame) {
		
		if(currentFrame[9][0] == 10 && currentFrame[9][2] != null) {
			return 10 + currentFrame[9][1] + currentFrame[9][2];
		} else if (currentFrame[9][0] + currentFrame[9][1] == 10 && currentFrame[9][2] != null) {
			return 10 + currentFrame[9][2];
		} else {
			return currentFrame[9][0] + currentFrame[9][1];
		}
	}
	
	/**
	 * Gets the first frames scoreboard.
	 *
	 * @param currentPlayerFrames the current player frames
	 * @param i the i
	 * @param display the display
	 * @return the first frames scoreboard
	 */
	public void getFirstFramesScoreboard(final Integer[][] currentPlayerFrames
			, final int i, AtomicReference<String> display) {
		
		for (int j = 0; j < 2; j++) {
			if (j == 0 && currentPlayerFrames[i][j] == 10 && i < 9) {
				display.set(display.get() + "		X");
				break;
			} else if(j == 0 && currentPlayerFrames[i][j] == 10 && i == 9) {
				display.set(display.get() + "	X");
			} else if (j == 1 && currentPlayerFrames[i][j] == 10 && i == 9 && currentPlayerFrames[i][j-1] == 10) {
				display.set(display.get() + "	X");
			} else if(j == 0 && currentPlayerFrames[i][j] + currentPlayerFrames[i][j+1] == 10) {
				display.set(display.get() + "	" + currentPlayerFrames[i][j] + "	/");
				break;
			} else {
				display.set(display.get() + "	" + currentPlayerFrames[i][j]);
			}	
		}
		
	}
	
	/**
	 * Gets the 10 th frame scoreboard.
	 *
	 * @param currentPlayerFrames the current player frames
	 * @param display the display
	 * @param i the i
	 * @return the 10 th frame scoreboard
	 */
	public void get10thFrameScoreboard(final Integer[][] currentPlayerFrames
			, AtomicReference<String> display, int i) {
		
		if (currentPlayerFrames[i][2] != null) {
			if (currentPlayerFrames[i][2] == 10) {
				display.set(display.get() + "	X");
			} else if (currentPlayerFrames[i][0] + currentPlayerFrames[i][1] != 20
					&& currentPlayerFrames[i][0] + currentPlayerFrames[i][1] != 10
					&& currentPlayerFrames[i][1] + currentPlayerFrames[i][2] == 10) {
				display.set(display.get() +  "	/");
			} else {
				display.set(display.get() + "	" + currentPlayerFrames[i][2]);
			}
			
		}
	}
	
	/**
	 * Check if all players finished.
	 *
	 * @param playersSet the players set
	 * @return the boolean
	 */
	public Boolean checkIfAllPlayersFinished(Collection<Player> playersSet) {
		
		if (playersSet.stream().filter(p -> p.getRoundsPlayed() < 9 
				|| p.getIsActive()).count() == 0) {
			return true;
		}
		throw new PlayerTurnHasNotEndedExeption("All the players need to finish the match!");
	}

	/**
	 * Generate scoreboard.
	 *
	 * @param playersMap the players map
	 * @return the atomic reference
	 */
	public AtomicReference<String> generateScoreboard(Map<String, Player> playersMap) {
		
		AtomicReference<String> display = new AtomicReference<>("");
		String frame = "Frame		1		2		3		4		5		"
				+ "6		7		8		9		10";
		String frameDivider = "\n---------------------------------"
				+ "-----------------------------------------------"
				+ "-----------------------------------------------"
				+ "-----------------------------------------------------";
		
		playersMap.values().stream().forEach((p) -> {
			display.set(display.get() + frameDivider + "\n" + p.getName() + frameDivider + "\nPinfalls  ");
			Integer[][] currentPlayerFrames = p.getScoreboard().getFrame();
			Integer[] framescore = p.getScoreboard().getFrameScore();
			String score = "Score";
			
			for(int i = 0; i <= 9; i ++) {
				getFirstFramesScoreboard(currentPlayerFrames, i, display);
				if (i == 9) {
					get10thFrameScoreboard(currentPlayerFrames, display, i);
				}
				score +=  "		" + framescore[i];
			}
			display.set(display.get() + "\n" + score);
		});	
		
		display.set(frame + display.get());
		return display;
	}

}
