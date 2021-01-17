package com.flastmc.sync.database;

import com.flastmc.sync.Sync;
import com.flastmc.sync.purchase.Purchase;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MySQL {

    private Connection connection;

    private String database;
    private int server_id;

    public MySQL(String user, String password, String database, String host, String nameDB, int server_id) {

        this.database = nameDB;
        this.server_id = server_id;

        try {
            Bukkit.getConsoleSender().sendMessage("§a[Sync] Estabelecendo conexão com o MYSQL...");
            String url = "jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true";
            this.connection = DriverManager.getConnection(url, user, password);
            Bukkit.getConsoleSender().sendMessage("§a[Sync] Conexão com o MYSQL estabelecida!");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

   public Set<Purchase> download(){
        Set<Purchase> purchase = new HashSet<Purchase>();
       try {
           PreparedStatement ps = connection.prepareStatement("SELECT * FROM `" + database + "` WHERE `charge_SERVER`=" + server_id);
           ResultSet rs = ps.executeQuery();

           while (rs.next()) {
               String owner = rs.getString("charge_USERNAME");;
               String command = rs.getString("charge_COMMAND");;

               int id = rs.getInt("charge_ID");
               int server = rs.getInt("charge_SERVER");;
               int online = rs.getInt("charge_ONLINE");;

               purchase.add(new Purchase(owner, command, id, server, online));
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       return purchase;
   }

    public void delete(int id) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM `" + database + "` WHERE `charge_ID`=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Sync.getInstance());
    }

}
