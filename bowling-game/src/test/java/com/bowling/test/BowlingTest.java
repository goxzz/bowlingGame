package com.bowling.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.bowling.app.Constants;
import com.bowling.exception.PlayerTurnHasNotEndedExeption;
import com.bowling.game.GameLoader;
import com.bowling.game.PlayableFactory;
import com.bowling.interfaces.Loadable;
import com.bowling.interfaces.Playable;
import com.bowling.interfaces.Player;

public class BowlingTest {
	
	@Test
	public void calculateScoreTest() throws ClassNotFoundException, FileNotFoundException {
		
		PlayableFactory pf = new PlayableFactory();
		Playable game = pf.getGame(Constants.BOWLING_GAME);
		Loadable loader = new GameLoader();
		Map<String, Player> playersMap = new HashMap<>();
		
		playersMap = loader.loadPlayersData("src/test/resources/normalMatch.txt");
		game.calculateScore(playersMap);
		
		assertFalse(playersMap.isEmpty());
		assertEquals(4, playersMap.values().size());
		playersMap.values().stream().forEach((p) -> {
			assertEquals(10, p.getRoundsPlayed());
			assertFalse(p.getIsActive());
			
			if (p.getName().equals("Reinier")) {
				assertEquals(300, p.getScore());
			} else if (p.getName().equals("Annie")) {
				assertEquals(0, p.getScore());
			} else if (p.getName().equals("Berthold")) {
				assertEquals(0, p.getScore());
			} else if (p.getName().equals("Pieck")) {
				assertEquals(135, p.getScore());
			}
		});
	}
	
	@Test
	public void generateScoreaboardTest() throws ClassNotFoundException, FileNotFoundException {
		
		PlayableFactory pf = new PlayableFactory();
		Playable game = pf.getGame(Constants.BOWLING_GAME);
		Loadable loader = new GameLoader();
		Map<String, Player> playersMap = new HashMap<>();
		
		playersMap = loader.loadPlayersData("src/test/resources/normalMatch.txt");
		game.calculateScore(playersMap);
		AtomicReference<String> display = game.generateScoreboard(playersMap);
		String expectedDisplay = "Frame		1		2		3		4		5"
				+ "		6		7		8		9		10\n" + 
				"-------------------------------------------------------------"
				+ "------------------------------------------------------------"
				+ "-----------------------------------------------------------\n" + 
				"Annie\n" + 
				"-------------------------------------------------------------"
				+ "--------------------------------------------------------------"
				+ "---------------------------------------------------------\n" + 
				"Pinfalls  	0	0	0	0	0	0	0	0	0	0	0	0	0	0"
				+ "	0	0	0	0	0	0\n" + 
				"Score		0		0		0		0		0		0		0	"
				+ "	0		0		0\n" + 
				"---------------------------------------------------------------"
				+ "-------------------------------------------------------------"
				+ "--------------------------------------------------------\n" + 
				"Berthold\n" + 
				"---------------------------------------------------------------"
				+ "-------------------------------------------------------------"
				+ "--------------------------------------------------------\n" + 
				"Pinfalls  	0	0	0	0	0	0	0	0	0	0	0	0	0	0"
				+ "	0	0	0	0	0	0\n" + 
				"Score		0		0		0		0		0		0		0	"
				+ "	0		0		0\n" + 
				"----------------------------------------------------------------"
				+ "---------------------------------------------------------------"
				+ "-----------------------------------------------------\n" + 
				"Pieck\n" + 
				"-----------------------------------------------------------------"
				+ "---------------------------------------------------------------"
				+ "----------------------------------------------------\n" + 
				"Pinfalls  	2	/	2	/	2	/	2	/	2	/	2	/	2	/	"
				+ "2	/	2	/	X	8	1\n" + 
				"Score		12		24		36		48		60		72		84		"
				+ "96		116		135\n" + 
				"-------------------------------------------------------------------"
				+ "-----------------------------------------------------------------"
				+ "------------------------------------------------\n" + 
				"Reinier\n" + 
				"--------------------------------------------------------------------"
				+ "------------------------------------------------------------------"
				+ "----------------------------------------------\n" + 
				"Pinfalls  		X		X		X		X		X		X		X	"
				+ "	X		X	X	X	X\n" + 
				"Score		30		60		90		120		150		180		210		"
				+ "240		270		300";
		
		assertFalse(display.get().trim().isEmpty());
		assertEquals(expectedDisplay.length(), display.get().length());
		assertEquals(expectedDisplay, display.get());
	}
	
	@Test
	public void unfinishedMatchTest() throws FileNotFoundException, ClassNotFoundException {
		
		PlayableFactory pf = new PlayableFactory();
		Playable game = pf.getGame(Constants.BOWLING_GAME);
		Loadable loader = new GameLoader();
		
		PlayerTurnHasNotEndedExeption e = assertThrows(PlayerTurnHasNotEndedExeption.class, () -> {
			Map<String, Player> playersMap = new HashMap<>();
			playersMap = loader.loadPlayersData("src/test/resources/unfinishedMatch.txt");
			game.checkIfAllPlayersFinished(playersMap.values());
		});
		
		assertTrue(e.getMessage().contains("All the players need to finish the match!"));
	}

}
