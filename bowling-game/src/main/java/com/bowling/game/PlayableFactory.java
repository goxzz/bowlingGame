package com.bowling.game;

import com.bowling.interfaces.Playable;

/**
 * A factory for creating Playable objects.
 */
public class PlayableFactory {
	
	/**
	 * Gets the game.
	 *
	 * @param name the name
	 * @return the game
	 * @throws ClassNotFoundException the class not found exception
	 */
	public Playable getGame(String name) throws ClassNotFoundException{
		
		if("Bowling".equals(name)) {
			return new Bowling();
		}
		
		throw new ClassNotFoundException("Not java class found");
	}
}
