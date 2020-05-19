package de.melanx.invswitch.items;

import de.melanx.invswitch.InventorySwitch;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, InventorySwitch.MODID);

    public static final Item.Properties itemProps = new Item.Properties().group(ItemGroup.MISC);

    public static final RegistryObject<Item> upside_gown_goggles = ITEMS.register("upside_down_goggles", () -> new ItemUpsideDownGoggles(itemProps));

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        InventorySwitch.LOGGER.info("Items registered.");
    }

}
