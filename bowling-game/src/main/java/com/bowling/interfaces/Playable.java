package com.bowling.interfaces;

import java.util.Map;

public interface Playable {
	
	public void startMatch() throws Exception;
	
	public void calculateScore(Map<String, Player> playersMap);
	
	public void showResults(Map<String, Player> playersMap);
	
	public void endMatch();
	
}
