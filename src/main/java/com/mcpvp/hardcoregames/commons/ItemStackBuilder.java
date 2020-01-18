package com.mcpvp.hardcoregames.commons;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class ItemStackBuilder
{
    private ItemStack itemStack;

    public ItemStackBuilder(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder(Material material)
    {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(Material material, short data)
    {
        this.itemStack = new ItemStack(material, 1, data);
    }

    public ItemStackBuilder(Material material, int amount)
    {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemMeta getItemMeta()
    {
        return this.itemStack.getItemMeta();
    }

    public ItemStackBuilder amount(int amount)
    {
        this.itemStack.setAmount(amount);
        return this;
    }


    public ItemStackBuilder lore(String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        build().setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder displayName(String displayName) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        build().setItemMeta(itemMeta);
        return this;
    }


    public void unbreakable()
    {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        build().setItemMeta(itemMeta);
    }

    public ItemStackBuilder enchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder durability(int amount)
    {
        this.build().setDurability((short)amount);
        return this;
    }

    public ItemStackBuilder durabilityLeft(int amount)
    {
        this.build().setDurability((short)(this.build().getType().getMaxDurability() - amount));
        return this;
    }

    public ItemStackBuilder setLeatherColour(Color color)
    {
        try
        {
            LeatherArmorMeta itemMeta = (LeatherArmorMeta)this.build().getItemMeta();
            itemMeta.setColor(color);
            this.build().setItemMeta((ItemMeta)itemMeta);
            return this;
        }
        catch (ClassCastException ex)
        {
            ex.printStackTrace();
        }
        return this;
    }

    public ItemStack build()
    {
        return this.itemStack;
    }
}