package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;

public class PlayerLeaveListener implements Listener {

    private final CurrentContracts currentContracts;
    private final MobContracts plugin;

    public PlayerLeaveListener(CurrentContracts currentContracts, MobContracts plugin) {
        this.currentContracts = currentContracts;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("settings.general.kill-contract-on-leave")) {
            if (currentContracts.inContract(event.getPlayer())) {
                currentContracts.removePlayerContract(event.getPlayer());
            }
        }
    }
}