package uk.co.minty_studios.mobcontracts.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.minty_studios.mobcontracts.MobContracts;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class PlayerDataDatabase {

    private static Connection connection;
    private static Statement statement;
    private final MobContracts plugin;
    private final Database database;

    public PlayerDataDatabase(MobContracts plugin, Database database) {
        this.plugin = plugin;
        this.database = database;
    }

    public void addPlayer(Player player){
        String sql = "INSERT INTO PLAYERDATA (UUID,COMMON,EPIC,LEGENDARY,TOTAL,XP,LEVEL,TOTALXP) VALUES (?,?,?,?,?,?,?,?);";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
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

    public Boolean uuidExists(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "';";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            return rs.isBeforeFirst();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getTotalCountPlayers(){
        String sql = "SELECT COUNT(*) FROM PLAYERDATA";

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

    public int getCommon(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(3);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int getEpic(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(4);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int getLegendary(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(5);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int getTotalSlain(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(6);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void addCommon(UUID uuid){
        String sql = "UPDATE PLAYERDATA SET COMMON = COMMON + 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
        addTotal(uuid);
    }

    public void addEpic(UUID uuid){
        String sql = "UPDATE PLAYERDATA SET EPIC = EPIC + 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
        addTotal(uuid);
    }

    public void addLegendary(UUID uuid){
        String sql = "UPDATE PLAYERDATA SET LEGENDARY = LEGENDARY + 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
        addTotal(uuid);
    }

    public void addTotal(UUID uuid){
        String sql = "UPDATE PLAYERDATA SET TOTAL = TOTAL + 1 WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public int getPlayerXp(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(7);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void addPlayerXp(UUID uuid, int amount){
        String sql = "UPDATE PLAYERDATA SET XP = XP + " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void removePlayerXp(UUID uuid, int amount){
        String sql = "UPDATE PLAYERDATA SET XP = XP - " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void setPlayerXp(UUID uuid, int amount){
        String sql = "UPDATE PLAYERDATA SET XP = " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public int getPlayerLevel(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){
            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(8);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 1;
    }

    public void addPlayerLevel(UUID uuid, int amount){
        String sql = "UPDATE PLAYERDATA SET LEVEL = LEVEL + " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void setPlayerLevel(UUID uuid, int amount){
        String sql = "UPDATE PLAYERDATA SET LEVEL = " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public int getPlayerTotalXp(UUID uuid){
        String sql = "SELECT * FROM PLAYERDATA WHERE UUID = '" + uuid + "'";

        try(Connection con = database.getConnected()){

            PreparedStatement prep = con.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getInt(9);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void addPlayerTotalXp(UUID uuid, int amount){
        String sql = "UPDATE PLAYERDATA SET TOTALXP = TOTALXP + " + amount + " WHERE UUID = '" + uuid + "'";

        new BukkitRunnable(){
            @Override
            public void run(){
                try(Connection con = database.getConnected()){
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
