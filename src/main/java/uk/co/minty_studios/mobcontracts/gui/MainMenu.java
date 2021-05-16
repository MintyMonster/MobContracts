package uk.co.minty_studios.mobcontracts.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.generic.AllContractsGui;
import uk.co.minty_studios.mobcontracts.gui.generic.ProfilesGui;
import uk.co.minty_studios.mobcontracts.gui.handler.Gui;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.gui.slain.CommonContractsGui;
import uk.co.minty_studios.mobcontracts.gui.slain.EpicContractsGui;
import uk.co.minty_studios.mobcontracts.gui.slain.LegendaryContractsGui;
import uk.co.minty_studios.mobcontracts.gui.stats.ContractsKilledGui;
import uk.co.minty_studios.mobcontracts.gui.stats.PlayerLevelGui;
import uk.co.minty_studios.mobcontracts.gui.stats.ServerStatsGui;
import uk.co.minty_studios.mobcontracts.gui.stats.TotalExperienceGui;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenu extends Gui {

    private final CreateCustomGuiItem createCustomGuiItem;
    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final MobDataDatabase mobDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;
    private final FileConfiguration config;

    public MainMenu(GuiUtil menuUtil, CreateCustomGuiItem createCustomGuiItem, MobContracts plugin, PlayerDataDatabase playerDataDatabase, MobDataDatabase mobDataDatabase, ContractStorageDatabase contractStorageDatabase) {
        super(menuUtil);
        this.createCustomGuiItem = createCustomGuiItem;
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.mobDataDatabase = mobDataDatabase;
        this.contractStorageDatabase = contractStorageDatabase;
        this.config = plugin.getConfig();
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
        switch (e.getSlot()) {
            case 3:
                new AllContractsGui(plugin.getMenuUtil((Player) e.getWhoClicked()), mobDataDatabase, createCustomGuiItem, plugin, contractStorageDatabase, playerDataDatabase).open();
                break;
            case 4:
                new ProfilesGui(plugin.getMenuUtil((Player) e.getWhoClicked()), plugin, playerDataDatabase, createCustomGuiItem, contractStorageDatabase, mobDataDatabase).open();
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
        for (int i = 0; i < getSlots(); i++) {

            if (i == 3) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.all-contracts.material"),
                        config.getString("gui.main-menu.all-contracts.name"),
                        config.getStringList("gui.main-menu.all-contracts.lore")
                                .stream().map(s ->
                                s.replace("%total_contracts%", String.valueOf(mobDataDatabase.getTotalCountContracts())))
                                .collect(Collectors.toList()));

                inventory.setItem(i, skull);
                continue;
            }

            if(i == 4){

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();

                meta.setOwningPlayer(menuUtil.getOwner());
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("gui.main-menu.profiles.name")));
                String[] lore = config.getStringList("gui.main-menu.profiles.lore").stream().toArray(String[]::new);
                meta.setLore(Arrays.stream(lore).map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
                skull.setItemMeta(meta);
                inventory.setItem(i, skull);
                continue;
            }

            if (i == 5) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.server-stats.material"),
                        config.getString("gui.main-menu.server-stats.name"),
                        config.getStringList("gui.main-menu.server-stats.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 12) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.contracts-killed.material"),
                        config.getString("gui.main-menu.contracts-killed.name"),
                        config.getStringList("gui.main-menu.contracts-killed.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 13) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.total-experience.material"),
                        config.getString("gui.main-menu.total-experience.name"), config.getStringList("gui.main-menu.total-experience.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 14) {
                ItemStack skull = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.player-level.material"),
                        config.getString("gui.main-menu.player-level.name"), config.getStringList("gui.main-menu.player-level.lore"));

                inventory.setItem(i, skull);
                continue;
            }

            if (i == 21) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.common-contracts.material"),
                        config.getString("gui.main-menu.common-contracts.name"), config.getStringList("gui.main-menu.common-contracts.lore"));

                inventory.setItem(i, item);
                continue;
            }

            if (i == 22) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.epic-contracts.material"),
                        config.getString("gui.main-menu.epic-contracts.name"), config.getStringList("gui.main-menu.epic-contracts.lore"));

                inventory.setItem(i, item);
                continue;
            }

            if (i == 23) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.legendary-contracts.material"),
                        config.getString("gui.main-menu.legendary-contracts.name"), config.getStringList("gui.main-menu.legendary-contracts.lore"));

                inventory.setItem(i, item);
                continue;
            }

            if ((i == 0) || (i == 8) || (i == 26) || (i == 18)) {
                ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.accent-item"), "&8", null);
                inventory.setItem(i, item);
                continue;
            }

            ItemStack item = createCustomGuiItem.checkMaterial(config.getString("gui.main-menu.filler-item"), "&8", null);
            inventory.setItem(i, item);
        }
    }
}
