package com.drakula.vanillaplus.mechanics;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.drakula.vanillaplus.VanillaPlus;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
