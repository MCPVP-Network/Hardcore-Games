package com.mcpvp.hardcoregames.playerdata;

import com.mcpvp.hardcoregames.kit.Kit;
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

    @Getter @Setter
    private Kit kit;

    public Player getPlayer()
    {
        return Bukkit.getPlayer(this.getUniqueId());
    }
}
