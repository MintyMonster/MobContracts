package uk.co.minty_studios.mobcontracts.database;

import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;

import java.sql.*;
import java.util.UUID;

public class MobDataDatabase {

    private static Connection connection;
    private static Statement statement;
    private final MobContracts plugin;
    private final Database database;

    public MobDataDatabase(MobContracts plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    public void addContract(String name, UUID uuid, String contracttype, String type, double health, double damage){
        String sql = "INSERT INTO MOBDATA (UUID,DISPLAYNAME,CONTRACTTYPE,TYPE,HEALTH,DAMAGE) VALUE (?,?,?,?,?,?);";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, String.valueOf(uuid));
                    prep.setString(2, name);
                    prep.setString(3, contracttype);
                    prep.setString(4, type);
                    prep.setDouble(5, health);
                    prep.setDouble(6, damage);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public int getTotalCountContracts(){
        String sql = "SELECT COUNT(*) FROM MOBDATA";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.last();
            return rs.getRow();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int countPlayerSlain(UUID uuid){
        String sql = "SELECT COUNT(*) AS ROWCOUNT FROM MOBDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
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