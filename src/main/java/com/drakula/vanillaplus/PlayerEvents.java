package com.drakula.vanillaplus;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerEvents implements Listener {

  @EventHandler
  public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
    Player player = event.getPlayer();
    String welcomeMessage = VanillaPlus.GetInstance().getConfig().getString("welcome-message");
    if (welcomeMessage != null) {
      welcomeMessage = welcomeMessage.replace("%player%", player.getName());
      player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(welcomeMessage));
    }
    event.joinMessage(null);
    String joinMessage = VanillaPlus.GetInstance().getConfig().getString("join-message");
    if (joinMessage != null) {
      joinMessage = joinMessage.replace("%player%", player.getName());
      event.joinMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(joinMessage));
    }
  }

  @EventHandler
  public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
    Player player = event.getPlayer();
    event.quitMessage(null);
    String joinMessage = VanillaPlus.GetInstance().getConfig().getString("quit-message");
    if (joinMessage != null) {
      joinMessage = joinMessage.replace("%player%", player.getName());
      event.quitMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(joinMessage));
    }
  }

  @EventHandler
  public void onPlayerChat(@NotNull AsyncChatEvent event) {
    event.setCancelled(true);

    Player player = event.getPlayer();
    Component message = event.message();

    Component text = Component.text(player.getName())
        .color(TextColor.fromCSSHexString("#9c999a")).decoration(TextDecoration.BOLD, true)
        .append(Component.text(" ").color(TextColor.fromCSSHexString("#000000")))
        .append(message.color(TextColor.fromCSSHexString("#6E696F")).decoration(TextDecoration.BOLD, false));


    Bukkit.broadcast(text);
  }

  @EventHandler
  public void onPlayerDeath(@NotNull PlayerDeathEvent event) {
    Player player = event.getPlayer();
    FileConfiguration config = VanillaPlus.GetInstance().getCustomConfig();
    String homePath = player.getName() + ".location";
    if (!config.isSet(homePath)) {
      player.sendMessage("Nie masz domu, użyj komendy Sethome aby go dodac");
      return;
    }
    // home ustawiony
    Location home = config.getLocation(homePath);
    Component text = Component.text(" Umarłeś! teleportuje do domu...").color(TextColor.fromHexString("#FF0001"));
    player.sendMessage(text);
    // PlayerDeathEvent jest callowany w momencie smierci i trzeba tepnac gracza tick pozniej
    Bukkit.getScheduler().runTask(VanillaPlus.GetInstance(), () -> {
      player.teleport(home);
    });
  }

}
