package de.melanx.invswitch.util;

import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.items.ItemLootBox;
import de.melanx.invswitch.items.ItemUpsideDownGoggles;
import de.melanx.invswitch.potion.FreezeEffect;
import de.melanx.invswitch.potion.WitherResistanceEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, InventorySwitch.MODID);
    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, InventorySwitch.MODID);
    public static final DeferredRegister<Potion> POTIONS = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, InventorySwitch.MODID);

    public static final Item.Properties itemProps = new Item.Properties().group(ItemGroup.MISC);

    public static final RegistryObject<Item> upside_gown_goggles = ITEMS.register("upside_down_goggles", () -> new ItemUpsideDownGoggles(itemProps));
    public static final RegistryObject<Item> loot_box = ITEMS.register("lootbox", () -> new ItemLootBox(itemProps));

    public static final RegistryObject<Effect> freeze = EFFECTS.register("freeze", FreezeEffect::new);
    public static final RegistryObject<Effect> witherResistance = EFFECTS.register("wither_resistance", WitherResistanceEffect::new);

    public static final RegistryObject<Potion> freezePotion = POTIONS.register("freeze", () -> new Potion("freeze", new EffectInstance(freeze.get(), 600)));
    public static final RegistryObject<Potion> longFreezePotion = POTIONS.register("long_freeze", () -> new Potion(new EffectInstance(freeze.get(), 2400)));
    public static final RegistryObject<Potion> witherResistancePotion = POTIONS.register("wither_resistance", () -> new Potion("wither_resistance", new EffectInstance(witherResistance.get(), 2400)));
    public static final RegistryObject<Potion> longWitherResistancePotion = POTIONS.register("long_wither_resistance", () -> new Potion("long_wither_resistance", new EffectInstance(witherResistance.get(), 9600)));

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        InventorySwitch.LOGGER.info("Items registered");
        EFFECTS.register(bus);
        InventorySwitch.LOGGER.info("Effects registered");
        POTIONS.register(bus);
        InventorySwitch.LOGGER.info("Potions registered");
    }

    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, Items.PACKED_ICE, freezePotion.get());
        PotionBrewing.addMix(freezePotion.get(), Items.REDSTONE, longFreezePotion.get());

        PotionBrewing.addMix(Potions.AWKWARD, Items.WITHER_SKELETON_SKULL, witherResistancePotion.get());
        PotionBrewing.addMix(witherResistancePotion.get(), Items.REDSTONE, longWitherResistancePotion.get());
    }
}
