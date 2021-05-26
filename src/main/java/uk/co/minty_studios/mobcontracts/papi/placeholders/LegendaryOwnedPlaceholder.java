package uk.co.minty_studios.mobcontracts.papi.placeholders;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;

public class LegendaryOwnedPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public LegendaryOwnedPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getLegendaryOwned());
    }

    @Override
    public String getName() {
        return "legendary_owned";
    }
}
