package com.bowling.app;

import com.bowling.game.PlayableFactory;
import com.bowling.interfaces.Playable;

public class Application {

	public static void main(String[] args) {
		
		PlayableFactory pf = new PlayableFactory();
		try {
			Playable bowling = pf.getGame("Bowling");
			bowling.startMatch();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
