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

public class ChatConfigCommand implements CommandExecutor, TabCompleter {
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
    if (args.length > 0) {
      if (args[0].equalsIgnoreCase("config")) {
        if (args.length > 1) {
          if (args[1].equalsIgnoreCase("format")) {
            String format = args[2];
            VanillaPlus.GetInstance().getConfig().set("chat-prefix", format);
            VanillaPlus.GetInstance().saveConfig();
            sender.sendMessage(Component.text("Changed format to '" + format + "'", NamedTextColor.GREEN));
            return true;
          }
          if (args[1].equalsIgnoreCase("name")) {
            String color = args[2];
            if (!color.startsWith("&")) {
              color = "&" + color;
            }
            String format = VanillaPlus.GetInstance().getConfig().getString("chat-prefix");
            if (format == null) {
              format = color + "%player%";
            } else {
              int placeholderIndex = format.indexOf("%player%");
              int colorIndex = format.indexOf('&');
              StringBuilder formatBuilder = new StringBuilder();
              formatBuilder.append(format);
              if (colorIndex > -1 && colorIndex < placeholderIndex) {
                formatBuilder.delete(colorIndex, placeholderIndex);
                formatBuilder.insert(formatBuilder.toString().indexOf("%player%"), color);
              } else {
                formatBuilder.insert(0, color);
              }
              format = formatBuilder.toString();
            }
            VanillaPlus.GetInstance().getConfig().set("chat-prefix", format);
            VanillaPlus.GetInstance().saveConfig();
            sender.sendMessage(Component.text("Changed format to '" + format + "'", NamedTextColor.GREEN));
            return true;
          }
          if (args[1].equalsIgnoreCase("message")) {
            String color = args[2];
            if (!color.startsWith("&")) {
              color = "&" + color;
            }
            String format = VanillaPlus.GetInstance().getConfig().getString("chat-prefix");
            if (format == null) {
              format = "%player%" + color;
            } else {
              int colorIndex = format.lastIndexOf('&');
              StringBuilder formatBuilder = new StringBuilder();
              formatBuilder.append(format);
              if (colorIndex > -1 && colorIndex > format.indexOf("%player%")) {
                formatBuilder.delete(colorIndex, formatBuilder.length());
              }
              formatBuilder.append(color);
              format = formatBuilder.toString();
            }
            VanillaPlus.GetInstance().getConfig().set("chat-prefix", format);
            VanillaPlus.GetInstance().saveConfig();
            sender.sendMessage(Component.text("Changed format to '" + format + "'", NamedTextColor.GREEN));
            return true;
          }
        }
      }
    }
    sender.sendMessage("Invalid usage.");
    return true;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (args.length == 1) {
      return List.of("config");
    }
    if (args.length == 2){
      return List.of("name", "message", "format");
    }
    if (args.length == 3){
      return List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f");
    }
    return null;
  }
}
