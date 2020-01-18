package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

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

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e)
    {
        String username = e.getName();
        UUID uniqueId = e.getUniqueId();

        PlayerData playerData = new PlayerData(uniqueId);



        HardcoreGames.getPlayerManager().registerPlayerData(playerData);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        HardcoreGames.getPlayerManager().unregisterPlayerData(player.getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e)
    {
        Player player = e.getPlayer();
        HardcoreGames.getPlayerManager().unregisterPlayerData(player.getUniqueId());
    }
}
