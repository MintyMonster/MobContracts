package uk.co.minty_studios.mobcontracts.database;

import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.utils.ContractObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobDataDatabase {

    private final MobContracts plugin;
    private final Database database;
    private static final Map<UUID, ContractObject> contractMap = new HashMap<>();

    public MobDataDatabase(MobContracts plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    public void addContract(String name, UUID uuid, UUID mobUuid, String contracttype, String type, double health, double damage) {
        String sql = "INSERT INTO MOBDATA (UUID,MOBUUID,DISPLAYNAME,CONTRACTTYPE,TYPE,HEALTH,DAMAGE) VALUE (?,?,?,?,?,?,?);";

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection con = database.getConnected()) {
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, String.valueOf(uuid));
                    prep.setString(2, String.valueOf(mobUuid));
                    prep.setString(3, name);
                    prep.setString(4, contracttype);
                    prep.setString(5, type);
                    prep.setDouble(6, health);
                    prep.setDouble(7, damage);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void loadHashMap() {
        String sql = "SELECT * FROM MOBDATA";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("UUID"));
                UUID mobUuid = UUID.fromString(rs.getString("MOBUUID"));
                String displayName = rs.getString("DISPLAYNAME");
                String tier = rs.getString("CONTRACTTYPE");
                EntityType type = EntityType.valueOf(rs.getString("TYPE"));
                int health = rs.getInt("HEALTH");
                int damage = rs.getInt("DAMAGE");
                Date date = formatter.parse(rs.getString("DATE"));

                String name = plugin.getServer().getPlayer(uuid) != null
                        ? plugin.getServer().getPlayer(uuid).getName()
                        : plugin.getServer().getOfflinePlayer(uuid).getName();

                contractMap.put(mobUuid, new ContractObject(uuid, name, mobUuid, displayName, tier, type, health, damage, date));
            }

            plugin.getLogger().info("Contract data loaded!");

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }

    public void unloadHashMap() {
        contractMap.clear();
    }

    public void addToHashMap(UUID uuid, String name, UUID mobUuid, String displayName, String tier, EntityType type, int health, int damage) {
        try {
            contractMap.put(mobUuid, new ContractObject(
                    uuid, name, mobUuid, displayName, tier, type, health, damage,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(LocalDate.now().toString() + " " + LocalTime.now().toString())
            ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, ContractObject> getContractMap() {
        return contractMap;
    }

    public String getOwner(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        String name = null;
        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            UUID playerUuid = UUID.fromString(rs.getString("UUID"));
            if (plugin.getServer().getPlayer(playerUuid) != null) {
                name = plugin.getServer().getPlayer(playerUuid).getName();
            } else {
                name = plugin.getServer().getOfflinePlayer(playerUuid).getName();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return name == null ? "Null" : name;
    }

    public String getTier(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        String type = null;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            type = rs.getString("CONTRACTTYPE");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return type == null ? "Empty" : type;
    }

    public String getName(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        String name = null;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            name = rs.getString("DISPLAYNAME");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return name == null ? "&cNo name" : name;
    }

    public int getDamage(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        int damage = 0;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            damage += rs.getInt("DAMAGE");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return damage;
    }

    public int getHealth(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        int health = 0;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            health += rs.getInt("HEALTH");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return health;
    }

    public String getDate(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        String date = null;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            date = rs.getString("DATE");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return date == null ? "Null" : date;
    }

    public String getType(UUID uuid) {
        String sql = "SELECT * FROM MOBDATA WHERE MOBUUID = '" + uuid + "'";
        String type = null;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            type = rs.getString("TYPE");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return type == null ? "NONE" : type;
    }

    // HEALTH DAMAGE
    public int getTotalStat(String column) {
        String sql = "SELECT * FROM MOBDATA";
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

    public int getTotalCountContracts() {
        String sql = "SELECT * FROM MOBDATA";
        int total = 0;

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                total++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return total;
    }

    public int countPlayerSlain(UUID uuid) {
        String sql = "SELECT COUNT(*) AS ROWCOUNT FROM MOBDATA WHERE UUID = '" + uuid + "'";

        try (Connection con = database.getConnected()) {
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt("ROWCOUNT");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }
}