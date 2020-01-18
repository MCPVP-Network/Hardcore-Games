package com.mcpvp.hardcoregames.world;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.HardcoreGamesPlugin;
import com.mcpvp.hardcoregames.HardcoreGamesSettings;
import com.mcpvp.hardcoregames.commons.Callback;
import com.mcpvp.hardcoregames.commons.CordPair;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * In bukkit, we can't unload the first world, which we normally just call the static world
 * so we always have to generate a secondary world to play around with and delete.
 *
 * Static World
 * - for the static world, we want it to use as little memory has possible
 */
public class WorldManager
{
    public WorldManager()
    {
        this.initStaticWorld(Bukkit.getWorlds().get(0));
    }

    public void initStaticWorld(World world)
    {
        //monsters will take for ever to spawn
        world.setTicksPerAnimalSpawns(Integer.MAX_VALUE);
        world.setTicksPerMonsterSpawns(Integer.MAX_VALUE);
        world.setWaterAnimalSpawnLimit(0);
        world.setAmbientSpawnLimit(0);
        world.setAnimalSpawnLimit(0);
        world.setMonsterSpawnLimit(0);
        world.setPVP(false);
        world.setGameRuleValue("doDaylightCycle", "false");

        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        world.setSpawnFlags(false, false);

        if (world.isThundering())
            world.setThunderDuration(Integer.MAX_VALUE);
        world.setWeatherDuration(Integer.MAX_VALUE);
    }

    /**
     * Create a new world (overwrites current world)
     *
     *
     */
    public void createWorld(String worldName, int size)
    {
        this.cleanUpWorld(worldName);
        World world = this.generateWorld(worldName, (worldCreator) -> {});
        this.loadChunks(world, size);
    }

    public World generateWorld(String worldName, Callback<WorldCreator> worldCreatorOptions)
    {
        WorldCreator worldCreator = new WorldCreator(worldName);

        worldCreatorOptions.done(worldCreator);

        World world = worldCreator.createWorld();

        //Apply world options
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);

        return world;
    }

    public void loadChunks(World world, int size)
    {
        int chunks = (int) Math.ceil(size / 16) + (Bukkit.getViewDistance() * 2);
        chunks /= 2; //half for radius

        Chunk spawn = world.getSpawnLocation().getChunk();

        ArrayList<CordPair> chunksToGenerate = new ArrayList<CordPair>();
        for (int x = -chunks; x <= chunks; x++)
        {
            for (int z = -chunks; z <= chunks; z++)
            {
                CordPair pair = new CordPair(spawn.getX() + x, spawn.getZ() + z);
                chunksToGenerate.add(pair);
            }
        }

        final double totalChunks = chunksToGenerate.size();
        final World finalWorld = world;

        BukkitRunnable runnable = new BukkitRunnable()
        {
            private double chunksGenerated = 0;
            private long lastLogged = 0;

            @Override
            public void run() {
                long startedGeneration = System.currentTimeMillis();
                Iterator<CordPair> cordsItel = chunksToGenerate.iterator();

                //50ms for async
                while (cordsItel.hasNext() && startedGeneration + 5_000 > System.currentTimeMillis())
                {
                    if(System.currentTimeMillis() - lastLogged > 5_000)
                    {
                        System.out.println(chunksGenerated + " / " + totalChunks +" chunks loaded...");
                        lastLogged = System.currentTimeMillis();
                    }

                    CordPair pair = cordsItel.next();
                    if (!world.isChunkLoaded(pair.getX(), pair.getZ())) {
                        world.loadChunk(pair.getX(), pair.getZ());
                        world.unloadChunk(pair.getX(), pair.getZ());
                    }
                    cordsItel.remove();
                    chunksGenerated++;
                }

                if (!cordsItel.hasNext())
                {
                    world.save();
                    System.out.println("Done " + world.getName() + "!");
                    cancel();
                }
            }
        };

        runnable.runTaskTimer(HardcoreGames.getPlugin(), 1, 3);
    }

    /**
     * Removes the last World generated and it's files
     *
     * @param worldName
     *            The name of the world you would like to destroy
     */
    public void cleanUpWorld(String worldName)
    {
        //unload world from server
        World hgWorld = Bukkit.getWorld(worldName);
        if(hgWorld != null)
        {
            for (Player onlinePlayer : hgWorld.getPlayers())
                onlinePlayer.kickPlayer("RESTARTING WORLD");

            Bukkit.unloadWorld(hgWorld, false);
        }

        //delete folder
        File hgWorldFolder = new File(worldName);
        if(hgWorldFolder.exists()) {
            try {

                FileUtils.deleteDirectory(hgWorldFolder);
            } catch (IOException e) {
                System.out.println("Failed to delete " + worldName + " world");
                e.printStackTrace();
            }
        }
    }
}
