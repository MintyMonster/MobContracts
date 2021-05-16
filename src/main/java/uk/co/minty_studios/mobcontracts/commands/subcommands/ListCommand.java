package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;

import java.util.List;

public class ListCommand extends ChildCommand {

    private final ContractStorageDatabase contractStorageDatabase;

    public ListCommand(String command, ContractStorageDatabase contractStorageDatabase) {
        super(command);
        this.contractStorageDatabase = contractStorageDatabase;
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
    public void perform(Player player, String[] args) {
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.AQUA + "Usable contracts:");
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GRAY + "Common: " + ChatColor.YELLOW + contractStorageDatabase.getCommonContracts(player.getUniqueId()));
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.DARK_PURPLE + "Epic: " + ChatColor.YELLOW + contractStorageDatabase.getEpicContracts(player.getUniqueId()));
        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GOLD + "Legendary: " + ChatColor.YELLOW + contractStorageDatabase.getLegendaryContracts(player.getUniqueId()));
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
