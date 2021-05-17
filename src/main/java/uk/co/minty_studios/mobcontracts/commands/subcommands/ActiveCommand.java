package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.List;

public class ActiveCommand extends ChildCommand {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final CurrentContracts currentContracts;
    private final GenericUseMethods genericUseMethods;

    public ActiveCommand(String command,
                         MobContracts plugin,
                         PlayerDataDatabase playerDataDatabase,
                         CurrentContracts currentContracts,
                         GenericUseMethods genericUseMethods) {
        super(command);
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.currentContracts = currentContracts;
        this.genericUseMethods = genericUseMethods;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.core";
    }

    @Override
    public String getDescription() {
        return "See all on-going contracts!";
    }

    @Override
    public String getSyntax() {
        return "/contracts active";
    }

    @Override
    public void perform(Player player, String[] args) {

        if(currentContracts.getContracts().isEmpty()){
            genericUseMethods.sendMessageWithPrefix(player, "&cError: No active contracts!");
            return;
        }

        player.sendMessage(ChatColor.AQUA + "Current Contracts:");
        currentContracts.getContracts().entrySet().forEach(e -> {
            Player p = plugin.getServer().getPlayer(e.getKey());
            player.sendMessage(ChatColor.GRAY + "-------------------");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&eEntity: " + e.getValue().getCustomName() + " &eOwner: &6" + p.getName()));
        });
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
