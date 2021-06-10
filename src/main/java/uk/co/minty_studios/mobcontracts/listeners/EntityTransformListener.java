package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class EntityTransformListener implements Listener {

    @EventHandler
    public void onEntityTransform(EntityTransformEvent event) {

        if (event.getEntity().getScoreboardTags().contains("Contract")) {
            event.setCancelled(true);
        }
    }
}
