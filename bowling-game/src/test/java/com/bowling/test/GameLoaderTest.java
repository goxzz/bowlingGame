package com.bowling.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.bowling.game.GameLoader;
import com.bowling.interfaces.Loadable;
import com.bowling.interfaces.Player;

/**
 * The Class GameLoaderTest.
 */
public class GameLoaderTest { 
	
	@Test
	public void loadPlayersDataTest() throws FileNotFoundException {
		Loadable loader = new GameLoader();
		Map<String, Player> playersMap = new HashMap<>();
		
		playersMap = 
				loader.loadPlayersData("src/test/resources/normalMatch.txt");
		
		assertFalse(playersMap.isEmpty());
		assertEquals(4, playersMap.values().size());
		playersMap.values().stream().forEach((p) -> {
			assertEquals(10, p.getRoundsPlayed());
			assertFalse(p.getIsActive());
		});
	}
	
	@Test
	public void loadPlayersInvalidNameTest() throws FileNotFoundException {
		Loadable loader = new GameLoader();
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			loader.loadPlayersData("src/test/resources/invalidName.txt");
		});
		
		assertTrue(e.getMessage().contains("The player name at line"));
		
	}
	
	@Test
	public void loadPlayersInvalidScoreTest() throws FileNotFoundException {
		Loadable loader = new GameLoader();
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			loader.loadPlayersData("src/test/resources/invalidScore.txt");
		});
		
		assertTrue(e.getMessage().contains("is not a valid score"));
		
	}

}
