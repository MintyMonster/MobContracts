package uk.co.minty_studios.mobcontracts.mobs;

import org.apache.commons.text.WordUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.events.ContractTargetEvent;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MobFeatures {

    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private static final Random rnd = new Random();
    private final FileConfiguration config;

    public MobFeatures(MobContracts plugin, GenericUseMethods genericUseMethods) {
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.config = plugin.getConfig();
    }

    public void giveWeapons(LivingEntity entity) {
        EntityType type = entity.getType();

        int chance = rnd.nextInt(2);
        int c = rnd.nextInt(2);

        if (chance == 1) {
            switch (type) {
                case ZOMBIE:
                    entity.getEquipment().setItemInMainHand(giveSword());
                    break;
                case HUSK:
                    entity.getEquipment().setItemInMainHand(giveSword());
                    break;
                case DROWNED:
                    entity.getEquipment().setItemInMainHand(giveSword());
                    break;
                case ZOMBIE_VILLAGER:
                    entity.getEquipment().setItemInMainHand(giveSword());
                    break;
                case ZOMBIFIED_PIGLIN:
                    entity.getEquipment().setItemInMainHand(giveSword());
                    break;
                case PIGLIN:
                    if (c == 0) {
                        entity.getEquipment().setItemInMainHand(giveSword());
                    } else {
                        entity.getEquipment().setItemInMainHand(giveCrossbow());
                    }
                    break;
                case VINDICATOR:
                    entity.getEquipment().setItemInMainHand(giveSword());
                    break;
                case SKELETON:
                    if (c == 0) {
                        entity.getEquipment().setItemInMainHand(giveSword());
                    } else {
                        entity.getEquipment().setItemInMainHand(giveBow());
                    }
                    break;
                case WITHER_SKELETON:
                    if (c == 0) {
                        entity.getEquipment().setItemInMainHand(giveSword());
                    } else {
                        entity.getEquipment().setItemInMainHand(giveBow());
                    }
                    break;
                case STRAY:
                    if (c == 0) {
                        entity.getEquipment().setItemInMainHand(giveSword());
                    } else {
                        entity.getEquipment().setItemInMainHand(giveBow());
                    }
                    break;
                case PILLAGER:
                    if (c == 0) {
                        entity.getEquipment().setItemInMainHand(giveSword());
                    } else {
                        entity.getEquipment().setItemInMainHand(giveCrossbow());
                    }
                    break;
            }
        }
    }

    public ItemStack giveBow() {
        ItemStack bow = new ItemStack(Material.BOW, 1);

        if (config.isSet("settings.mob-weapons.bows.enchantments")) {
            for (String key : config.getConfigurationSection("settings.mob-weapons.bows.enchantments").getKeys(false)) {
                int level = config.isSet("settings.mob-weapons.bows.enchantments." + key + ".level")
                        ? config.getInt("settings.mob-weapons.bows.enchantments." + key + ".level")
                        : 1;
                String enchant = config.getString("settings.mob-weapons.bows.enchantments." + key + ".enchantment-name");

                bow.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), level);
            }
        }

        return bow;
    }

    public ItemStack giveSword() {

        Material material = config.getString("settings.mob-weapons.swords.type") == "diamond" ? Material.DIAMOND_SWORD
                : config.getString("settings.mob-weapons.swords.type") == "netherite" ? Material.NETHERITE_SWORD
                : config.getString("settings.mob-weapons.swords.type") == "wooden" || config.getString("settings.mob-weapons.swords.type") == "wood" ? Material.WOODEN_SWORD
                : config.getString("settings.mob-weapons.swords.type") == "golden" || config.getString("settings.mob-weapons.swords.type") == "gold" ? Material.GOLDEN_SWORD
                : config.getString("settings.mob-weapons.swords.type") == "iron" ? Material.IRON_SWORD
                : config.getString("settings.mob-weapons.swords.type") == "stone" ? Material.STONE_SWORD
                : Material.DIAMOND_SWORD;

        ItemStack sword = new ItemStack(material);

        if (config.isSet("settings.mob-weapons.swords.enchantments")) {
            for (String key : config.getConfigurationSection("settings.mob-weapons.swords.enchantments").getKeys(false)) {
                int level = config.isSet("settings.mob-weapons.swords.enchantments." + key + ".level")
                        ? config.getInt("settings.mob-weapons.swords.enchantments." + key + ".level")
                        : 1;

                String enchant = config.getString("settings.mob-weapons.swords.enchantments." + key + ".enchantment-name");

                sword.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), level);
            }
        }

        return sword;
    }

    public ItemStack giveCrossbow() {
        ItemStack crossbow = new ItemStack(Material.CROSSBOW, 1);

        if (config.isSet("settings.mob-weapons.crossbow.enchantments")) {
            for (String key : config.getConfigurationSection("settings.mob-weapons.crossbow.enchantments").getKeys(false)) {
                int level = config.isSet("settings.mob-weapons.crossbow.enchantments." + key + ".level")
                        ? config.getInt("settings.mob-weapons.crossbow.enchantments." + key + ".level")
                        : 1;
                String enchant = config.getString("settings.mob-weapons.crossbow.enchantments." + key + ".enchantment-name");

                crossbow.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchant)), level);
            }
        }

        return crossbow;
    }

    // revamp this
    public void getTarget(Creature entity) {

        int maxDistance = config.getInt("settings.targeting.max-distance-from-target");
        int minDistance = config.getInt("settings.targeting.min-distance-from-target");
        boolean allowTeleport = config.getBoolean("settings.targeting.allow-teleport");
        boolean allowMessage = config.getBoolean("settings.targeting.allow-target-message");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()){
                    cancel();
                    return;
                }
                List<Player> plist = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= maxDistance)
                        plist.add(p);

                Player player = plist.get(rnd.nextInt(plist.size()));

                ContractTargetEvent contractTargetEvent = new ContractTargetEvent(entity, player, player.getLocation(), entity.getLocation());
                Bukkit.getServer().getPluginManager().callEvent(contractTargetEvent);


                entity.setTarget(player);
                if ((allowTeleport) && (player.getLocation().distance(entity.getLocation()) >= minDistance))
                    entity.teleport(player);

                if (allowMessage)
                    genericUseMethods.sendTargetMessage(entity, config.getString("messages.target-message")
                            .replace("%entity%", entity.getCustomName())
                            .replace("%player%", player.getName()));


            }
        }.runTaskTimer(plugin, 0L, config.getInt("settings.targeting.target-delay"));
    }

    public void dropItems(Entity e, String str) {
        World world = e.getLocation().getWorld();
        String tier = str.toLowerCase();

        if (!(config.getBoolean("rewards.items-enabled")))
            return;

        if (config.getConfigurationSection("rewards.items." + tier) == null
                || !(config.isSet("rewards.items." + tier)))
            return;

        for (String k : config.getConfigurationSection("rewards.items." + tier).getKeys(false)) {

            String key = k.toLowerCase();
            int amount = config.isSet("rewards.items." + tier + "." + key + ".amount")
                    ? config.getInt("rewards.items." + tier + "." + key + ".amount")
                    : 1;

            ItemStack item = new ItemStack(Material.valueOf(config.getString("rewards.items." + tier + "." + key + ".material")), amount);
            ItemMeta meta = item.getItemMeta();

            String name = config.isSet("rewards.items." + tier + "." + key + ".name") || !(config.getString("rewards.items." + tier + "." + key + ".name") == null)
                    ? config.getString("rewards.items." + tier + "." + key + ".name")
                    : WordUtils.capitalizeFully(item.getType().name());

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

            if (config.isSet("rewards.items." + tier + "." + key + ".lore"))
                meta.setLore(Arrays.stream(config.getStringList("rewards.items." + tier + "." + key + ".lore").stream().toArray(String[]::new))
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));

            if (config.isSet("rewards.items." + tier + "." + key + ".unbreakable"))
                meta.setUnbreakable(config.getBoolean("rewards.items." + tier + "." + key + ".unbreakable"));

            if (config.isSet("rewards.items." + tier + "." + key + ".show-unbreakable"))
                if (!(config.getBoolean("rewards.items." + tier + "." + key + ".show-unbreakable")))
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            if (config.isSet("rewards.items." + tier + "." + key + ".show-enchantments"))
                if (!(config.getBoolean("rewards.items." + tier + "." + key + ".show-enchantments")))
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            if (config.isSet("rewards.items." + tier + "." + key + ".show-attributes"))
                if (!(config.getBoolean("rewards.items." + tier + "." + key + ".show-attributes")))
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


            item.setItemMeta(meta);

            if (config.isSet("rewards.items." + tier + "." + key + ".enchantments")) {
                for (String enchantKey : config.getConfigurationSection("rewards.items." + tier + "." + key + ".enchantments").getKeys(false)) {
                    String enchantName = config.getString("rewards.items." + tier + "." + key + ".enchantments." + enchantKey + ".enchantment-name");

                    int level = config.isSet("rewards.items." + tier + "." + key + ".enchantments." + enchantKey + ".level")
                            ? config.getInt("rewards.items." + tier + "." + key + ".enchantments." + enchantKey + ".level")
                            : 1;

                    item.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(enchantName)), level);
                }
            }

            world.dropItemNaturally(e.getLocation(), item);
        }
    }
}