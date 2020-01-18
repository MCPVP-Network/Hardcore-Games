package com.mcpvp.hardcoregames.playerdata;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.BiConsumer;

public class PlayerManager
{
    private HashMap<UUID, PlayerData> playerDatas = new HashMap<>();

    public void registerPlayerData(PlayerData playerData)
    {
        this.playerDatas.put(playerData.getUniqueId(), playerData);
    }

    public PlayerData getPlayerData(Player player)
    {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uniqueId)
    {
        return this.playerDatas.get(uniqueId);
    }

    public void unregisterPlayerData(UUID uniqueId)
    {
        this.playerDatas.remove(uniqueId);
    }

    public void applyAll(BiConsumer<UUID, PlayerData> consumer)
    {
        this.playerDatas.forEach(consumer);
    }

    public int getAlivePlayerCount()
    {
        int size = 0;
        Iterator<Map.Entry<UUID, PlayerData>> entryIterator = this.playerDatas.entrySet().iterator();
        while(entryIterator.hasNext())
        {
            Map.Entry<UUID, PlayerData> entry = entryIterator.next();
            PlayerData playerData = entry.getValue();
            if(playerData.isAlive())
                size++;
        }
        return size;
    }

    /**
     * excludes staff
     * @return
     */
    public int getPlayerCount()
    {
        int size = 0;
        Iterator<Map.Entry<UUID, PlayerData>> entryIterator = this.playerDatas.entrySet().iterator();
        while(entryIterator.hasNext())
        {
            Map.Entry<UUID, PlayerData> entry = entryIterator.next();
            PlayerData playerData = entry.getValue();
            if(!playerData.isStaffMode())
                size++;
        }
        return size;
    }

    /**
     * excludes staff in staff mode
     * @return
     */
    public List<PlayerData> getPlayers()
    {
        ArrayList<PlayerData> playerDatas = new ArrayList<>();
        Iterator<Map.Entry<UUID, PlayerData>> entryIterator = this.playerDatas.entrySet().iterator();
        while(entryIterator.hasNext())
        {
            Map.Entry<UUID, PlayerData> entry = entryIterator.next();
            PlayerData playerData = entry.getValue();
            if(!playerData.isStaffMode())
                playerDatas.add(playerData);
        }
        return playerDatas;
    }
}
