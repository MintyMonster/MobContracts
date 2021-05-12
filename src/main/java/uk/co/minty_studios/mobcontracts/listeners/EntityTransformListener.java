package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class EntityTransformListener implements Listener {

    @EventHandler
    public void onEntityTransform(EntityTransformEvent event) {

        if (event.getEntity().getScoreboardTags().contains("Contract")) {
            if (event.getTransformReason().equals(EntityTransformEvent.TransformReason.DROWNED))
                event.setCancelled(true);

            if (event.getTransformReason().equals(EntityTransformEvent.TransformReason.CURED))
                event.setCancelled(true);

            if (event.getTransformReason().equals(EntityTransformEvent.TransformReason.SPLIT))
                event.setCancelled(true);

            if (event.getTransformReason().equals(EntityTransformEvent.TransformReason.LIGHTNING))
                event.setCancelled(true);
        }
    }
}
