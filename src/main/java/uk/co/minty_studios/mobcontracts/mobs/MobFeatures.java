package uk.co.minty_studios.mobcontracts.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.events.ContractTargetEvent;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobFeatures {

    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private static Random rnd = new Random();

    public MobFeatures(MobContracts plugin, GenericUseMethods genericUseMethods) {
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;

    }

    public void giveWeapons(LivingEntity entity){
        int chance = rnd.nextInt() * (5 - 1) + 1;
        int damage = rnd.nextInt() * (5 - 1) + 1;
        int effect = rnd.nextInt() * (3 - 1) + 1;
        EntityType type = entity.getType();
        if(chance == 1){
            if(type.equals(EntityType.SKELETON)){
                entity.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
                ItemStack main = entity.getEquipment().getItemInMainHand();
                main.addEnchantment(Enchantment.ARROW_DAMAGE, damage);
                main.addEnchantment(Enchantment.ARROW_KNOCKBACK, effect);
                main.addEnchantment(Enchantment.ARROW_FIRE, 2);
            }else if(type.equals(EntityType.WITHER_SKELETON)
                    || type.equals(EntityType.ZOMBIE_VILLAGER)
                    || type.equals(EntityType.ZOMBIE)){
                entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                ItemStack main = entity.getEquipment().getItemInMainHand();
                main.addEnchantment(Enchantment.DAMAGE_ALL, damage);
                main.addEnchantment(Enchantment.KNOCKBACK, effect);
                main.addEnchantment(Enchantment.FIRE_ASPECT, 2);
            }else if(type.equals(EntityType.PILLAGER)){
                entity.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
                entity.getEquipment().getItemInMainHand().addEnchantment(Enchantment.DAMAGE_ALL, damage);
            }
        }
    }

    public void getTarget(Creature entity){

        int maxDistance = plugin.getConfig().getInt("settings.targeting.max-distance-from-target");
        int minDistance = plugin.getConfig().getInt("settings.targeting.min-distance-from-target");
        boolean allowTeleport = plugin.getConfig().getBoolean("settings.targeting.allow-teleport");
        boolean allowMessage = plugin.getConfig().getBoolean("settings.targeting.allow-target-message");

        new BukkitRunnable(){
            @Override
            public void run(){
                if(entity.isDead()) cancel();
                List<Player> plist = new ArrayList<>();
                for(Player p : Bukkit.getOnlinePlayers())
                    if(p.getLocation().distance(entity.getLocation()) <= maxDistance)
                        plist.add(p);

                Player player = plist.get(rnd.nextInt(plist.size()));

                ContractTargetEvent contractTargetEvent = new ContractTargetEvent(entity, player, player.getLocation(), entity.getLocation());
                Bukkit.getServer().getPluginManager().callEvent(contractTargetEvent);


                entity.setTarget(player);
                if((allowTeleport) && (player.getLocation().distance(entity.getLocation()) >= minDistance))
                    entity.teleport(player);

                if(allowMessage)
                    genericUseMethods.sendTargetMessage(entity, plugin.getConfig().getString("messages.target-message")
                            .replace("%entity%", entity.getCustomName())
                            .replace("%player%", player.getName()));


            }
        }.runTaskTimer(plugin, 0L, plugin.getConfig().getInt("settings.targeting.target-delay"));
    }
}