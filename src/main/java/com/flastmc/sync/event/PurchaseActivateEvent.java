package com.flastmc.sync.event;

import com.flastmc.sync.purchase.Purchase;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PurchaseActivateEvent extends Event {

    public static HandlerList handlerList = new HandlerList();

    private Purchase purchase;

    public PurchaseActivateEvent(Purchase purchase){
        this.purchase = purchase;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
