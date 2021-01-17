package com.flastmc.sync.listener;

import com.flastmc.sync.database.MySQL;
import com.flastmc.sync.event.PurchaseActivateEvent;
import com.flastmc.sync.purchase.Purchase;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SyncListener implements Listener {

    private MySQL mySQL;

    public SyncListener(MySQL mySQL){
        this.mySQL = mySQL;
    }

    @EventHandler
    public void onActivate(PurchaseActivateEvent event){

        Purchase purchase = event.getPurchase();

        if(Bukkit.getPlayer(purchase.getOwner()) != null){
            Bukkit.getConsoleSender().sendMessage("§a[Sync] Compra do jogador " + purchase.getOwner() + " ativado!");
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), purchase.getCommand().replaceFirst("/", ""));
            mySQL.delete(purchase.getId());
        }

    }


}
