package com.unclecole.blackmarket.utils;

import com.unclecole.blackmarket.BlackMarket;
import com.unclecole.blackmarket.objects.DarkItems;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.itemutils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Config {
    private BlackMarket blackMarket;

    @Getter private ArrayList<DarkItems> darkItems;
    private Material material;
    private String name;
    private boolean sellBlocked;
    private int modelData;
    private double globalChance;
    private List<String> lore;
    private int buyPrice;
    private double buyChance;
    private int sellPrice;
    private double sellChance;
    private boolean giveItem;
    private String buyCommand;

    @Getter private ItemStack sellwand;
    @Getter private List<String> sellwandLore;

    public Config(BlackMarket blackMarket) {
        sellwandLore = new ArrayList<>();
        this.blackMarket = blackMarket;
        this.darkItems = new ArrayList<>();
    }


    public void loadItems() {

        ItemBuilder sellwand = new ItemBuilder(Material.getMaterial(blackMarket.getConfig().getString("SellWand.Material")));
        sellwand.setName(C.color(blackMarket.getConfig().getString("SellWand.Name")));
        sellwandLore = blackMarket.getConfig().getStringList("SellWand.Lore");

        this.sellwand = sellwand;

        for (String key : blackMarket.getConfig().getConfigurationSection("items.").getKeys(false)) {

            if(Material.getMaterial(blackMarket.getConfig().getString("items." + key + ".material")) == null) {
                blackMarket.getServer().getConsoleSender().sendMessage(C.color("&4Idiot Proofed Material (" + blackMarket.getConfig().getString("items." + key + ".material") +"). SKIPPED."));
                continue;
            }

            material = Material.getMaterial(blackMarket.getConfig().getString("items." + key + ".material"));
            name = blackMarket.getConfig().getString("items." + key + ".name");
            sellBlocked = blackMarket.getConfig().getBoolean("items." + key + ".sellblocked");
            modelData = blackMarket.getConfig().getInt("items." + key + ".model-data");
            globalChance = blackMarket.getConfig().getDouble("items." + key + ".global-chance");
            lore = blackMarket.getConfig().getStringList("items." + key + ".lore");
            buyPrice = blackMarket.getConfig().getInt("items." + key + ".categories.buy.price");
            buyChance = blackMarket.getConfig().getDouble("items." + key + ".categories.buy.chance");
            sellPrice = blackMarket.getConfig().getInt("items." + key + ".categories.sell.price");
            sellChance = blackMarket.getConfig().getDouble("items." + key + ".categories.sell.chance");
            giveItem = blackMarket.getConfig().getBoolean("items." + key + ".categories.buy.give-item");
            buyCommand = blackMarket.getConfig().getString("items." + key + ".categories.buy.command");

            darkItems.add(new DarkItems(material,
                    name,
                    modelData,
                    globalChance,
                    lore,
                    buyPrice,
                    buyChance,
                    sellPrice,
                    sellChance,
                    giveItem,
                    buyCommand,
                    sellBlocked));
        }
    }
}
