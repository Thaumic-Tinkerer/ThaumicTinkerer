/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.nekokittygames.thaumictinkerer.client.gui.GuiEnchanter;
import com.nekokittygames.thaumictinkerer.client.misc.EnchantmentGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Enchantment Button
 * @author Katrina Knight
 */
public class GuiEnchantmentButton extends GuiButton {


    /**
     * The {@link Enchantment} represented.
     */
    protected Enchantment enchant;
    /**
     * The Parent Enchanter GUI {@link GuiEnchanter}.
     */
    protected GuiEnchanter parent;

    /**
     * Level of enchantment
     *
     * @return level of enchantment
     */
    protected int getLevel() {
        return 1;
    }

    /**
     * Constructor for the Enchantment button
     *
     * @param parent   parent GUI
     * @param buttonId ID of the button in the gui
     * @param x        X position of the button
     * @param y        Y position of the button
     */
    public GuiEnchantmentButton(GuiEnchanter parent, int buttonId, int x, int y) {
        super(buttonId, x, y, 16, 16, "");
        this.parent = parent;
    }

    /**
     * Should this button be rendered?
     *
     * @return true if the button should be rendered false otherwise
     */
    protected boolean dontRender() {
        return enchant == null || !enabled;
    }

    /**
     * Draws the button
     * @param mc {@link Minecraft} Instance
     * @param mouseX current X position of the mouse
     * @param mouseY current Y position of the mouse
     * @param partialTicks number of ticks since last draw
     */
    @Override
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (dontRender())
            return;
        ResourceLocation resourceLocation = EnchantmentGui.getEnchantmentIcon(enchant);
        mc.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        drawTexturedModalRect16(x, y, 0, 0, 16, 16);
        GlStateManager.disableBlend();
        if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
            List<String> tooltip = new ArrayList<>();
            tooltip.add(ChatFormatting.AQUA + enchant.getTranslatedName(getLevel()));
            parent.getTooltip().addAll(tooltip);
        }
    }

    /**
     * Draws a rectangle textured
     *
     * @param x        X position to draw in
     * @param y        Y position to draw in
     * @param textureX X position of the texture
     * @param textureY Y position of the texture
     * @param width    width to draw
     * @param height   height to draw
     */
    @SuppressWarnings("PointlessArithmeticExpression")
    public void drawTexturedModalRect16(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 1F / 16F;
        float f1 = 1F / 16F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x + 0, y + height, this.zLevel).tex((float) (textureX + 0) * f, (float) (textureY + height) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, this.zLevel).tex((float) (textureX + width) * f, (float) (textureY + height) * f1).endVertex();
        bufferbuilder.pos(x + width, y + 0, this.zLevel).tex((float) (textureX + width) * f, (float) (textureY + 0) * f1).endVertex();
        bufferbuilder.pos(x + 0, y + 0, this.zLevel).tex((float) (textureX + 0) * f, (float) (textureY + 0) * f1).endVertex();
        tessellator.draw();
    }

    /**
     * Gets the enchantment the button is representing
     *
     * @return an {@link Enchantment}, or null if none
     */
    public Enchantment getEnchant() {
        return enchant;
    }

    /**
     * Sets the enchantment this button is representing
     *
     * @param enchant {@link Enchantment} to represent
     * @return this object
     */
    public GuiEnchantmentButton setEnchant(Enchantment enchant) {
        this.enchant = enchant;
        return this;
    }

    /**
     * Gets the parent GUI for the button
     *
     * @return Parent GUI
     */
    public GuiEnchanter getParent() {
        return parent;
    }


    /**
     * Sets parent.
     *
     * @param parent the parent
     * @return this object
     */
    public GuiEnchantmentButton setParent(GuiEnchanter parent) {
        this.parent = parent;
        return this;
    }
}
