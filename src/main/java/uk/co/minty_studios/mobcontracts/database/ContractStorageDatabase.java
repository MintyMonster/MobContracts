package uk.co.minty_studios.mobcontracts.database;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;

import java.sql.*;
import java.util.UUID;

public class ContractStorageDatabase {

    private static Connection connection;
    private static Statement statement;
    private final MobContracts plugin;
    private final Database database;

    public ContractStorageDatabase(MobContracts plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    // COMMON EPIC LEGENDARY
    public int getTotalStat(String column) {
        String sql = "SELECT * FROM CONTRACTSTORAGE";
        int total = 0;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                total += rs.getInt(column.toUpperCase());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return total;
    }

    public void addPlayer(Player player) {
        String sql = "INSERT INTO CONTRACTSTORAGE (UUID,COMMON,EPIC,LEGENDARY) " +
                "VALUES (?,?,?,?)";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, String.valueOf(player.getUniqueId()));
                    prep.setInt(2, 0);
                    prep.setInt(3, 0);
                    prep.setInt(4, 0);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public Boolean uuidExists(UUID uuid) {
        String sql = "SELECT * FROM CONTRACTSTORAGE WHERE UUID = '" + uuid + "';";
        Boolean exists;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            return rs.isBeforeFirst();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public int getCommonContracts(UUID uuid) {
        String sql = "SELECT * FROM CONTRACTSTORAGE WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(3);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int getEpicContracts(UUID uuid) {
        String sql = "SELECT * FROM CONTRACTSTORAGE WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(4);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int getLegendaryContracts(UUID uuid) {
        String sql = "SELECT * FROM CONTRACTSTORAGE WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(5);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void useCommonContract(UUID uuid) {
        String sql = "UPDATE CONTRACTSTORAGE SET COMMON = COMMON - 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void useEpicContract(UUID uuid) {
        String sql = "UPDATE CONTRACTSTORAGE SET EPIC = EPIC - 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void useLegendaryContract(UUID uuid) {
        String sql = "UPDATE CONTRACTSTORAGE SET LEGENDARY = LEGENDARY - 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addCommonContract(UUID uuid, int amount) {
        String sql = "UPDATE CONTRACTSTORAGE SET COMMON = COMMON + " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addEpicContract(UUID uuid, int amount) {
        String sql = "UPDATE CONTRACTSTORAGE SET EPIC = EPIC + " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addLegendaryContract(UUID uuid, int amount) {
        String sql = "UPDATE CONTRACTSTORAGE SET LEGENDARY = LEGENDARY + " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
