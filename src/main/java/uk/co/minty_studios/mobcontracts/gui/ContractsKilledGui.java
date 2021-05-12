package uk.co.minty_studios.mobcontracts.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.gui.handler.PaginatedGui;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

import java.util.*;

public class ContractsKilledGui extends PaginatedGui {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final ContractStorageDatabase contractStorageDatabase;
    private final MobDataDatabase mobDataDatabase;
    private int index;

    public ContractsKilledGui(GuiUtil guiUtil,
                              CreateCustomGuiItem createCustomGuiItem,
                              MobContracts plugin,
                              PlayerDataDatabase playerDataDatabase,
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

        ArrayList<Map.Entry<UUID, Integer>> sorted = new ArrayList<>(playerDataDatabase.getTotalSlainMap().entrySet());

        if(e.getCurrentItem().getType().equals(Material.PAPER)){
            if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Previous page")){
                if(page != 0){
                    page -= 1;
                    super.open();
                }else{
                    new MainMenu(plugin.getMenuUtil((Player)e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, mobDataDatabase, contractStorageDatabase).open();
                }
            }else if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Next page")){
                if(!((index + 1) >= sorted.size())){
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

        ArrayList<Map.Entry<UUID, Integer>> sorted = new ArrayList<>(playerDataDatabase.getTotalSlainMap().entrySet());
        sorted.sort(Collections.reverseOrder(Comparator.comparingInt(Map.Entry::getValue)));

        if(sorted == null && sorted.isEmpty())
            return;

        for(int i = 0; i < super.maxItemsPerPage; i++){
            index = super.maxItemsPerPage * page + i;
            if(index >= sorted.size()) break;
            if(sorted.get(index) != null){

                ItemStack head = null;

                // Turn into one method
                head = createCustomGuiItem.getPlayerHead(sorted.get(index).getKey(),
                        "&8➟ &aSlain",
                        "&7Total: &6" + playerDataDatabase.getTotalSlain(sorted.get(index).getKey()) + " &7 slain",
                        "",
                        "&8➟ &aStats",
                        "&7Level: &e" + playerDataDatabase.getPlayerLevel(sorted.get(index).getKey()),
                        "&7Experience: &e" + playerDataDatabase.getPlayerXp(sorted.get(index).getKey()) + "&7xp");


                // Get UUID from player's head for future profile
                /*ItemMeta meta = head.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "uuid"), PersistentDataType.STRING, sorted.get(index).getKey().toString());
                head.setItemMeta(meta);*/

                inventory.addItem(head);
            }
        }
    }
}