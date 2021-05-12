package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;

public class EntityDamageListener implements Listener {

    private final MobContracts plugin;

    public EntityDamageListener(MobContracts plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getScoreboardTags().contains("Contract")) {

            if (event.getCause() == EntityDamageEvent.DamageCause.CRAMMING)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.cramming"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.drowning"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.falling"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.falling-block"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.fire"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.fire"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.hot-blocks"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.LAVA)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.lava"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.lightning"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.magic"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.POISON)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.poison"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.projectile"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.suffocation"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.WITHER)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.wither"))
                    event.setCancelled(true);

            if (event.getCause() == EntityDamageEvent.DamageCause.THORNS)
                if (plugin.getConfig().getBoolean("settings.general.damageevents.thorns"))
                    event.setCancelled(true);
        }
    }
}
