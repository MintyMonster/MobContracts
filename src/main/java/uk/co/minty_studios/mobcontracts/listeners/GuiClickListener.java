package uk.co.minty_studios.mobcontracts.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import uk.co.minty_studios.mobcontracts.gui.handler.Gui;

public class GuiClickListener implements Listener {

    @EventHandler
    public void onGuiClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        InventoryHolder holder = e.getClickedInventory().getHolder();

        if (holder instanceof Gui) {

            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;

            Gui gui = (Gui) holder;
            gui.handleMenu(e);
        }
    }
}
