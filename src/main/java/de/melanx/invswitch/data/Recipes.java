package de.melanx.invswitch.data;

import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.items.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(Registration.upside_gown_goggles.get())
                .key('I', Tags.Items.INGOTS_IRON)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .key('G', Tags.Items.GLASS)
                .patternLine("III")
                .patternLine("I I")
                .patternLine("GRG")
                .addCriterion("has_material", hasItem(Tags.Items.DUSTS_REDSTONE))
                .build(consumer, new ResourceLocation(InventorySwitch.MODID, Registration.upside_gown_goggles.get().getRegistryName().getPath()));
    }
}
