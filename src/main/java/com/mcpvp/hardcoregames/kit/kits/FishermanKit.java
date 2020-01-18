package com.mcpvp.hardcoregames.kit.kits;

import com.mcpvp.hardcoregames.commons.ItemStackBuilder;
import com.mcpvp.hardcoregames.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FishermanKit extends Kit
{
    public static final ItemStack FISHING_ROD = new ItemStackBuilder(Material.FISHING_ROD).build();

    public FishermanKit()
    {
        super("fisherman", new ItemStack(Material.FISHING_ROD));

        this.addItem(FISHING_ROD);
    }
}
