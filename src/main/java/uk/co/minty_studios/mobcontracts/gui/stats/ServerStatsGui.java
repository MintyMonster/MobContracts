package uk.co.minty_studios.mobcontracts.gui.stats;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.MainMenu;
import uk.co.minty_studios.mobcontracts.gui.handler.Gui;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerStatsGui extends Gui {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final MobDataDatabase mobDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final FileConfiguration config;

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
        this.config = plugin.getConfig();
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

        if (e.getSlot() == 39)
            new MainMenu(plugin.getMenuUtil((Player) e.getWhoClicked()), createCustomGuiItem, plugin, playerDataDatabase, mobDataDatabase, contractStorageDatabase).open();

    }

    @Override //&8➟
    public void setItems() {
        for (int i = 0; i < getSlots(); i++) {
            if (i == 4) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.stats.material"),
                        config.getString("gui.server-stats.stats.name"), config.getStringList("gui.server-stats.stats.lore")
                                .stream().map(s -> s.replace("%total_players%", String.valueOf(playerDataDatabase.getTotalCountPlayers()))
                                .replace("%total_contracts%", String.valueOf(mobDataDatabase.getTotalCountContracts()))).collect(Collectors.toList())));
                continue;
            }

            if (i == 12) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.common-killed.material"),
                        config.getString("gui.server-stats.common-killed.name"), config.getStringList("gui.server-stats.common-killed.lore")
                                .stream().map(s -> s.replace("%common_killed%", String.valueOf(playerDataDatabase.getTotalStat("COMMON")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 13) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.epic-killed.material"),
                        config.getString("gui.server-stats.epic-killed.name"), config.getStringList("gui.server-stats.epic-killed.lore")
                                .stream().map(s -> s.replace("%epic_killed%", String.valueOf(playerDataDatabase.getTotalStat("EPIC")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 14) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.legendary-killed.material"),
                        config.getString("gui.server-stats.legendary-killed.name"), config.getStringList("gui.server-stats.legendary-killed.lore")
                                .stream().map(s -> s.replace("%legendary_killed%", String.valueOf(playerDataDatabase.getTotalStat("LEGENDARY")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 21) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.common-stored.material"),
                        config.getString("gui.server-stats.common-stored.name"), config.getStringList("gui.server-stats.common-stored.lore")
                                .stream().map(s -> s.replace("%common_stored%", String.valueOf(contractStorageDatabase.getTotalStat("COMMON")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 22) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.epic-stored.material"),
                        config.getString("gui.server-stats.epic-stored.name"), config.getStringList("gui.server-stats.epic-stored.lore")
                                .stream().map(s -> s.replace("%epic_stored%", String.valueOf(contractStorageDatabase.getTotalStat("EPIC")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 23) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.legendary-stored.material"),
                        config.getString("gui.server-stats.legendary-stored.name"), config.getStringList("gui.server-stats.legendary-stored.lore")
                                .stream().map(s -> s.replace("%legendary_stored%", String.valueOf(contractStorageDatabase.getTotalStat("LEGENDARY")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 30) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.player-levels.material"),
                        config.getString("gui.server-stats.player-levels.name"), config.getStringList("gui.server-stats.player-levels.lore")
                                .stream().map(s -> s.replace("%level%", String.valueOf(playerDataDatabase.getTotalStat("LEVEL")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 31) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.total-experience.material"),
                        config.getString("gui.server-stats.total-experience.name"), config.getStringList("gui.server-stats.total-experience.lore")
                                .stream().map(s -> s.replace("%total_experience%", String.valueOf(playerDataDatabase.getTotalStat("TOTALXP")))).collect(Collectors.toList())));
                continue;
            }

            if (i == 32) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.total-damage.material"),
                        config.getString("gui.server-stats.total-damage.name"), config.getStringList("gui.server-stats.total-damage.lore")
                                .stream().map(s -> s.replace("%damage%", String.valueOf(mobDataDatabase.getTotalStat("DAMAGE")))).collect(Collectors.toList())));

                continue;
            }

            if (i == 39) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.back-button.material"),
                        config.getString("gui.server-stats.back-button.name"), config.getStringList("gui.server-stats.back-button.lore")));
                continue;
            }

            if (i == 41) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.total-health.material"),
                        config.getString("gui.server-stats.total-health.name"), config.getStringList("gui.server-stats.total-health.lore")
                                .stream().map(s -> s.replace("%health%", String.valueOf(mobDataDatabase.getTotalStat("HEALTH")))).collect(Collectors.toList())));
                continue;
            }

            if ((i == 0) || (i == 8) || (i == 36) || (i == 44)) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.accent-item"), "&7", null));
                continue;
            }

            inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.filler-item"), "&7", null));
        }
    }
}
