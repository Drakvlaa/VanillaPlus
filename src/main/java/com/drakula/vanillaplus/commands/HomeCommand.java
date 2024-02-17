package com.drakula.vanillaplus.commands;

import com.drakula.vanillaplus.VanillaPlus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class HomeCommand implements CommandExecutor {
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player player) {
      FileConfiguration data = VanillaPlus.GetInstance().getCustomConfig();
      String homePath = player.getName() + ".location";
      if (!data.isSet(homePath)) {
        player.sendMessage(Component.text("Nie masz domu! użyj komendy /sethome aby go ustawić").color(NamedTextColor.RED));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        return true;
      }
      player.sendMessage(Component.text("Teleportuje do domu!").color(TextColor.fromHexString("#00ff00")));
      Location home = data.getLocation(homePath);
      player.teleport(home);
      player.sendMessage(Component.text("Pomyślnie przeteleportowano!").color(TextColor.fromHexString("#00ff00")));
      player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
      return true;
    }
    sender.sendMessage(Component.text("Nie jesteś graczem!"));
    return true;
  }
  }

