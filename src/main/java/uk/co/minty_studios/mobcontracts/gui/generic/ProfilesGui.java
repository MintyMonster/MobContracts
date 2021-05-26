package uk.co.minty_studios.mobcontracts.gui.generic;

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

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ProfilesGui extends PaginatedGui {

    private final MobContracts plugin;
    private final DatabaseManager databaseManager;
    private final CreateCustomGuiItem createCustomGuiItem;
    private int index;


    public ProfilesGui(GuiUtil guiUtil, MobContracts plugin,
                       DatabaseManager databaseManager,
                       CreateCustomGuiItem createCustomGuiItem) {
        super(guiUtil);
        this.plugin = plugin;
        this.databaseManager = databaseManager;
        this.createCustomGuiItem = createCustomGuiItem;
    }

    @Override
    public String getMenuName() {
        return "Profiles";
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

        ArrayList<Map.Entry<UUID, PlayerObject>> profiles = new ArrayList<>(databaseManager.getPlayerMap().entrySet());

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= profiles.size()) break;
            if (profiles.get(index) != null) {

                UUID uuid = profiles.get(index).getKey();

                inventory.addItem(createCustomGuiItem.getPlayerHead(uuid,
                        plugin.getConfig().getString("gui.profiles.name-color"),
                        createCustomGuiItem.parsePlayerLore(profiles, index, plugin.getConfig().getStringList("gui.profiles.lore"))));
            }
        }
    }
}
