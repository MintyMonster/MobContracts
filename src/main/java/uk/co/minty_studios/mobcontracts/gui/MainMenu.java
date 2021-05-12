package uk.co.minty_studios.mobcontracts.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.handler.Gui;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

public class MainMenu extends Gui {

    private final CreateCustomGuiItem createCustomGuiItem;
    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final MobDataDatabase mobDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;

    public MainMenu(GuiUtil menuUtil, CreateCustomGuiItem createCustomGuiItem, MobContracts plugin, PlayerDataDatabase playerDataDatabase, MobDataDatabase mobDataDatabase, ContractStorageDatabase contractStorageDatabase) {
        super(menuUtil);
        this.createCustomGuiItem = createCustomGuiItem;
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.mobDataDatabase = mobDataDatabase;
        this.contractStorageDatabase = contractStorageDatabase;
    }

    @Override
    public String getMenuName() {
        return "Main Menu";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch(e.getSlot()){
            case 3:
                new AllContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), mobDataDatabase, createCustomGuiItem, plugin, contractStorageDatabase, playerDataDatabase).open();
                break;
            case 5:
                new ServerStatsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, mobDataDatabase, contractStorageDatabase, createCustomGuiItem).open();
                break;
            case 12:// "&e&lCONTRACTS KILLED":
                new ContractsKilledGui(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, contractStorageDatabase, mobDataDatabase).open();
                break;
            case 13:
                new TotalExperienceGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, createCustomGuiItem, contractStorageDatabase, mobDataDatabase).open();
                break;
            case 14:
                new PlayerLevelGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, createCustomGuiItem, contractStorageDatabase, mobDataDatabase).open();
                break;
            case 21:
                new CommonContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, createCustomGuiItem, contractStorageDatabase, mobDataDatabase).open();
                break;
            case 22:
                new EpicContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, createCustomGuiItem, contractStorageDatabase, mobDataDatabase).open();
                break;
            case 23:
                new LegendaryContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, createCustomGuiItem, contractStorageDatabase, mobDataDatabase).open();
                break;
        }
    }

    @Override
    public void setItems() {
        for(int i = 0; i < getSlots(); i++){
            if(i == 5){
                ItemStack skull = createCustomGuiItem.getCustomSkull("&e&lSERVER STATS &r&8[Click]",
                        "ac9f813ae82cd688574a849ab9ab441346763adb9ce7cf1b1c727de795df9b",
                        "&8➟ &7Server-wide statistics!");

                inventory.setItem(i, skull);
                continue;
            }

            if(i == 3){
                ItemStack skull = createCustomGuiItem.getCustomItem(Material.WITHER_SKELETON_SKULL, "&6&lALL CONTRACTS &8[Click]",
                        "&8➟ &7Every contract!",
                        "&8➟ &a" + mobDataDatabase.getTotalCountContracts() + " &7total contracts!");

                inventory.setItem(i, skull);
                continue;
            }

            if(i == 12){
                ItemStack skull = createCustomGuiItem.getCustomSkull("&e&lCONTRACTS KILLED",
                        "c493130537fc4d358cdb387c9db08088846b8be54f1c11c256a37eb4c638c0",
                        "&8➟ &aLeaderboard &8[Click]",
                        "&7Click to view the leaderboard",
                        "&7for the total &econtracts killed&7!");

                inventory.setItem(i, skull);
                continue;
            }

            if(i == 13){
                ItemStack skull = createCustomGuiItem.getCustomSkull("&f&lTOTAL EXPERIENCE",
                        "d62379ce9ab2b7376bfa18b01655b2d58f95875740f2d4972ccb7e4781141",
                        "&8➟ &aLeaderboard &8[Click]",
                        "&7Click to view the leaderboard",
                        "&7for the total &fPlayer Experience&7!");

                inventory.setItem(i, skull);
                continue;
            }

            if(i == 14){
                ItemStack skull = createCustomGuiItem.getCustomSkull("&6&lPLAYER LEVEL",
                        "e3e45831c1ea817f7477bcaebfa3d2ee3a936ee8ea2b8bde29006b7e9bdf58",
                        "&8➟ &aLeaderboard &8[Click]",
                        "&7Click to view the leaderboard",
                        "&7for all users' &6Player Level&7!");

                inventory.setItem(i, skull);
                continue;
            }

            if(i == 21){
                ItemStack item = createCustomGuiItem.getCustomItem(Material.PAPER, "&7&lCOMMON CONTRACTS",
                        "&8➟ &aLeaderboard &8[Click]",
                        "&7Click to view the top players",
                        "&7who've beat Common contracts!");

                inventory.setItem(i, item);
                continue;
            }

            if(i == 22){
                ItemStack item = createCustomGuiItem.getCustomItem(Material.MAP, "&5&lEPIC CONTRACTS",
                        "&8➟ &aLeaderboard &8[Click]",
                        "&7Click to view the top players",
                        "&7who've beat &5Epic &7contracts!");

                inventory.setItem(i, item);
                continue;
            }

            if(i == 23){
                ItemStack item = createCustomGuiItem.getCustomItem(Material.BLAZE_POWDER, "&6&lLEGENDARY CONTRACTS",
                        "&8➟ &aLeaderboard &8[Click]",
                        "&7Click to view the top players",
                        "&7who've beat &6Legendary &7contracts!");

                inventory.setItem(i, item);
                continue;
            }

            if((i == 0) || (i == 8) || (i == 26) || (i == 18)){
                ItemStack item = createCustomGuiItem.getCustomItem(Material.YELLOW_STAINED_GLASS_PANE, "&8");
                inventory.setItem(i, item);
                continue;
            }

            ItemStack item = createCustomGuiItem.getCustomItem(Material.BLACK_STAINED_GLASS_PANE, "&8");
            inventory.setItem(i, item);
        }
    }
}
