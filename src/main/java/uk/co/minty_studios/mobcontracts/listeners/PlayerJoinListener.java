package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;

public class PlayerJoinListener implements Listener {

    private final PlayerDataDatabase playerDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;
    private final MobContracts plugin;

    public PlayerJoinListener(PlayerDataDatabase playerDataDatabase, ContractStorageDatabase contractStorageDatabase, MobContracts plugin) {
        this.playerDataDatabase = playerDataDatabase;
        this.contractStorageDatabase = contractStorageDatabase;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!(playerDataDatabase.uuidExists(event.getPlayer().getUniqueId())))
            playerDataDatabase.addPlayer(event.getPlayer());

        if (!(contractStorageDatabase.uuidExists(event.getPlayer().getUniqueId())))
            contractStorageDatabase.addPlayer(event.getPlayer());

        new BukkitRunnable(){
            @Override
            public void run(){
                playerDataDatabase.updatePlayer(event.getPlayer().getUniqueId());
            }
        }.runTaskLater(plugin, 40L);
    }
}
