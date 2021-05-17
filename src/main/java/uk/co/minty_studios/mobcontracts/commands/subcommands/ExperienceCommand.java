package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceCommand extends ChildCommand {

    private final PlayerDataDatabase playerDataDatabase;
    private final GenericUseMethods genericUseMethods;
    private final MobContracts plugin;

    public ExperienceCommand(String command, PlayerDataDatabase playerDataDatabase, GenericUseMethods genericUseMethods, MobContracts plugin) {
        super(command);
        this.playerDataDatabase = playerDataDatabase;
        this.genericUseMethods = genericUseMethods;
        this.plugin = plugin;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return "Edit a player's experience!";
    }

    @Override
    public String getSyntax() {
        return "/contracts xp [add|remove] [amount] [player]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(!(args.length >= 4)){
            genericUseMethods.sendMessageWithPrefix(player, "&e" + this.getSyntax());
            return;
        }

        int amount = Integer.parseInt(args[2]);
        Player p = null;

        if (plugin.getServer().getPlayer(args[3]) == null) {
            genericUseMethods.sendMessageWithPrefix(player, "&cError: Player is not online!");
            return;
        } else
            p = plugin.getServer().getPlayer(args[3]);

        if(args[1].equalsIgnoreCase("add")){
            int level = playerDataDatabase.getPlayerLevel(p.getUniqueId());
            if(playerDataDatabase.getPlayerXp(p.getUniqueId()) + amount >= level * plugin.getConfig().getInt("settings.levels.xp-multi")){
                while(playerDataDatabase.getPlayerXp(p.getUniqueId()) + amount >= level * plugin.getConfig().getInt("settings.levels.xp-multi")){
                    level++;
                }
                int newAmount = (playerDataDatabase.getPlayerXp(p.getUniqueId()) + amount) - ((level - 1) * plugin.getConfig().getInt("settings.levels.xp-multi"));
                playerDataDatabase.setPlayerXp(p.getUniqueId(), newAmount);
                playerDataDatabase.setPlayerLevel(p.getUniqueId(), level);
                playerDataDatabase.addPlayerTotalXp(p.getUniqueId(), amount);
                genericUseMethods.sendMessageWithPrefix(p, "&e" + player.getName() + " &7has added &e" + amount + "&7xp to you!");
                genericUseMethods.sendMessageWithPrefix(p, "&7You are now level &e" + level + "&7, with &e" + newAmount + "&7xp");
            }else{
                playerDataDatabase.addPlayerXp(p.getUniqueId(), amount);
                playerDataDatabase.addPlayerTotalXp(p.getUniqueId(), amount);
                genericUseMethods.sendMessageWithPrefix(p, "&e" + player.getName() + " &7has added &e" + amount + "&7xp to you!");
            }

        }else if(args[1].equalsIgnoreCase("remove")){
            if(playerDataDatabase.getPlayerXp(p.getUniqueId()) - amount < 0){
                genericUseMethods.sendMessageWithPrefix(player, "&cYou cannot remove more xp than they have.");
                genericUseMethods.sendMessageWithPrefix(player, "&cWant to reduce their level? use &e/contracts level");
                return;
            }

            playerDataDatabase.removePlayerXp(p.getUniqueId(), amount);
            genericUseMethods.sendMessageWithPrefix(p, "&e" + player.getName() + "&7 has removed &e" + amount + "&7xp from you :(");
        }

        new BukkitRunnable(){
            @Override
            public void run(){
                playerDataDatabase.updatePlayer(Bukkit.getServer().getPlayer(args[3]).getUniqueId());
            }
        }.runTaskLater(plugin, 20L);
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {

        if(args.length == 2)
            return Arrays.asList("add", "remove");

        if(args.length == 3)
            return Arrays.asList("1", "2", "3", "4", "5");

        if(args.length == 4)
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());

        return null;
    }
}
