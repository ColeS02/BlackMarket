package com.unclecole.blackmarket.commands;

import com.unclecole.blackmarket.BlackMarket;
import com.unclecole.blackmarket.utils.C;
import com.unclecole.blackmarket.utils.Config;
import com.unclecole.blackmarket.utils.PlaceHolder;
import com.unclecole.blackmarket.utils.TL;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackMarketCmd implements CommandExecutor {

    private BlackMarket plugin;
    private Config config;

    public BlackMarketCmd() {
        plugin = BlackMarket.getInstance();
        config = BlackMarket.getInstance().getConfigUtils();
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {

        /*if(args.length >= 4) {
            if(!s.hasPermission("blackmarket.admin")) return false;
            if(!args[0].equalsIgnoreCase("give") || Bukkit.getPlayer(args[1]) == null || !isParsableINT(args[2]) || !isParsableDOUBLE(args[3])) {
                //TODO: Invalid Command
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            int uses = Integer.parseInt(args[2]);
            double multi = Double.parseDouble(args[3]);

            ItemBuilder sellWand = new ItemBuilder(config.getSellwand().clone());
            sellWand.setName(config.getSellwand().getItemMeta().getDisplayName().replace("%uses%", String.valueOf(uses)).replace("%multiplier%", String.valueOf(multi)));

            List<String> lore = new ArrayList<>();
            config.getSellwandLore().forEach(str -> {
                lore.add(C.color(str).replace("%uses%", String.valueOf(uses)).replace("%multiplier%", String.valueOf(multi)));
            });

            sellWand.addLore(lore);

            NBTItem sellwandNBT = new NBTItem(sellWand);
            sellwandNBT.setInteger("BM-uses", uses);
            sellwandNBT.setDouble("BM-multi", multi);

            ItemStack item = sellwandNBT.getItem();

            player.getInventory().addItem(item);
            return false;
        }*/

        if(!(s instanceof Player)) return false;

        Player player = (Player) s;

        if(!player.hasPermission("darkauction.use.menu")) {
            TL.NO_PERMISSION.send(player);
            return false;
        }

        InventoryGUI gui = new InventoryGUI(27, C.color("&6Black Market"));

        ItemBuilder purple = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE);
        purple = (ItemBuilder) removeMeta(purple);
        ItemBuilder gray = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
        gray = (ItemBuilder) removeMeta(gray);

        ItemButton PurplePlaceholder = new ItemButton(purple) {
            @Override
            public void onClick(InventoryClickEvent e) {

            }
        };

        ItemButton GrayPlaceholder = new ItemButton(gray) {
            @Override
            public void onClick(InventoryClickEvent e) {

            }
        };

        gui.addButton(0,PurplePlaceholder);
        gui.addButton(1,GrayPlaceholder);
        gui.addButton(2,GrayPlaceholder);
        gui.addButton(3,PurplePlaceholder);
        gui.addButton(4,GrayPlaceholder);
        gui.addButton(5,PurplePlaceholder);
        gui.addButton(6,GrayPlaceholder);
        gui.addButton(7,GrayPlaceholder);
        gui.addButton(8,PurplePlaceholder);
        gui.addButton(9,GrayPlaceholder);
        gui.addButton(17,GrayPlaceholder);
        gui.addButton(18,PurplePlaceholder);
        gui.addButton(19,GrayPlaceholder);
        gui.addButton(20,GrayPlaceholder);
        gui.addButton(21,PurplePlaceholder);
        gui.addButton(22,GrayPlaceholder);
        gui.addButton(23,PurplePlaceholder);
        gui.addButton(24,GrayPlaceholder);
        gui.addButton(25,GrayPlaceholder);
        gui.addButton(26,PurplePlaceholder);

        ItemBuilder buy = new ItemBuilder(Material.WRITABLE_BOOK).setName(C.color("&6&lBuy"));
        ItemBuilder sell = new ItemBuilder(Material.NAME_TAG).setName(C.color("&b&lSell"));


        gui.addButton(12, new ItemButton(buy) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if(player.hasPermission("darkauction.use.buy")) buyMenu(player);
            }
        });

        gui.addButton(14, new ItemButton(sell) {
            @Override
            public void onClick(InventoryClickEvent e) {
                if(player.hasPermission("darkauction.use.sell")) sellMenu(player);
            }
        });

        gui.open(player);


        return false;
    }

    public void buyMenu(Player player) {
        InventoryGUI gui = new InventoryGUI(27, C.color("&6Buy"));

        ItemBuilder yellow = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE);
        yellow = (ItemBuilder) removeMeta(yellow);
        ItemBuilder gray = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
        gray = (ItemBuilder) removeMeta(gray);

        ItemButton YellowPlaceholder = new ItemButton(yellow) {
            @Override
            public void onClick(InventoryClickEvent e) {

            }
        };

        ItemButton GrayPlaceholder = new ItemButton(gray) {
            @Override
            public void onClick(InventoryClickEvent e) {

            }
        };

        gui.addButton(0,YellowPlaceholder);
        gui.addButton(1,GrayPlaceholder);
        gui.addButton(2,GrayPlaceholder);
        gui.addButton(3,YellowPlaceholder);
        gui.addButton(4,GrayPlaceholder);
        gui.addButton(5,YellowPlaceholder);
        gui.addButton(6,GrayPlaceholder);
        gui.addButton(7,GrayPlaceholder);
        gui.addButton(8,YellowPlaceholder);
        gui.addButton(9,GrayPlaceholder);
        gui.addButton(17,GrayPlaceholder);
        gui.addButton(18,YellowPlaceholder);
        gui.addButton(19,GrayPlaceholder);
        gui.addButton(20,GrayPlaceholder);
        gui.addButton(21,YellowPlaceholder);
        gui.addButton(22,GrayPlaceholder);
        gui.addButton(23,YellowPlaceholder);
        gui.addButton(24,GrayPlaceholder);
        gui.addButton(25,GrayPlaceholder);
        gui.addButton(26,YellowPlaceholder);

        AtomicInteger i = new AtomicInteger(9 );

        BlackMarket.getInstance().getBuyItems().forEach((darkItems, itemStack) -> {
            i.getAndIncrement();
            gui.addButton(i.get(), new ItemButton(itemStack) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    Player player = (Player) e.getWhoClicked();
                    if(BlackMarket.getInstance().getEcon().getBalance(player) < darkItems.getBuyPrice()) {
                        TL.INSUFFICIENT_FUNDS.send(player);
                        return;
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                    if(darkItems.getName() != null) TL.BOUGHT_ITEMS.send(player, new PlaceHolder("%item%", darkItems.getName()));
                    else TL.BOUGHT_ITEMS.send(player, new PlaceHolder("%item%", darkItems.getMaterial().toString()));

                    if(darkItems.getCommand() == null || darkItems.isGiveItem()) {
                        player.getInventory().addItem(new ItemStack(darkItems.getMaterial()));
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), darkItems.getCommand().replaceAll("%player%", player.getName()));
                    }
                    plugin.getEcon().withdrawPlayer(player, darkItems.getBuyPrice());
                }
            });
        });

        gui.open(player);
        BlackMarket.getInstance().getBuyGUI().add(player.getUniqueId());
    }

    public void sellMenu(Player player) {
        InventoryGUI gui = new InventoryGUI(27, C.color("&b&lSell"));

        ItemBuilder blue = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        blue = (ItemBuilder) removeMeta(blue);
        ItemBuilder gray = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);
        gray = (ItemBuilder) removeMeta(gray);

        ItemButton BluePlaceholder = new ItemButton(blue) {
            @Override
            public void onClick(InventoryClickEvent e) {

            }
        };

        ItemButton GrayPlaceholder = new ItemButton(gray) {
            @Override
            public void onClick(InventoryClickEvent e) {

            }
        };

        gui.addButton(0,BluePlaceholder);
        gui.addButton(1,GrayPlaceholder);
        gui.addButton(2,GrayPlaceholder);
        gui.addButton(3,BluePlaceholder);
        gui.addButton(4,GrayPlaceholder);
        gui.addButton(5,BluePlaceholder);
        gui.addButton(6,GrayPlaceholder);
        gui.addButton(7,GrayPlaceholder);
        gui.addButton(8,BluePlaceholder);
        gui.addButton(9,GrayPlaceholder);
        gui.addButton(17,GrayPlaceholder);
        gui.addButton(18,BluePlaceholder);
        gui.addButton(19,GrayPlaceholder);
        gui.addButton(20,GrayPlaceholder);
        gui.addButton(21,BluePlaceholder);
        gui.addButton(22,GrayPlaceholder);
        gui.addButton(23,BluePlaceholder);
        gui.addButton(24,GrayPlaceholder);
        gui.addButton(25,GrayPlaceholder);
        gui.addButton(26,BluePlaceholder);

        AtomicInteger i = new AtomicInteger(9);

        BlackMarket.getInstance().getSellItems().forEach((darkItems, itemStack) -> {
            i.getAndIncrement();
            gui.addButton(i.get(), new ItemButton(itemStack) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if(!e.getInventory().contains(darkItems.getMaterial())) {
                        TL.NOTHING_TOO_SELL.send(player);
                        return;
                    } else {
                        AtomicInteger totalSold = new AtomicInteger();
                        e.getWhoClicked().getInventory().forEach(itemStack1 -> {
                            if(itemStack1 != null) {
                                if (darkItems.isGiveItem() && itemStack1.getType() == darkItems.getMaterial() && itemStack1.getItemMeta().getDisplayName().equals(darkItems.getName())) {
                                    totalSold.addAndGet(itemStack1.getAmount());
                                    itemStack1.setAmount(0);
                                }
                                if (!darkItems.isGiveItem() && itemStack1.getType() == darkItems.getMaterial() && !itemStack1.getItemMeta().hasDisplayName()) {
                                    totalSold.addAndGet(itemStack1.getAmount());
                                    itemStack1.setAmount(0);
                                }
                            }
                        });
                        float costSold = totalSold.get() * darkItems.getSellPrice();
                        if(costSold > 0) {
                            BlackMarket.getInstance().getEcon().depositPlayer(player, costSold);
                            if(darkItems.getName() != null) {
                                TL.SOLD_ITEMS.send(player, new PlaceHolder("%money%", costSold), new PlaceHolder("%amount%", totalSold.get()), new PlaceHolder("%item%", darkItems.getName()));
                            }
                            else TL.SOLD_ITEMS.send(player, new PlaceHolder("%money%", costSold), new PlaceHolder("%amount%", totalSold.get()), new PlaceHolder("%item%", darkItems.getMaterial().toString()));

                        }
                    }
                }
            });
        });

        gui.open(player);
        BlackMarket.getInstance().getSellGUI().add(player.getUniqueId());
    }

    public void resetGUI() {
        BlackMarket.getInstance().getBuyGUI().forEach(UUID -> {
            Player player = Bukkit.getPlayer(UUID);
            Bukkit.getScheduler().runTask(BlackMarket.getInstance(), new Runnable() {
                @Override
                public void run() {
                    buyMenu(player);
                }
            });
        });
        BlackMarket.getInstance().getSellGUI().forEach(UUID -> {
            Player player = Bukkit.getPlayer(UUID);
            Bukkit.getScheduler().runTask(BlackMarket.getInstance(), new Runnable() {
                @Override
                public void run() {
                    sellMenu(player);
                }
            });
        });
    }

    public ItemStack removeMeta(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(C.color("&a"));
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(meta);
        return itemStack;
    }



    public boolean isParsableINT(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
    public boolean isParsableDOUBLE(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
