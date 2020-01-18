package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.commons.MathUtils;
import com.mcpvp.hardcoregames.customevents.GameStateChangeEvent;
import com.mcpvp.hardcoregames.feast.Feast;
import com.mcpvp.hardcoregames.game.GameState;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CompassListener extends Listenable<HardcoreGamesPlugin>
{

    public CompassListener()
    {
        super(HardcoreGames.getPlugin());
    }

    @EventHandler
    public void onCompass(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Location loc = player.getLocation();
        ItemStack item = e.getItem();
        if(item == null || item.getType() == Material.AIR) return;
        if(item.getType() != Material.COMPASS) return;

        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            //find closest player
            double closetsDis = 10000000;
            Player victim = null;
            for (PlayerData playerData : HardcoreGames.getPlayerManager().getPlayers())
            {
                if(!playerData.isAlive()) continue;
                Player curTarget = playerData.getPlayer();
                if(curTarget == null) continue;

                double distance = loc.distanceSquared(curTarget.getLocation());
                if(distance < closetsDis && closetsDis > HardcoreGamesSettings.COMPASS_MIN_DISTANCE_SQARED)
                {
                    closetsDis = distance;
                    victim = curTarget;
                }
            }

            if(victim != null)
            {
                player.sendMessage("Compass targeting: " + victim.getName());
                player.setCompassTarget(victim.getLocation());
            }
            else
            {
                player.sendMessage("No targets found");
            }
        }
        else
        {
            Feast feast = HardcoreGames.getGame().getFeast();
            if(feast == null)
            {
                player.sendMessage("There currently no feast");
            }
            else
            {
                player.sendMessage("The compass is now pointing at the feast");
                player.setCompassTarget(feast.getLocation());
            }
        }
    }
}
