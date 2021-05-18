package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.events.ContractKillEvent;
import uk.co.minty_studios.mobcontracts.utils.ContractType;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;

public class EntityDeathListener implements Listener {

    private final MobContracts plugin;
    private final ContractType contractType;
    private final CurrentContracts currentContracts;

    public EntityDeathListener(MobContracts plugin, ContractType contractType, CurrentContracts currentContracts) {
        this.plugin = plugin;
        this.contractType = contractType;
        this.currentContracts = currentContracts;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity().getKiller() == null)
            return;

        if (currentContracts.isContract(event.getEntity())) {
            event.getDrops().clear();
            ContractKillEvent contractKillEvent = new ContractKillEvent(contractType, event.getEntity().getKiller(), event.getEntity(), event.getDrops());
            plugin.getServer().getPluginManager().callEvent(contractKillEvent);
        }
    }
}
