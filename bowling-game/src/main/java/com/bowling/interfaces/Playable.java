package com.bowling.interfaces;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public interface Playable {
	
	public void startMatch() throws Exception;
	
	public Boolean checkIfAllPlayersFinished(Collection<Player> playersSet);
	
	public void calculateScore(Map<String, Player> playersMap);
	
	public AtomicReference<String> generateScoreboard(Map<String, Player> playersMap);
	
	public void showResults(Map<String, Player> playersMap);
	
	public void endMatch();
	
}
