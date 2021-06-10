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

            switch(event.getCause()){
                case CRAMMING:
                    if(plugin.getConfig().getBoolean("settings.general.damageevents.cramming"))
                        event.setCancelled(true);
                    break;
                case DROWNING:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.drowning"))
                        event.setCancelled(true);
                    break;
                case FALL:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.falling"))
                        event.setCancelled(true);
                    break;
                case FALLING_BLOCK:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.falling-block"))
                        event.setCancelled(true);
                    break;
                case FIRE:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.fire"))
                        event.setCancelled(true);
                    break;
                case FIRE_TICK:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.fire"))
                        event.setCancelled(true);
                    break;
                case HOT_FLOOR:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.hot-blocks"))
                        event.setCancelled(true);
                    break;
                case LAVA:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.lava"))
                        event.setCancelled(true);
                    break;
                case LIGHTNING:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.lightning"))
                        event.setCancelled(true);
                    break;
                case MAGIC:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.magic"))
                        event.setCancelled(true);
                    break;
                case POISON:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.poison"))
                        event.setCancelled(true);
                    break;
                case PROJECTILE:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.projectile"))
                        event.setCancelled(true);
                    break;
                case SUFFOCATION:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.suffocation"))
                        event.setCancelled(true);
                    break;
                case WITHER:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.wither"))
                        event.setCancelled(true);
                    break;
                case THORNS:
                    if (plugin.getConfig().getBoolean("settings.general.damageevents.thorns"))
                        event.setCancelled(true);
                    break;
                case ENTITY_EXPLOSION:
                    if(plugin.getConfig().getBoolean("settings.general.damageevents.explosions"))
                        event.setCancelled(true);
                    break;
                case BLOCK_EXPLOSION:
                    if(plugin.getConfig().getBoolean("settings.general.damageevents.explosions"))
                        event.setCancelled(true);
                    break;
            }
        }
    }
}
