package com.mcpvp.hardcoregames.listeners;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.CC;
import com.mcpvp.hardcoregames.commons.ItemStackBuilder;
import com.mcpvp.hardcoregames.commons.Listenable;
import com.mcpvp.hardcoregames.commons.MathUtils;
import com.mcpvp.hardcoregames.customevents.GameStateChangeEvent;
import com.mcpvp.hardcoregames.game.GameState;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GameListener extends Listenable<HardcoreGamesPlugin>
{
    private static final ItemStack COMPASS = new ItemStackBuilder(Material.COMPASS).displayName(CC.green + "Tracking Compass").build();

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
                Player player = playerData.getPlayer();
                playerData.setAlive(true);
                int x = MathUtils.r(16);
                int z = MathUtils.r(16);

                int y = world.getHighestBlockYAt(x, z) + 8;
                Location personalSpawn = spawn.clone();
                personalSpawn.add(x, 0, z);
                personalSpawn.setY(y);

                player.teleport(personalSpawn);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.getInventory().clear();
                player.setLevel(0);
                player.setExp(0.0f);

                if(playerData.getKit() != null)
                    playerData.getKit().apply(player);
                player.getInventory().addItem(COMPASS);

            }
        }
    }
}
