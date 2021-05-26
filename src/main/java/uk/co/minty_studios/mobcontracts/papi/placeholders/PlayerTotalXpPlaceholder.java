package uk.co.minty_studios.mobcontracts.papi.placeholders;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;

public class PlayerTotalXpPlaceholder implements Placeholder {

    private final DatabaseManager databaseManager;

    public PlayerTotalXpPlaceholder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(databaseManager.getPlayerMap().get(player.getUniqueId()).getTotalXp());
    }

    @Override
    public String getName() {
        return "player_total_experience";
    }
}
