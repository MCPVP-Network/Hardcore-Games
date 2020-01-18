package com.mcpvp.hardcoregames.kit.kits;

import com.mcpvp.hardcoregames.commons.ItemStackBuilder;
import com.mcpvp.hardcoregames.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BerserkerKit extends Kit
{
    public static final int LENGTH_OF_TICKS = 40;
    public static final int LEVEL = 2;
    public static final PotionEffectType POTION_EFFECT = PotionEffectType.INCREASE_DAMAGE;

    public BerserkerKit()
    {
        super("berserker", new ItemStack(Material.STONE_SWORD));
    }


    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e)
    {
        Entity entity = e.getDamager();
        if(!(entity instanceof Player)) return;
        if(!(e.getEntity() instanceof LivingEntity)) return;
        Player player = (Player) entity;
        if(!this.hasKit(player)) return;

        boolean killed = ((LivingEntity) e.getEntity()).getHealth() - e.getFinalDamage() > 0;
        if(killed) return;
        player.addPotionEffect(new PotionEffect(POTION_EFFECT, LENGTH_OF_TICKS, LEVEL));
    }
}
