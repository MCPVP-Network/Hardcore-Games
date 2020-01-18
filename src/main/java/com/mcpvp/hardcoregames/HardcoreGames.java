package com.mcpvp.hardcoregames;

import com.mcpvp.hardcoregames.game.Game;
import com.mcpvp.hardcoregames.playerdata.PlayerManager;
import com.mcpvp.hardcoregames.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Api class for calling all aspects of the program
 */
public class HardcoreGames
{

    private static WorldManager worldManager;
    private static PlayerManager playerManager;
    private static Game game;

    private static HardcoreGamesPlugin plugin;

    public static WorldManager getWorldManager()
    {
        if(worldManager == null)
            worldManager = new WorldManager();
        return worldManager;
    }

    public static PlayerManager getPlayerManager()
    {
        if(playerManager == null)
            playerManager = new PlayerManager();
        return playerManager;
    }

    public static Game getGame()
    {
        if(game == null)
            game = new Game();
        return game;
    }

    public static HardcoreGamesPlugin getPlugin()
    {
        if(plugin == null)
            plugin = JavaPlugin.getPlugin(HardcoreGamesPlugin.class);
        return plugin;
    }
}
