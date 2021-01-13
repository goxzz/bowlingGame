package com.bowling.interfaces;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * The Interface Loadable.
 */
public interface Loadable {

	public Map<String,Player> loadPlayersData(final String path) throws FileNotFoundException;
	
	public Player setPlayer(final Map<String, Player> playersMap, final String nextPlayerName);
	
	public String parsePlayerName(final String playerName, int readedLine);
	
	public int parsePlayerScore(final String score, final int readedLine);
	
	public void validatePlayerTurnIsLoaded(final Player currentPlayer
			, final String nextPlayerName
			, final int readedLine);
	
	public void updatePlayerInfo(Player currentPlayer, int nextPlayerScore, final int readedLine);
	
	public void checkScoreIsValid(final Player currentPlayer, final int nextPlayerScore, final int readedLine);
}
