package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LevelCommand extends ChildCommand {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final GenericUseMethods genericUseMethods;

    public LevelCommand(String command, MobContracts plugin, PlayerDataDatabase playerDataDatabase, GenericUseMethods genericUseMethods) {
        super(command);
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.genericUseMethods = genericUseMethods;
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
    public Boolean consoleUse(){
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if(!(args.length >= 4)){
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

        if(args[1].equalsIgnoreCase("add")){
            playerDataDatabase.addPlayerLevel(p.getUniqueId(), amount);

            genericUseMethods.sendVariedSenderMessage(sender,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-add")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", p.getName())));

            genericUseMethods.sendMessageWithPrefix(p,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-add-received")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", sender.getName())));


        }else if(args[1].equalsIgnoreCase("remove")){
            playerDataDatabase.removePlayerLevel(p.getUniqueId(), amount);
            playerDataDatabase.setPlayerXp(p.getUniqueId(), 0);
            genericUseMethods.sendVariedSenderMessage(sender,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-removed")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", p.getName())));

            genericUseMethods.sendMessageWithPrefix(p,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-removed-received")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", sender.getName())));

        }else if(args[1].equalsIgnoreCase("set")){
            playerDataDatabase.setPlayerXp(p.getUniqueId(), 0);
            playerDataDatabase.setPlayerLevel(p.getUniqueId(), amount);

            genericUseMethods.sendVariedSenderMessage(sender,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-set")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", p.getName())));

            genericUseMethods.sendMessageWithPrefix(p,
                    ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.level-set-received")
                            .replace("%amount%", String.valueOf(amount)).replace("%player%", sender.getName())));
        }

        new BukkitRunnable(){
            @Override
            public void run(){
                playerDataDatabase.updatePlayer(plugin.getServer().getPlayer(args[3]).getUniqueId());
            }
        }.runTaskLater(plugin, 30);
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {

        if(args.length == 2){
            return Arrays.asList("add", "remove", "set");

        }else if(args.length == 3){
            return Arrays.asList("1", "2", "3", "4", "5");

        }else if(args.length == 4){
            List<String> players = new ArrayList<>();
            players.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            return players;
        }

        return null;
    }
}
