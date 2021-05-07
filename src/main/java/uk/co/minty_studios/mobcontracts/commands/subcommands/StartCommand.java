package uk.co.minty_studios.mobcontracts.commands.subcommands;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.commands.ChildCommand;
import uk.co.minty_studios.mobcontracts.contracts.CommonContract;
import uk.co.minty_studios.mobcontracts.contracts.EpicContract;
import uk.co.minty_studios.mobcontracts.contracts.LegendaryContract;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.utils.CurrentContracts;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

public class StartCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final CommonContract commonContract;
    private final EpicContract epicContract;
    private final LegendaryContract legendaryContract;
    private final ContractStorageDatabase contractStorageDatabase;
    private final MobContracts plugin;
    private final CurrentContracts currentContracts;

    public StartCommand(String command, GenericUseMethods genericUseMethods, CommonContract commonContract, EpicContract epicContract, LegendaryContract legendaryContract, ContractStorageDatabase contractStorageDatabase, MobContracts plugin, CurrentContracts currentContracts) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.commonContract = commonContract;
        this.epicContract = epicContract;
        this.legendaryContract = legendaryContract;
        this.contractStorageDatabase = contractStorageDatabase;
        this.plugin = plugin;
        this.currentContracts = currentContracts;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.core";
    }

    @Override
    public String getDescription() {
        return "Start a new contract!";
    }

    @Override
    public String getSyntax() {
        return "/contracts start [type]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(!(args.length > 1)){
            genericUseMethods.sendMessageWithPrefix(player, "&e" + this.getSyntax());
            return;
        }

        if(currentContracts.inContract(player)){
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.already-in-contract"));
            return;
        }

        String type = args[1];
        if((type.equalsIgnoreCase("legendary")) || (type.equalsIgnoreCase("leg")) || (type.equalsIgnoreCase("l"))){
            if(contractStorageDatabase.getLegendaryContracts(player.getUniqueId()) > 0){
                legendaryContract.summonLegendaryContract(player);
                contractStorageDatabase.useLegendaryContract(player.getUniqueId());
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.start-legendary"));
            }else{
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contracts-left")
                        .replace("%type%", "Legendary"));
            }

        }else if((type.equalsIgnoreCase("epic")) || (type.equalsIgnoreCase("ep"))|| (type.equalsIgnoreCase("e"))){
            if(contractStorageDatabase.getEpicContracts(player.getUniqueId()) > 0){
                epicContract.summonEpicContract(player);
                contractStorageDatabase.useEpicContract(player.getUniqueId());
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.start-epic"));
            }else{
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contracts-left")
                        .replace("%type%", "Epic"));
            }

        }else if((type.equalsIgnoreCase("common")) || (type.equalsIgnoreCase("com"))|| (type.equalsIgnoreCase("c"))){
            if(contractStorageDatabase.getCommonContracts(player.getUniqueId()) > 0){
                commonContract.summonCommonContract(player);
                contractStorageDatabase.useCommonContract(player.getUniqueId());
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.start-common"));
            }else{
                genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.command.no-contracts-left")
                        .replace("%type%", "Common"));
            }
        }
    }
}
