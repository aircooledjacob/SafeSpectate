package com.jacobneave.safespectate.commands;

import com.jacobneave.safespectate.EntryState;
import com.jacobneave.safespectate.SafeSpectate;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Spectate implements CommandExecutor {

	Plugin plugin = SafeSpectate.getPlugin(SafeSpectate.class);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.getGameMode().equals(GameMode.SPECTATOR)) {
				if (SafeSpectate.spectators_and_entry_state.containsKey(player)) {
					SafeSpectate.spectators_and_entry_state.get(player).restore();
					SafeSpectate.spectators_and_entry_state.remove(player);
					player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are no longer in SafeSpectate");
					plugin.getLogger().info(player.getDisplayName() + " Has Left SafeSpectate");
				} else {
					player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Please use /gamemode or alternative to leave regular spectator");
					plugin.getLogger().info(player.getDisplayName() + " Unaffected");
				}
			} else {
				SafeSpectate.spectators_and_entry_state.put(player, new EntryState(player));
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You are now in SafeSpectate");
				plugin.getLogger().info(player.getDisplayName() + " Has Entered SafeSpectate");
			}
		} else {
			System.out.println("You must be a player to run this command");
		}
		return true;
	}

}




