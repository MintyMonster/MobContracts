package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.List;

public class ActiveCommand extends ChildCommand {

    private final MobContracts plugin;
    private final CurrentContracts currentContracts;
    private final GenericUseMethods genericUseMethods;

    public ActiveCommand(String command, MobContracts plugin, CurrentContracts currentContracts, GenericUseMethods genericUseMethods) {
        super(command);
        this.plugin = plugin;
        this.currentContracts = currentContracts;
        this.genericUseMethods = genericUseMethods;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return "See all active contracts!";
    }

    @Override
    public String getSyntax() {
        return "/contracts active";
    }

    @Override
    public Boolean consoleUse() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (currentContracts.getContracts().isEmpty()) {
            genericUseMethods.sendMessageWithPrefix(player, "&cError: No active contracts!");
            return;
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.command.current-active-title")));
        currentContracts.getContracts().entrySet().forEach(e -> {
            Player p = plugin.getServer().getPlayer(e.getKey());
            player.sendMessage(ChatColor.GRAY + "-------------------");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.command.current-active")
                            .replace("%entity%", e.getValue().getCustomName())
                            .replace("%name%", p.getName())));
        });
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
