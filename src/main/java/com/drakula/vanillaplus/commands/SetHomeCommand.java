package com.drakula.vanillaplus.commands;

import com.drakula.vanillaplus.VanillaPlus;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class SetHomeCommand implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player){
      Player player = (Player) sender;
      Location lokacja = player.getLocation();
      VanillaPlus.GetInstance().getCustomConfig().set(player.getName() + ".location", lokacja);
      VanillaPlus.GetInstance().saveCustomConfig();
      player.sendMessage("Ustawiono dom!");
    }

    return true;
  }
}
