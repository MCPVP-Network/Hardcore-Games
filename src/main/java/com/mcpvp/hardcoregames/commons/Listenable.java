package com.mcpvp.hardcoregames.commons;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public class Listenable<p extends JavaPlugin> implements Listener
{
    @NonNull
    @Getter
    private p plugin;
    @Getter
    private boolean enabled = false;

    public void enable()
    {
        if(this.enabled)
            throw new RuntimeException("Listener already enabled");
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.enabled = true;
    }

    public void disable()
    {
        if(this.enabled)
            HandlerList.unregisterAll(this);
        this.enabled = false;
    }
}
