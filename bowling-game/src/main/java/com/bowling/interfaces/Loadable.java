package com.bowling.interfaces;

import java.io.FileNotFoundException;
import java.util.Map;
import com.bowling.game.BowlingPlayer;

public interface Loadable {

	public Map<String,BowlingPlayer> loadPlayersData(final String path) throws FileNotFoundException;
	
	public String parsePlayerName(final String playerName);
	
	public int parsePlayerScore(final String score);
	
	public void validatePlayerTurnHasEnded(final Player currentPlayer
			, final String nextPlayerName
			, final int readedLine);
	
	public void updatePlayer(Player currentPlayer, int nextPlayerScore);
}
