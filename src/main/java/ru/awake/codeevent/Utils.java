package ru.awake.codeevent;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    private final CodeEvent plugin;
    private final Config config;

    BukkitTask bukkitTask;
    private static String currentCode;

    public Utils(CodeEvent plugin) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
    }


    public static String SUB_VERSION = Bukkit.getBukkitVersion().split("\\.")[1];
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F\\d]{6})");

    public static String color(String message) {
        if (SUB_VERSION.contains("-")) {
            SUB_VERSION = SUB_VERSION.split("-")[0];
        }
        if (Integer.parseInt(SUB_VERSION) >= 16) {
            Matcher matcher = HEX_PATTERN.matcher(message);
            StringBuilder builder = new StringBuilder(message.length() + 32);
            while (matcher.find()) {
                String group = matcher.group(1);
                matcher.appendReplacement(builder, "§x§" +
                        group.charAt(0) + "§" + group.charAt(1) + "§" +
                        group.charAt(2) + "§" + group.charAt(3) + "§" + group.charAt(4) + "§" +
                        group.charAt(5));
            }
            message = matcher.appendTail(builder).toString();
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> text) {
        return (List<String>) text.stream().map(Utils::color).collect(Collectors.toList());
    }

    public String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < this.config.codeLength; i++) {
            code.append(this.config.characters.charAt(random.nextInt(this.config.characters.length())));
        }
        return code.toString();
    }

    public String getRandomString (List<String> stringList) {
        Random random = new Random();
        int StringIndex = random.nextInt(stringList.size());
        return stringList.get(StringIndex);
    }

    public void cancelTask(BukkitTask bukkitTask) {
        if (bukkitTask.isCancelled()) {
            bukkitTask.cancel();
        }
    }
    public void startTask() {
        bukkitTask = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
            currentCode = generateCode();
            for (String message : this.config.globalWarning) {
                Bukkit.broadcastMessage(message.replace("{code}", currentCode));
            }
        }, this.config.delay * 20L, this.config.delay * 20L);
    }


    public boolean isPlayerAdmin(CommandSender sender) {
        return sender.isOp();
    }

    public boolean isNotPlayer(CommandSender sender) {
        return !(sender instanceof Player);
    }

    public void removeCurrentlyCode() {
        currentCode = null;
    }

    public boolean isCurrentlyCode(String code) {
        return code.equals(currentCode);
    }

}
