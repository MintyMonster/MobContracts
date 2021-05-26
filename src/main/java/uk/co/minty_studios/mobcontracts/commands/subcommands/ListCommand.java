package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ListCommand extends ChildCommand {

    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private final DatabaseManager databaseManager;

    public ListCommand(String command, MobContracts plugin, GenericUseMethods genericUseMethods, DatabaseManager databaseManager) {
        super(command);
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.databaseManager = databaseManager;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.core";
    }

    @Override
    public String getDescription() {
        return "List your owned contracts!";
    }

    @Override
    public String getSyntax() {
        return "/contracts list";
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();
        Player player = (Player) sender;
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-title"));
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-common").replace("%common-amount%", String.valueOf(map.get(player.getUniqueId()).getCommonOwned())));
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-epic").replace("%epic-amount%", String.valueOf(map.get(player.getUniqueId()).getEpicOwned())));
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-legendary").replace("%legendary-amount%", String.valueOf(map.get(player.getUniqueId()).getLegendaryOwned())));

    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
