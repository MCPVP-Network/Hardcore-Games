package com.mcpvp.hardcoregames.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandManager
{
    public CommandManager()
    {

    }

    public void registerCommand(Command command)
    {
        CommandMap commandMap = this.getCommandMap();
        if(commandMap == null) {
            for (int i = 0; i < 20; i++)
                Bukkit.broadcastMessage("");

            for (int i = 0; i < 20; i++) {
                Bukkit.broadcastMessage(" FUCK, I can't get access to the commandMap");
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("");
            }
            return;
        }

        commandMap.register(command.getName(), command);
    }

    private CommandMap getCommandMap()
    {
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        }
        catch(Exception exception){
            exception.printStackTrace();
        }

        return null;
    }
}
