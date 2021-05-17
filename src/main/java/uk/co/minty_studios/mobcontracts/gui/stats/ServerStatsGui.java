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
                        config.getString("gui.server-stats.stats.name"), fillPlaceholders(config.getStringList("gui.server-stats.stats.lore"))));
                continue;
            }

            if (i == 12) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.common-killed.material"),
                        config.getString("gui.server-stats.common-killed.name"), fillPlaceholders(config.getStringList("gui.server-stats.common-killed.lore"))));
                continue;
            }

            if (i == 13) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.epic-killed.material"),
                        config.getString("gui.server-stats.epic-killed.name"), fillPlaceholders(config.getStringList("gui.server-stats.epic-killed.lore"))));
                continue;
            }

            if (i == 14) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.legendary-killed.material"),
                        config.getString("gui.server-stats.legendary-killed.name"), fillPlaceholders(config.getStringList("gui.server-stats.legendary-killed.lore"))));
                continue;
            }

            if (i == 21) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.common-stored.material"),
                        config.getString("gui.server-stats.common-stored.name"), fillPlaceholders(config.getStringList("gui.server-stats.common-stored.lore"))));
                continue;
            }

            if (i == 22) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.epic-stored.material"),
                        config.getString("gui.server-stats.epic-stored.name"), fillPlaceholders(config.getStringList("gui.server-stats.epic-stored.lore"))));
                continue;
            }

            if (i == 23) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.legendary-stored.material"),
                        config.getString("gui.server-stats.legendary-stored.name"), fillPlaceholders(config.getStringList("gui.server-stats.legendary-stored.lore"))));
                continue;
            }

            if (i == 30) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.player-levels.material"),
                        config.getString("gui.server-stats.player-levels.name"), fillPlaceholders(config.getStringList("gui.server-stats.player-levels.lore"))));
                continue;
            }

            if (i == 31) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.total-experience.material"),
                        config.getString("gui.server-stats.total-experience.name"), fillPlaceholders(config.getStringList("gui.server-stats.total-experience.lore"))));
                continue;
            }

            if (i == 32) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.total-damage.material"),
                        config.getString("gui.server-stats.total-damage.name"), fillPlaceholders(config.getStringList("gui.server-stats.total-damage.lore"))));

                continue;
            }

            if (i == 39) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.back-button.material"),
                        config.getString("gui.server-stats.back-button.name"), fillPlaceholders(config.getStringList("gui.server-stats.back-button.lore"))));
                continue;
            }

            if (i == 41) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.total-health.material"),
                        config.getString("gui.server-stats.total-health.name"), fillPlaceholders(config.getStringList("gui.server-stats.total-health.lore"))));
                continue;
            }

            if ((i == 0) || (i == 8) || (i == 36) || (i == 44)) {
                inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.accent-item"), "&7", null));
                continue;
            }

            inventory.setItem(i, createCustomGuiItem.checkMaterial(config.getString("gui.server-stats.filler-item"), "&7", null));
        }
    }

    public List<String> fillPlaceholders(List<String> lore){

        return lore.stream().map(s ->
                s.replace("%total_players%", String.valueOf(playerDataDatabase.getTotalCountPlayers()))
                        .replace("%total_contracts%", String.valueOf(mobDataDatabase.getTotalCountContracts()))
                        .replace("%common_killed%", String.valueOf(playerDataDatabase.getTotalStat("COMMON")))
                        .replace("%epic_killed%", String.valueOf(playerDataDatabase.getTotalStat("EPIC")))
                        .replace("%legendary_killed%", String.valueOf(playerDataDatabase.getTotalStat("LEGENDARY")))
                        .replace("%common_stored%", String.valueOf(contractStorageDatabase.getTotalStat("COMMON")))
                        .replace("%epic_stored%", String.valueOf(contractStorageDatabase.getTotalStat("EPIC")))
                        .replace("%legendary_stored%", String.valueOf(contractStorageDatabase.getTotalStat("LEGENDARY")))
                        .replace("%total_levels%", String.valueOf(playerDataDatabase.getTotalStat("LEVEL")))
                        .replace("%global_total_experience%", String.valueOf(playerDataDatabase.getTotalStat("TOTALXP")))
                        .replace("%total_damage%", String.valueOf(mobDataDatabase.getTotalStat("DAMAGE")))
                        .replace("%total_health%", String.valueOf(mobDataDatabase.getTotalStat("HEALTH")))).collect(Collectors.toList());
    }
}
