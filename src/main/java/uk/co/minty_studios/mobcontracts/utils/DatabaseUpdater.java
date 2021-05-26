package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.database.DatabaseManager;

public class DatabaseUpdater {

    private final DatabaseManager databaseManager;
    private final MobContracts plugin;

    public DatabaseUpdater(DatabaseManager databaseManager, MobContracts plugin) {
        this.databaseManager = databaseManager;
        this.plugin = plugin;
    }

    public void updateDatabaseTimer(){
        new BukkitRunnable(){
            @Override
            public void run(){
                if(plugin.isEnabled){
                    databaseManager.updateDatabase();
                    databaseManager.saveContracts();
                }
            }
        }.runTaskTimer(plugin, 6000, 6000);
    }
}