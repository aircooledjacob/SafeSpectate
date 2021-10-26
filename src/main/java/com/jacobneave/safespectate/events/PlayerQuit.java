package com.jacobneave.safespectate.events;

import com.jacobneave.safespectate.SafeSpectate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (SafeSpectate.spectators_and_entry_state.containsKey(player)) {
			SafeSpectate.spectators_and_entry_state.get(player).restore();
			SafeSpectate.spectators_and_entry_state.remove(player);
		}
	}

}
