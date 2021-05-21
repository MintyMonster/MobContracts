package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.List;

public class ListCommand extends ChildCommand {

    private final ContractStorageDatabase contractStorageDatabase;
    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;

    public ListCommand(String command, ContractStorageDatabase contractStorageDatabase, MobContracts plugin, GenericUseMethods genericUseMethods) {
        super(command);
        this.contractStorageDatabase = contractStorageDatabase;
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
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
    public Boolean consoleUse(){
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) return;
        Player player = (Player) sender;
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-title"));
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-common").replace("%common-amount%", String.valueOf(contractStorageDatabase.getCommonContracts(player.getUniqueId()))));
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-epic").replace("%epic-amount%", String.valueOf(contractStorageDatabase.getEpicContracts(player.getUniqueId()))));
        genericUseMethods.sendMessageNoPrefix(player, plugin.getConfig().getString("messages.command.list-legendary").replace("%legendary-amount%", String.valueOf(contractStorageDatabase.getLegendaryContracts(player.getUniqueId()))));

    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
