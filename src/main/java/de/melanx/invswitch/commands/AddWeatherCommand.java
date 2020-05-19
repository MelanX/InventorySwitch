package de.melanx.invswitch.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.Lib;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;

public class AddWeatherCommand {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("weather")
                .requires((cs) -> {
                    return cs.hasPermissionLevel(3);
                }).then(Commands.literal("add")
                        .then(Commands.literal("rain")
                                .then(Commands.argument("duration", IntegerArgumentType.integer()).executes((command) -> {
                                    return addRain(command.getSource(), IntegerArgumentType.getInteger(command, "duration") * 20);
                                })))
                        .then(Commands.literal("thunder")
                                .then(Commands.argument("duration", IntegerArgumentType.integer()).executes((command) -> {
                                    return addThunder(command.getSource(), IntegerArgumentType.getInteger(command, "duration") * 20);
                                }))))
                .then(Commands.literal("remove")
                        .then(Commands.literal("rain")
                                .then(Commands.argument("duration", IntegerArgumentType.integer()).executes((command) -> {
                                    return removeRain(command.getSource(), IntegerArgumentType.getInteger(command, "duration") * 20);
                                })))
                        .then(Commands.literal("thunder")
                                .then(Commands.argument("duration", IntegerArgumentType.integer()).executes((command) -> {
                                    return removeThunder(command.getSource(), IntegerArgumentType.getInteger(command, "duration") * 20);
                                }))));
    }

    private static int addRain(CommandSource source, int duration) {
        WorldInfo worldInfo = source.getServer().getWorld(DimensionType.OVERWORLD).getWorldInfo();
        int totalDuration = worldInfo.getRainTime() + duration;
        return setRainValues(source, worldInfo, totalDuration);
    }

    private static int addThunder(CommandSource source, int duration) {
        WorldInfo worldInfo = source.getServer().getWorld(DimensionType.OVERWORLD).getWorldInfo();
        int totalDuration = worldInfo.getThunderTime() + duration;
        return setThunderValues(source, worldInfo, totalDuration);
    }

    private static int removeRain(CommandSource source, int duration) {
        WorldInfo worldInfo = source.getServer().getWorld(DimensionType.OVERWORLD).getWorldInfo();
        int totalDuration = Math.max(worldInfo.getRainTime() - duration, 0);
        return setRainValues(source, worldInfo, totalDuration);
    }

    private static int removeThunder(CommandSource source, int duration) {
        WorldInfo worldInfo = source.getServer().getWorld(DimensionType.OVERWORLD).getWorldInfo();
        int totalDuration = Math.max(worldInfo.getThunderTime() - duration, 0);
        return setThunderValues(source, worldInfo, totalDuration);
    }

    private static int setRainValues(CommandSource source, WorldInfo worldInfo, int totalDuration) {
        worldInfo.setClearWeatherTime(0);
        worldInfo.setRainTime(totalDuration);
        worldInfo.setThunderTime(totalDuration);
        worldInfo.setRaining(true);
        worldInfo.setThundering(false);
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "weather.rain", totalDuration / 20);
        InventorySwitch.LOGGER.info(String.format("Set rain duration to %s seconds", totalDuration / 20));
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }

    private static int setThunderValues(CommandSource source, WorldInfo worldInfo, int totalDuration) {
        worldInfo.setClearWeatherTime(0);
        worldInfo.setRainTime(totalDuration);
        worldInfo.setThunderTime(totalDuration);
        worldInfo.setRaining(true);
        worldInfo.setThundering(true);
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + ".weather.thunder", totalDuration / 20);
        InventorySwitch.LOGGER.info(String.format("Set thunder duration to %s seconds", totalDuration / 20));
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }
}
