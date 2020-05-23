package de.melanx.invswitch.items;

import de.melanx.invswitch.InventorySwitch;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;

import java.util.List;

public class ItemLootBox extends Item {

    public static final ResourceLocation loot_table = new ResourceLocation(InventorySwitch.MODID, "lootbox");

    public ItemLootBox(Properties properties) {
        super(properties.maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            LootTable table = worldIn.getServer().getLootTableManager().getLootTableFromLocation(loot_table);
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) worldIn).withParameter(LootParameters.TOOL, playerIn.getHeldItem(handIn)).withParameter(LootParameters.POSITION, playerIn.getPosition());
            List<ItemStack> items = table.generate(builder.build(LootParameterSets.FISHING));
            for (ItemStack item : items) {
                if (item.getItem() instanceof SpawnEggItem) {
                    EntityType<?> entityType = ((SpawnEggItem) item.getItem()).getType(item.getTag());
                    for (int i = 0; i < item.getCount(); i++)
                        entityType.spawn(worldIn, item, playerIn, playerIn.getPosition(), SpawnReason.SPAWN_EGG, false, false);
                } else if (!playerIn.inventory.addItemStackToInventory(item)) {
                    worldIn.addEntity(new ItemEntity(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, item));
                }
            }
            playerIn.getHeldItem(handIn).shrink(1);
        } else {
            worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
