package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.events.ContractKillEvent;
import uk.co.minty_studios.mobcontracts.levelsystem.LevellingSystem;
import uk.co.minty_studios.mobcontracts.utils.ContractType;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

public class ContractKillListener implements Listener {

    private final GenericUseMethods genericUseMethods;
    private final LevellingSystem levellingSystem;
    private final MobContracts plugin;
    private final CurrentContracts currentContracts;
    private final ContractType contractType;
    private final PlayerDataDatabase database;

    public ContractKillListener(MobContracts plugin, GenericUseMethods genericUseMethods, LevellingSystem levellingSystem, CurrentContracts currentContracts, ContractType contractType, PlayerDataDatabase database) {
        this.genericUseMethods = genericUseMethods;
        this.levellingSystem = levellingSystem;
        this.plugin = plugin;
        this.currentContracts = currentContracts;
        this.contractType = contractType;
        this.database = database;
    }

    @EventHandler
    public void onContractKill(ContractKillEvent event){
        if(plugin.getConfig().getBoolean("settings.general.announce-contract-kill")){
            genericUseMethods.sendGlobalMessagePrefix(plugin.getConfig().getString("messages.event.contract-kill")
                    .replace("%player%", event.getKiller().getName())
                    .replace("%entity%", event.getEntity().getCustomName())
                    .replace("%tier%", event.getTier())
                    .replace("%effect%", event.getEffect()));
        }

        levellingSystem.levels(event.getKiller(), event.getTier());

        if(event.getTier().equalsIgnoreCase("Common"))
            database.addCommon(event.getKiller().getUniqueId());
        else if(event.getTier().equalsIgnoreCase("Epic"))
            database.addEpic(event.getKiller().getUniqueId());
        else if(event.getTier().equalsIgnoreCase("Legendary"))
            database.addLegendary(event.getKiller().getUniqueId());


        contractType.removeContract(event.getEntity().getUniqueId());
        currentContracts.removePlayerContract(event.getKiller());
        // rewards
    }
}