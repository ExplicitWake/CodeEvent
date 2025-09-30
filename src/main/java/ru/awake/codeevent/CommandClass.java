package ru.awake.codeevent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandClass implements CommandExecutor {

    private final Utils utils;
    private final Config config;

    public CommandClass(CodeEvent plugin) {
        this.utils = new Utils(plugin);
        this.config = plugin.getPluginConfig();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (utils.isNotPlayer(commandSender)) {
            commandSender.sendMessage(this.config.onlyPlayer);
            return true;
        }

        if (strings.length == 0) {
            commandSender.sendMessage(this.config.usageCode);
            return true;
        }

        String enteredCode = strings[0];
        String senderName = commandSender.getName();

        if (utils.isCurrentlyCode(enteredCode)) {
            commandSender.sendMessage(this.config.successfulInput);
            if (this.config.isRandom) {
                String randomCommand = utils.getRandomString(this.config.codeCommands).replace("{player}", senderName);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), randomCommand);
            } else {
                for (String string : this.config.codeCommands) {
                    String replace = string.replace("{player}", senderName);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replace);
                }
            }
            for (String string : this.config.globalActivate) {
                String replace = string.replace("{player}", senderName);
                Bukkit.broadcastMessage(replace);
            }
            utils.removeCurrentlyCode();
        } else {
            commandSender.sendMessage(this.config.errorCode);
        }
        return true;
    }
}
