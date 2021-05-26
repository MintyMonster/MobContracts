package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExperienceCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public ExperienceCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin, DatabaseManager databaseManager) {
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
        return "Edit a player's experience!";
    }

    @Override
    public String getSyntax() {
        return "/contracts experience [add|remove] [amount] [player]";
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
        }

        int amount = Integer.parseInt(args[2]);
        Player p = null;

        if (plugin.getServer().getPlayer(args[3]) == null) {
            genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player is not online!");
            return;
        } else
            p = plugin.getServer().getPlayer(args[3]);

        UUID uuid = p.getUniqueId();

        if (args[1].equalsIgnoreCase("add")) {

            int levelxp = map.get(uuid).getCurrentLevel() * plugin.getConfig().getInt("settings.levels.xp-multi");
            map.get(uuid).setTotalXp(map.get(uuid).getTotalXp() + amount);

            if(map.get(uuid).getCurrentXp() + amount >= levelxp){
                int level = map.get(uuid).getCurrentLevel();
                while(map.get(uuid).getCurrentXp() + amount >= levelxp){
                    level++;
                }

                int newAmount = (map.get(uuid).getCurrentXp() + amount) - ((level - 1) * plugin.getConfig().getInt("settings.levels.xp-multi"));
                map.get(uuid).setCurrentXp(newAmount);
                map.get(uuid).setCurrentLevel(level);

                genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.experience-added")
                        .replace("%player%", sender.getName()).replace("%amount%", String.valueOf(amount)));

                genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.experience-levelup")
                        .replace("%level%", String.valueOf(level)).replace("%currentxp", String.valueOf(newAmount)));
            }else{
                map.get(uuid).setCurrentXp(map.get(uuid).getCurrentXp() + amount);

                genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.experience-added")
                        .replace("%player%", sender.getName()).replace("%amount%", String.valueOf(amount)));
            }

        } else if (args[1].equalsIgnoreCase("remove")) {
            if (map.get(uuid).getCurrentXp() - amount < 0) {
                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.experience-remove-error"));
                return;
            }

            map.get(uuid).setCurrentXp(map.get(uuid).getCurrentXp() - amount);
            genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.experience-remove")
                    .replace("%player%", sender.getName()).replace("%amount%", String.valueOf(amount)));

        }
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2)
            return Arrays.asList("add", "remove");

        if (args.length == 3)
            return Arrays.asList("1", "2", "3", "4", "5");

        if (args.length == 4)
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());

        return null;
    }
}
