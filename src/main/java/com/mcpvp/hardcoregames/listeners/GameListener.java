package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.commons.MathUtils;
import com.mcpvp.hardcoregames.customevents.GameStateChangeEvent;
import com.mcpvp.hardcoregames.game.GameState;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class GameListener extends Listenable<HardcoreGamesPlugin>
{

    public GameListener()
    {
        super(HardcoreGames.getPlugin());
    }

    @EventHandler
    public void onSpawn(GameStateChangeEvent e)
    {
        World world = HardcoreGamesSettings.getHGWorld();
        Location spawn = world.getSpawnLocation();
        List<PlayerData> playerDatas = HardcoreGames.getPlayerManager().getPlayers();
        if(e.getTo() == GameState.GRACE_PERIOD)
        {
            for (PlayerData playerData : playerDatas)
            {
                playerData.setAlive(true);
                int x = MathUtils.r(16);
                int z = MathUtils.r(16);

                int y = world.getHighestBlockYAt(x, z) + 8;
                Location personalSpawn = spawn.clone();
                personalSpawn.add(x, 0, z);
                personalSpawn.setY(y);
                playerData.getPlayer().sendMessage("Spawning at " + x + ", " + y + ", " + z);
                playerData.getPlayer().teleport(personalSpawn);
            }
        }
    }
}
