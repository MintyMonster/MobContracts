package uk.co.minty_studios.mobcontracts.mobeffects;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;

import java.util.Random;

public class EpicEffects {

    private final MobContracts plugin;
    private final CurrentContracts currentContracts;
    private int distance;
    private long repeat;
    private static Random rnd = new Random();

    public EpicEffects(MobContracts plugin, CurrentContracts currentContracts) {
        this.plugin = plugin;
        this.distance = plugin.getConfig().getInt("settings.epic.distance-from-entity");
        this.repeat = plugin.getConfig().getLong("settings.epic.repeat-check");
        this.currentContracts = currentContracts;
    }

    private void epicFire(LivingEntity entity){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                };
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.setFireTicks(100);
                });
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    private void epicPoison(LivingEntity entity){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                };
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
                });
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    private void epicSick(LivingEntity entity){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                };
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
                });
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    private void epicSmite(LivingEntity entity){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                };
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.getWorld().strikeLightning(p.getLocation());
                });
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    private void epicSlow(LivingEntity entity){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                };
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
                });
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    private void epicWither(LivingEntity entity){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!(currentContracts.isContract(entity))){
                    cancel();
                    return;
                };
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if(p.getLocation().distance(entity.getLocation()) <= distance)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                });
            }
        }.runTaskTimer(plugin, 0L, repeat);
    }

    public String randomEpicEffect(LivingEntity entity){
        int chance = rnd.nextInt(6);
        String effect = "";
        switch(chance){
            case 0:
                epicFire(entity);
                effect = "Fire";
                break;
            case 1:
                epicPoison(entity);
                effect = "Poison";
                break;
            case 2:
                epicSick(entity);
                effect = "Nausea";
                break;
            case 3:
                epicSmite(entity);
                effect = "Smite";
                break;
            case 4:
                epicSlow(entity);
                effect = "Slow";
                break;
            case 5:
                epicWither(entity);
                effect = "Wither";
                break;
        }

        return effect;
    }
}
