package com.drakula.vanillaplus;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public final class ServerEvents implements Listener {

  @EventHandler
  public void onServerPing(@NotNull PaperServerListPingEvent event) {
    String motd = VanillaPlus.GetInstance().getConfig().getString("motd");
    if (motd == null) {
      motd = "&fVanilla&ePlus &cUstaw motd w config.yml!";
    }
    event.motd(LegacyComponentSerializer.legacyAmpersand().deserialize(motd));
  }

}
