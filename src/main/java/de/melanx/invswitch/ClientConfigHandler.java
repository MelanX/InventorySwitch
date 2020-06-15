package de.melanx.invswitch;

import de.melanx.invswitch.util.PositionPreset;
import de.melanx.invswitch.util.TextColor;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigHandler {

    public static ForgeConfigSpec.BooleanValue showPickupNotifier;
    public static ForgeConfigSpec.BooleanValue showSprite;
    public static ForgeConfigSpec.EnumValue<TextColor> color;
    public static ForgeConfigSpec.BooleanValue ignoreRarity;
    public static ForgeConfigSpec.BooleanValue combineEntries;
    public static ForgeConfigSpec.IntValue displayTime;
    public static ForgeConfigSpec.BooleanValue move;
    public static ForgeConfigSpec.IntValue moveTime;
    public static ForgeConfigSpec.BooleanValue fadeAway;
    public static ForgeConfigSpec.IntValue maxCount;

    public static ForgeConfigSpec.EnumValue<PositionPreset> position;
    public static ForgeConfigSpec.IntValue xOffset;
    public static ForgeConfigSpec.IntValue yOffset;
    public static ForgeConfigSpec.DoubleValue height;
    public static ForgeConfigSpec.IntValue scale;

    public ClientConfigHandler(ForgeConfigSpec.Builder builder) {
        builder.push("general");
        showPickupNotifier = builder.comment("In case you have other mods that show the picked up items, disable this.").define("show-notifier", true);
        showSprite = builder.comment("Show a small sprite next to the name of each entry showing its contents.").define("draw-sprites", true);
        color = builder.comment("Color of the entry name text.").defineEnum("text-color", TextColor.WHITE);
        ignoreRarity = builder.comment("Ignore rarity of items and always use color specified in \"Text Color\" instead.").define("ignore-rarity", false);
        combineEntries = builder.comment("Combine entries of the same type instead of showing each one individually.").define("combine-entries", true);
        displayTime = builder.comment("Amount of ticks each entry will be shown for. Set to 0 to only remove entries when space for new ones is needed.").defineInRange("display-time", 100, 0, Integer.MAX_VALUE);
        move = builder.comment("Make outdated entries slowly move out of the screen instead of disappearing in place.").define("move-out-of-screen", true);
        moveTime = builder.comment("Amount of ticks it takes for an entry to move out of the screen. Value cannot be larger than \"Display Time\".").defineInRange("move-time", 20, 0, Integer.MAX_VALUE);
        fadeAway = builder.comment("Make outdated entry names slowly fade away instead of simply vanishing.").define("fade-away", true);
        maxCount = builder.comment("Maximum count number displayed. Setting this to 0 will prevent the count from being displayed at all.").defineInRange("max-amount", 9999, 0, Integer.MAX_VALUE);
        builder.pop();

        builder.push("display");
        position = builder.comment("Screen corner for entry list to be drawn in.").defineEnum("corner", PositionPreset.BOTTOM_RIGHT);
        xOffset = builder.comment("Offset on x-axis from screen border.").defineInRange("offsetX", 8, 0, Integer.MAX_VALUE);
        yOffset = builder.comment("Offset on y-axis from screen border.").defineInRange("offsetY", 4, 0, Integer.MAX_VALUE);
        height = builder.comment("Percentage of relative screen height entries are allowed to fill at max.").defineInRange("max-height", 0.5, 0.0, 1.0);
        scale = builder.comment("Scale of entries. A lower scale will make room for more rows to show. Works in tandem with \"GUI Scale\" option in \"Video Settings\".").defineInRange("scale", 4, 1, 24);
        builder.pop();
    }
}
