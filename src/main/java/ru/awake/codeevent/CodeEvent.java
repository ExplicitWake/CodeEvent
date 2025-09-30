package ru.awake.codeevent;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CodeEvent extends JavaPlugin {

    private final Config pluginConfig = new Config();
    private final Utils utils = new Utils(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupConfig();
        getCommand("code").setExecutor(new CommandClass(this));
        getCommand("codeadm").setExecutor(new CommandClassForAdmin(this));
        this.utils.startTask();
    }
    public Config getPluginConfig() {
        return pluginConfig;
    }

    public void setupConfig() {
        FileConfiguration configuration = getConfig();
        this.pluginConfig.setupMessages(configuration);
        this.pluginConfig.setupSettings(configuration);
    }

    public void reloadPlugin(CommandSender sender) {
        this.utils.cancelTask(utils.bukkitTask);
        reloadConfig();
        setupConfig();
        this.utils.startTask();
        sender.sendMessage(this.pluginConfig.reloadPlugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
