package com.mcpvp.hardcoregames.feast;

import com.mcpvp.hardcoregames.commons.ShapeUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

@RequiredArgsConstructor
public class Feast
{
    @NonNull @Getter
    private Location location;

    public void generatePlatform()
    {
        List<Location> blocks = ShapeUtils.getCircleBlocks(this.getLocation(), 16, 1, false, false);
        for (Location loc : blocks)
            loc.getBlock().setType(Material.GRASS);
    }

    public void generateChests()
    {

    }
}
