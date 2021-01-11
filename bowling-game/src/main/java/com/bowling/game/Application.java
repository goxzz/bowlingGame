package com.bowling.game;

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
