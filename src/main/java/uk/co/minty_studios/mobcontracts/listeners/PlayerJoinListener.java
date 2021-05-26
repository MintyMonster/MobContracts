package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;

public class PlayerJoinListener implements Listener {

    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public PlayerJoinListener(MobContracts plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        databaseManager.newPlayer(event.getPlayer());
    }
}
