package com.jacobneave.safespectate;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EntryState {

	Player player;
	Location location;
	GameMode gameMode;

	public EntryState(Player p) {
		player = p;
		location = player.getLocation();
		gameMode = player.getGameMode();

	}

	public void restore() {
		player.teleport(location);
		player.setGameMode(gameMode);
	}

}
