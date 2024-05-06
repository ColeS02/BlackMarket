package com.unclecole.blackmarket;

import com.unclecole.blackmarket.commands.BlackMarketCmd;
import com.unclecole.blackmarket.listeners.ContainerListener;
import com.unclecole.blackmarket.listeners.InventoryListener;
import com.unclecole.blackmarket.objects.DarkItems;
import com.unclecole.blackmarket.utils.C;
import com.unclecole.blackmarket.utils.Config;
import com.unclecole.blackmarket.utils.ConfigFile;
import com.unclecole.blackmarket.utils.TL;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.itemutils.ItemBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public final class BlackMarket extends JavaPlugin {

    public static BlackMarket blackMarket;
    public static BlackMarket getInstance() { return blackMarket; }

    @Getter private HashMap<DarkItems, ItemStack> buyItems;
    @Getter private HashMap<DarkItems, ItemStack> sellItems;
    @Getter private ArrayList<UUID> sellGUI;
    @Getter private ArrayList<UUID> buyGUI;
    @Getter private Economy econ = null;
    @Getter private BlackMarketCmd blackMarketCmd;
    @Getter private long updateInterval;
    @Getter private HashMap<Material, Integer> sellable;

    private Config config;
    public Config getConfigUtils() { return config; }

    @Override
    public void onEnable() {
        blackMarket = this;
        buyItems = new HashMap<>();
        sellItems = new HashMap<>();
        sellGUI = new ArrayList<>();
        buyGUI = new ArrayList<>();
        sellable = new HashMap<>();

        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
        TL.loadMessages(new ConfigFile("messages.yml", BlackMarket.getInstance()));

        if (!setupEconomy() ) {
            Bukkit.getLogger().log(Level.SEVERE, String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(BlackMarket.getInstance());
            return;
        }

        saveDefaultConfig();
                config = new Config(BlackMarket.getInstance());
                config.loadItems();
        blackMarketCmd = new BlackMarketCmd();
        getCommand("blackmarket").setExecutor(blackMarketCmd);

        updateInterval = convertTime(getConfig().getString("update-interval"));
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(), BlackMarket.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new ContainerListener(), BlackMarket.getInstance());
        runTask();
            }
        });
        // Plugin startup logic

    }

    private long convertTime(String time) {

        String[] timeSplit = time.split(":");

        long hours = Long.parseLong(timeSplit[0]);
        long minutes = Long.parseLong(timeSplit[1]);
        long seconds = Long.parseLong(timeSplit[2]);

        long totalSeconds = hours * 3600 + minutes * 60 + seconds;

        return totalSeconds * 20;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void runTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(TL.RESET_AUCTION::send);
                buyItems.clear();
                sellItems.clear();
                while(buyItems.size() < 4) {
                    int random = (int) (Math.random()*(config.getDarkItems().size()));
                    if(buyItems.containsKey(config.getDarkItems().get(random))) {
                        continue;
                    }

                    DarkItems darkItem = config.getDarkItems().get(random);

                    ItemBuilder item = new ItemBuilder(darkItem.getMaterial());


                    if(darkItem.getName() != null) item.setName(C.color(darkItem.getName()));

                    NumberFormat format = NumberFormat.getIntegerInstance();
                    String cost = format.format(darkItem.getBuyPrice());

                    darkItem.getLore().forEach(string -> {
                        item.addLore(C.color(string).replace("%cost%", cost));
                    });
                    item.setCustomModelData(darkItem.getModelData());

                    buyItems.put(config.getDarkItems().get(random), item);
                }
                while(sellItems.size() < 4) {
                    int random = (int) (Math.random()*(config.getDarkItems().size()));
                    if(sellItems.containsKey(config.getDarkItems().get(random))) {
                        continue;
                    }
                    if(config.getDarkItems().get(random).isSellBlocked()) continue;

                    DarkItems darkItem = config.getDarkItems().get(random);

                    ItemBuilder item = new ItemBuilder(darkItem.getMaterial());

                    if(darkItem.getName() != null) item.setName(C.color(darkItem.getName()));

                    NumberFormat format = NumberFormat.getIntegerInstance();
                    String cost = format.format(darkItem.getSellPrice());

                    darkItem.getLore().forEach(string -> {
                        item.addLore(C.color(string).replace("%cost%", cost));
                    });
                    item.setCustomModelData(darkItem.getModelData());

                    sellItems.put(config.getDarkItems().get(random), item);

                }
                sellable.clear();
                sellItems.forEach((darkItems, itemStack) -> {
                    if(!darkItems.isSellBlocked()) sellable.put(itemStack.getType(), darkItems.getSellPrice());
                });
                blackMarketCmd.resetGUI();
            }
        }, 0, updateInterval);
    }
}
