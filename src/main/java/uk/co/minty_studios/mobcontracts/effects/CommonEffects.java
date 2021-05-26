package uk.co.minty_studios.mobcontracts.effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;

import java.util.Random;

public class CommonEffects {

    private final MobContracts plugin;
    private final int distance;
    private final long repeat;
    private final int amplifier;
    private final int duration;
    private static final Random rnd = new Random();

    public CommonEffects(MobContracts plugin) {
        this.plugin = plugin;
        this.distance = plugin.getConfig().getInt("settings.common.distance-from-entity");
        this.repeat = plugin.getConfig().getLong("settings.common.repeat-check");
        this.amplifier = plugin.getConfig().getInt("settings.common.effect-amplifier");
        this.duration = plugin.getConfig().getInt("settings.common.effect-duration");
    }

    public void commonFire(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.setFireTicks(duration);

            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonPoison(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, amplifier));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonSick(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, amplifier));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonSlow(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, amplifier));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonWither(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, duration, amplifier));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonSmite(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.getWorld().strikeLightning(p.getLocation());
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonBlind(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void commonWeakness(LivingEntity entity) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration, amplifier));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public String randomCommonEffect(LivingEntity entity) {
        int chance = rnd.nextInt(8);
        String effect = "";
        switch (chance) {
            case 0:
                commonFire(entity);
                effect = "Fire";
                break;
            case 1:
                commonPoison(entity);
                effect = "Poison";
                break;
            case 2:
                commonSick(entity);
                effect = "Nausea";
                break;
            case 3:
                commonSmite(entity);
                effect = "Smite";
                break;
            case 4:
                commonSlow(entity);
                effect = "Slow";
                break;
            case 5:
                commonWither(entity);
                effect = "Wither";
                break;
            case 6:
                commonBlind(entity);
                effect = "Blind";
                break;
            case 7:
                commonWeakness(entity);
                effect = "Weakness";
                break;
        }

        return effect;
    }
}
