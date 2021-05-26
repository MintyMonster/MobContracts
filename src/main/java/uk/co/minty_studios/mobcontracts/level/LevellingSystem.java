package uk.co.minty_studios.mobcontracts.level;

import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;
import uk.co.minty_studios.mobcontracts.utils.GenericUseMethods;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.util.Map;
import java.util.UUID;

public class LevellingSystem {

    private final MobContracts plugin;
    private final GenericUseMethods genericUseMethods;
    private final DatabaseManager databaseManager;

    public LevellingSystem(MobContracts plugin, GenericUseMethods genericUseMethods, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.genericUseMethods = genericUseMethods;
        this.databaseManager = databaseManager;
    }

    public void levels(Player player, String type) {
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();
        UUID uuid = player.getUniqueId();
        int levelXp = map.get(uuid).getCurrentLevel() * plugin.getConfig().getInt("settings.levels.xp-multi");
        int maxLevel = plugin.getConfig().getInt("settings.levels.max-level");
        int xp = 0;
        if (type.equalsIgnoreCase("Legendary"))
            xp = plugin.getConfig().getInt("settings.levels.xp-legendary");
        else if (type.equalsIgnoreCase("Epic"))
            xp = plugin.getConfig().getInt("settings.levels.xp-epic");
        else if (type.equalsIgnoreCase("Common"))
            xp = plugin.getConfig().getInt("settings.levels.xp-common");

        map.get(uuid).setTotalXp(map.get(uuid).getTotalXp() + xp);

        if (map.get(uuid).getCurrentLevel() >= maxLevel) {
            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.level.highest-level"));
            return;
        }

        if(map.get(uuid).getCurrentXp() + xp >= levelXp){
            int level = map.get(uuid).getCurrentLevel();
            while(map.get(uuid).getCurrentXp() + xp >= levelXp){
                level++;
            }
            int newXp = (map.get(uuid).getCurrentXp() + xp) - ((level - 1) * plugin.getConfig().getInt("settings.levels.xp-multi"));
            map.get(uuid).setCurrentXp(newXp);
            map.get(uuid).setCurrentLevel(level);

            genericUseMethods.sendMessageWithPrefix(player, plugin.getConfig().getString("messages.levels.level-up")
                    .replace("%level%", String.valueOf(map.get(uuid).getCurrentLevel())));

        }else{
            map.get(uuid).setCurrentXp(map.get(uuid).getCurrentXp() + xp);

            genericUseMethods.sendMessageWithPrefix(player,
                    plugin.getConfig().getString("messages.levels.added-xp")
                            .replace("%xp%", String.valueOf(xp))
                            .replace("%levelxp%", String.valueOf(map.get(uuid).getCurrentXp()))
                            .replace("%level%", String.valueOf(map.get(uuid).getCurrentLevel())));
        }
    }
}
