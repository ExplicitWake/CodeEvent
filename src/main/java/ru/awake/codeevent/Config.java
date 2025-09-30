package ru.awake.codeevent;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    public int delay;

    public int codeLength;

    public String characters;

    public boolean isRandom;

    public List<String> codeCommands;

    public String errorCode;

    public String usageCode;

    public String reloadPlugin;

    public String helpReload;

    public String onlyPlayer;

    public String successfulInput;

    public List<String> globalWarning;

    public List<String> globalActivate;


    public void setupSettings(FileConfiguration configuration) {
        ConfigurationSection settings = configuration.getConfigurationSection("event-settings");
        this.delay = settings.getInt("delay");
        this.codeLength = settings.getInt("code-length");
        this.characters = settings.getString("characters");
        this.isRandom = settings.getBoolean("random-command");
        this.codeCommands = settings.getStringList("commands");
    }

    public void setupMessages(FileConfiguration configuration) {
        ConfigurationSection messages = configuration.getConfigurationSection("messages");
        this.errorCode = Utils.color(messages.getString("error-code"));
        this.usageCode = Utils.color(messages.getString("usage"));
        this.reloadPlugin = Utils.color(messages.getString("reload"));
        this.helpReload = Utils.color(messages.getString("help"));
        this.onlyPlayer = Utils.color(messages.getString("only-player"));
        this.successfulInput = Utils.color(messages.getString("successful-input"));
        this.globalWarning = Utils.color(messages.getStringList("global-warning"));
        this.globalActivate = Utils.color(messages.getStringList("global-activate"));
    }
}
