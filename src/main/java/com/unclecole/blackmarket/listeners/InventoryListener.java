package com.unclecole.blackmarket.listeners;

import com.unclecole.blackmarket.BlackMarket;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        BlackMarket.getInstance().getSellGUI().remove(event.getPlayer().getUniqueId());
        BlackMarket.getInstance().getBuyGUI().remove(event.getPlayer().getUniqueId());
    }
}
