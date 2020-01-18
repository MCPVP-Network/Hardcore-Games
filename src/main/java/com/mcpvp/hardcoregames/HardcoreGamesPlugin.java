package com.mcpvp.hardcoregames;

import com.mcpvp.hardcoregames.commons.BiomeUtils;
import com.mcpvp.hardcoregames.feast.Feast;
import com.mcpvp.hardcoregames.game.Game;
import com.mcpvp.hardcoregames.listeners.CompassListener;
import com.mcpvp.hardcoregames.listeners.GameListener;
import com.mcpvp.hardcoregames.listeners.PlayerListener;
import com.mcpvp.hardcoregames.listeners.WorldListener;
import com.mcpvp.hardcoregames.world.WorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
 * 2. players join
 *  2.1, playerdata gets loaded
 *  2.2, player picks their kit
 * 3. game starts
 *   3.1, 2 minutes of grace period
 *   3.2, then players can attack eachother
 * 4. Feast
 *  4.1, the feast platform spawns
 *  4.2, after 5 minutes, chests spawn on the feast with loot
 * 5. FINISH
 *  5.1, Last player alive wins
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
        new GameListener().enable();
        new WorldListener().enable();
        new CompassListener().enable();

    }

    public void initApi()
    {
        HardcoreGames.getWorldManager();
        HardcoreGames.getPlayerManager();

        HardcoreGames.getPlugin();

        HardcoreGames.getGame();
    }


}
