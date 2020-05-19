package de.melanx.invswitch.util;

import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.items.Registration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InventorySwitch.MODID)
public class Events {

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

}
