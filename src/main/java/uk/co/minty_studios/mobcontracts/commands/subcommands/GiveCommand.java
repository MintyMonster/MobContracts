package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

public class GiveCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final MobContracts plugin;
    private final ContractStorageDatabase contractStorageDatabase;

    public GiveCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin, ContractStorageDatabase contractStorageDatabase) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.plugin = plugin;
        this.contractStorageDatabase = contractStorageDatabase;
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
    public void perform(Player player, String[] args) {
        if(!(args.length >= 3)){
            genericUseMethods.sendMessageWithPrefix(player, "&c" + this.getSyntax());
            return;
        }

        Player p = plugin.getServer().getPlayer(args[1]);
        int amount = args.length == 3 ? 1 : Integer.parseInt(args[3]);
        if((args[2].equalsIgnoreCase("legendary")) || (args[2].equalsIgnoreCase("leg")) || (args[2].equalsIgnoreCase("l"))){
            contractStorageDatabase.addLegendaryContract(p.getUniqueId(), amount);
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.give-legendary")
                    .replace("%player%", p.getName())
                    .replace("%amount%", String.valueOf(amount)));
            genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.received-legendary")
                    .replace("%amount%", String.valueOf(amount)));

        }else if((args[2].equalsIgnoreCase("epic")) || (args[2].equalsIgnoreCase("ep")) || (args[2].equalsIgnoreCase("e"))){
            contractStorageDatabase.addEpicContract(p.getUniqueId(), amount);
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.give-epic")
                    .replace("%player%", p.getName())
                    .replace("%amount%", String.valueOf(amount)));
            genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.received-epic")
                    .replace("%amount%", String.valueOf(amount)));

        }else if((args[2].equalsIgnoreCase("common")) || (args[2].equalsIgnoreCase("com")) || (args[2].equalsIgnoreCase("c"))){
            contractStorageDatabase.addCommonContract(p.getUniqueId(), amount);
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.give-common")
                    .replace("%player%", p.getName())
                    .replace("%amount%", String.valueOf(amount)));
            genericUseMethods.sendMessageWithPrefix(p, plugin.getConfig().getString("messages.command.received-common")
                    .replace("%amount%", String.valueOf(amount)));
        }
    }
}
