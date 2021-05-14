package uk.co.minty_studios.mobcontracts.database;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.utils.PlayerObject;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataDatabase {

    private static Connection connection;
    private static Statement statement;
    private final MobContracts plugin;
    private final Database database;
    private final ContractStorageDatabase contractStorageDatabase;
    private static final Map<UUID, PlayerObject> playerMap = new HashMap<>();

    public PlayerDataDatabase(MobContracts plugin, Database database, ContractStorageDatabase contractStorageDatabase) {
        this.plugin = plugin;
        this.database = database;
        this.contractStorageDatabase = contractStorageDatabase;
    }

    public void addPlayer(Player player) {
        String sql = "INSERT INTO PLAYERDATA (UUID,COMMON,EPIC,LEGENDARY,TOTAL,XP,LEVEL,TOTALXP) VALUES (?,?,?,?,?,?,?,?);";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, String.valueOf(player.getUniqueId()));
                    prep.setInt(2, 0);
                    prep.setInt(3, 0);
                    prep.setInt(4, 0);
                    prep.setInt(5, 0);
                    prep.setInt(6, 0);
                    prep.setInt(7, 1);
                    prep.setInt(8, 0);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void loadPlayers() {
        String sql = "SELECT * FROM PLAYERDATA";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
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
                playerMap.put(uuid, new PlayerObject(
                        uuid, name, commonSlain, epicSlain, legendarySlain, totalSlain, currentXp, currentLevel, totalXp
                ));
            }
            plugin.getLogger().info("Players loaded!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updatePlayer(final UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";
        playerMap.remove(uuid);

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
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
            playerMap.put(uuid, new PlayerObject(
                    uuid, name, commonSlain, epicSlain, legendarySlain, totalSlain, currentXp, currentLevel, totalXp
            ));

            plugin.getLogger().info("Player - " + name + " updated!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Map<UUID, PlayerObject> getPlayerMap() {
        return playerMap;
    }

    public Boolean uuidExists(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "';";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            return rs.isBeforeFirst();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getTotalCountPlayers() {
        String sql = "SELECT * FROM PLAYERDATA";
        int amount = 0;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                amount++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return amount;
    }

    public int getCommon(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

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

    public int getEpic(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

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

    public int getLegendary(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

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

    public int getTotalSlain(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(6);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void addContract(UUID uuid, String contract) {
        String sql = "UPDATE PLAYERDATA SET " + contract.toUpperCase() + " = " + contract.toUpperCase() + " + 1 WHERE UUID = '" + uuid + "'";
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
        addTotal(uuid);
    }

    public void addTotal(UUID uuid) {
        String sql = "UPDATE PLAYERDATA SET TOTAL = TOTAL + 1 WHERE UUID = '" + uuid + "'";

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

    public int getPlayerXp(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(7);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void addPlayerXp(UUID uuid, int amount) {
        String sql = "UPDATE PLAYERDATA SET XP = XP + " + amount + " WHERE UUID = '" + uuid + "'";

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

    public void removePlayerXp(UUID uuid, int amount) {
        String sql = "UPDATE PLAYERDATA SET XP = XP - " + amount + " WHERE UUID = '" + uuid + "'";

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

    public void setPlayerXp(UUID uuid, int amount) {
        String sql = "UPDATE PLAYERDATA SET XP = " + amount + " WHERE UUID = '" + uuid + "'";

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

    public int getPlayerLevel(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(8);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 1;
    }

    // Level Booleans for returning error messages etc

    public Boolean addPlayerLevel(UUID uuid, int amount) {
        int currentLevel = getPlayerLevel(uuid);
        String sql = "UPDATE PLAYERDATA SET LEVEL = LEVEL + " + amount + " WHERE UUID = '" + uuid + "'";

        if (currentLevel >= plugin.getConfig().getInt("settings.levels.max-level"))
            return false;

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

        return true;
    }

    public Boolean removePlayerLevel(UUID uuid, int amount) {
        int currentLevel = getPlayerLevel(uuid);
        String sql = "UPDATE PLAYERDATA SET LEVEL = LEVEL - " + amount + " WHERE UUID = '" + uuid + "'";

        if (amount > currentLevel)
            return false;

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

        return true;
    }

    public Boolean setPlayerLevel(UUID uuid, int amount) {
        int currentLevel = getPlayerLevel(uuid);
        String sql = "UPDATE PLAYERDATA SET LEVEL = " + amount + " WHERE UUID = '" + uuid + "'";

        if (currentLevel > plugin.getConfig().getInt("settings.levels.max-level"))
            return false;

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

        return true;
    }

    public int getPlayerTotalXp(UUID uuid) {
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {

            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(9);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void addPlayerTotalXp(UUID uuid, int amount) {
        String sql = "UPDATE PLAYERDATA SET TOTALXP = TOTALXP + " + amount + " WHERE UUID = '" + uuid + "'";

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

    // SLAIN COMMON EPIC LEGENDARY
    // LEVELS EXP
    public int getTotalStat(String column) {
        String sql = "SELECT * FROM PLAYERDATA";
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


}
