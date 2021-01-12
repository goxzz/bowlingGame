package com.bowling.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import org.apache.commons.lang3.math.NumberUtils;

import com.bowling.app.Constants;
import com.bowling.interfaces.Loadable;
import com.bowling.interfaces.Player;

public class GameLoader implements Loadable{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger("com.bowling.game.PlayerLoader");
	
	public Map<String,Player> loadPlayersData(final String path) throws FileNotFoundException {
		
		File input;
		
		if(path.isEmpty()) {
			input = new File("/Volumes/Workspace/Projects/bowling.txt");
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
				
				if(lineData.length > 2) {
					System.out.println("The frame has more than the two admited values");
				}
				
				nextPlayerName = parsePlayerName(lineData[0], readedLine);
				nextPlayerScore = parsePlayerScore(lineData[1], readedLine);
				
				validatePlayerTurnIsLoaded(currentPlayer, nextPlayerName, readedLine);
				
				if(playersMap.isEmpty() || !playersMap.containsKey(nextPlayerName)) {
					currentPlayer = new BowlingPlayer(nextPlayerName);
				} else {
					currentPlayer = playersMap.get(nextPlayerName);
					if(currentPlayer.getRoundsPlayed() > 9) {
						System.out.println("The player " + currentPlayer.getName() + " has played more than 10 rounds!");
					}
				}
				
				updatePlayerInfo(currentPlayer, nextPlayerScore);
				
				playersMap.put(nextPlayerName, currentPlayer);
				
			}
			
			return playersMap;
		}
		
	}
	
	public String parsePlayerName(final String playerName, final int readedLine) {
		
		String name = "";
		
		if (playerName.isEmpty()) {
			throw new IllegalArgumentException(
					"The player name at line " + readedLine + " cannot be empty");
		} else {
			name = playerName.substring(0, 1).toUpperCase() + playerName.substring(1).toLowerCase();
		}
		
		return name;
	}
	
	public int parsePlayerScore(final String score, final int readedline) {
		
		if(score.isEmpty()) {
			throw new IllegalArgumentException(
					"The player score at line " + readedline + " cannot be empty");
		}
		
		if (Constants.FOUL_POINT.equals(score)) {
			return 0;
		} else if (NumberUtils.isDigits(score)){
			int frameScore = Integer.parseInt(score);
			if(frameScore >= 0 && frameScore <= 10) {
				return frameScore;
			}
		} 
		
		throw new NumberFormatException(
				score + " at line " + readedline + " is not a valid score");
	}
	
	public void validatePlayerTurnIsLoaded(final Player currentPlayer
			, final String nextPlayerName
			, final int readedLine) {
		if(currentPlayer != null) {
			if(currentPlayer.getIsActive() && !nextPlayerName.equals(currentPlayer.getName())) {
				System.out.println("The turn of " + currentPlayer.getName() 
					+ " at line " + readedLine + " hasn't ended!");
			} else if(!currentPlayer.getIsActive() && nextPlayerName.equals(currentPlayer.getName())) {
				System.out.println("The turn of " + currentPlayer.getName() 
					+ " at line " + readedLine + " has ended!");
			}
		}
	}
	
	public void updatePlayerInfo(Player currentPlayer, int nextPlayerScore) {
		
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
	
}
