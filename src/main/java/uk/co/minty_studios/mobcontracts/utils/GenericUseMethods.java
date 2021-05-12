package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;

public class GenericUseMethods {

    private final MobContracts plugin;

    public GenericUseMethods(MobContracts plugin) {
        this.plugin = plugin;
    }

    public void sendMessageWithPrefix(Player player, String message) {
        String fullMessage = plugin.getConfig().getString("prefix") + message;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', fullMessage));
    }

    public void sendBossMessage(LivingEntity entity, String message) {
        if (plugin.getConfig().getBoolean("settings.legendary.enable-boss-message")) {
            if (plugin.getConfig().getBoolean("settings.legendary.global-boss-message")) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', entity.getCustomName() + "&7: " + message));
                });
            } else {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (p.getLocation().distance(entity.getLocation()) <= plugin.getConfig().getInt("settings.targeting.distance-from-target"))
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', entity.getCustomName() + "&7: " + message));
                });
            }
        }
    }

    public void sendTargetMessage(LivingEntity entity, String message) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.getLocation().distance(entity.getLocation()) <= plugin.getConfig().getInt("settings.targeting.distance-from-target"))
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', message)); // send message to all nearby players
        });
    }

    public void sendGlobalMessagePrefix(String message) {
        String fullMessage = plugin.getConfig().getString("prefix") + message;
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', fullMessage));
        });
    }
}
