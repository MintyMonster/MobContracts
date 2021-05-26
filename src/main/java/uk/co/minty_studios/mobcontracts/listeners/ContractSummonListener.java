package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.events.ContractSummonEvent;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;

public class ContractSummonListener implements Listener {

    private final CurrentContracts currentContracts;
    private final DatabaseManager databaseManager;

    public ContractSummonListener(CurrentContracts currentContracts, DatabaseManager databaseManager) {
        this.currentContracts = currentContracts;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onContractSpawn(ContractSummonEvent event) {
        currentContracts.addEntity(event.getPlayer(), event.getEntity());

        databaseManager.addToContractsMap(event.getPlayer().getUniqueId(), event.getPlayer().getName(), event.getUuid(), event.getName(),
                event.getTier(), event.getEntity().getType(), (int) Math.round(event.getHealth()), (int) Math.round(event.getDamage()));

    }
}
