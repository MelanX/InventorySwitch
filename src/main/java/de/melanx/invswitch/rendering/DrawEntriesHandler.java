package de.melanx.invswitch.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import de.melanx.invswitch.items.ItemLootBox;
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

        if (event.phase != TickEvent.Phase.END || this.mc.isGamePaused()) {
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

        float scale = 4 / 6.0F;
        int scaledWidth = (int) (event.getWindow().getScaledWidth() / scale);
        int scaledHeight = (int) (event.getWindow().getScaledHeight() / scale);
        int posX = (int) (8 / scale);
        int posY = (int) (4 / scale);
        int offset = Math.abs(scaledHeight - DisplayEntry.HEIGHT - posY);
        int totalFade = (int) (ItemLootBox.PICK_UPS.stream().mapToDouble(DisplayEntry::getRelativeLife).average().orElse(0.0) * ItemLootBox.PICK_UPS.size() * DisplayEntry.HEIGHT);
        int renderY = offset + totalFade;
        GlStateManager.scalef(scale, scale, 1.0F);

        for (DisplayEntry entry : ItemLootBox.PICK_UPS) {
            int renderX = Math.abs(scaledWidth - entry.getTotalWidth(this.mc) - posX);
                if (renderY < offset + DisplayEntry.HEIGHT) {
                    entry.render(this.mc, renderX, renderY, MathHelper.clamp((float) (renderY - offset) / DisplayEntry.HEIGHT, 0.0F, 1.0F));
                }
            renderY += -DisplayEntry.HEIGHT;
        }

        GlStateManager.scalef(1.0F / scale, 1.0F / scale, 1.0F);
    }
}
