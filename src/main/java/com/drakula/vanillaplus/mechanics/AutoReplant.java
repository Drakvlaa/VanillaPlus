package com.drakula.vanillaplus.mechanics;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class AutoReplant implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void RePlant(@NotNull BlockBreakEvent event) {
    if (event.isCancelled()) return;
    Player player = event.getPlayer();
    Block block = event.getBlock();
    if (!isCrop(block)) return;
    Location loc = block.getLocation();
    World world = block.getWorld();
    BlockData data = block.getState().getBlockData();
    if (!(data instanceof Ageable ageable)) return;
    boolean canHarvest = ageable.getAge() == ageable.getMaximumAge();
    event.setCancelled(true);
    if (!canHarvest) {
      if (player.isSneaking()) {
        event.setCancelled(false);
        return;
      }
      player.sendMessage(Component.text("Kucnij aby zniszczyć małe nasionka.", NamedTextColor.YELLOW));
      return;
    }
    block.getDrops(player.getInventory().getItemInMainHand()).forEach((item) -> world.dropItemNaturally(loc, item));
    if (!consumeItem(player, 1, getSeeds(block))) {
      block.setType(Material.AIR);
      return;
    }
    block.setType(block.getType());
  }

  public @Nullable Material getSeeds(@NotNull Block block) {
    return switch (block.getType()) {
      case WHEAT -> Material.WHEAT_SEEDS;
      case CARROTS -> Material.CARROT;
      case POTATOES -> Material.POTATO;
      case BEETROOTS -> Material.BEETROOT_SEEDS;
      case MELON_STEM -> Material.MELON_SEEDS;
      case PUMPKIN_STEM -> Material.PUMPKIN_SEEDS;
      case NETHER_WART -> Material.NETHER_WART;
      default -> null;
    };
  }

  public boolean isCrop(@NotNull Block block) {
    return getSeeds(block) != null;
  }

  public boolean consumeItem(@NotNull Player player, int count, Material mat) {
    Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

    int found = 0;
    for (ItemStack stack : ammo.values())
      found += stack.getAmount();
    if (count > found)
      return false;

    for (Integer index : ammo.keySet()) {
      ItemStack stack = ammo.get(index);

      int removed = Math.min(count, stack.getAmount());
      count -= removed;

      if (stack.getAmount() == removed)
        player.getInventory().setItem(index, null);
      else
        stack.setAmount(stack.getAmount() - removed);

    }
    return true;
  }

}
