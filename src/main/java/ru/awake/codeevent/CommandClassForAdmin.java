package ru.awake.codeevent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandClassForAdmin implements CommandExecutor {

    private final CodeEvent plugin;
    private final Config config;
    private final Utils utils;

    public CommandClassForAdmin(CodeEvent plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
        this.utils = new Utils(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (utils.isPlayerAdmin(commandSender) && strings.length == 0) {
            commandSender.sendMessage(this.config.helpReload);
            return true;
        }

        if (utils.isPlayerAdmin(commandSender) && strings[0].equals("reload")) {
            this.plugin.reloadPlugin(commandSender);
            return true;
        }

        return true;
    }
}
