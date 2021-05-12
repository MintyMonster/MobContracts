package uk.co.minty_studios.mobcontracts.gui;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.gui.handler.PaginatedGui;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

import java.util.*;
import java.text.SimpleDateFormat;

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

        ArrayList<Map.Entry<UUID, Date>> mobs = new ArrayList<>(database.getTotalMobs().entrySet());

        if(e.getCurrentItem().getType().equals(Material.PAPER)){
            if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Previous page")){
                if(page != 0){
                    page -= 1;
                    super.open();
                }else{
                    new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, database, contractStorageDatabase).open();
                }
            }else if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Next page")){
                if(!((index + 1) >= mobs.size())){
                    page += 1;
                    super.open();
                }
            }
        }else if(e.getCurrentItem().getType().equals(Material.BARRIER)){
            e.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void setItems() {

        addBottomRow();

        ArrayList<Map.Entry<UUID, Date>> sorted = new ArrayList<>(database.getTotalMobs().entrySet());
        sorted.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

        if(sorted == null && sorted.isEmpty())
            return;

        for(int i = 0; i < super.maxItemsPerPage; i++){
            index = super.maxItemsPerPage * page + i;
            if(index >= sorted.size()) break;
            if(sorted.get(index) != null){

                UUID uuid = sorted.get(index).getKey();
                String color = "&7";
                String tier = database.getTier(uuid);

                color = tier.equalsIgnoreCase("Legendary") ? "&6" : tier.equalsIgnoreCase("Epic") ? "&5" : "&7";
                String base = CreateCustomGuiItem.MobType.valueOf(database.getType(uuid)).getBase();
                String type = WordUtils.capitalizeFully(database.getType(uuid).replace("_", " "));
                inventory.addItem(createCustomGuiItem.getCustomSkull(database.getName(uuid)
                                .replace("§", "&")
                                .replace("[", "")
                                .replace("]", ""), base,
                        "&8➟ &aStats",
                        "&7Summoner: &c" + database.getOwner(uuid),
                        "&7Health: &e" + database.getHealth(uuid),
                        "&7Damage: &e" + database.getDamage(uuid),
                        "&7Type: " + color + tier,
                        "&7Entity: &e" + type,
                        "&7Date: " + database.getDate(uuid)));
            }
        }
    }
}
