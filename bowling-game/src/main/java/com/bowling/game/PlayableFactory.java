package com.bowling.game;

public class PlayableFactory {
	
	public Playable getGame(String name) {
		
		if("Bowling".equals(name)) {
			return new Bowling();
		}
		
		return null;
	}
}
