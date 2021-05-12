package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.gui.MainMenu;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

public class LeaderboardsCommand extends ChildCommand {

    private final MobContracts plugin;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final PlayerDataDatabase playerDataDatabase;
    private final MobDataDatabase mobDataDatabase;
    private final ContractStorageDatabase contractStorageDatabase;

    public LeaderboardsCommand(String command, MobContracts plugin, CreateCustomGuiItem createCustomGuiItem, PlayerDataDatabase playerDataDatabase, MobDataDatabase mobDataDatabase, ContractStorageDatabase contractStorageDatabase) {
        super(command);
        this.plugin = plugin;
        this.createCustomGuiItem = createCustomGuiItem;
        this.playerDataDatabase = playerDataDatabase;
        this.mobDataDatabase = mobDataDatabase;
        this.contractStorageDatabase = contractStorageDatabase;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.core";
    }

    @Override
    public String getDescription() {
        return "Open the leaderboards!";
    }

    @Override
    public String getSyntax() {
        return "/contracts leaderboard";
    }

    @Override
    public void perform(Player player, String[] args) {
        new MainMenu(plugin.getMenuUtil(player), createCustomGuiItem, plugin, playerDataDatabase, mobDataDatabase, contractStorageDatabase).open();
    }
}
