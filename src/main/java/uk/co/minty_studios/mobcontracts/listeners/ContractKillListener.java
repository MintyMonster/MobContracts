package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.events.ContractKillEvent;
import uk.co.minty_studios.mobcontracts.level.LevellingSystem;
import uk.co.minty_studios.mobcontracts.mobs.MobFeatures;
import uk.co.minty_studios.mobcontracts.utils.ContractType;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.Map;
import java.util.UUID;

public class ContractKillListener implements Listener {

    private final GenericUseMethods genericUseMethods;
    private final LevellingSystem levellingSystem;
    private final MobContracts plugin;
    private final CurrentContracts currentContracts;
    private final ContractType contractType;
    private final MobFeatures mobFeatures;
    private final DatabaseManager databaseManager;

    public ContractKillListener(MobContracts plugin,
                                GenericUseMethods genericUseMethods,
                                LevellingSystem levellingSystem,
                                CurrentContracts currentContracts,
                                ContractType contractType,
                                MobFeatures mobFeatures, DatabaseManager databaseManager) {
        this.genericUseMethods = genericUseMethods;
        this.levellingSystem = levellingSystem;
        this.plugin = plugin;
        this.currentContracts = currentContracts;
        this.contractType = contractType;
        this.mobFeatures = mobFeatures;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onContractKill(ContractKillEvent event) {
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();
        if (plugin.getConfig().getBoolean("settings.general.announce-contract-kill")) {
            genericUseMethods.sendGlobalMessagePrefix(plugin.getConfig().getString("messages.event.contract-kill")
                    .replace("%player%", event.getKiller().getName())
                    .replace("%entity%", event.getEntity().getCustomName())
                    .replace("%tier%", event.getTier())
                    .replace("%effect%", event.getEffect()));
        }

        levellingSystem.levels(event.getKiller(), event.getTier());

        if(event.getTier().equalsIgnoreCase("common")){
            map.get(event.getKiller().getUniqueId()).setCommonSlain(
                    map.get(event.getKiller().getUniqueId()).getCommonSlain() + 1
            );
        } else if(event.getTier().equalsIgnoreCase("epic")){
            map.get(event.getKiller().getUniqueId()).setEpicSlain(
                    map.get(event.getKiller().getUniqueId()).getEpicSlain() + 1
            );
        } else if(event.getTier().equalsIgnoreCase("legendary")){
            map.get(event.getKiller().getUniqueId()).setLegendarySlain(
                    map.get(event.getKiller().getUniqueId()).getLegendarySlain() + 1
            );
        }

        // rewards
        mobFeatures.dropItems(event.getEntity(), event.getTier());
        if (plugin.getConfig().getBoolean("rewards.commands-enabled")) {
            plugin.getConfig().getStringList("rewards.commands." + event.getTier().toLowerCase() + ".commands").forEach(c ->
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", event.getKiller().getName())));
        }

        // remove after everything ya know.
        contractType.removeContract(event.getEntity().getUniqueId());
        currentContracts.removePlayerContract(event.getKiller());
    }
}