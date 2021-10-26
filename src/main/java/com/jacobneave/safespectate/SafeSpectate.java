package com.jacobneave.safespectate;

import com.jacobneave.safespectate.commands.Spectate;
import com.jacobneave.safespectate.events.PlayerCommandPreprocess;
import com.jacobneave.safespectate.events.PlayerQuit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SafeSpectate extends JavaPlugin implements Listener {

	public static Map<Player, EntryState> spectators_and_entry_state = new HashMap<>();

	@Override
	public void onLoad() {

	}

	@Override
	public void onEnable() {
		// Plugin startup logic

		//register config
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		//set command executors
		Objects.requireNonNull(getCommand("spectate")).setExecutor(new Spectate());

		//register event listeners
		getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic

		// Restore all EntryStates
		spectators_and_entry_state.forEach((Player player, EntryState entryState) -> {
			entryState.restore();
		});

	}

}
