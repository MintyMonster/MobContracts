package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.*;
import java.util.stream.Collectors;

public class LevelCommand extends ChildCommand {

    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private final DatabaseManager databaseManager;

    public LevelCommand(String command, MobContracts plugin, GenericUseMethods genericUseMethods, DatabaseManager databaseManager) {
        super(command);
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.databaseManager = databaseManager;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return "Edit a player's levels!";
    }

    @Override
    public String getSyntax() {
        return "/contracts level [add|remove|set] [amount] [player]";
    }

    @Override
    public Boolean consoleUse() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();
        if (!(args.length >= 4)) {
            genericUseMethods.sendVariedSenderMessage(sender, "&e" + this.getSyntax());
            return;
        }

        int amount = Integer.parseInt(args[2]);

        Player p = null;

        if (plugin.getServer().getPlayer(args[3]) == null) {
            genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player is not online!");
            return;
        } else
            p = plugin.getServer().getPlayer(args[3]);

        if (args[1].equalsIgnoreCase("add")) {
            map.get(p.getUniqueId()).setCurrentLevel(map.get(p.getUniqueId()).getCurrentLevel() + amount);

            genericUseMethods.sendVariedSenderMessage(sender,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-add")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", p.getName())));

            genericUseMethods.sendMessageWithPrefix(p,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-add-received")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", sender.getName())));


        } else if (args[1].equalsIgnoreCase("remove")) {
            map.get(p.getUniqueId()).setCurrentLevel(map.get(p.getUniqueId()).getCurrentLevel() - amount);
            map.get(p.getUniqueId()).setCurrentXp(0);

            genericUseMethods.sendVariedSenderMessage(sender,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-removed")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", p.getName())));

            genericUseMethods.sendMessageWithPrefix(p,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-removed-received")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", sender.getName())));

        } else if (args[1].equalsIgnoreCase("set")) {
            map.get(p.getUniqueId()).setCurrentLevel(amount);
            map.get(p.getUniqueId()).setCurrentXp(0);

            genericUseMethods.sendVariedSenderMessage(sender,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-set")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", p.getName())));

            genericUseMethods.sendMessageWithPrefix(p,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-set-received")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", sender.getName())));
        }
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {

        if (args.length == 2) {
            return Arrays.asList("add", "remove", "set");

        } else if (args.length == 3) {
            return Arrays.asList("1", "2", "3", "4", "5");

        } else if (args.length == 4) {
            List<String> players = new ArrayList<>();
            players.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            return players;
        }

        return null;
    }
}
