/*
 * This class is taken by "Pick Up Notifier":
 * https://github.com/Fuzss/pickupnotifier/blob/1.15/src/main/java/com/fuzs/pickupnotifier/handler/DrawEntriesHandler.java
 */

package de.melanx.invswitch.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import de.melanx.invswitch.ClientConfigHandler;
import de.melanx.invswitch.items.ItemLootBox;
import de.melanx.invswitch.util.PositionPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DrawEntriesHandler {

    private final Minecraft mc = Minecraft.getInstance();

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END || this.mc.isGamePaused() && ClientConfigHandler.displayTime.get() != 0) {
            return;
        }

        if (!ItemLootBox.PICK_UPS.isEmpty() && !this.mc.isGamePaused()) {
            ItemLootBox.PICK_UPS.forEach(it -> it.tick(event.renderTickTime));
            ItemLootBox.PICK_UPS.removeIf(DisplayEntry::isDead);
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        if (ItemLootBox.PICK_UPS.isEmpty()) {
            return;
        }
        MatrixStack ms = event.getMatrixStack();

        float scale = ClientConfigHandler.scale.get() / 6.0F;
        int scaledWidth = (int) (event.getWindow().getScaledWidth() / scale);
        int scaledHeight = (int) (event.getWindow().getScaledHeight() / scale);
        PositionPreset position = ClientConfigHandler.position.get();
        boolean bottom = position.isBottom();
        int posX = (int) (ClientConfigHandler.xOffset.get() / scale);
        int posY = (int) (ClientConfigHandler.yOffset.get() / scale);
        int offset = position.getY(DisplayEntry.HEIGHT, scaledHeight, posY);
        boolean move = ClientConfigHandler.move.get();
        int totalFade = move ? (int) (ItemLootBox.PICK_UPS.stream().mapToDouble(DisplayEntry::getRelativeLife).average().orElse(0.0) * ItemLootBox.PICK_UPS.size() * DisplayEntry.HEIGHT) : 0;
        int renderY = offset + (bottom ? totalFade : -totalFade);
        GlStateManager.scalef(scale, scale, 1.0F);

        for (DisplayEntry entry : ItemLootBox.PICK_UPS) {
            int renderX = position.getX(entry.getTotalWidth(this.mc), scaledWidth, posX);
            if (bottom) {
                if (renderY < offset + DisplayEntry.HEIGHT) {
                    entry.render(ms, this.mc, renderX, renderY, move ? MathHelper.clamp((float) (renderY - offset) / DisplayEntry.HEIGHT, 0.0F, 1.0F) : entry.getRelativeLife());
                }
            } else if (renderY > offset - DisplayEntry.HEIGHT) {
                entry.render(ms, this.mc, renderX, renderY, move ? MathHelper.clamp((float) (renderY - offset) / -DisplayEntry.HEIGHT, 0.0F, 1.0F) : entry.getRelativeLife());
            }
            renderY += bottom ? -DisplayEntry.HEIGHT : DisplayEntry.HEIGHT;
        }

        GlStateManager.scalef(1.0F / scale, 1.0F / scale, 1.0F);
    }
}
