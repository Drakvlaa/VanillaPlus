package com.drakula.vanillaplus.commands;

import com.drakula.vanillaplus.VanillaPlus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class SetHomeCommand implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player player){
      String path = player.getName() + ".location";
      VanillaPlus.GetInstance().getCustomConfig().set(path, player.getLocation());
      VanillaPlus.GetInstance().saveCustomConfig();
      player.sendMessage(Component.text("Pomyślnie ustawiono nowy dom!", NamedTextColor.GREEN));
      return true;
    }
    sender.sendMessage(Component.text("Nie jesteś graczem.", NamedTextColor.RED));
    return true;
  }
}
