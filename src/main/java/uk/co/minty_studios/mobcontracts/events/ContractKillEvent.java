package uk.co.minty_studios.mobcontracts.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import uk.co.minty_studios.mobcontracts.utils.ContractType;

import java.util.List;

public class ContractKillEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final ContractType contractType;
    private final Player player;
    private final Entity contract;
    private final List<ItemStack> drops;

    public ContractKillEvent(ContractType contractType, Player killer, Entity contract, List<ItemStack> drops) {
        this.contractType = contractType;
        this.contract = contract;
        this.player = killer;
        this.drops = drops;
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

    public List<ItemStack> getDrops() {
        return drops;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
