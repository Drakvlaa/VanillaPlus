package com.drakula.vanillaplus.commands;

import com.drakula.vanillaplus.VanillaPlus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class HomeCommand implements CommandExecutor {
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      FileConfiguration config = VanillaPlus.GetInstance().getCustomConfig();
      String homePath = player.getName() + ".location";
      if (!config.isSet(homePath)) {
        player.sendMessage("Nie masz domu, użyj komendy Sethome aby go dodac");
        return true;
      }
      // home ustawiony
      Location home = config.getLocation(homePath);
      Component text = Component.text("Teleportuje do domu!").color(TextColor.fromHexString("#00ff00"));
      player.sendMessage(text);
      player.teleport(home);
      return true;
    }
    sender.sendMessage(Component.text("Nie jesteś graczem!").color(NamedTextColor.RED));
    return true;
  }
  }

