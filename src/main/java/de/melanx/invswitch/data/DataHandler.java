package de.melanx.invswitch.data;

import de.melanx.invswitch.InventorySwitch;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = InventorySwitch.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            generator.addProvider(new ModTags.ModItemTags(generator));
            generator.addProvider(new ModLootTables(generator));
            generator.addProvider(new Recipes(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new ItemModels(generator, helper));
            new Languages(generator);
        }
    }

}
