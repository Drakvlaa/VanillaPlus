package com.drakula.vanillaplus.commands;

import com.drakula.vanillaplus.VanillaPlus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VanillaPlusCommand implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
    if (args.length > 0) {
      if (args[0].equalsIgnoreCase("config")) {
        if (args.length > 1) {
          if (args[1].equalsIgnoreCase("reload")) {
            VanillaPlus.GetInstance().reloadConfig();
            sender.sendMessage(Component.text("Config reloaded.").color(NamedTextColor.GREEN));
            return true;
          }
        }
      }
      sender.sendMessage(Component.text("Invalid command use.").color(NamedTextColor.RED));
      return false;
    }
    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
    if (args.length == 1) {
      return List.of("config");
    }
    if (args.length == 2 && args[0].equalsIgnoreCase("config")) {
      return List.of("reload");
    }
    return List.of();
  }
}
