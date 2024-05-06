package com.unclecole.blackmarket.objects;

import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

public class DarkItems {

    @Getter private Material material;
    @Getter private String name;
    @Getter private int modelData;
    @Getter private double globalChance;
    @Getter private List<String> lore;
    @Getter private int buyPrice;
    @Getter private double buyChance;
    @Getter private int sellPrice;
    @Getter private double sellChance;
    @Getter private boolean giveItem;
    @Getter private String command;
    @Getter private boolean sellBlocked;

    public DarkItems(Material material, String name, int modelData, double globalChance, List<String> lore, int buyPrice, double buyChance, int sellPrice, double sellChance, boolean giveItem, String command, boolean sellable) {
        this.material = material;
        this.name = name;
        this.modelData = modelData;
        this.globalChance = globalChance;
        this.lore = lore;
        this.buyPrice = buyPrice;
        this.buyChance = buyChance;
        this.sellPrice = sellPrice;
        this.sellChance = sellChance;
        this.giveItem = giveItem;
        this.command = command;
        this.sellBlocked = sellable;
    }


}
