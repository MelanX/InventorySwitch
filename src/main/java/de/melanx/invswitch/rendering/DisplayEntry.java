/*
 * This class is taken by "Pick Up Notifier" merged by DisplayEntry and ItemDisplayEntry:
 * https://github.com/Fuzss/pickupnotifier/tree/1.15/src/main/java/com/fuzs/pickupnotifier/util
 */

package de.melanx.invswitch.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.melanx.invswitch.ClientConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.*;
import org.apache.commons.lang3.mutable.MutableFloat;

public class DisplayEntry {

    public static final int HEIGHT = 18;
    private static final int MARGIN = 4;

    private final Rarity rarity;
    private final MutableFloat life;
    private final ItemStack stack;
    protected int count;

    public DisplayEntry(ItemStack stack) {
        this.stack = stack;
        this.count = Math.min(stack.getCount(), ClientConfigHandler.maxCount.get());
        this.rarity = stack.getRarity();
        this.life = new MutableFloat(ClientConfigHandler.displayTime.get());
    }

    public boolean isDead() {
        return this.life.compareTo(new MutableFloat(0.0F)) < 1;
    }

    public final void tick(float f) {
        this.life.subtract(f);
    }

    public final int getCount() {
        return this.count;
    }

    public final void addCount(int i) {
        this.count = Math.min(this.count + i, ClientConfigHandler.maxCount.get());
    }

    protected ITextComponent getName() {
        return ClientConfigHandler.combineEntries.get() ? this.stack.getItem().getName() : this.stack.getDisplayName();
    }

    public boolean canMerge(DisplayEntry entry) {
        return this.stack.getItem() == entry.stack.getItem();
    }

    private IFormattableTextComponent getNameComponent() {
        IFormattableTextComponent name = (IFormattableTextComponent) this.getName();
        if (this.count <= 0) {
            return name;
        }
        if (ClientConfigHandler.position.get().isMirrored()) {
            return new StringTextComponent(this.count + "x ").func_230529_a_(name);
        } else {
            return name.func_240702_b_(" x" + this.count);
        }
    }

    public final float getRelativeLife() {
        return 1.0F - Math.min(1.0F, this.getLife() / Math.min(ClientConfigHandler.moveTime.get(),
                ClientConfigHandler.displayTime.get()));
    }

    protected final float getLife() {
        return Math.max(0.0F, this.life.floatValue());
    }

    public final void resetLife() {
        this.life.setValue(ClientConfigHandler.displayTime.get());
    }

    public void merge(DisplayEntry entry) {
        this.addCount(entry.getCount());
        this.resetLife();
    }

    private Style getStyle() {
        if (!ClientConfigHandler.ignoreRarity.get() && this.rarity != Rarity.COMMON) {
            return Style.EMPTY.setFormatting(this.rarity.color);
        } else {
            return Style.EMPTY.setFormatting(ClientConfigHandler.color.get().getChatColor());
        }
    }

    private String getNameString() {
        return this.getNameComponent().func_230530_a_(this.getStyle()).getString(); // todo getFormattedText
    }

    private int getTextWidth(Minecraft mc) {
        String s = this.getNameComponent().getString();
        return mc.fontRenderer.getStringWidth(TextFormatting.getTextWithoutFormattingCodes(s));
    }

    public int getTotalWidth(Minecraft mc) {
        int length = this.getTextWidth(mc);
        return ClientConfigHandler.showSprite.get() ? length + MARGIN + 16 : length;
    }

    public final void render(MatrixStack ms, Minecraft mc, int posX, int posY, float alpha) {
        boolean mirrored = ClientConfigHandler.position.get().isMirrored();
        boolean sprite = ClientConfigHandler.showSprite.get();
        int i = mirrored || !sprite ? posX : posX + 16 + MARGIN;
        int textWidth = this.getTextWidth(mc);
        int opacity = mc.gameSettings.getChatBackgroundColor(0);
        if (opacity != 0) {
            AbstractGui.fill(ms, i - 2, posY + 3 - 2, i + textWidth + 2, posY + 3 + mc.fontRenderer.FONT_HEIGHT + 2, opacity);
        }

        int k = ClientConfigHandler.fadeAway.get() ? 255 - (int) (255 * alpha) : 255;
        if (k >= 5) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            mc.fontRenderer.drawStringWithShadow(ms, this.getNameString(), i, posY + 3, 16777215 + (k << 24));
            RenderSystem.disableBlend();
            if (sprite) {
                this.renderSprite(mc, mirrored ? posX + textWidth + MARGIN : posX, posY);
            }
            RenderSystem.popMatrix();
        }
    }

    protected void renderSprite(Minecraft mc, int posX, int posY) {
        RenderSystem.enableDepthTest();
        RenderSystem.disableLighting();
        mc.getItemRenderer().renderItemAndEffectIntoGUI(this.stack, posX, posY);
        RenderSystem.enableLighting();
        RenderHelper.disableStandardItemLighting();
        RenderSystem.disableDepthTest();
    }

    @SuppressWarnings("unused")
    public DisplayEntry copy() {
        return new DisplayEntry(this.stack);
    }

}
