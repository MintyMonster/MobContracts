package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.*;
import java.util.stream.Collectors;

public class GiveCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public GiveCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin, DatabaseManager databaseManager) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.plugin = plugin;
        this.databaseManager = databaseManager;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return "Give player a new contract!";
    }

    @Override
    public String getSyntax() {
        return "/contracts give [player] [type] [amount]";
    }

    @Override
    public Boolean consoleUse() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();

        if (!(args.length >= 3)) {
            genericUseMethods.sendVariedSenderMessage(sender, "&e" + this.getSyntax());
            return;
        }
        int amount = args.length == 3 ? 1 : Integer.parseInt(args[3]);

        Player p = null;

        if (plugin.getServer().getPlayer(args[1]) == null) {
            genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player is not online!");
            return;
        } else
            p = plugin.getServer().getPlayer(args[1]);

        switch(args[2].toLowerCase()){
            case "legendary":
                map.get(p.getUniqueId()).setLegendaryOwned(map.get(p.getUniqueId()).getLegendaryOwned() + amount);

                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.give-legendary")
                        .replace("%player%", p.getName())
                        .replace("%amount%", String.valueOf(amount)));
                genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.received-legendary")
                        .replace("%amount%", String.valueOf(amount)));
                break;
            case "epic":
                map.get(p.getUniqueId()).setEpicOwned(map.get(p.getUniqueId()).getEpicOwned() + amount);

                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.give-epic")
                        .replace("%player%", p.getName())
                        .replace("%amount%", String.valueOf(amount)));
                genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.received-epic")
                        .replace("%amount%", String.valueOf(amount)));
                break;
            case "common":
                map.get(p.getUniqueId()).setCommonOwned(map.get(p.getUniqueId()).getCommonOwned() + amount);

                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.give-common")
                        .replace("%player%", p.getName())
                        .replace("%amount%", String.valueOf(amount)));
                genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.received-common")
                        .replace("%amount%", String.valueOf(amount)));
                break;
        }
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2) {
            List<String> players = new ArrayList<>();
            players.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            return players;
        } else if (args.length == 3) {
            return Arrays.asList("Common", "Epic", "Legendary");
        } else if (args.length == 4) {
            return Arrays.asList("1", "2", "3", "4", "5");
        }
        return null;
    }
}
