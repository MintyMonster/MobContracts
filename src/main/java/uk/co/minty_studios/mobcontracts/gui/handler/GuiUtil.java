package uk.co.minty_studios.mobcontracts.gui.handler;

import org.bukkit.entity.Player;

public class GuiUtil {

    private Player owner;
    private Player playerForProfile;

    public GuiUtil(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getPlayerForProfile() {
        return playerForProfile;
    }

    public void setPlayerForProfile(Player playerForProfile) {
        this.playerForProfile = playerForProfile;
    }
}
