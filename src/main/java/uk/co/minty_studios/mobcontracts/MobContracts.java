package uk.co.minty_studios.mobcontracts;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.minty_studios.mobcontracts.commands.CommandManager;
import uk.co.minty_studios.mobcontracts.contracts.CommonContract;
import uk.co.minty_studios.mobcontracts.contracts.EpicContract;
import uk.co.minty_studios.mobcontracts.contracts.LegendaryContract;
import uk.co.minty_studios.mobcontracts.database.ContractStorageDatabase;
import uk.co.minty_studios.mobcontracts.database.Database;
import uk.co.minty_studios.mobcontracts.database.MobDataDatabase;
import uk.co.minty_studios.mobcontracts.database.PlayerDataDatabase;
import uk.co.minty_studios.mobcontracts.levelsystem.LevellingSystem;
import uk.co.minty_studios.mobcontracts.listeners.*;
import uk.co.minty_studios.mobcontracts.mobeffects.EpicEffects;
import uk.co.minty_studios.mobcontracts.mobeffects.LegendaryEffects;
import uk.co.minty_studios.mobcontracts.mobs.MobFeatures;
import uk.co.minty_studios.mobcontracts.utils.*;

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

    @Override
    public void onEnable(){
        this.saveDefaultConfig();

        getLogger().info("Loading all of the things!");
        this.database = new Database(this);
        this.contractType = new ContractType();
        this.currentContracts = new CurrentContracts();
        this.playerDataDatabase = new PlayerDataDatabase(this, database);
        this.contractStorageDatabase = new ContractStorageDatabase(this, database);
        this.genericUseMethods = new GenericUseMethods(this);
        this.levellingSystem = new LevellingSystem(this, playerDataDatabase, genericUseMethods);
        this.legendaryEffects = new LegendaryEffects(this, genericUseMethods, currentContracts);
        this.epicEffects = new EpicEffects(this, currentContracts);
        this.mobDataDatabase = new MobDataDatabase(this, database);
        this.mobFeatures = new MobFeatures(this, genericUseMethods);
        this.legendaryContract = new LegendaryContract(this, mobFeatures, mobDataDatabase, legendaryEffects, contractType);
        this.commonContract = new CommonContract(this, mobFeatures, contractType);
        this.epicContract = new EpicContract(this, mobFeatures, epicEffects, contractType);
        this.commandManager = new CommandManager(this,
                genericUseMethods,
                legendaryContract,
                epicContract,
                commonContract,
                contractStorageDatabase,
                currentContracts, database, contractType);

        getLogger().info("All of the things are loaded!");

        getLogger().info("Connecting to database.");

        try{
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

        }catch(Exception e){
            getLogger().severe("Database connection not successful. Do you have the correct details? Check config.yml");
        }

        getLogger().info("Registering events...");
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(playerDataDatabase, contractStorageDatabase), this);
        pluginManager.registerEvents(new PlayerLeaveListener(currentContracts, this), this);
        pluginManager.registerEvents(new ContractSummonListener(currentContracts, mobDataDatabase), this);
        pluginManager.registerEvents(new EntityDeathListener(this, contractType, currentContracts), this);
        pluginManager.registerEvents(new ContractKillListener(this, genericUseMethods, levellingSystem, currentContracts, contractType, playerDataDatabase), this);
        pluginManager.registerEvents(new EntityDamageListener(this), this);
        pluginManager.registerEvents(new EntityTransformListener(), this);
        getLogger().info("Events registered");

        getLogger().info("Enabled! <3 ~Minty");
    }

    public void onDisable(){
        currentContracts.removeAllContracts();
        getLogger().info("Contracts removed.");
        contractType.removeAllContracts();
        database.disconnect();
        getLogger().info("Database disconnected.");
        reloadConfig();
        getLogger().info("Config.yml saved");
        getLogger().info("Disabled");
    }

    public void sendConsoleError(String message){
        getLogger().severe(message);
    }

    public void sendConsoleMessage(String message){
        getLogger().info(message);
    }
}
