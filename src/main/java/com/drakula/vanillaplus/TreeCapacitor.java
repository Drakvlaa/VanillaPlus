package com.drakula.vanillaplus;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class TreeCapacitor implements Listener {

  private final static Set<GameMode> DISABLED_GAMEMODES = Set.of(GameMode.CREATIVE);
  private final static boolean MUST_DESTROY_WITH_AXE = true;

  private static final Set<Material> LOGS;

  static {
    LOGS = new HashSet<>();
    for (Material material : Material.values()) {
      if (material.toString().toLowerCase().contains("log")) {
        LOGS.add(material);
      }
    }
  }

  private void treeCapitator(@NotNull Location origin, @NotNull Player breaker) {
    Material brokenBlockType = origin.getBlock().getType();
    int logCounter = 0;

    while (origin.getBlock().getType() == brokenBlockType) {
      Block log = origin.getBlock();

      for (ItemStack drop : log.getDrops(breaker.getActiveItem())) {
        log.getWorld().dropItemNaturally(log.getLocation(), drop);
      }

      log.setType(Material.AIR);
      origin.add(0, 1, 0);
      logCounter += 1;
    }

    breaker.incrementStatistic(Statistic.MINE_BLOCK, brokenBlockType, logCounter);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerBreakBlock(@NotNull BlockBreakEvent event) {
    if (event.isCancelled()) return;
    if (!VanillaPlus.GetInstance().getConfig().getBoolean("enabled-treecapacitor")) return;
    Player player = event.getPlayer();
    Material logType = event.getBlock().getType();
    if (DISABLED_GAMEMODES.contains(player.getGameMode())) return;
    if (!LOGS.contains(logType)) return;
    if (MUST_DESTROY_WITH_AXE && !player.getActiveItem().getType().toString().contains("axe")) return;
    event.setCancelled(true);
    treeCapitator(event.getBlock().getLocation(), player);
  }

}
