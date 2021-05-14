package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;

public class PlayerJoinListener implements Listener {

    private final PlayerDataDatabase playerDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;

    public PlayerJoinListener(PlayerDataDatabase playerDataDatabase, ContractStorageDatabase contractStorageDatabase) {
        this.playerDataDatabase = playerDataDatabase;
        this.contractStorageDatabase = contractStorageDatabase;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!(playerDataDatabase.uuidExists(event.getPlayer().getUniqueId())))
            playerDataDatabase.addPlayer(event.getPlayer());

        if (!(contractStorageDatabase.uuidExists(event.getPlayer().getUniqueId())))
            contractStorageDatabase.addPlayer(event.getPlayer());

        playerDataDatabase.updatePlayer(event.getPlayer().getUniqueId());
    }
}
