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

public class ContractsKilledGui extends PaginatedGui {

    private final MobContracts plugin;
    private final DatabaseManager databaseManager;
    private final CreateCustomGuiItem createCustomGuiItem;
    private int index;

    public ContractsKilledGui(GuiUtil guiUtil,
                              DatabaseManager databaseManager, CreateCustomGuiItem createCustomGuiItem,
                              MobContracts plugin) {
        super(guiUtil);
        this.databaseManager = databaseManager;
        this.plugin = plugin;
        this.createCustomGuiItem = createCustomGuiItem;
    }

    @Override
    public String getMenuName() {
        return "Leaderboard: Contracts killed";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        /*switch(e.getCurrentItem().getType()){
            case PLAYER_HEAD:

                GuiUtil util = plugin.getMenuUtil((Player) e.getWhoClicked());
                util.setPlayerForProfile(Bukkit.getPlayer(
                        UUID.fromString(e.getCurrentItem().getItemMeta()
                        .getPersistentDataContainer().get(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING))));

                break;
        }*/
        // Add pages handling
        // Add profile
        /*GuiUtil guiUtil = plugin.getMenuUtil((Player) e.getWhoClicked());
        Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta()
                .getPersistentDataContainer().get(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING));*/
        // Use getter/setter in GuiUtil
        // open profile
        // Player target = super.guiUtil.getPlayerForProfile();

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
        sorted.sort(Collections.reverseOrder(Comparator.comparing(k -> k.getValue().getTotalSlain())));

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= sorted.size()) break;
            if (sorted.get(index) != null) {

                UUID uuid = sorted.get(index).getKey();

                inventory.addItem(createCustomGuiItem.getPlayerHead(uuid,
                        plugin.getConfig().getString("gui.contracts-killed.name-color"),
                        createCustomGuiItem.parsePlayerLore(sorted, index, plugin.getConfig().getStringList("gui.contracts-killed.lore"))));

                // Get UUID from player's head for future profile
                /*ItemMeta meta = head.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING, sorted.get(index).getKey().toString());
                head.setItemMeta(meta);*/
            }
        }
    }
}
