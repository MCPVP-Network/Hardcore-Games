package com.mcpvp.hardcoregames.feast;

import com.mcpvp.hardcoregames.commons.MathUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Loot
{
    private List<ItemStack> loots = new ArrayList<>();

    public Loot()
    {
        loots.add(new ItemStack(Material.DIAMOND_HELMET));
        loots.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
        loots.add(new ItemStack(Material.DIAMOND_LEGGINGS));
        loots.add(new ItemStack(Material.DIAMOND_BOOTS));

        loots.add(new ItemStack(Material.TNT, MathUtils.r(10) + 1));
        loots.add(new ItemStack(Material.TNT, MathUtils.r(15) + 1));
        loots.add(new ItemStack(Material.TNT, MathUtils.r(5) + 1));

        loots.add(new ItemStack(Material.COOKED_BEEF, MathUtils.r(32) + 1));
        loots.add(new ItemStack(Material.COOKED_BEEF, MathUtils.r(32) + 1));
        loots.add(new ItemStack(Material.COOKED_BEEF, MathUtils.r(32) + 1));
        loots.add(new ItemStack(Material.COOKED_CHICKEN, MathUtils.r(32) + 1));
        loots.add(new ItemStack(Material.BREAD, MathUtils.r(10) + 1));

        loots.add(new ItemStack(Material.ARROW, MathUtils.r(31) + 1));
        loots.add(new ItemStack(Material.ARROW, MathUtils.r(63) + 1));

        loots.add(new ItemStack(Material.BOW));
        loots.add(new ItemStack(Material.DIAMOND_SWORD));

        loots.add(new ItemStack(Material.FLINT_AND_STEEL));
        loots.add(new ItemStack(Material.FLINT_AND_STEEL));

        loots.add(new ItemStack(Material.MUSHROOM_SOUP, 32));
        loots.add(new ItemStack(Material.MUSHROOM_SOUP, 32));
        loots.add(new ItemStack(Material.MUSHROOM_SOUP, 32));

        loots.add(new ItemStack(Material.WEB, 10));

        loots.add(new ItemStack(Material.ENDER_PEARL, MathUtils.r(6) + 1));
        loots.add(new ItemStack(Material.ENDER_PEARL, MathUtils.r(9) + 1));
        loots.add(new ItemStack(Material.ENDER_PEARL, MathUtils.r(12) + 1));

        loots.add(new ItemStack(Material.EXP_BOTTLE, MathUtils.r(40) + 1));

        loots.add(new ItemStack(Material.LAVA_BUCKET, 1));
        loots.add(new ItemStack(Material.WATER_BUCKET, 1));

        loots.add(new ItemStack(Material.POTION, 1, (short) 16418)); // SPEED 1
        loots.add(new ItemStack(Material.POTION, 1, (short) 16425)); // Strength
        loots.add(new ItemStack(Material.POTION, 1, (short) 16420)); // Poisons
        loots.add(new ItemStack(Material.POTION, 1, (short) 16417)); // Regen
        loots.add(new ItemStack(Material.POTION, 1, (short) 16428)); // Heal
    }

    private int countItems(Inventory inv)
    {
        int i = 0;
        for (ItemStack item : inv.getContents())
            if (item != null && item.getType() != Material.AIR)
                i++;
        return i;
    }

    public void fillChest(Inventory inv) {
        inv.clear();
        if (loots.size() > 0)
        {
            Collections.shuffle(loots, new Random());
            for (int i = 0; i < MathUtils.r(2) + 4; i++)
                inv.setItem(MathUtils.r(inv.getSize()), loots.get(MathUtils.r(loots.size())));
        }
    }
}
