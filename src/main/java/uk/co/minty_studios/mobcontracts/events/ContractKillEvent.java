package uk.co.minty_studios.mobcontracts.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.co.minty_studios.mobcontracts.utils.ContractType;

public class ContractKillEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final ContractType contractType;
    private final Player player;
    private final Entity contract;

    public ContractKillEvent(ContractType contractType, Player killer, Entity contract) {
        this.contractType = contractType;
        this.contract = contract;
        this.player = killer;
    }

    public Player getKiller() {
        return player;
    }

    public Entity getEntity() {
        return contract;
    }

    public String getTier() {
        return contractType.getContractTier(contract.getUniqueId());
    }

    public String getEffect() {
        return contractType.getEffectType(contract.getUniqueId());
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
