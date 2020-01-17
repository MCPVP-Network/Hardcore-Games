package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.Listenable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener extends Listenable<HardcoreGamesPlugin>
{
    public PlayerListener()
    {
        super(HardcoreGames.getPlugin());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        player.teleport(HardcoreGamesSettings.getHGWorld().getSpawnLocation());
        player.sendMessage("Welcome! =");
    }
}
