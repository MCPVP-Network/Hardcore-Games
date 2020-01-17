package com.mcpvp.hardcoregames;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class HardcoreGamesSettings
{
    public static String HG_WORLD_NAME = "hg_world";
    public static int HG_WORLD_SIZE = 1000;//dimestion

    // called
    private static World HG_WORLD;

    public static World getHGWorld()
    {
        if(HG_WORLD == null)
            HG_WORLD = Bukkit.getWorld(HG_WORLD_NAME);
        return HG_WORLD;
    }
}
