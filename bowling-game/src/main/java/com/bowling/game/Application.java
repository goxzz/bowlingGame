package com.bowling.game;

public class Application {

	public static void main(String[] args) {
		
		PlayableFactory pf = new PlayableFactory();
		Playable bowling = pf.getGame("Bowling");
		try {
			bowling.startMatch();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
