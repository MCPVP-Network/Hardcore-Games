package com.mcpvp.hardcoregames;

import com.mcpvp.hardcoregames.commons.BiomeUtils;
import com.mcpvp.hardcoregames.listeners.PlayerListener;
import com.mcpvp.hardcoregames.world.WorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * A minigame where players are dropped into a randomly generated world
 * force to battle to the death, last player alive wins.
 *
 * Steps
 * 1. Plugin enables,
 *  1.2, Last world is deleted
 *  1.3, New world is created
 *  1.4, New world has 1000 x 1000 preloaded
 *
 */
public class HardcoreGamesPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        try {
            BiomeUtils.removeLargeOceans();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        initApi();

        HardcoreGames.getWorldManager().createWorld(HardcoreGamesSettings.HG_WORLD_NAME, HardcoreGamesSettings.HG_WORLD_SIZE);

        //Listeners
        new PlayerListener().enable();

    }

    public void initApi()
    {
        HardcoreGames.getWorldManager();

        HardcoreGames.getPlugin();
    }


}
