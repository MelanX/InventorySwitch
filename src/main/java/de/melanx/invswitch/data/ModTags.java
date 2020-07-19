package de.melanx.invswitch.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModTags {

    public static class ModItems {
        public static final ITag.INamedTag<Item> FOOD = tag("food");

        private static ITag.INamedTag<Item> tag(String name) {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", name).toString());
        }
    }

    public static class ModItemTags extends ItemTagsProvider {
        public ModItemTags(DataGenerator generator) {
            super(generator, new BlockTagsProvider(generator));
        }

        @Override
        protected void registerTags() {
            Registry.ITEM.forEach(item -> {
                if (item.getFood() != null && (item != Items.ENCHANTED_GOLDEN_APPLE)) {
                    func_240522_a_(ModItems.FOOD).func_240534_a_(item);
                }
            });
        }
    }

}
