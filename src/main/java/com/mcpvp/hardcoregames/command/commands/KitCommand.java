package com.mcpvp.hardcoregames.command.commands;

import com.google.common.collect.ImmutableList;
import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.commons.CC;
import com.mcpvp.hardcoregames.kit.Kit;
import com.mcpvp.hardcoregames.kit.KitManager;
import com.mcpvp.hardcoregames.playerdata.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KitCommand extends Command
{

    public KitCommand()
    {
        super("kit");
    }

    @Override
    public boolean execute(CommandSender sender, String cmd, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(CC.red + "You must be a player to do this");
            return false;
        }

        if(args.length != 1)
        {
            sender.sendMessage(CC.red + "Usage: /kit <KitName>");
            return false;
        }

        String kitName = args[0];
        Kit kit = HardcoreGames.getKitManager().getKit(kitName);
        if(kit == null)
        {
            sender.sendMessage(CC.red + "Kit " + kitName + " not found!");
            return false;
        }

        Player player =  (Player) sender;
        PlayerData playerData = HardcoreGames.getPlayerManager().getPlayerData(player);
        if(playerData == null) return false;
        playerData.setKit(kit);
        player.sendMessage(CC.green + "You're now " + kit.getName());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> kitOptions = HardcoreGames.getKitManager().getKitNames();
            return StringUtil.copyPartialMatches(args[0], kitOptions, new ArrayList<String>(kitOptions.size()));

        }
        return ImmutableList.of();
    }
}