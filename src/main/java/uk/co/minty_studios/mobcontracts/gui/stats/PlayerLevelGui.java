package uk.co.minty_studios.mobcontracts.gui.stats;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.gui.MainMenu;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.gui.handler.PaginatedGui;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.*;

public class PlayerLevelGui extends PaginatedGui {

    private final MobContracts plugin;
    private final DatabaseManager databaseManager;
    private final CreateCustomGuiItem createCustomGuiItem;
    private int index;

    public PlayerLevelGui(GuiUtil guiUtil,
                          MobContracts plugin,
                          DatabaseManager databaseManager,
                          CreateCustomGuiItem createCustomGuiItem) {
        super(guiUtil);
        this.plugin = plugin;
        this.databaseManager = databaseManager;
        this.createCustomGuiItem = createCustomGuiItem;
    }

    @Override
    public String getMenuName() {
        return "Leaderboard: Player levels";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        ArrayList<Map.Entry<UUID, PlayerObject>> sorted = new ArrayList<>(databaseManager.getPlayerMap().entrySet());

        if (e.getCurrentItem().getType().equals(Material.PAPER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Previous page")) {
                if (page != 0) {
                    page -= 1;
                    super.open();
                } else {
                    new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, databaseManager).open();
                }
            } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Next page")) {
                if (!((index + 1) >= sorted.size())) {
                    page += 1;
                    super.open();
                }
            }
        } else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            e.getWhoClicked().closeInventory();
        }

    }

    @Override
    public void setItems() {

        addBottomRow();

        ArrayList<Map.Entry<UUID, PlayerObject>> sorted = new ArrayList<>(databaseManager.getPlayerMap().entrySet());
        sorted.sort(Collections.reverseOrder(Comparator.comparing(l -> l.getValue().getCurrentLevel())));

        if (sorted == null && sorted.isEmpty())
            return;

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= sorted.size()) break;
            if (sorted.get(index) != null) {

                UUID uuid = sorted.get(index).getKey();

                inventory.addItem(createCustomGuiItem.getPlayerHead(uuid,
                        plugin.getConfig().getString("gui.player-level.name-color"),
                        createCustomGuiItem.parsePlayerLore(sorted, index, plugin.getConfig().getStringList("gui.player-level.lore"))));
            }
        }
    }
}
