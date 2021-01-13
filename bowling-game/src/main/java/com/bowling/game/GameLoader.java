package com.bowling.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import org.apache.commons.lang3.math.NumberUtils;

import com.bowling.app.Constants;
import com.bowling.exception.InvalidFileFormatException;
import com.bowling.exception.PlayerTurnHasEndedExeption;
import com.bowling.exception.PlayerTurnHasNotEndedExeption;
import com.bowling.exception.ScoreLimitReachedException;
import com.bowling.interfaces.Loadable;
import com.bowling.interfaces.Player;

/**
 * The Class GameLoader.
 * 
 * Handles loading the game data into a map to be consumed
 * by the game implementation.
 * To ensure efficiency, updates and validates the players data
 * before the game can start the match.
 * 
 */
public class GameLoader implements Loadable{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger("com.bowling.game.PlayerLoader");
	
	/**
	 * Load players data.
	 * 
	 * Reads the input file and calls the methods that handle
	 * validations and players updates to store all found players
	 * in a map<String, Player>.
	 *
	 * @param path the path
	 * @return the map
	 * @throws FileNotFoundException the file not found exception
	 */
	public Map<String,Player> loadPlayersData(final String path) throws FileNotFoundException {
		
		File input;
		
		if(path.isEmpty()) {
			input = new File("Gamedata/bowling.txt");
		} else {
			input = new File(path);
		}
		
		String nextLine = "";
		int readedLine = 0;
		String lineData[];
		String nextPlayerName = "";
		int nextPlayerScore = 0;
		Player currentPlayer = null;
		Map<String,Player> playersMap = new HashMap<>();
		
		try (Scanner sc = new Scanner(input);) {
			
			System.out.println("Loading game...\n");
			
			while(sc.hasNextLine()) {
				readedLine++;
				nextLine = sc.nextLine();
				
				if(nextLine.trim().isEmpty()) {
					continue;
				}
				
				lineData = nextLine.split("\\s+");
				
				if(lineData.length != 2) {
					throw new InvalidFileFormatException(
							"The line " + readedLine + " has an invalid number of values");
				}
				
				for (int i = 0; i < lineData.length; i++) {
					if (i == 0) {
						nextPlayerName = parsePlayerName(lineData[0], readedLine);
					} else if (i == 1) {
						nextPlayerScore = parsePlayerScore(lineData[1], readedLine);
					}
				} 
				
				validatePlayerTurnIsLoaded(currentPlayer, nextPlayerName, readedLine);	
				currentPlayer = setPlayer(playersMap, nextPlayerName);		
				updatePlayerInfo(currentPlayer, nextPlayerScore, readedLine);
				playersMap.put(nextPlayerName, currentPlayer);
			}
			
			return playersMap;
		}
		
	}
	
	/**
	 * Sets the player.
	 *
	 * @param playersMap the players map
	 * @param nextPlayerName the next player name
	 * @return the player
	 */
	public Player setPlayer(Map<String, Player> playersMap, final String nextPlayerName) {
		if(playersMap.isEmpty() || !playersMap.containsKey(nextPlayerName)) {
			return new BowlingPlayer(nextPlayerName);
		} else {
			Player currentPlayer = playersMap.get(nextPlayerName);
			if(currentPlayer.getRoundsPlayed() > 9) {
				throw new InvalidFileFormatException(
						"The player " + currentPlayer.getName() + " has played more than 10 rounds!");
			}
			
			return currentPlayer;
		}
	}
	
	/**
	 * Parses the player name.
	 * 
	 * Checks the name is not empty
	 * and is a valid word and returns
	 * it's parsed value.
	 *
	 * @param playerName the player name
	 * @param readedLine the readed line
	 * @return the string
	 */
	public String parsePlayerName(final String playerName, final int readedLine) {
		
		if (playerName.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The player name at line " + readedLine + " cannot be empty");
		} else if (playerName.matches("[a-zA-Z]+")){
			return playerName.substring(0, 1).toUpperCase() + playerName.substring(1).toLowerCase();
		}
		
		throw new IllegalArgumentException(
				"The player name at line " + readedLine + " is invalid!");
	}
	
	/**
	 * Parses the player score.
	 * 
	 * Checks the score is a valid 
	 * and returns it's parsed value.
	 *
	 * @param score the score
	 * @param readedLine the readed line
	 * @return the int
	 */
	public int parsePlayerScore(final String score, final int readedLine) {

		if(score.trim().isEmpty()) {
			throw new IllegalArgumentException(
					"The player score at line " + readedLine + " cannot be empty");
		} else if (Constants.FOUL_POINT.equals(score)) {
			return 0;
		} else if (NumberUtils.isDigits(score)){
			int frameScore = Integer.parseInt(score);
			if(frameScore >= 0 && frameScore <= 10) {
				return frameScore;
			}
		} 
		
		throw new NumberFormatException(
				score + " at line " + readedLine + " is not a valid score");
	}
	
	/**
	 * Validate player turn is loaded.
	 * 
	 * Checks if the player turn has already finished
	 * or if a new player comes and the previous one 
	 * hasn't finished his turn.
	 *
	 * @param currentPlayer the current player
	 * @param nextPlayerName the next player name
	 * @param readedLine the readed line
	 */
	public void validatePlayerTurnIsLoaded(final Player currentPlayer
			, final String nextPlayerName
			, final int readedLine) {
		if(currentPlayer != null) {
			if(currentPlayer.getIsActive() && !nextPlayerName.equals(currentPlayer.getName())) {
				
				throw new PlayerTurnHasNotEndedExeption("The turn of " + currentPlayer.getName() 
													+ " at line " + readedLine + " hasn't ended!");
				
			} else if(!currentPlayer.getIsActive() && nextPlayerName.equals(currentPlayer.getName())) {
				
				throw new PlayerTurnHasEndedExeption("The turn of " + currentPlayer.getName() 
													+ " at line " + readedLine + " has ended!");
			}
		}
	}
	
	/**
	 * Update player info.
	 * 
	 * Updates the player score, rounds, remaining shots
	 * and does a general values cleanup and reset after
	 * every round.
	 *
	 * @param currentPlayer the current player
	 * @param nextPlayerScore the next player score
	 * @param readedLine the readed line
	 */
	public void updatePlayerInfo(Player currentPlayer, final int nextPlayerScore, final int readedLine) {
		
		if(currentPlayer.getTryNumber() > 0) {
			checkScoreIsValid(currentPlayer, nextPlayerScore, readedLine);
		}
		
		currentPlayer.getScoreboard().loadFrame(
				currentPlayer.getTryNumber(), currentPlayer.getRoundsPlayed(), nextPlayerScore);
		
		// Determine the remaining shots for the player in this round
		if (currentPlayer.getRoundsPlayed() < 9 &&
				nextPlayerScore == 10 && currentPlayer.getTryNumber() == 0) {
			
			currentPlayer.setRemainingShots(0);
			
		} else if(currentPlayer.getRoundsPlayed() == 9) {
			if (nextPlayerScore == 10 && currentPlayer.getTryNumber() == 0) {
				
				currentPlayer.setRemainingShots(2);
			} else if(currentPlayer.getTryNumber() == 1 && currentPlayer.getScoreboard().getFrame()[9][0]
					+ currentPlayer.getScoreboard().getFrame()[9][1] == 10) {
				
				currentPlayer.setRemainingShots(1);
			}
		}
		
		// Updates round info
		if (currentPlayer.getRemainingShots() == 0) {
			currentPlayer.setIsActive(false);
			currentPlayer.setRemainingShots(1);
			currentPlayer.setTryNumber(0);
			currentPlayer.setRoundsPlayed(currentPlayer.getRoundsPlayed()+1);
		} else {
			currentPlayer.setIsActive(true);
			currentPlayer.setTryNumber(currentPlayer.getTryNumber()+1);
			currentPlayer.setRemainingShots(currentPlayer.getRemainingShots()-1);
		}
	}
	
	/**
	 * Check score is valid.
	 *
	 * @param currentPlayer the current player
	 * @param nextPlayerScore the next player score
	 * @param readedLine the readed line
	 */
	public void checkScoreIsValid(final Player currentPlayer, final int nextPlayerScore, final int readedLine) {
		int tryNumber = currentPlayer.getTryNumber();
		int roundNumber = currentPlayer.getRoundsPlayed();
		
		if (tryNumber > 0 && currentPlayer.getScoreboard().getFrame()[roundNumber][tryNumber-1] != null
				&& (roundNumber < 9 && currentPlayer.getScoreboard().getFrame()[roundNumber][tryNumber-1]
						+ nextPlayerScore > 10)
				||  (roundNumber == 9 && currentPlayer.getScoreboard().getFrame()[roundNumber][tryNumber-1] != 10
						&& currentPlayer.getScoreboard().getFrame()[roundNumber][tryNumber-1]
								+ nextPlayerScore > 10
						)
				) {
			throw new ScoreLimitReachedException("The score " + nextPlayerScore + " at line " + readedLine
					+ " exceeds the score limit");
		}
	}
	
}
