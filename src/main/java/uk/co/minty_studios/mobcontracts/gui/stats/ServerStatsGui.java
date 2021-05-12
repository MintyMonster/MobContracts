package uk.co.minty_studios.mobcontracts.gui.stats;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.MainMenu;
import uk.co.minty_studios.mobcontracts.gui.handler.Gui;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

public class ServerStatsGui extends Gui {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final MobDataDatabase mobDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;
    private final CreateCustomGuiItem createCustomGuiItem;

    public ServerStatsGui(GuiUtil menuUtil,
                          MobContracts plugin,
                          PlayerDataDatabase playerDataDatabase,
                          MobDataDatabase mobDataDatabase,
                          ContractStorageDatabase contractStorageDatabase,
                          CreateCustomGuiItem createCustomGuiItem) {
        super(menuUtil);
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.mobDataDatabase = mobDataDatabase;
        this.contractStorageDatabase = contractStorageDatabase;
        this.createCustomGuiItem = createCustomGuiItem;
    }

    @Override
    public String getMenuName() {
        return "Server-wide statistics";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if(e.getCurrentItem().getType().equals(Material.ARROW))
            new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, mobDataDatabase, contractStorageDatabase).open();

    }

    @Override //&8➟
    public void setItems() {
        for(int i = 0; i < getSlots(); i++){
            if(i == 4){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&e&lSERVER STATS", "c9c8881e42915a9d29bb61a16fb26d059913204d265df5b439b3d792acd56",
                        "&8➟ &6" + playerDataDatabase.getTotalCountPlayers() + " &7Players",
                        "&8➟ &a" + mobDataDatabase.getTotalCountContracts() + " &7Contracts"));
                continue;
            }

            if(i == 12){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&7&lCOMMON KILLED", "149e48c0df7995e91db5bd3c930e5bcc0abcfaf31273732aeabf33c5d86491",
                        "&8➟ &a" + playerDataDatabase.getTotalStat("COMMON") + " &7Killed"));
                continue;
            }

            if(i == 13){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&5&lEPIC KILLED", "95372c5f441c36d96a33a0c3cba514568049d811742b7a90c01ea1c1bc39",
                        "&8➟ &a" + playerDataDatabase.getTotalStat("EPIC") + " &7Killed"));
                continue;
            }

            if(i == 14){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&6&lLEGENDARY KILLED", "597463d7181e83a143a6ced1a1f77f66d1f28e6f272fe8cd95e7fb89ea0dc69",
                        "&8➟ &a" + playerDataDatabase.getTotalStat("LEGENDARY") + " &7Killed"));
                continue;
            }

            if(i == 21){
                inventory.setItem(i, createCustomGuiItem.getCustomItem(Material.PAPER, "&7&lCOMMON STORED",
                        "&8➟ &a" + contractStorageDatabase.getTotalStat("COMMON") + " &7Contracts stored"));
                continue;
            }

            if(i == 22){
                inventory.setItem(i, createCustomGuiItem.getCustomItem(Material.MAP, "&5&lEPIC STORED",
                        "&8➟ &a" + contractStorageDatabase.getTotalStat("EPIC") + " &7Contracts stored"));
                continue;
            }

            if(i == 23){
                inventory.setItem(i, createCustomGuiItem.getCustomItem(Material.BLAZE_POWDER, "&6&lLEGENDARY STORED",
                        "&8➟ &a" + contractStorageDatabase.getTotalStat("LEGENDARY") + " &7Contracts stored"));
                continue;
            }

            if(i == 30){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&6&lPLAYER LEVELS", "6ccbf9883dd359fdf2385c90a459d737765382ec4117b04895ac4dc4b60fc",
                        "&8➟ &a" + playerDataDatabase.getTotalStat("LEVEL") + " &7Total levels"));
                continue;
            }

            if(i == 31){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&f&lTOTAL EXPERIENCE", "f2fc23866523caaa8a9534566127a6f8389af3e76b8e3c33c2473cba6889c4",
                        "&8➟ &a" + playerDataDatabase.getTotalStat("TOTALXP") + " &7Total experience"));
                continue;
            }

            if(i == 32){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&9&lTOTAL DAMAGE", "d6cc6b83763a67fcada1ea184c2d1752ad240746c6be258a73983d8b657f4bb5",
                        "&8➟ &a" + mobDataDatabase.getTotalStat("DAMAGE") + " &7Total damage",
                        "&8➟ &7from all contracts"));
                continue;
            }

            if(i == 39){
                inventory.setItem(i, createCustomGuiItem.getCustomItem(Material.ARROW, "&7Back", "&8➟ [Click for Main Menu]"));
                continue;
            }

            if(i == 41){
                inventory.setItem(i, createCustomGuiItem.getCustomSkull("&c&lTOTAL HEALTH", "b837f3db13a40d4979de77179e18af6e0bc3cc39ea6aba518bb080a6f01a40",
                        "&8➟ &a" + mobDataDatabase.getTotalStat("HEALTH") + " &7Total health",
                        "&8➟ &7from all contracts"));
                continue;
            }

            if((i == 0) || (i == 8) || (i == 36) || (i == 44)){
                inventory.setItem(i, createCustomGuiItem.getCustomItem(Material.YELLOW_STAINED_GLASS_PANE, "&7"));
                continue;
            }

            inventory.setItem(i, createCustomGuiItem.getCustomItem(Material.BLACK_STAINED_GLASS_PANE, "&8"));
        }
    }
}
