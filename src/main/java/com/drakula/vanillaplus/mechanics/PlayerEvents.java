package com.drakula.vanillaplus.mechanics;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import com.drakula.vanillaplus.VanillaPlus;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    String chatMessage = VanillaPlus.GetInstance().getConfig().getString("chat-prefix");
    if (chatMessage != null) {
      chatMessage = chatMessage.replace("%player%", player.getName());
    } else {
      chatMessage = player.getName();
    }

    Component chat = LegacyComponentSerializer.legacyAmpersand().deserialize(chatMessage + " " + PlainTextComponentSerializer.plainText().serialize(message));
    Bukkit.broadcast(chat);
  }

  @EventHandler
  public void onPlayerDeath(@NotNull PlayerPostRespawnEvent event) {
    Player player = event.getPlayer();
    FileConfiguration config = VanillaPlus.GetInstance().getCustomConfig();
    String homePath = player.getName() + ".location";
    if (!config.isSet(homePath)) return;
    player.teleport(Objects.requireNonNull(config.getLocation(homePath)));
    player.sendMessage(Component.text("Umarłeś! Zostałeś przeteleportowany do domu.", TextColor.fromHexString("#FF0001")));
  }

}
