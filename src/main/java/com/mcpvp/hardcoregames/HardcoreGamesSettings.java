package com.mcpvp.hardcoregames;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class HardcoreGamesSettings
{
    public static String HG_WORLD_NAME = "hg_world";
    public static int HG_WORLD_SIZE = 50;//dimestion in blocks
    public static int MIN_PLAYERS_TO_START = 1;

    public static double COMPASS_MIN_DISTANCE = 16;
    public static double COMPASS_MIN_DISTANCE_SQARED = COMPASS_MIN_DISTANCE * COMPASS_MIN_DISTANCE;

    // called
    private static World HG_WORLD;

    public static World getHGWorld()
    {
        if(HG_WORLD == null)
            HG_WORLD = Bukkit.getWorld(HG_WORLD_NAME);
        return HG_WORLD;
    }
}
