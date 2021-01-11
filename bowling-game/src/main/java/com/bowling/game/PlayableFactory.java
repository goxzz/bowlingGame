package com.bowling.game;

import com.bowling.interfaces.Playable;

public class PlayableFactory {
	
	public Playable getGame(String name) throws ClassNotFoundException{
		
		if("Bowling".equals(name)) {
			return new Bowling();
		}
		
		throw new ClassNotFoundException("Not java class found");
	}
}
