package de.melanx.invswitch.data;

import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.items.Registration;
import de.melanx.invswitch.util.Lib;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

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

            addItem(Registration.upside_gown_goggles.get().getRegistryName().getPath(), "Upside Down Goggles");
            addItem(Registration.loot_box.get().getRegistryName().getPath(), "Lootbox");
        }

        private void addCommand(String key, String value) {
            add(Lib.COMMAND_PREFIX + key, value);
        }

        private void addItem(String key, String value) {
            add(Lib.ITEM_PREFIX + key, value);
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

            addItem(Registration.upside_gown_goggles.get().getRegistryName().getPath(), "Kopfstehende Schutzbrille");
            addItem(Registration.loot_box.get().getRegistryName().getPath(), "Lootbox");
        }

        private void addCommand(String key, String value) {
            add(Lib.COMMAND_PREFIX + key, value);
        }

        private void addItem(String key, String value) {
            add(Lib.ITEM_PREFIX + key, value);
        }
    }
}
