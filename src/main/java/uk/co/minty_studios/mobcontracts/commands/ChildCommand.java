package uk.co.minty_studios.mobcontracts.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class ChildCommand {

    private final String command;

    protected ChildCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return command;
    }

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract Boolean consoleUse();

    public abstract void perform(CommandSender sender, String[] args);

    public abstract List<String> onTab(CommandSender sender, String... args);
}
