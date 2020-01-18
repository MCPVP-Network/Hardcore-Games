package com.mcpvp.hardcoregames.feast;

import com.mcpvp.hardcoregames.commons.ShapeUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;

import java.util.List;

@RequiredArgsConstructor
public class Feast
{
    @NonNull @Getter
    private Location location;
    private int radius = 22;
    private Loot loot = new Loot();

    private static final BlockFace[] PRIME_CHEST_AREAS = new BlockFace[] { BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST };

    public void generatePlatform() {
        List<Location> clearBlocks = ShapeUtils.getCircleBlocks(this.getLocation(), radius, 50, false, false);
        List<Location> blocks = ShapeUtils.getCircleBlocks(this.getLocation(), radius, 0, false, false);
        for (Location loc : clearBlocks)
            loc.getBlock().setType(Material.AIR);

        for (Location loc : blocks) {
            loc.getBlock().setType(Material.GRASS);
            loc.getBlock().setBiome(Biome.PLAINS);
        }
    }

    /**
     * CACAC
     * ACACA
     * CAEAC
     * ACACA
     * CACAC
     * https://www.google.com/search?q=mcpvp+feast&safe=active&sxsrf=ACYBGNTckgQPnYQ1NsqgOXZg5eV55Y1jlA:1579315902500&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjy8OyakoznAhVZ6nMBHUhmAOcQ_AUoAXoECAoQAw&biw=1247&bih=639#imgrc=VSGt9Bdx0gc8oM:
     */
    public void generateChests()
    {
        Location chestLocs = location.clone();
        chestLocs.add(0, 1,0 );
        chestLocs.getBlock().setType(Material.ENCHANTMENT_TABLE);

        //who needs recursion
        for(BlockFace blockFace : PRIME_CHEST_AREAS)
        {
            Block block = chestLocs.getBlock().getRelative(blockFace);
            if(block.getType() == Material.AIR)
            {
                block.setType(Material.CHEST);

                Chest chest = (Chest) block.getState();
                loot.fillChest(chest.getInventory());
                chest.update();


                for(BlockFace blockFace2 : PRIME_CHEST_AREAS)
                {
                    Block block2 = block.getRelative(blockFace2);
                    if(block2.getType() == Material.AIR)
                    {
                        block2.setType(Material.CHEST);

                        Chest chest2 = (Chest) block2.getState();
                        loot.fillChest(chest2.getInventory());
                        chest2.update();
                    }
                }
            }
        }


    }



}
