package de.melanx.invswitch.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.melanx.invswitch.items.ItemLootBox;
import de.melanx.invswitch.util.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.functions.EnchantRandomly;
import net.minecraft.world.storage.loot.functions.SetCount;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTables extends LootTableProvider {

    public ModLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(LootBox::new, LootParameterSets.EMPTY)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.func_227508_a_(validationtracker, name, table));
    }

    private class LootBox extends FishingLootTables {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
            builder.accept(ItemLootBox.loot_table, LootTable.builder()
                    .addLootPool(LootPool.builder()
                            .addEntry(ItemLootEntry.builder(Registration.upside_gown_goggles.get()).weight(5))
                            .addEntry(ItemLootEntry.builder(Items.BOOK).acceptFunction(EnchantRandomly.func_215900_c()).weight(3))
                            .addEntry(ItemLootEntry.builder(Items.ENCHANTED_GOLDEN_APPLE)))
                    .addLootPool(LootPool.builder()
                            .rolls(ConstantRange.of(3))
                            .addEntry(TagLootEntry.func_216176_b(ModTags.ModItems.FOOD).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 5.0F))))));
        }
    }
}
