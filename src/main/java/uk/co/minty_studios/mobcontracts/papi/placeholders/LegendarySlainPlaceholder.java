package uk.co.minty_studios.mobcontracts.papi.placeholders;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;

public class LegendarySlainPlaceholder implements Placeholder{

    private final PlayerDataDatabase playerDataDatabase;

    public LegendarySlainPlaceholder(PlayerDataDatabase playerDataDatabase) {
        this.playerDataDatabase = playerDataDatabase;
    }

    @Override
    public String process(Player player, String id) {
        return String.valueOf(playerDataDatabase.getPlayerMap().get(player.getUniqueId()).getLegendarySlain());
    }

    @Override
    public String getName() {
        return "legendary_slain";
    }
}