package de.melanx.invswitch.data;

import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.util.Lib;
import de.melanx.invswitch.util.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

import java.util.Locale;

public class Languages {

    public Languages(DataGenerator generator) {
        generator.addProvider(new English(generator));
        generator.addProvider(new German(generator));
    }

    private static class English extends LanguageProvider {

        public English(DataGenerator generator) {
            super(generator, InventorySwitch.MODID, Locale.US.toString());
        }

        @Override
        protected void addTranslations() {
            addCommand("change_inventories", "%s and %s changed their inventories");
            addCommand("change_inventories_shuffled", "%s and %s changed their inventories but shuffled");
            addCommand("player_null", "One of the player was not found");
            addCommand("inv_null", "One of the inventories was not found");
            addCommand("player_equals", "It's the same player");
            addCommand("inv_equals", "Both players share one inventory");
            addCommand("weather.rain", "Set rain duration to %s seconds");
            addCommand("weather.thunder", "Set thunder duration to %s seconds");

            addItem(Registration.upside_gown_goggles, "Upside Down Goggles");
            addItem(Registration.loot_box, "Lootbox");

            addEffect(Registration.freeze, "Freeze");
            addEffect(Registration.witherResistance, "Wither Resistance");

            addPotion(Registration.freezePotion, "Freeze");
            addPotion(Registration.longFreezePotion, "Freeze");

            addPotion(Registration.witherResistancePotion, "Wither Resistance");
            addPotion(Registration.longWitherResistancePotion, "Wither Resistance");
        }

        private void addCommand(String key, String value) {
            add(Lib.COMMAND_PREFIX + key, value);
        }

        private void addPotion(RegistryObject<Potion> object, String value) {
            String key = object.get().getRegistryName().getPath();
            add(Lib.POTION_PREFIX + key, "Potion of " + value);
            add(Lib.SPLASH_POTION_PREFIX + key, "Splash Potion of " + value);
            add(Lib.LINGERING_POTION_PREFIX + key, "Lingering Potion of " + value);
            add(Lib.TIPPED_ARROW_PREFIX + key, "Arrow of " + value);
        }
    }

    private static class German extends LanguageProvider {

        public German(DataGenerator generator) {
            super(generator, InventorySwitch.MODID, Locale.GERMANY.toString());
        }

        @Override
        protected void addTranslations() {
            addCommand("change_inventories", "%s und %s haben ihre Inventare getauscht");
            addCommand("change_inventories_shuffled", "%s und %s haben ihre Inventare gemischt getauscht");
            addCommand("player_null", "Einer der Spieler wurde nicht gefunden");
            addCommand("inv_null", "Eines der Inventare wurde nicht gefunden");
            addCommand("player_equals", "Es ist derselbe Spieler");
            addCommand("inv_equals", "Beide Spieler teilen sich ein Inventar");
            addCommand("weather.rain", "Regendauer auf %s Sekunden gesetzt");
            addCommand("weather.thunder", "Gewitterdauer auf %s Sekunden gesetzt");

            addItem(Registration.upside_gown_goggles, "Kopfstehende Schutzbrille");
            addItem(Registration.loot_box, "Lootbox");

            addEffect(Registration.freeze, "Gefrierung");
            addEffect(Registration.witherResistance, "Witherresistenz");

            addMPotion(Registration.freezePotion, "Gefrierens");
            addMPotion(Registration.longFreezePotion, "Gefrierens");

            addWPotion(Registration.witherResistancePotion, "Witherresistenz");
            addWPotion(Registration.longWitherResistancePotion, "Witherresistenz");
        }

        private void addCommand(String key, String value) {
            add(Lib.COMMAND_PREFIX + key, value);
        }

        private void addWPotion(RegistryObject<Potion> object, String value) {
            String key = object.get().getRegistryName().getPath();
            add(Lib.POTION_PREFIX + key, "Trank der " + value);
            add(Lib.SPLASH_POTION_PREFIX + key, "Wurftrank der " + value);
            add(Lib.LINGERING_POTION_PREFIX + key, "Verweiltrank der " + value);
            add(Lib.TIPPED_ARROW_PREFIX + key, "Pfeil der " + value);
        }

        private void addMPotion(RegistryObject<Potion> object, String value) {
            String key = object.get().getRegistryName().getPath();
            add(Lib.POTION_PREFIX + key, "Trank des " + value);
            add(Lib.SPLASH_POTION_PREFIX + key, "Wurftrank des " + value);
            add(Lib.LINGERING_POTION_PREFIX + key, "Verweiltrank des " + value);
            add(Lib.TIPPED_ARROW_PREFIX + key, "Pfeil des " + value);
        }
    }
}
