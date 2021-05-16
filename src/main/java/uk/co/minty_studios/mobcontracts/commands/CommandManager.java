package uk.co.minty_studios.mobcontracts.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.subcommands.*;
import uk.co.minty_studios.mobcontracts.contracts.CommonContract;
import uk.co.minty_studios.mobcontracts.contracts.EpicContract;
import uk.co.minty_studios.mobcontracts.contracts.LegendaryContract;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.Database;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
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
    private final ContractStorageDatabase contractStorageDatabase;
    private final CurrentContracts currentContracts;
    private final Database database;
    private final ContractType contractType;
    private final CreateCustomGuiItem createCustomGuiItem;
    private final PlayerDataDatabase playerDataDatabase;
    private final MobDataDatabase mobDataDatabase;

    public CommandManager(MobContracts plugin,
                          GenericUseMethods genericUseMethods,
                          LegendaryContract legendaryContract,
                          EpicContract epicContract,
                          CommonContract commonContract,
                          ContractStorageDatabase contractStorageDatabase,
                          CurrentContracts currentContracts,
                          Database database, ContractType contractType,
                          CreateCustomGuiItem createCustomGuiItem,
                          PlayerDataDatabase playerDataDatabase,
                          MobDataDatabase mobDataDatabase) {

        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.legendaryContract = legendaryContract;
        this.epicContract = epicContract;
        this.commonContract = commonContract;
        this.contractStorageDatabase = contractStorageDatabase;
        this.currentContracts = currentContracts;
        this.database = database;
        this.contractType = contractType;
        this.createCustomGuiItem = createCustomGuiItem;
        this.playerDataDatabase = playerDataDatabase;
        this.mobDataDatabase = mobDataDatabase;

        plugin.getCommand("mobcontracts").setExecutor(this);

        // add commands here
        addCommands("mobcontracts",
                new StartCommand("start", genericUseMethods, commonContract, epicContract, legendaryContract, contractStorageDatabase, plugin, currentContracts),
                new RemoveCommand("clear", genericUseMethods, currentContracts, plugin),
                new GiveCommand("give", genericUseMethods, plugin, contractStorageDatabase, playerDataDatabase),
                new LeaderboardsCommand("leaderboard", plugin, createCustomGuiItem, playerDataDatabase, this.mobDataDatabase, contractStorageDatabase),
                new LevelCommand("level", plugin, playerDataDatabase, genericUseMethods),
                new ActiveCommand("active", plugin, playerDataDatabase, currentContracts, genericUseMethods),
                new ListCommand("list", contractStorageDatabase)
        );
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!(database.dataSourceExists())) {
            genericUseMethods.sendMessageWithPrefix(player, "&cDatabase error: Are you connected?");
            genericUseMethods.sendMessageWithPrefix(player, "&cPlease check config and try again. You may need to restart.");
            plugin.sendConsoleError("Database error: Are you connected? Please check config and try again.");
            return true;
        }

        if (args.length > 0) {
            MasterCommand master = commands.get(command.getName().toLowerCase());
            if (master != null) {
                for (ChildCommand child : master.getChildCommands().values())
                    if (args[0].equalsIgnoreCase(child.getName()))
                        if (player.hasPermission(child.getPermission()))
                            child.perform(player, args);
                        else
                            genericUseMethods.sendMessageWithPrefix(player, "&cPermission denied");

                return true;
            }
        } else {
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
        if(master != null){
            if(args.length == 1){
                List<String> children = new ArrayList<>();
                for(ChildCommand child : master.getChildCommands().values()){
                    children.add(child.getName());
                }

                return children;
            }else{
                for(ChildCommand child : master.getChildCommands().values()){
                    if(args[0].equalsIgnoreCase(child.getName())){
                        return child.onTab(sender, args);
                    }
                }
            }
        }

        return null;
    }
}
