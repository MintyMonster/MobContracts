package uk.co.minty_studios.mobcontracts.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.subcommands.*;
import uk.co.minty_studios.mobcontracts.contracts.CommonContract;
import uk.co.minty_studios.mobcontracts.contracts.EpicContract;
import uk.co.minty_studios.mobcontracts.contracts.LegendaryContract;
import uk.co.minty_studios.mobcontracts.database.*;
import uk.co.minty_studios.mobcontracts.utils.ContractType;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements TabExecutor {

    private final Map<String, MasterCommand> commands = new HashMap<>();
    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private final LegendaryContract legendaryContract;
    private final EpicContract epicContract;
    private final CommonContract commonContract;
    private final CurrentContracts currentContracts;
    private final ContractType contractType;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final DatabaseManager databaseManager;

    public CommandManager(MobContracts plugin,
                          GenericUseMethods genericUseMethods,
                          LegendaryContract legendaryContract,
                          EpicContract epicContract,
                          CommonContract commonContract,
                          CurrentContracts currentContracts,
                          ContractType contractType,
                          CreateCustomGuiItem createCustomGuiItem,
                          DatabaseManager databaseManager) {

        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.legendaryContract = legendaryContract;
        this.epicContract = epicContract;
        this.commonContract = commonContract;
        this.currentContracts = currentContracts;
        this.contractType = contractType;
        this.createCustomGuiItem = createCustomGuiItem;
        this.databaseManager = databaseManager;

        plugin.getCommand("mobcontracts").setExecutor(this);

        // add commands here
        addCommands("mobcontracts",
                new StartCommand("start", genericUseMethods, commonContract, epicContract, legendaryContract, plugin, currentContracts, databaseManager),
                new RemoveCommand("clear", genericUseMethods, currentContracts, plugin),
                new GiveCommand("give", genericUseMethods, plugin, databaseManager),
                new LeaderboardsCommand("leaderboard", plugin, createCustomGuiItem, databaseManager),
                new LevelCommand("level", plugin, genericUseMethods, databaseManager),
                new ActiveCommand("active", plugin, currentContracts, genericUseMethods),
                new ListCommand("list", plugin, genericUseMethods, databaseManager),
                new ExperienceCommand("experience", genericUseMethods, plugin, databaseManager)
        );
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0) {
            MasterCommand master = commands.get(command.getName().toLowerCase());
            if (master != null) {
                for (ChildCommand child : master.getChildCommands().values()) {
                    if (args[0].equalsIgnoreCase(child.getName())) {
                        if (sender instanceof ConsoleCommandSender) {
                            if (child.consoleUse()) {
                                child.perform(sender, args);
                            } else {
                                Bukkit.getConsoleSender().sendMessage("This command cannot be used from console!");
                            }
                        } else if (sender instanceof Player) {
                            if (sender.hasPermission(child.getPermission())) {
                                child.perform(sender, args);
                            }
                        }
                    }
                }
            }
        } else {
            if (sender instanceof ConsoleCommandSender) return true;
            Player player = (Player) sender;
            MasterCommand master = commands.get(command.getName().toLowerCase());
            if (master != null) {
                player.sendMessage(ChatColor.DARK_GRAY + "-------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &8 | &6&lMobContracts &r&7-> Made by &bMintyMonster"));
                player.sendMessage(ChatColor.DARK_GRAY + "-------------------------------------");
                master.getChildCommands().values().forEach(c -> {
                    if (player.hasPermission(c.getPermission()) || player.isOp())
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8| &6" + c.getSyntax() + " &8- &7" + c.getDescription()));
                });
            }

        }

        return true;
    }

    public void addCommand(String command, ChildCommand child) {
        commands.computeIfAbsent(command.toLowerCase(), c -> new MasterCommand()).addCommand(child);
    }

    public void addCommands(String command, ChildCommand... children) {
        MasterCommand master = commands.computeIfAbsent(command.toLowerCase(), c -> new MasterCommand()); // research this and the one above
        for (ChildCommand child : children)
            master.addCommand(child);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        MasterCommand master = commands.get(command.getName().toLowerCase());
        if (master != null) {
            if (args.length == 1) {
                List<String> children = new ArrayList<>();
                for (ChildCommand child : master.getChildCommands().values()) {
                    children.add(child.getName());
                }

                return children;
            } else {
                for (ChildCommand child : master.getChildCommands().values()) {
                    if (args[0].equalsIgnoreCase(child.getName())) {
                        return child.onTab(sender, args);
                    }
                }
            }
        }

        return null;
    }
}
