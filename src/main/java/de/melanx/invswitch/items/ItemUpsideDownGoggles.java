/*
 * This class was an idea by the mod "Stupid Things"
 * The source class can be found here: https://github.com/Furgl/Stupid-Things/blob/1.12/src/main/java/furgl/stupidThings/common/item/ItemUpsideDownGoggles.java
 */

package de.melanx.invswitch.items;

import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.models.ModelUpsideDownGoggles;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;

public class ItemUpsideDownGoggles extends ArmorItem {

    private final LazyValue<BipedModel> model;

    public ItemUpsideDownGoggles(Item.Properties builder) {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, builder);
        this.model = DistExecutor.runForDist(() -> () -> new LazyValue<>(ModelUpsideDownGoggles::new),
                () -> () -> null);
    }

    @Nullable
    @Override
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        return new ModelUpsideDownGoggles();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return InventorySwitch.MODID + ":textures/models/armor/upside_down_goggles.png";
    }
}
