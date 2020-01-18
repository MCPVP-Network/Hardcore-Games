package com.mcpvp.hardcoregames.kit;

import com.mcpvp.hardcoregames.HardcoreGames;
import com.mcpvp.hardcoregames.commons.ClassGetterUtils;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class KitManager
{
    private HashMap<String, Kit> kits = new HashMap<>();
    @Getter
    private List<String> kitNames = new LinkedList<>();//used for commands auto complete

    public KitManager()
    {
        for (Class<?> aClass : ClassGetterUtils.getClassesForPackage(HardcoreGames.getPlugin(), "com.mcpvp.hardcoregames.kit.kits")) {
            try {
                Kit kit = (Kit) aClass.newInstance();
                this.registerKit(kit);
            } catch (Exception e) {
                System.out.println("failed to register a kit");
                e.printStackTrace();
            }
        }
    }

    public void registerKit(Kit kit)
    {
        System.out.println("REGISTERED " + kit.getName());
        this.kits.put(kit.getName().toLowerCase(), kit);
        this.kitNames.add(kit.getName());
    }

    public Kit getKit(String name)
    {
        return this.kits.get(name.toLowerCase());
    }
}
