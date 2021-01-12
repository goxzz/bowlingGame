package com.bowling.interfaces;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Loadable {

	public Map<String,Player> loadPlayersData(final String path) throws FileNotFoundException;
	
	public String parsePlayerName(final String playerName, int readedline);
	
	public int parsePlayerScore(final String score, int readedline);
	
	public void validatePlayerTurnIsLoaded(final Player currentPlayer
			, final String nextPlayerName
			, final int readedLine);
	
	public void updatePlayerInfo(Player currentPlayer, int nextPlayerScore);
}
