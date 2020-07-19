package de.melanx.invswitch.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.ArgumentBuilder;
import de.melanx.invswitch.InventorySwitch;
import de.melanx.invswitch.util.Lib;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.text.*;

import java.util.UUID;

public class SwitchCommand {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("switch")
                .requires((cs) -> {
                    return cs.hasPermissionLevel(3);
                }).then(Commands.argument("target1", EntityArgument.player())
                        .then(Commands.argument("target2", EntityArgument.player()).executes((command) -> {
                            return changeInventory(command.getSource(), EntityArgument.getEntity(command, "target1"), EntityArgument.getEntity(command, "target2"));
                        })));
    }

    private static int changeInventory(CommandSource source, Entity target1, Entity target2) {
        PlayerEntity player1 = null;
        PlayerEntity player2 = null;
        PlayerInventory inventory1 = null;
        PlayerInventory inventory2 = null;
        Style red = Style.EMPTY.applyFormatting(TextFormatting.DARK_RED);
        if (target1 instanceof PlayerEntity) {
            player1 = (PlayerEntity) target1;
            inventory1 = player1.inventory;
        }
        if (target2 instanceof PlayerEntity) {
            player2 = (PlayerEntity) target2;
            inventory2 = player2.inventory;
        }
        if (player1 == null || player2 == null) {
            IFormattableTextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "player_null");
            textComponent.func_230530_a_(red);
            InventorySwitch.LOGGER.error("One of the player was not found");
            source.sendFeedback(textComponent, false);
            return 0;
        }
        if (inventory1 == null || inventory2 == null) {
            IFormattableTextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "inv_null");
            textComponent.func_230530_a_(red);
            InventorySwitch.LOGGER.error("One of the inventories was not found");
            source.sendFeedback(textComponent, false);
            return 0;
        }
        if (player1 == player2) {
            IFormattableTextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "player_equals");
            textComponent.func_230530_a_(red);
            InventorySwitch.LOGGER.error("It's the same player");
            source.sendFeedback(textComponent, false);
            return 0;
        }
        if (inventory1 == inventory2) {
            IFormattableTextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "inv_equals");
            textComponent.func_230530_a_(red);
            InventorySwitch.LOGGER.error("Both players share one inventory");
            source.sendFeedback(textComponent, false);
            return 0;
        }
        ServerPlayerEntity fakePlayer = new ServerPlayerEntity(source.getServer(), source.getWorld(), new GameProfile(UUID.randomUUID(), "[" + InventorySwitch.MODID + "]"), new PlayerInteractionManager(source.getWorld()));
        fakePlayer.inventory.copyInventory(player1.inventory);
        player1.inventory.copyInventory(player2.inventory);
        player2.inventory.copyInventory(fakePlayer.inventory);
        fakePlayer.remove();
        ITextComponent textComponent = new TranslationTextComponent(Lib.COMMAND_PREFIX + "change_inventories", player1.getDisplayName().getUnformattedComponentText(), player2.getDisplayName().getUnformattedComponentText());
        InventorySwitch.LOGGER.info(String.format("%s and %s changed their inventories", player1.getDisplayName().getString(), player2.getDisplayName().getString()));
        source.sendFeedback(textComponent, false);
        return 1;
    }
}
