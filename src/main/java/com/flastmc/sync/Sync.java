package com.flastmc.sync;

import com.flastmc.sync.database.MySQL;
import com.flastmc.sync.event.PurchaseActivateEvent;
import com.flastmc.sync.listener.SyncListener;
import com.flastmc.sync.purchase.Purchase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class Sync extends JavaPlugin {

    public static Sync instance;

    private MySQL storage;

    public void onEnable() {
        instance = this;

        setConnection();

        Bukkit.getPluginManager().registerEvents(new SyncListener(storage), this);
    }

    public static Sync getInstance() {
        return instance;
    }

    private void setConnection(){
        String user = getConfig().getString("mysql.user");
        String password = getConfig().getString("mysql.password");
        String database = getConfig().getString("mysql.database");
        String host = getConfig().getString("mysql.host");

        String nameDB = getConfig().getString("NomeDaTabela");
        int serverID = getConfig().getInt("Servidor");

        int delay = getConfig().getInt("Delay");

        this.storage = new MySQL(user, password, database, host, nameDB, serverID);
        handlerVerification(delay);
    }

    private void handlerVerification(int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Set<Purchase> download = storage.download();
                for (Purchase purchase : download) {
                    PurchaseActivateEvent event = new PurchaseActivateEvent(purchase);
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        }.runTaskTimer(this, 0, 20 * delay);
    }

}
