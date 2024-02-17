package com.drakula.vanillaplus.mechanics;

import com.drakula.vanillaplus.VanillaPlus;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public final class AutoSmelter implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerBreakOre(@NotNull BlockBreakEvent event){
      if (event.isCancelled()) return;
      if (!VanillaPlus.GetInstance().getConfig().getBoolean("enabled-autosmelter")) return;
      Player breaker = event.getPlayer();
      Block block = event.getBlock();
      if (!block.getType().toString().toLowerCase().contains("ore")) return;
      ArrayList<ItemStack> drops = new ArrayList<>(block.getDrops(breaker.getInventory().getItemInMainHand(), breaker));
      for (int i = 0; i < drops.size(); i += 1) {
        ItemStack item = drops.get(i);
        if (!item.getType().toString().toLowerCase().contains("raw")) continue;
        Iterator<Recipe> iterator = Bukkit.recipeIterator();
        while (iterator.hasNext()) {
          Recipe wildcardRecipe = iterator.next();
          if (!(wildcardRecipe instanceof FurnaceRecipe recipe)) continue;
          if (!recipe.getInputChoice().test(item)) continue;
          ItemStack result = recipe.getResult();
          result.setAmount(result.getAmount() * item.getAmount());
          drops.set(i, result);
          break;
        }
      }
      drops.forEach(item -> block.getWorld().dropItemNaturally(block.getLocation(), item));
      event.setDropItems(false);
  }

}
