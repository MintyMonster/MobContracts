package uk.co.minty_studios.mobcontracts;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.commands.CommandManager;
import uk.co.minty_studios.mobcontracts.contracts.CommonContract;
import uk.co.minty_studios.mobcontracts.contracts.EpicContract;
import uk.co.minty_studios.mobcontracts.contracts.LegendaryContract;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.Database;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.effects.CommonEffects;
import uk.co.minty_studios.mobcontracts.effects.EpicEffects;
import uk.co.minty_studios.mobcontracts.effects.LegendaryEffects;
import uk.co.minty_studios.mobcontracts.gui.handler.GuiUtil;
import uk.co.minty_studios.mobcontracts.level.LevellingSystem;
import uk.co.minty_studios.mobcontracts.listeners.*;
import uk.co.minty_studios.mobcontracts.mobs.MobFeatures;
import uk.co.minty_studios.mobcontracts.papi.MobContractsPlaceholderExpansion;
import uk.co.minty_studios.mobcontracts.papi.placeholders.*;
import uk.co.minty_studios.mobcontracts.utils.*;

import java.util.HashMap;
import java.util.Map;

public class MobContracts extends JavaPlugin {

    private CommandManager commandManager;
    private GenericUseMethods genericUseMethods;
    private Database database;
    private LegendaryContract legendaryContract;
    private CommonContract commonContract;
    private EpicContract epicContract;
    private MobFeatures mobFeatures;
    private MobDataDatabase mobDataDatabase;
    private LegendaryEffects legendaryEffects;
    private EpicEffects epicEffects;
    private ContractStorageDatabase contractStorageDatabase;
    private PlayerDataDatabase playerDataDatabase;
    private CurrentContracts currentContracts;
    private ContractType contractType;
    private LevellingSystem levellingSystem;
    private ContractStats contractStats;
    private CreateCustomGuiItem createCustomGuiItem;
    private MobContractsPlaceholderExpansion expansion;
    private CommonEffects commonEffects;

    private static final Map<Player, GuiUtil> playerMap = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getLogger().info("Loading classes...");
        database = new Database(this);
        contractType = new ContractType();
        currentContracts = new CurrentContracts();
        contractStorageDatabase = new ContractStorageDatabase(this, database);
        playerDataDatabase = new PlayerDataDatabase(this, database, contractStorageDatabase);
        genericUseMethods = new GenericUseMethods(this);
        levellingSystem = new LevellingSystem(this, playerDataDatabase, genericUseMethods);
        commonEffects = new CommonEffects(this);
        legendaryEffects = new LegendaryEffects(this, genericUseMethods, currentContracts);
        epicEffects = new EpicEffects(this, currentContracts);
        createCustomGuiItem = new CreateCustomGuiItem(this);
        mobDataDatabase = new MobDataDatabase(this, database);
        mobFeatures = new MobFeatures(this, genericUseMethods);
        legendaryContract = new LegendaryContract(this, mobFeatures, mobDataDatabase, legendaryEffects, contractType);
        commonContract = new CommonContract(this, mobFeatures, contractType, commonEffects);
        epicContract = new EpicContract(this, mobFeatures, epicEffects, contractType);
        commandManager = new CommandManager(this,
                genericUseMethods,
                legendaryContract,
                epicContract,
                commonContract,
                contractStorageDatabase,
                currentContracts, database, contractType, createCustomGuiItem, playerDataDatabase, mobDataDatabase);

        getLogger().info("Classes loaded!");

        getLogger().info("Connecting to database.");

        database.connect();
        getLogger().info("Connected to database!");


        getLogger().info("Checking databases exist...");
        // Do create things ya know

        database.createSlainDatabase();
        getLogger().info("Mob database exists/created!");
        database.createPlayerDatabase();
        getLogger().info("Player database exists/created!");
        database.createContractStorage();
        getLogger().info("Contract storage database exists/created!");
        getLogger().info("Databases are A-okay!");

        new BukkitRunnable(){
            @Override
            public void run(){
                getLogger().info("Loading leaderboard dependants");
                mobDataDatabase.loadHashMap();
                playerDataDatabase.loadPlayers();
                getLogger().info("Leaderboard dependants loaded");
            }
        }.runTaskLater(this, 100);

        getLogger().info("Registering events...");
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(playerDataDatabase, contractStorageDatabase, this), this);
        pluginManager.registerEvents(new PlayerLeaveListener(currentContracts, this), this);
        pluginManager.registerEvents(new ContractSummonListener(currentContracts, mobDataDatabase), this);
        pluginManager.registerEvents(new EntityDeathListener(this, contractType, currentContracts), this);
        pluginManager.registerEvents(new ContractKillListener(this, genericUseMethods, levellingSystem, currentContracts, contractType, playerDataDatabase, mobFeatures), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityTransformListener(), this);
        pluginManager.registerEvents(new GuiClickListener(), this);
        getLogger().info("Events registered");

        if(pluginManager.getPlugin("PlaceholderAPI") != null){
            getLogger().info("Registering placeholders...");
            expansion = new MobContractsPlaceholderExpansion(this);

            // %mobcontracts_player_level%
            expansion.registerPlaceholders(new PlayerLevelPlaceholder(playerDataDatabase));
            // %mobcontracts_player_experience%
            expansion.registerPlaceholders(new PlayerCurrentXpPlaceholder(playerDataDatabase));
            // %mobcontracts_player_total_experience%
            expansion.registerPlaceholders(new PlayerTotalXpPlaceholder(playerDataDatabase));
            // %mobcontracts_common_owned%
            expansion.registerPlaceholders(new CommonOwnedPlaceholder(playerDataDatabase));
            // %mobcontracts_epic_owned%
            expansion.registerPlaceholders(new EpicOwnedPlaceholder(playerDataDatabase));
            // %mobcontracts_legendary_owned%
            expansion.registerPlaceholders(new LegendaryOwnedPlaceholder(playerDataDatabase));
            // %mobcontracts_total_owned%
            expansion.registerPlaceholders(new TotalOwnedPlaceholder(playerDataDatabase));
            // %mobcontracts_common_slain%
            expansion.registerPlaceholders(new CommonSlainPlaceholder(playerDataDatabase));
            // %mobcontracts_epic_slain%
            expansion.registerPlaceholders(new EpicSlainPlaceholder(playerDataDatabase));
            // %mobcontracts_legendary_slain%
            expansion.registerPlaceholders(new LegendarySlainPlaceholder(playerDataDatabase));
            // %mobcontracts_total_slain%
            expansion.registerPlaceholders(new TotalSlainPlaceholder(playerDataDatabase));

            getLogger().info("Placeholders registered!");
            getLogger().info("Placeholders ready to use!");
        }else{
            getLogger().warning("To use my placeholders, please install PlaceholderAPI and restart the server! :)");
        }

        getLogger().info("Everything is now enabled. Hopefully we don't explode! ~Minty");
    }

    public void onDisable() {
        currentContracts.removeAllContracts();
        contractType.removeAllContracts();
        getLogger().info("Contracts removed.");
        database.disconnect();
        getLogger().info("Database disconnected.");
        reloadConfig();
        getLogger().info("Config.yml saved");
        getLogger().info("Disabled");
    }

    public void sendConsoleError(String message) {
        getLogger().severe(message);
    }

    public void sendConsoleMessage(String message) {
        getLogger().info(message);
    }

    public GuiUtil getMenuUtil(Player p) {
        GuiUtil util;

        if (playerMap.containsKey(p))
            return playerMap.get(p);
        else {
            util = new GuiUtil(p);
            playerMap.put(p, util);

            return util;
        }
    }
}
