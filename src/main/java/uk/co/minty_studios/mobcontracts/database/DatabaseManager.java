package uk.co.minty_studios.mobcontracts.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.utils.ContractObject;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.io.File;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

public class DatabaseManager {

    private final MobContracts plugin;
    private static final Map<UUID, PlayerObject> playerMap = new HashMap<>();
    private static final Map<UUID, ContractObject> contractMap = new HashMap<>();

    public DatabaseManager(MobContracts plugin) {
        this.plugin = plugin;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, PlayerObject> getPlayerMap(){
        return playerMap;
    }

    public Map<UUID, ContractObject> getContractMap(){
        return contractMap;
    }

    private Connection playerConnect(){
        File file = new File(plugin.getDataFolder(), "PlayerDatabase.db");
        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return con;
    }

    private Connection contractConnect(){
        File file = new File(plugin.getDataFolder(), "ContractsDatabase.db");
        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    public void async(Runnable runnable){
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, runnable);
    }

    public void sync(Runnable runnable){
        Bukkit.getScheduler().runTask(this.plugin, runnable);
    }

    public void createPlayerDatabase(){
        this.async(() -> {
            try(Connection con = this.playerConnect()) {
                String sql = "CREATE TABLE IF NOT EXISTS PLAYERDATA (" +
                        "UUID TEXT PRIMARY KEY NOT NULL, " +
                        "COMMON INTEGER NOT NULL, " +
                        "EPIC INTEGER NOT NULL, " +
                        "LEGENDARY INTEGER NOT NULL, " +
                        "TOTAL INTEGER NOT NULL, " +
                        "COMMON_OWNED INTEGER NOT NULL, " +
                        "EPIC_OWNED INTEGER NOT NULL, " +
                        "LEGENDARY_OWNED INTEGER NOT NULL, " +
                        "TOTAL_OWNED INTEGER NOT NULL, " +
                        "XP INTEGER NOT NULL, " +
                        "LEVEL INTEGER NOT NULL, " +
                        "TOTALXP INTEGER NOT NULL);";

                PreparedStatement prep = con.prepareStatement(sql);
                prep.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void newPlayer(Player player){
        if(!playerExists(player.getUniqueId())){
            playerMap.put(player.getUniqueId(), new PlayerObject(player.getUniqueId(), player.getName(), 0, 0, 0, 0, 0,
                    1, 0, 0, 0, 0, 0));
        }
    }

    private Boolean playerExists(UUID uuid){
        if(playerMap.containsKey(uuid))
            return true;
        return false;
    }

    public void loadAllPlayers(){ // for main class after connect()
        String sql = "SELECT * FROM PLAYERDATA";

        this.sync(() -> {
            try(Connection con = this.playerConnect(); PreparedStatement prep = con.prepareStatement(sql)){
                ResultSet rs = prep.executeQuery();
                while(rs.next()){
                    UUID uuid = UUID.fromString(rs.getString("UUID"));

                    String name = plugin.getServer().getPlayer(uuid) != null
                            ? plugin.getServer().getPlayer(uuid).getName()
                            : plugin.getServer().getOfflinePlayer(uuid).getName();

                    int commonSlain = rs.getInt("COMMON");
                    int epicSlain = rs.getInt("EPIC");
                    int legendarySlain = rs.getInt("LEGENDARY");
                    int totalSlain = rs.getInt("TOTAL");
                    int currentXp = rs.getInt("XP");
                    int currentLevel = rs.getInt("LEVEL");
                    int totalXp = rs.getInt("TOTALXP");
                    int commonOwned = rs.getInt("COMMON_OWNED");
                    int epicOwned = rs.getInt("EPIC_OWNED");
                    int legendaryOwned = rs.getInt("LEGENDARY_OWNED");
                    int totalOwned = rs.getInt("TOTAL_OWNED");

                    playerMap.put(uuid, new PlayerObject(uuid, name, commonSlain, epicSlain, legendarySlain, totalSlain, currentXp,
                            currentLevel, totalXp, commonOwned, epicOwned, legendaryOwned, totalOwned));
                }

                plugin.getLogger().info("Players loaded!");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void loadPlayer(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        this.sync( () -> {
            try(Connection con = this.playerConnect(); PreparedStatement prep = con.prepareStatement(sql)){
                ResultSet rs = prep.executeQuery();
                rs.next();

                String name = plugin.getServer().getPlayer(uuid) != null
                        ? plugin.getServer().getPlayer(uuid).getName()
                        : plugin.getServer().getOfflinePlayer(uuid).getName();

                int commonSlain = rs.getInt("COMMON");
                int epicSlain = rs.getInt("EPIC");
                int legendarySlain = rs.getInt("LEGENDARY");
                int totalSlain = rs.getInt("TOTAL");
                int currentXp = rs.getInt("XP");
                int currentLevel = rs.getInt("LEVEL");
                int totalXp = rs.getInt("TOTALXP");
                int commonOwned = rs.getInt("COMMON_OWNED");
                int epicOwned = rs.getInt("EPIC_OWNED");
                int legendaryOwned = rs.getInt("LEGENDARY_OWNED");
                int totalOwned = rs.getInt("TOTAL_OWNED");

                if(playerMap.containsKey(uuid))
                    playerMap.get(uuid).updatePlayer(commonSlain, epicSlain, legendarySlain, totalSlain, currentXp, currentLevel,
                            totalXp, commonOwned, epicOwned, legendaryOwned, totalOwned);
                else
                    playerMap.put(uuid, new PlayerObject(uuid, name, commonSlain, epicSlain, legendarySlain, totalSlain, currentXp,
                            currentLevel, totalXp, commonOwned, epicOwned, legendaryOwned, totalOwned));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void updateDatabase(){
        String sql = "INSERT INTO PLAYERDATA (UUID,COMMON,EPIC,LEGENDARY,TOTAL,COMMON_OWNED,EPIC_OWNED,LEGENDARY_OWNED,TOTAL_OWNED,XP,LEVEL,TOTALXP) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT(UUID) DO UPDATE SET " +
                "COMMON = EXCLUDED.COMMON, EPIC = EXCLUDED.EPIC, LEGENDARY = EXCLUDED.LEGENDARY, TOTAL = EXCLUDED.TOTAL, " +
                "COMMON_OWNED = EXCLUDED.COMMON_OWNED, EPIC_OWNED = EXCLUDED.EPIC_OWNED, LEGENDARY_OWNED = EXCLUDED.LEGENDARY_OWNED, TOTAL_OWNED = EXCLUDED.TOTAL_OWNED, " +
                "XP = EXCLUDED.XP, LEVEL = EXCLUDED.LEVEL, TOTALXP = EXCLUDED.TOTALXP";

        this.async(() -> {
            try(Connection con = this.playerConnect(); PreparedStatement prep = con.prepareStatement(sql)){
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()){
                    prep.setString(1, String.valueOf(entry.getKey()));
                    prep.setInt(2, entry.getValue().getCommonSlain());
                    prep.setInt(3, entry.getValue().getEpicSlain());
                    prep.setInt(4, entry.getValue().getLegendarySlain());
                    prep.setInt(5, entry.getValue().getTotalSlain());
                    prep.setInt(6, entry.getValue().getCommonOwned());
                    prep.setInt(7, entry.getValue().getEpicOwned());
                    prep.setInt(8, entry.getValue().getLegendaryOwned());
                    prep.setInt(9, entry.getValue().getTotalOwned());
                    prep.setInt(10, entry.getValue().getCurrentXp());
                    prep.setInt(11, entry.getValue().getCurrentLevel());
                    prep.setInt(12, entry.getValue().getTotalXp());
                    prep.addBatch();
                }
                prep.executeBatch(); // not working?
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void savePlayers(){
        plugin.getLogger().info("Saving player data... This may take a moment.");

        String sql = "INSERT INTO PLAYERDATA (UUID,COMMON,EPIC,LEGENDARY,TOTAL,COMMON_OWNED,EPIC_OWNED,LEGENDARY_OWNED,TOTAL_OWNED,XP,LEVEL,TOTALXP) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT(UUID) DO UPDATE SET " +
                "COMMON = EXCLUDED.COMMON, EPIC = EXCLUDED.EPIC, LEGENDARY = EXCLUDED.LEGENDARY, TOTAL = EXCLUDED.TOTAL, " +
                "COMMON_OWNED = EXCLUDED.COMMON_OWNED, EPIC_OWNED = EXCLUDED.EPIC_OWNED, LEGENDARY_OWNED = EXCLUDED.LEGENDARY_OWNED, TOTAL_OWNED = EXCLUDED.TOTAL_OWNED, " +
                "XP = EXCLUDED.XP, LEVEL = EXCLUDED.LEVEL, TOTALXP = EXCLUDED.TOTALXP";

        try(Connection con = this.playerConnect(); PreparedStatement prep = con.prepareStatement(sql)){
            for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()){
                prep.setString(1, String.valueOf(entry.getKey()));
                prep.setInt(2, entry.getValue().getCommonSlain());
                prep.setInt(3, entry.getValue().getEpicSlain());
                prep.setInt(4, entry.getValue().getLegendarySlain());
                prep.setInt(5, entry.getValue().getTotalSlain());
                prep.setInt(6, entry.getValue().getCommonOwned());
                prep.setInt(7, entry.getValue().getEpicOwned());
                prep.setInt(8, entry.getValue().getLegendaryOwned());
                prep.setInt(9, entry.getValue().getTotalOwned());
                prep.setInt(10, entry.getValue().getCurrentXp());
                prep.setInt(11, entry.getValue().getCurrentLevel());
                prep.setInt(12, entry.getValue().getTotalXp());
                prep.addBatch();
            }
            prep.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        plugin.getLogger().info("Player data saved!");
    }




    // CONTRACTS DATABASE STUFFZ

    public void createContractsDatabase(){
        this.async(() -> {
            try(Connection con = this.contractConnect()){

                String sql = "CREATE TABLE IF NOT EXISTS MOBDATA (" +
                        "UUID TEXT NOT NULL, " +
                        "MOBUUID TEXT PRIMARY KEY NOT NULL, " +
                        "DISPLAYNAME TEXT NOT NULL, " +
                        "CONTRACTTYPE TEXT NOT NULL, " +
                        "TYPE TEXT NOT NULL, " +
                        "HEALTH INTEGER NOT NULL, " +
                        "DAMAGE INTEGER NOT NULL, " +
                        "DATE INTEGER NOT NULL);";

                PreparedStatement prep = con.prepareStatement(sql);
                prep.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void addToContractsMap(UUID uuid, String name, UUID mobUuid, String displayName, String tier, EntityType type, int health, int damage) {
        contractMap.put(mobUuid, new ContractObject(
                uuid, name, mobUuid, displayName, tier, type, health, damage, Instant.now().getEpochSecond())
        );
    }

    public void saveContracts(){
        String sql = "INSERT INTO MOBDATA (UUID,MOBUUID,DISPLAYNAME,CONTRACTTYPE,TYPE,HEALTH,DAMAGE,DATE) VALUES (?,?,?,?,?,?,?,?) ON CONFLICT(MOBUUID) DO NOTHING";

        this.async(() -> {
            try(Connection con = this.contractConnect(); PreparedStatement prep = con.prepareStatement(sql)){
                for(Map.Entry<UUID, ContractObject> entry : contractMap.entrySet()){
                    prep.setString(1, String.valueOf(entry.getValue().getSummonerUuid()));
                    prep.setString(2, String.valueOf(entry.getValue().getContractUuid()));
                    prep.setString(3, entry.getValue().getDisplayName());
                    prep.setString(4, entry.getValue().getTier());
                    prep.setString(5, entry.getValue().getMobType().name());
                    prep.setInt(6, entry.getValue().getHealth());
                    prep.setInt(7, entry.getValue().getDamage());
                    prep.setLong(8, entry.getValue().getDate());
                    prep.addBatch();
                }

                prep.executeBatch();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public void saveContractsOnDisable(){
        String sql = "INSERT INTO MOBDATA (UUID,MOBUUID,DISPLAYNAME,CONTRACTTYPE,TYPE,HEALTH,DAMAGE,DATE) VALUES (?,?,?,?,?,?,?,?) ON CONFLICT(MOBUUID) DO NOTHING";

        try(Connection con = this.contractConnect(); PreparedStatement prep = con.prepareStatement(sql)){
            for(Map.Entry<UUID, ContractObject> entry : contractMap.entrySet()){
                prep.setString(1, String.valueOf(entry.getValue().getSummonerUuid()));
                prep.setString(2, String.valueOf(entry.getValue().getContractUuid()));
                prep.setString(3, entry.getValue().getDisplayName());
                prep.setString(4, entry.getValue().getTier());
                prep.setString(5, entry.getValue().getMobType().name());
                prep.setInt(6, entry.getValue().getHealth());
                prep.setInt(7, entry.getValue().getDamage());
                prep.setString(8, String.valueOf(entry.getValue().getDate()));
                prep.addBatch();
            }

            prep.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadAllContracts(){
        String sql = "SELECT * FROM MOBDATA";

        this.sync( () -> {
            try(Connection con = this.contractConnect()){
                PreparedStatement prep = con.prepareStatement(sql);
                ResultSet rs = prep.executeQuery();
                while(rs.next()){
                    UUID uuid = UUID.fromString(rs.getString("UUID"));
                    UUID mobUuid = UUID.fromString(rs.getString("MOBUUID"));
                    String displayName = rs.getString("DISPLAYNAME");
                    String tier = rs.getString("CONTRACTTYPE");
                    EntityType type = EntityType.valueOf(rs.getString("TYPE"));
                    int health = rs.getInt("HEALTH");
                    int damage = rs.getInt("DAMAGE");
                    long date = rs.getLong("DATE");

                    String name = plugin.getServer().getPlayer(uuid) != null
                            ? plugin.getServer().getPlayer(uuid).getName()
                            : plugin.getServer().getOfflinePlayer(uuid).getName();

                    contractMap.put(mobUuid, new ContractObject(uuid, name, mobUuid, displayName, tier, type, health, damage, date));
                }

                plugin.getLogger().info("Contracts loaded!");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    // INFORMATION GETTERS

    public int getStat(StatType type){
        int i = 0;
        switch(type){
            case TOTAL_PLAYERS:
                return playerMap.size();
            case TOTAL_CONTRACTS:
                return contractMap.size();
            case SLAIN_COMMON:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getCommonSlain();
                return i;
            case SLAIN_EPIC:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getEpicSlain();
                return i;
            case SLAIN_LEGENDARY:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getLegendarySlain();
                return i;
            case OWNED_COMMON:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getCommonOwned();
                return i;
            case OWNED_EPIC:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getEpicOwned();
                return i;
            case OWNED_LEGENDARY:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getLegendaryOwned();
                return i;
            case TOTAL_LEVELS:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getCurrentLevel();
                return i;
            case TOTAL_TOTALXP:
                for(Map.Entry<UUID, PlayerObject> entry : playerMap.entrySet()) i += entry.getValue().getTotalXp();
                return i;
            case TOTAL_DAMAGE:
                for(Map.Entry<UUID, ContractObject> entry : contractMap.entrySet()) i += entry.getValue().getDamage();
                return i;
            case TOTAL_HEALTH:
                for(Map.Entry<UUID, ContractObject> entry : contractMap.entrySet()) i += entry.getValue().getHealth();
                return i;
        }

        return i;
    }

    public enum StatType {
        TOTAL_PLAYERS, TOTAL_CONTRACTS, SLAIN_COMMON, SLAIN_EPIC, SLAIN_LEGENDARY, OWNED_COMMON, OWNED_EPIC, OWNED_LEGENDARY,
        TOTAL_LEVELS, TOTAL_TOTALXP, TOTAL_DAMAGE, TOTAL_HEALTH
    }

}