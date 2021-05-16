package uk.co.minty_studios.mobcontracts.gui.generic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.MainMenu;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.gui.handler.PaginatedGui;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfilesGui extends PaginatedGui {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final ContractStorageDatabase contractStorageDatabase;
    private final MobDataDatabase mobDataDatabase;
    private int index;


    public ProfilesGui(GuiUtil guiUtil, MobContracts plugin,
                       PlayerDataDatabase playerDataDatabase,
                       CreateCustomGuiItem createCustomGuiItem,
                       ContractStorageDatabase contractStorageDatabase,
                       MobDataDatabase mobDataDatabase) {
        super(guiUtil);
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.createCustomGuiItem = createCustomGuiItem;
        this.contractStorageDatabase = contractStorageDatabase;
        this.mobDataDatabase = mobDataDatabase;
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

        ArrayList<Map.Entry<UUID, PlayerObject>> sorted = new ArrayList<>(playerDataDatabase.getPlayerMap().entrySet());

        if (e.getCurrentItem().getType().equals(Material.PAPER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Previous page")) {
                if (page != 0) {
                    page -= 1;
                    super.open();
                } else {
                    new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, mobDataDatabase, contractStorageDatabase).open();
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

        ArrayList<Map.Entry<UUID, PlayerObject>> profiles = new ArrayList<>(playerDataDatabase.getPlayerMap().entrySet());

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= profiles.size()) break;
            if (profiles.get(index) != null) {

                UUID uuid = profiles.get(index).getKey();
                int commonSlain = profiles.get(index).getValue().getCommonSlain();
                int epicSlain = profiles.get(index).getValue().getEpicSlain();
                int legendarySlain = profiles.get(index).getValue().getLegendarySlain();
                int level = profiles.get(index).getValue().getCurrentLevel();
                int xp = profiles.get(index).getValue().getCurrentXp();
                int totalXp = profiles.get(index).getValue().getTotalXp();
                int commonOwned = profiles.get(index).getValue().getCommonOwned();
                int epicOwned = profiles.get(index).getValue().getEpicOwned();
                int legendaryOwned = profiles.get(index).getValue().getLegendaryOwned();

                inventory.addItem(createCustomGuiItem.getPlayerHead(uuid,
                        plugin.getConfig().getString("gui.profiles.name-color"),
                        plugin.getConfig().getStringList("gui.profiles.lore")
                        .stream().map(s -> s.replace("%level%", String.valueOf(level))
                        .replace("%xp%", String.valueOf(xp))
                        .replace("%totalxp%", String.valueOf(totalXp))
                        .replace("%common_slain%", String.valueOf(commonSlain))
                        .replace("%epic_slain%", String.valueOf(epicSlain))
                        .replace("%legendary_slain%", String.valueOf(legendarySlain))
                        .replace("%common_owned%", String.valueOf(commonOwned))
                        .replace("%epic_owned%", String.valueOf(epicOwned))
                        .replace("%legendary_owned%", String.valueOf(legendaryOwned)))
                                .collect(Collectors.toList())));
            }
        }
    }
}
