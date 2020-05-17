package de.melanx.invswitch.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import de.melanx.invswitch.InventorySwitch;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;

public class AddWeatherCommand {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("addweathertime")
                .requires((cs) -> {
                    return cs.hasPermissionLevel(3);
                }).then(Commands.literal("rain")
                        .then(Commands.argument("duration", IntegerArgumentType.integer()).executes((command) -> {
                            return addRain(command.getSource(), IntegerArgumentType.getInteger(command, "duration") * 20);
                        })))
                .then(Commands.literal("thunder")
                        .then(Commands.argument("duration", IntegerArgumentType.integer()).executes((command) -> {
                            return addThunder(command.getSource(), IntegerArgumentType.getInteger(command, "duration") * 20);
                        })));
    }

    private static int addRain(CommandSource source, int duration) {
        WorldInfo worldInfo = source.getServer().getWorld(DimensionType.OVERWORLD).getWorldInfo();
        int totalDuration = worldInfo.getRainTime() + duration;
        worldInfo.setClearWeatherTime(0);
        worldInfo.setRainTime(totalDuration);
        worldInfo.setThunderTime(totalDuration);
        worldInfo.setRaining(true);
        worldInfo.setThundering(false);
        ITextComponent textComponent = new TranslationTextComponent(InventorySwitch.MODID + ".weather.add.rain", totalDuration / 20);
        InventorySwitch.LOGGER.info(textComponent.getString());
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }

    private static int addThunder(CommandSource source, int duration) {
        WorldInfo worldInfo = source.getServer().getWorld(DimensionType.OVERWORLD).getWorldInfo();
        int totalDuration = worldInfo.getRainTime() + duration;
        worldInfo.setClearWeatherTime(0);
        worldInfo.setRainTime(totalDuration);
        worldInfo.setThunderTime(totalDuration);
        worldInfo.setRaining(true);
        worldInfo.setThundering(true);
        ITextComponent textComponent = new TranslationTextComponent(InventorySwitch.MODID + ".weather.add.thunder", totalDuration / 20);
        InventorySwitch.LOGGER.info(textComponent.getString());
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }

}
