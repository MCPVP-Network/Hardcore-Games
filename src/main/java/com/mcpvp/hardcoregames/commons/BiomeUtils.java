package com.mcpvp.hardcoregames.commons;

import net.minecraft.server.v1_8_R3.BiomeBase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BiomeUtils
{
    /**
     * Ummm ew, removes all biomes ocean biomes from generating on the server
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void removeLargeOceans() throws NoSuchFieldException, IllegalAccessException {
        Field biomesField = BiomeBase.class.getDeclaredField("biomes");

        final int mods = biomesField.getModifiers() & ~Modifier.FINAL;
        Field modField = Field.class.getDeclaredField("modifiers");
        modField.setAccessible(true);
        modField.setInt(biomesField,mods);
        biomesField.setAccessible(true);

        BiomeBase[] biomes = (BiomeBase[]) biomesField.get(null);

        biomes[BiomeBase.DEEP_OCEAN.id] = BiomeBase.PLAINS;
        biomes[BiomeBase.OCEAN.id] = BiomeBase.FOREST;

        biomesField.set(null, biomes);

    }
}
