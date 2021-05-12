package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.events.ContractSummonEvent;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;

public class ContractSummonListener implements Listener {

    private final CurrentContracts currentContracts;
    private final MobDataDatabase mobDataDatabase;

    public ContractSummonListener(CurrentContracts currentContracts, MobDataDatabase mobDataDatabase) {
        this.currentContracts = currentContracts;
        this.mobDataDatabase = mobDataDatabase;
    }

    @EventHandler
    public void onContractSpawn(ContractSummonEvent event) {
        currentContracts.addEntity(event.getPlayer(), event.getEntity());

        mobDataDatabase.addContract(event.getName(),
                event.getPlayer().getUniqueId(),
                event.getUuid(),
                event.getTier(),
                event.getEntity().getType().name(),
                event.getHealth(),
                event.getDamage());
    }
}
