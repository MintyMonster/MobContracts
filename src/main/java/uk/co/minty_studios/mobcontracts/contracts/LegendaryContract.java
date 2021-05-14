package uk.co.minty_studios.mobcontracts.contracts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.effects.LegendaryEffects;
import uk.co.minty_studios.mobcontracts.events.ContractSummonEvent;
import uk.co.minty_studios.mobcontracts.mobs.MobFeatures;
import uk.co.minty_studios.mobcontracts.utils.ContractType;

import java.util.Random;
import java.util.UUID;

public class LegendaryContract {

    private final MobContracts plugin;
    private final MobFeatures mobFeatures;
    private final MobDataDatabase mobDataDatabase;
    private final LegendaryEffects legendaryEffects;
    private final ContractType contractType;
    private static final Random rnd = new Random();

    public LegendaryContract(MobContracts plugin, MobFeatures mobFeatures, MobDataDatabase mobDataDatabase, LegendaryEffects legendaryEffects, ContractType contractType) {
        this.plugin = plugin;
        this.mobFeatures = mobFeatures;
        this.mobDataDatabase = mobDataDatabase;
        this.legendaryEffects = legendaryEffects;
        this.contractType = contractType;
    }

    public void summonLegendaryContract(Player player) {
        UUID uuid = player.getUniqueId();
        UUID mobUuid = UUID.randomUUID();
        double maxHp = plugin.getConfig().getDouble("settings.legendary.max-health");
        double minHp = plugin.getConfig().getDouble("settings.legendary.min-health");
        double maxDmg = plugin.getConfig().getDouble("settings.legendary.max-damage");
        double minDmg = plugin.getConfig().getDouble("settings.legendary.min-damage");
        double maxSpeed = plugin.getConfig().getDouble("settings.legendary.max-speed");
        double minSpeed = plugin.getConfig().getDouble("settings.legendary.min-speed");
        double health = rnd.nextDouble() * (maxHp - minHp) + minHp;
        double damage = rnd.nextDouble() * (maxDmg - minDmg) + minDmg;
        double armor = rnd.nextDouble() * (20 - 1) + 1;
        double speed = rnd.nextDouble() * (maxSpeed - minSpeed) + minSpeed;
        String name = plugin.getConfig().getStringList("settings.names.name").get(rnd.nextInt(plugin.getConfig().getStringList("settings.names.name").size()));
        String adj = plugin.getConfig().getStringList("settings.names.adjectives").get(rnd.nextInt(plugin.getConfig().getStringList("settings.names.adjectives").size()));
        String fullName = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("settings.legendary.name-format")
                .replace("%name%", name).replace("%adjective%", adj));

        Entity entity = player.getWorld().spawnEntity(player.getLocation(),
                EntityType.valueOf(plugin.getConfig().getStringList("contract-type").get(rnd.nextInt(plugin.getConfig().getStringList("contract-type").size()))));
        LivingEntity spawned = (LivingEntity) entity;

        // stats
        spawned.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armor);
        spawned.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(armor);
        spawned.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        spawned.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        spawned.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        spawned.setHealth(health);
        spawned.addScoreboardTag("LegendaryContract");
        spawned.addScoreboardTag("Contract");
        spawned.setCustomName(fullName);
        spawned.setCustomNameVisible(true);

        if (plugin.getConfig().getBoolean("settings.legendary.allow-weapon"))
            mobFeatures.giveWeapons(spawned);

        if (plugin.getConfig().getBoolean("settings.legendary.allow-targeting"))
            mobFeatures.getTarget((Creature) spawned);

        String effect = "No effect";
        if (plugin.getConfig().getBoolean("settings.legendary.enable-aoe-effects"))
            effect = legendaryEffects.randomLegendaryEffect(spawned);

        contractType.addContract(spawned.getUniqueId(), "Legendary", effect, spawned.getType());
        ContractSummonEvent event = new ContractSummonEvent(spawned, mobUuid, spawned.getCustomName(), damage, speed, health, armor, "Legendary", effect, spawned.getType().name(), player);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
