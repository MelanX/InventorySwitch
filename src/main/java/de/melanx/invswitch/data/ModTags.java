package de.melanx.invswitch.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModTags {

    public static class ModItems {
        public static final Tag<Item> FOOD = tag("food");

        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
        }
    }

    public static class ModItemTags extends ItemTagsProvider {
        public ModItemTags(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void registerTags() {
            Registry.ITEM.forEach(item -> {
                if (item.getFood() != null && (item != Items.ENCHANTED_GOLDEN_APPLE)) {
                    getBuilder(ModItems.FOOD).add(item);
                }
            });
        }
    }

}
