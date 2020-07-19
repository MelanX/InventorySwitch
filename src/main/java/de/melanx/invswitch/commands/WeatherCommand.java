package de.melanx.invswitch.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import de.melanx.invswitch.util.Lib;
import de.melanx.invswitch.InventorySwitch;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.ServerWorldInfo;

public class WeatherCommand {

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
        ServerWorldInfo worldInfo = (ServerWorldInfo) source.getWorld().getWorldInfo();
        int totalDuration = worldInfo.getRainTime() + duration;
        source.getWorld().func_241113_a_(0, totalDuration, true, false);
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "weather.rain", totalDuration / 20);
        InventorySwitch.LOGGER.info(String.format("Set rain duration to %s seconds", totalDuration / 20));
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }

    private static int addThunder(CommandSource source, int duration) {
        ServerWorldInfo worldInfo = (ServerWorldInfo) source.getWorld().getWorldInfo();
        int totalDuration = worldInfo.getThunderTime() + duration;
        source.getWorld().func_241113_a_(0, totalDuration, true, true);
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + ".weather.thunder", totalDuration / 20);
        InventorySwitch.LOGGER.info(String.format("Set thunder duration to %s seconds", totalDuration / 20));
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }

    private static int removeRain(CommandSource source, int duration) {
        ServerWorldInfo worldInfo = (ServerWorldInfo) source.getWorld().getWorldInfo();
        int totalDuration = Math.max(worldInfo.getRainTime() - duration, 0);
        source.getWorld().func_241113_a_(0, totalDuration, totalDuration > 0, false);
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "weather.rain", totalDuration / 20);
        InventorySwitch.LOGGER.info(String.format("Set rain duration to %s seconds", totalDuration / 20));
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }

    private static int removeThunder(CommandSource source, int duration) {
        ServerWorldInfo worldInfo = (ServerWorldInfo) source.getWorld().getWorldInfo();
        int totalDuration = Math.max(worldInfo.getThunderTime() - duration, 0);
        source.getWorld().func_241113_a_(0, totalDuration, totalDuration > 0, totalDuration > 0);
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + ".weather.thunder", totalDuration / 20);
        InventorySwitch.LOGGER.info(String.format("Set thunder duration to %s seconds", totalDuration / 20));
        source.sendFeedback(textComponent, false);
        return totalDuration;
    }
}
