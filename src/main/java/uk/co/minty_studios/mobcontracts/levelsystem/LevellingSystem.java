package uk.co.minty_studios.mobcontracts.levelsystem;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;

import java.util.UUID;

public class LevellingSystem {

    private final MobContracts plugin;
    private final PlayerDataDatabase playerDataDatabase;
    private final GenericUseMethods genericUseMethods;

    public LevellingSystem(MobContracts plugin, PlayerDataDatabase playerDataDatabase, GenericUseMethods genericUseMethods) {
        this.plugin = plugin;
        this.playerDataDatabase = playerDataDatabase;
        this.genericUseMethods = genericUseMethods;
    }

    public void levels(Player player, String type) {
        UUID uuid = player.getUniqueId();
        int levelXp = playerDataDatabase.getPlayerLevel(uuid) * plugin.getConfig().getInt("settings.levels.xp-multi");
        int maxLevel = plugin.getConfig().getInt("settings.levels.max-level");
        int xp = 0;
        if(type.equalsIgnoreCase("Legendary"))
            xp = plugin.getConfig().getInt("settings.levels.xp-legendary");
        else if(type.equalsIgnoreCase("Epic"))
            xp = plugin.getConfig().getInt("settings.levels.xp-epic");
        else if(type.equalsIgnoreCase("Common"))
            xp = plugin.getConfig().getInt("settings.levels.xp-common");

        playerDataDatabase.addPlayerTotalXp(uuid, xp);
        if(playerDataDatabase.getPlayerLevel(uuid) >= maxLevel){
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.level.highest-level"));
            return;
        }

        playerDataDatabase.addPlayerXp(uuid, xp);

        if(playerDataDatabase.getPlayerXp(uuid) + xp >= levelXp){
            playerDataDatabase.removePlayerXp(uuid, levelXp);
            playerDataDatabase.addPlayerLevel(uuid, 1);
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.levels.level-up")
                    .replace("%level%", String.valueOf(playerDataDatabase.getPlayerLevel(uuid) + 1)));
            return;
        }

        genericUseMethods.sendMessageWithPrefix(player,
                plugin.getConfig().getString("messages.levels.added-xp")
                        .replace("%xp%", String.valueOf(xp))
                        .replace("%levelxp%", String.valueOf(playerDataDatabase.getPlayerXp(uuid) + xp))
                        .replace("%level%", String.valueOf(playerDataDatabase.getPlayerLevel(uuid))));
    }
}
