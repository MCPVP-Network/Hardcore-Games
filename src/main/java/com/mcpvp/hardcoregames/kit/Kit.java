package com.mcpvp.hardcoregames.kit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.Potion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kit extends Listenable<HardcoreGamesPlugin> {
    @Getter
    private String name;
    @Getter
    private ItemStack icon;

    //<Slot, item to go in slot>
    private @Getter(AccessLevel.PRIVATE) HashMap<Integer, ItemStack> items = Maps.newHashMap();
    private @Getter(AccessLevel.PRIVATE) ItemStack[] armor = new ItemStack[4];
    private @Getter(AccessLevel.PRIVATE) List<Potion> potionEffects = Lists.newArrayList();

    public Kit(String name, ItemStack icon)
    {
        super(HardcoreGames.getPlugin());
        this.name = name;
        this.icon = icon;


    }

    public boolean hasKit(Player player)
    {
        PlayerData playerData = HardcoreGames.getPlayerManager().getPlayerData(player);
        return playerData.getKit() != null && playerData.getKit() == this;
    }

    public void setItem(int slot, ItemStack item)
    {
        this.getItems().put(slot, item);
    }

    public void addItem(ItemStack item)
    {
        int lastSlotEmpty = 0;
        for(int i = 0; i < this.getItems().size(); i++)
        {
            ItemStack slottedItem = this.getItem(i);
            if(slottedItem == null || slottedItem.getType() == Material.AIR) {
                lastSlotEmpty = i;
                break;
            }
        }
        this.setItem(lastSlotEmpty, item);
    }

    public ItemStack getItem(int slot)
    {
        return this.getItems().get(slot);
    }

    /**
     * Give the player all the items of the kit,
     * including potions effects and armor
     * @param player
     */
    public void apply(Player player)
    {
        PlayerInventory inv = player.getInventory();
        //apply items
        for (Map.Entry<Integer, ItemStack> slottedItem : this.getItems().entrySet())
            inv.setItem(slottedItem.getKey(), slottedItem.getValue());

        //apply armor
        inv.setArmorContents(armor);

        //Apply potionEffects
        for (Potion potionEffect : this.potionEffects)
        {
            potionEffect.apply(player);
        }
    }
}
