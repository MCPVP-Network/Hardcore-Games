package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class WorldListener extends Listenable<HardcoreGamesPlugin>
{
    public WorldListener()
    {
        super(HardcoreGames.getPlugin());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(HardcoreGames.getGame().isInteractable())
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(HardcoreGames.getGame().isInteractable())
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void blockPlace(EntityDamageEvent e)
    {
        if(HardcoreGames.getGame().isInteractable())
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(HardcoreGames.getGame().isInteractable())
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e)
    {
        Player player = (Player) e.getEntity();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(HardcoreGames.getGame().isInteractable())
            return;
        e.setFoodLevel(20);
    }

    @EventHandler
    public void onGracePeriodDamage(EntityDamageEvent e)
    {
        if(HardcoreGames.getGame().getGameState() != GameState.GRACE_PERIOD)
            return;

        if(e.getEntity() instanceof Player)
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent event)
    {
        event.setCancelled(true);
    }
}
