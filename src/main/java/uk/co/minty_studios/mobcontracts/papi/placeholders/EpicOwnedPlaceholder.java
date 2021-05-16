package uk.co.minty_studios.mobcontracts.papi.placeholders;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;

public class EpicOwnedPlaceholder implements Placeholder{

    private final PlayerDataDatabase playerDataDatabase;

    public EpicOwnedPlaceholder(PlayerDataDatabase playerDataDatabase) {
        this.playerDataDatabase = playerDataDatabase;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(playerDataDatabase.getPlayerMap().get(player.getUniqueId()).getEpicOwned());
    }

    @Override
    public String getName() {
        return "epic_owned";
    }
}
