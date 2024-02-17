package com.drakula.vanillaplus;

import com.drakula.vanillaplus.commands.HomeCommand;
import com.drakula.vanillaplus.commands.SetHomeCommand;
import com.drakula.vanillaplus.commands.VanillaPlusCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class VanillaPlus extends JavaPlugin {

    private static VanillaPlus instance;
    public static VanillaPlus GetInstance() {
        return instance;
    }

    private final Logger logger = getSLF4JLogger();

    private File customConfigFile;
    private FileConfiguration customConfig;

    public FileConfiguration getCustomConfig() { return this.customConfig; }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        createCustomConfig();

        Objects.requireNonNull(getCommand("sethome")).setExecutor(new SetHomeCommand());
        Objects.requireNonNull(getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(getCommand("vanillaplus")).setExecutor(new VanillaPlusCommand());

        Bukkit.getPluginManager().registerEvents(new ServerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
        Bukkit.getPluginManager().registerEvents(new AutoReplant(), this);
        Bukkit.getPluginManager().registerEvents(new TreeCapacitor(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSmelter(), this);

        logger.info("VanillaPlus enabled");
    }

    @Override
    public void onDisable() {
        saveCustomConfig();
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "custom.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("custom.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            logger.error("Custom config file could not be loaded");
            e.printStackTrace();
        }

    }

    public void saveCustomConfig() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            logger.error("Custom config file could not be saved");
            e.printStackTrace();
        }
    }

    public @Nullable Component getConfigComponent(@NotNull String path, @Nullable Player player) {
        String message = getConfig().getString(path);
        if (message == null) return null;
        message = message.replace("%player%", player != null ? player.getName() : "null");
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }
}
