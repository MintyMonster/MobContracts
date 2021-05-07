package uk.co.minty_studios.mobcontracts.mobeffects;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.Random;

public class LegendaryEffects {

    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private static FileConfiguration config;
    private final CurrentContracts currentContracts;
    private int distance;
    private long repeat;
    private static Random rnd = new Random();

    public LegendaryEffects(MobContracts plugin, GenericUseMethods genericUseMethods, CurrentContracts currentContracts) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.currentContracts = currentContracts;
        this.distance = config.getInt("settings.legendary.distance-from-entity");
        this.repeat = config.getLong("settings.legendary.repeat-check");
        this.genericUseMethods = genericUseMethods;
    }

    public void legendaryHunger(LivingEntity entity){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= repeat)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendaryFire(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.fire")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.fire").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.setFireTicks(200);
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendaryPoison(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.poison")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.poison").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendarySick(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.nausea")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.nausea").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendarySlow(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.slow")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.slow").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendaryWither(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.wither")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.wither").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendarySmite(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.smite")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.smite").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.getWorld().strikeLightning(p.getLocation());
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendaryBlind(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.blind")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.blind").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public void legendaryWeakness(LivingEntity entity){

        genericUseMethods.sendBossMessage(entity, config.getStringList("messages.bossmessage.weakness")
                .get(rnd.nextInt(config.getStringList("messages.bossmessage.weakness").size())));

        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                }
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100, 2));
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }


    public String randomLegendaryEffect(LivingEntity entity){
        int chance = rnd.nextInt(8);
        String effect = "";
        switch(chance){
            case 0:
                legendaryFire(entity);
                effect = "Fire";
                break;
            case 1:
                legendaryPoison(entity);
                effect = "Poison";
                break;
            case 2:
                legendarySick(entity);
                effect = "Nausea";
                break;
            case 3:
                legendarySmite(entity);
                effect = "Smite";
                break;
            case 4:
                legendarySlow(entity);
                effect = "Slow";
                break;
            case 5:
                legendaryWither(entity);
                effect = "Wither";
                break;
            case 6:
                legendaryBlind(entity);
                effect = "Blind";
                break;
            case 7:
                legendaryWeakness(entity);
                effect = "Weakness";
                break;
        }
        if(config.getBoolean("settings.legendary.enable-hunger-two")) legendaryHunger(entity);
        return effect;
    }
}
