package de.melanx.invswitch.util;

import de.melanx.invswitch.InventorySwitch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InventorySwitch.MODID)
public class Events {
    private static Vec3d playerPosition;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void fovEvent(EntityViewRenderEvent.FOVModifier event) {
        Entity entity = event.getInfo().getRenderViewEntity();
        if (entity instanceof LivingEntity) {
            ItemStack helm = ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (helm.getItem() == Registration.upside_gown_goggles.get()) {
                event.setFOV(1000);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (playerPosition == null) playerPosition = new Vec3d(player.posX, player.posY, player.posZ);
        if (hasEffect(player, Registration.freeze.get())) {
            player.setPosition(playerPosition.getX(), playerPosition.getY(), playerPosition.getZ());
        } else {
            playerPosition = new Vec3d(player.posX, player.posY, player.posZ);
        }
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.getEntityWorld().isRemote && hasEffect(player, Registration.freeze.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onHit(LivingAttackEvent event) {
        DamageSource source = event.getSource();
        Entity entity = source.getTrueSource();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (hasEffect(player, Registration.freeze.get())) {
                event.setCanceled(true);
            }
        }
        LivingEntity victim = event.getEntityLiving();
        if (source == DamageSource.WITHER && hasEffect(victim, Registration.witherResistance.get())) {
            event.setCanceled(true);
        }
    }

    private static boolean hasEffect(LivingEntity entity, Effect effect) {
        return entity.getActivePotionEffect(effect) != null;
    }

}
