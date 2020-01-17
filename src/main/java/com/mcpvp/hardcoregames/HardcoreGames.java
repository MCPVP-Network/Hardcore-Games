package com.mcpvp.hardcoregames;

import com.mcpvp.hardcoregames.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HardcoreGames
{

    private static WorldManager worldManager;

    private static HardcoreGamesPlugin plugin;

    public static WorldManager getWorldManager()
    {
        if(worldManager == null)
            worldManager = new WorldManager();
        return worldManager;
    }

    public static HardcoreGamesPlugin getPlugin()
    {
        if(plugin == null)
            plugin = JavaPlugin.getPlugin(HardcoreGamesPlugin.class);
        return plugin;
    }
}
