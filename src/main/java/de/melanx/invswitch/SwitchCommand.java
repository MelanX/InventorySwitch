package de.melanx.invswitch;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class SwitchCommand {

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
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
        if (target1 instanceof PlayerEntity) {
            player1 = (PlayerEntity) target1;
            inventory1 = player1.inventory;
        }
        if (target2 instanceof PlayerEntity) {
            player2 = (PlayerEntity) target2;
            inventory2 = player2.inventory;
        }
        if (inventory1 == null || inventory2 == null) return 0;
        List<ItemStack> invCache1 = getCache(inventory1);
        List<ItemStack> invCache2 = getCache(inventory2);
        setInventory(inventory1, invCache2);
        setInventory(inventory2, invCache1);
        ITextComponent textComponent = new TranslationTextComponent(InventorySwitch.MODID + ".change_inventories", player1.getDisplayName().getFormattedText(), player2.getDisplayName().getFormattedText());
        source.sendFeedback(textComponent, true);
        PlayerList playerList = source.getServer().getPlayerList();
        playerList.sendMessage(textComponent);
        return 1;
    }

    private static List<ItemStack> getCache(PlayerInventory inv) {
        List<ItemStack> cache = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            cache.add(i, inv.getStackInSlot(i));
        }
        return cache;
    }

    private static void setInventory(PlayerInventory inv, List<ItemStack> cache) {
        inv.clear();
        for (int i = 0; i < cache.size(); ++i) {
            inv.add(i, cache.get(i));
        }
    }
}
