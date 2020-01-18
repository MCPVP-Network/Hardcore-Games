package com.mcpvp.hardcoregames.kit.kits;

import com.mcpvp.hardcoregames.commons.ItemStackBuilder;
import com.mcpvp.hardcoregames.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ArcherKit extends Kit
{
    public static final ItemStack BOW = new ItemStackBuilder(Material.BOW).build();
    public static final ItemStack ARROWS = new ItemStackBuilder(Material.ARROW).amount(10).build();

    public ArcherKit()
    {
        super("archer", new ItemStack(Material.ARROW));

        this.addItem(BOW);
        this.addItem(ARROWS);
    }
}
