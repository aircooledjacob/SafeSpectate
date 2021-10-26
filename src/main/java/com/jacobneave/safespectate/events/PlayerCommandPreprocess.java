package com.jacobneave.safespectate.events;

import com.jacobneave.safespectate.SafeSpectate;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class PlayerCommandPreprocess implements Listener {

	Plugin plugin = SafeSpectate.getPlugin(SafeSpectate.class);

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();

		//check if player is in SafeSpectate
		if (SafeSpectate.spectators_and_entry_state.containsKey(player) && player.getGameMode().equals(GameMode.SPECTATOR)) {

			//if commands are blocked then it is cancelled
			if (plugin.getConfig().options().configuration().getBoolean("block-all-commands")) {

				//if all commands blocked still need to allow exit command.
				if (!Objects.requireNonNull(plugin.getServer().getPluginCommand("spectate")).getAliases().contains(e.getMessage().replace("/", ""))) {

					//cancel code
					player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You cannot send that command while in SafeSpectate");
					e.setCancelled(true);
					plugin.getLogger().info(player.getDisplayName() + "'s command was not executed");

				}

			//if not all commands are blocked then check for blocked perm nodes list
			} else {

				//check if command being ran is in the blocked perm nodes list.
				try {
					plugin.getLogger().info(ChatColor.BLUE + plugin.getConfig().getStringList("blocked-permission-nodes").get(0));
					plugin.getLogger().info(ChatColor.BLUE + plugin.getServer().getPluginCommand(e.getMessage().replace("/", "").split(" ")[0]).getPermission());
					if (plugin.getConfig().getStringList("blocked-permission-nodes").contains(plugin.getServer().getPluginCommand(e.getMessage().replace("/", "")).getPermission())) {

						//cancel code
						player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You cannot send that command while in SafeSpectate");
						e.setCancelled(true);
						plugin.getLogger().info(player.getDisplayName() + "'s command was not executed");

					}

				} catch (NullPointerException ignored ) {}

			}

		}

	}

}
