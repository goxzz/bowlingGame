package com.bowling.interfaces;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.bowling.game.PlayerTurnHasNotEndedExeption;

public interface Playable {
	
	public void startMatch() throws Exception;
	
	public void checkIfAllPlayersFinished(Collection<Player> playersSet) throws PlayerTurnHasNotEndedExeption;
	
	public void calculateScore(Map<String, Player> playersMap);
	
	public AtomicReference<String> generateScoreboard(Map<String, Player> playersMap);
	
	public void showResults(Map<String, Player> playersMap);
	
	public void endMatch();
	
}
