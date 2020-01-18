package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.CC;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.kit.Kit;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

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

        if(!HardcoreGames.getGame().getGameState().isJoinnable())
        {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CC.green + "This game is currently not joinable");
            return;
        }


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

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e)
    {
        Player player = e.getPlayer();
        
        deathKick(player);
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent e)
    {
        e.setDeathMessage(null);
        Player player = (Player) e.getEntity();
        if (player.getVehicle() != null)
            player.getVehicle().eject();

        deathKick(player);
    }

    public void deathKick(Player player)
    {
        StringBuilder message = new StringBuilder();
        Player killer = player.getKiller();

        Kit kit = HardcoreGames.getPlayerManager().getPlayerData(player).getKit();
        String kitName = kit != null ? kit.getName() : "None";
        message.append(player.getName()).append("(").append(kitName).append(") ");

        if(killer != null)
        {
            Kit kilerKit = HardcoreGames.getPlayerManager().getPlayerData(killer).getKit();
            String killerKitName = kit != null ? kit.getName() : "None";

            message.append("was killed by ");
            message.append(killer.getName()).append("(").append(killerKitName).append(") ");
        }
        else
        {
            message.append("died!");
        }

        player.kickPlayer("You lost! " + CC.aqua + message);
        Bukkit.broadcastMessage(CC.aqua + message.toString());
        Bukkit.broadcastMessage(CC.aqua + HardcoreGames.getPlayerManager().getAlivePlayerCount() + " Players remaining.");
    }
}
