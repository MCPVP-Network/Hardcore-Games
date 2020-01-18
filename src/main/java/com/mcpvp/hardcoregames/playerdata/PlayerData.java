package com.mcpvp.hardcoregames.playerdata;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class PlayerData
{
    @Getter @NonNull
    private UUID uniqueId;


    @Getter @Setter
    private boolean alive = false;
    @Getter @Setter
    private boolean staffMode = false;

    public Player getPlayer()
    {
        return Bukkit.getPlayer(this.getUniqueId());
    }
}
