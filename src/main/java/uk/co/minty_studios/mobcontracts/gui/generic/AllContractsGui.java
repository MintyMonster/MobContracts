package uk.co.minty_studios.mobcontracts.gui.generic;

import org.apache.commons.lang3.text.WordUtils;
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
import uk.co.minty_studios.mobcontracts.utils.ContractObject;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

import java.util.*;
import java.util.stream.Collectors;

public class AllContractsGui extends PaginatedGui {

    private final MobDataDatabase database;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final MobContracts plugin;
    private final ContractStorageDatabase contractStorageDatabase;
    private final PlayerDataDatabase playerDataDatabase;
    private int index;

    public AllContractsGui(GuiUtil guiUtil,
                           MobDataDatabase database,
                           CreateCustomGuiItem createCustomGuiItem,
                           MobContracts plugin,
                           ContractStorageDatabase contractStorageDatabase,
                           PlayerDataDatabase playerDataDatabase) {
        super(guiUtil);
        this.database = database;
        this.createCustomGuiItem = createCustomGuiItem;
        this.plugin = plugin;
        this.contractStorageDatabase = contractStorageDatabase;
        this.playerDataDatabase = playerDataDatabase;
    }

    @Override
    public String getMenuName() {
        return "All the contracts!";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        ArrayList<Map.Entry<UUID, ContractObject>> mobs = new ArrayList<>(database.getContractMap().entrySet());

        if (e.getCurrentItem().getType().equals(Material.PAPER)) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Previous page")) {
                if (page != 0) {
                    page -= 1;
                    super.open();
                } else {
                    new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, database, contractStorageDatabase).open();
                }
            } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Next page")) {
                if (!((index + 1) >= mobs.size())) {
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

        ArrayList<Map.Entry<UUID, ContractObject>> sorted = new ArrayList<>(database.getContractMap().entrySet());
        //sorted.sort(Collections.reverseOrder((Comparator) Map.Entry.comparingByValue()));
        sorted.sort(Collections.reverseOrder(Comparator.comparing(d -> d.getValue().getDate())));

        for (int i = 0; i < super.maxItemsPerPage; i++) {
            index = super.maxItemsPerPage * page + i;
            if (index >= sorted.size()) break;
            if (sorted.get(index) != null) {

                UUID mobUuid = sorted.get(index).getKey();
                String tier = sorted.get(index).getValue().getTier();
                String type = sorted.get(index).getValue().getMobType().name();
                String name = sorted.get(index).getValue().getDisplayName();
                String summonerName = sorted.get(index).getValue().getSummonerName();
                int health = sorted.get(index).getValue().getHealth();
                int damage = sorted.get(index).getValue().getDamage();
                Date date = sorted.get(index).getValue().getDate();

                String color = tier.equalsIgnoreCase("Legendary") ? "&6" : tier.equalsIgnoreCase("Epic") ? "&5" : "&7";
                String base = CreateCustomGuiItem.MobType.valueOf(type).getBase();
                String formattedType = WordUtils.capitalizeFully(type.replace("_", " "));

                inventory.addItem(createCustomGuiItem.getCustomSkull(name
                                .replace("§", "&")
                                .replace("[", "")
                                .replace("]", ""),
                                base, plugin.getConfig().getStringList("gui.all-contracts.lore")
                                .stream().map(s -> s.replace("%name%", summonerName)
                                .replace("%health%", String.valueOf(health))
                                .replace("%damage%", String.valueOf(damage))
                                .replace("%color%", color)
                                .replace("%tier%", tier)
                                .replace("%entity_type%", formattedType)
                                .replace("%date%", String.valueOf(date)))
                                .collect(Collectors.toList())));
            }
        }
    }
}
