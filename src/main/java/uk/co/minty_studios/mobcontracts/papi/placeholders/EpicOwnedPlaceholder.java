package uk.co.minty_studios.mobcontracts.papi.placeholders;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;

public class EpicOwnedPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public EpicOwnedPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getEpicOwned());
    }

    @Override
    public String getName() {
        return "epic_owned";
    }
}
