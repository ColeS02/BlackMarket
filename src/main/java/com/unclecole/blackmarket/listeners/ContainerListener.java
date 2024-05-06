package com.unclecole.blackmarket.listeners;

import com.unclecole.blackmarket.BlackMarket;
import com.unclecole.blackmarket.objects.DarkItems;
import com.unclecole.blackmarket.utils.Config;
import com.unclecole.blackmarket.utils.PlaceHolder;
import com.unclecole.blackmarket.utils.TL;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ContainerListener implements Listener {

    private BlackMarket blackMarket;
    private Config config;

    public ContainerListener() {
        blackMarket = BlackMarket.getInstance();
        config = BlackMarket.getInstance().getConfigUtils();
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(!event.getPlayer().getInventory().getItemInMainHand().getType().equals(config.getSellwand().getType())) return;
        NBTItem nbtItem = new NBTItem(event.getPlayer().getInventory().getItemInMainHand());
        if(!nbtItem.hasKey("BM-uses")) return;
        event.setCancelled(true);

        if(event.getClickedBlock().getType().equals(Material.CHEST)) {
            Chest chest = (Chest) event.getClickedBlock().getState();
            sellContents(event.getPlayer(), chest.getInventory(), nbtItem.getDouble("BM-multi"));
        }
    }

    public void sellContents(Player player, Inventory contents, double multi) {
        long money = 0L;
        for(int i = 0; i < contents.getSize(); i++) {
            ItemStack itemStack = contents.getItem(i);
            if(itemStack != null && BlackMarket.getInstance().getSellable().containsKey(itemStack.getType())) {
                money = money + ((long) BlackMarket.getInstance().getSellable().get(itemStack.getType()) * itemStack.getAmount());
                contents.setItem(i, new ItemStack(Material.AIR));
            }
        }
        BlackMarket.getInstance().getEcon().depositPlayer(player, money * multi);
        TL.SOLD_CHEST.send(player, new PlaceHolder("%money%", money*multi));

        /*for(ItemStack item : contents) {
            if(item == null) continue;
            for(Map.Entry<DarkItems, ItemStack> entry : blackMarket.getSellItems().entrySet()) {
                DarkItems darkItems = entry.getKey();
                ItemStack darkItemstack = entry.getValue();
                if(item.getType().equals(darkItemstack.getType())) {
                    money.getAndAdd((long) (item.getAmount() * (darkItems.getSellPrice() * multi)));
                    contents.remove(item);
                }
            }
        }*/
    }
}
