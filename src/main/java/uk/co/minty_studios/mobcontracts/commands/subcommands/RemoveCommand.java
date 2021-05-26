package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final CurrentContracts currentContracts;
    private final MobContracts plugin;

    public RemoveCommand(String command, GenericUseMethods genericUseMethods, CurrentContracts currentContracts, MobContracts plugin) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.currentContracts = currentContracts;
        this.plugin = plugin;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return "Clear all, or a specific player's contract";
    }

    @Override
    public String getSyntax() {
        return "/contracts clear [Playername || all]";
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;

        if (!(args.length > 1)) {
            genericUseMethods.sendMessageWithPrefix(player, "&e" + this.getSyntax());
            return;
        }

        if (args[1].equalsIgnoreCase("all")) {
            currentContracts.removeAllContracts();
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.remove-all"));
        } else {
            Player p = plugin.getServer().getPlayer(args[1]);
            if (currentContracts.inContract(p)) {
                currentContracts.removePlayerContract(p);
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.remove-player-contract")
                        .replace("%player%", p.getName()));
            } else {
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contract-remove"));
            }
        }
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2) {
            List<String> players = new ArrayList<>();
            players.add("all");
            players.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            return players;
        }

        return null;
    }
}
