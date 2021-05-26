package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
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

    public void sendMessageNoPrefix(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendVariedSenderMessage(CommandSender sender, String message) {
        if (sender instanceof Player)
            sendMessageWithPrefix((Player) sender, message);
        if (sender instanceof ConsoleCommandSender)
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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

    public Enchantment parseEnchantment(String enchant) {

        enchant = enchant.toLowerCase();
        switch (enchant) {
            case "protection":
                return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "fire_protection":
                return Enchantment.PROTECTION_FIRE;
            case "feather_falling":
                return Enchantment.PROTECTION_FALL;
            case "blast_protection":
                return Enchantment.PROTECTION_EXPLOSIONS;
            case "projectile_protection":
                return Enchantment.PROTECTION_PROJECTILE;
            case "respiration":
                return Enchantment.OXYGEN;
            case "aqua_affinity":
                return Enchantment.WATER_WORKER;
            case "thorns":
                return Enchantment.THORNS;
            case "depth_strider":
                return Enchantment.DEPTH_STRIDER;
            case "frost_walker":
                return Enchantment.FROST_WALKER;
            case "sharpness":
                return Enchantment.DAMAGE_ALL;
            case "smite":
                return Enchantment.DAMAGE_UNDEAD;
            case "bane_of_arthropods":
                return Enchantment.DAMAGE_ARTHROPODS;
            case "knockback":
                return Enchantment.KNOCKBACK;
            case "fire_aspect":
                return Enchantment.FIRE_ASPECT;
            case "looting":
                return Enchantment.LOOT_BONUS_MOBS;
            case "efficiency":
                return Enchantment.DIG_SPEED;
            case "silk_touch":
                return Enchantment.SILK_TOUCH;
            case "unbreaking":
                return Enchantment.DURABILITY;
            case "fortune":
                return Enchantment.LOOT_BONUS_BLOCKS;
            case "power":
                return Enchantment.ARROW_DAMAGE;
            case "punch":
                return Enchantment.ARROW_KNOCKBACK;
            case "flame":
                return Enchantment.ARROW_FIRE;
            case "infinity":
                return Enchantment.ARROW_INFINITE;
            case "luck_of_the_sea":
                return Enchantment.LUCK;
            case "lure":
                return Enchantment.LURE;
            case "mending":
                return Enchantment.MENDING;
        }

        return null;
    }
}
