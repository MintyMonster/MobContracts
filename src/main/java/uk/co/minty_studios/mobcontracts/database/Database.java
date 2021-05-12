package uk.co.minty_studios.mobcontracts.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static Connection connection;
    private static Statement statement;
    private final MobContracts plugin;
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    public Database(MobContracts plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        config.setJdbcUrl("jdbc:mysql://" + plugin.getConfig().getString("settings.database.host")
                + ":" + plugin.getConfig().getInt("settings.database.port")
                + "/" + plugin.getConfig().getString("settings.database.database-name"));
        config.setUsername(plugin.getConfig().getString("settings.database.username"));
        config.setPassword(plugin.getConfig().getString("settings.database.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    public void disconnect() {
        if (dataSource != null)
            dataSource.close();
    }

    public Boolean dataSourceExists() {
        return dataSource != null;
    }

    protected Connection getConnected() throws SQLException {
        return dataSource.getConnection();
    }

    public void createPlayerDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS PLAYERDATA (" +
                "ID INT AUTO_INCREMENT NOT NULL, " +
                "UUID VARCHAR(255) NOT NULL, " +
                "COMMON INT NOT NULL, " +
                "EPIC INT NOT NULL, " +
                "LEGENDARY INT NOT NULL, " +
                "TOTAL INT NOT NULL, " +
                "XP INT NOT NULL," +
                "LEVEL INT NOT NULL," +
                "TOTALXP INT NOT NULL, " +
                "PRIMARY KEY (ID));";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();
                    prep.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    plugin.getLogger().severe("PlayerData database ran into an error.");
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void createSlainDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS MOBDATA (" +
                "ID INT AUTO_INCREMENT NOT NULL, " +
                "UUID VARCHAR(255) NOT NULL, " +
                "MOBUUID VARCHAR(255) NOT NULL, " +
                "DISPLAYNAME VARCHAR(255) NOT NULL, " +
                "CONTRACTTYPE TEXT NOT NULL, " +
                "TYPE TEXT NOT NULL, " +
                "HEALTH INT NOT NULL, " +
                "DAMAGE INT NOT NULL, " +
                "DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "PRIMARY KEY(ID));";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();
                    prep.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    plugin.getLogger().severe("MobData database ran into an error.");
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void createContractStorage() {
        String sql = "CREATE TABLE IF NOT EXISTS CONTRACTSTORAGE (" +
                "ID INT AUTO_INCREMENT NOT NULL, " +
                "UUID VARCHAR(255) NOT NULL, " +
                "COMMON INT NOT NULL," +
                "EPIC INT NOT NULL, " +
                "LEGENDARY INT NOT NULL, " +
                "PRIMARY KEY(ID));";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();
                    prep.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    plugin.getLogger().severe("ContractStorage database ran into an error.");
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
