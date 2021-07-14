package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
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

public class EntityDeathListener implements Listener {

    private final MobContracts plugin;
    private final ContractType contractType;
    private final CurrentContracts currentContracts;
    private final DatabaseManager databaseManager;
    private final GenericUseMethods genericUseMethods;
    private final LevellingSystem levellingSystem;
    private final MobFeatures mobFeatures;

    public EntityDeathListener(MobContracts plugin,
                               ContractType contractType,
                               CurrentContracts currentContracts,
                               DatabaseManager databaseManager,
                               GenericUseMethods genericUseMethods,
                               LevellingSystem levellingSystem,
                               MobFeatures mobFeatures) {
        this.plugin = plugin;
        this.contractType = contractType;
        this.currentContracts = currentContracts;
        this.databaseManager = databaseManager;
        this.genericUseMethods = genericUseMethods;
        this.levellingSystem = levellingSystem;
        this.mobFeatures = mobFeatures;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if(currentContracts.isContract(event.getEntity())) {
            Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();
            event.getDrops().clear();

            if (!(plugin.getServer().getOfflinePlayer(currentContracts.getPlayerUuid(event.getEntity()))).isOnline())
                return;

            Player player = plugin.getServer().getPlayer(currentContracts.getPlayerUuid(event.getEntity()));
            Entity entity = event.getEntity();
            String tier = contractType.getContractTier(entity.getUniqueId());
            String type = contractType.getEffectType(entity.getUniqueId());

            if (plugin.getConfig().getBoolean("settings.general.announce-contract-kill")) {
                genericUseMethods.sendGlobalMessagePrefix(plugin.getConfig().getString("messages.event.contract-kill")
                        .replace("%player%", player.getName())
                        .replace("%entity%", entity.getCustomName())
                        .replace("%tier%", tier)
                        .replace("%effect%", type));
            }

            levellingSystem.levels(player, tier);

            if (tier.equalsIgnoreCase("common"))
                map.get(player.getUniqueId()).setCommonSlain(map.get(player.getUniqueId()).getCommonSlain() + 1);
            else if (tier.equalsIgnoreCase("epic"))
                map.get(player.getUniqueId()).setEpicSlain(map.get(player.getUniqueId()).getEpicSlain() + 1);
            else if (tier.equalsIgnoreCase("legendary"))
                map.get(player.getUniqueId()).setLegendarySlain(map.get(player.getUniqueId()).getLegendarySlain() + 1);

            if(plugin.getConfig().getBoolean("rewards.items-enabled"))
                mobFeatures.dropItems(entity, tier);

            if (plugin.getConfig().getBoolean("rewards.commands-enabled")) {
                plugin.getConfig().getStringList("rewards.commands." + tier.toLowerCase() + ".commands").forEach(c ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", player.getName())));
            }

            ContractKillEvent contractKillEvent = new ContractKillEvent(type, tier, player, entity);
            plugin.getServer().getPluginManager().callEvent(contractKillEvent);

            contractType.removeContract(entity.getUniqueId());
            currentContracts.removePlayerContract(player);
        }
    }
}
