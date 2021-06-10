package uk.co.minty_studios.mobcontracts.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ContractKillEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final String type;
    private final String tier;
    private final Player player;
    private final Entity contract;

    public ContractKillEvent(String type, String tier, Player killer, Entity contract) {
        this.type = type;
        this.contract = contract;
        this.player = killer;
        this.tier = tier;
    }

    public Player getKiller() {
        return player;
    }

    public Entity getEntity() {
        return contract;
    }

    public String getTier() {
        return tier;
    }

    public String getEffect() {
        return type;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
