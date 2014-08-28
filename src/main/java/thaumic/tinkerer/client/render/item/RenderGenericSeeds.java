package thaumic.tinkerer.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.ItemInfusedSeeds;

import javax.swing.*;

/**
 * Created by pixlepix on 8/6/14.
 */
public class RenderGenericSeeds implements IItemRenderer {

    private static RenderItem renderItem = new RenderItem();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (type == ItemRenderType.INVENTORY)
            return true;
        if (type == ItemRenderType.EQUIPPED)
            return true;
        if (type == ItemRenderType.ENTITY)
            return true;
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (helper == ItemRendererHelper.ENTITY_BOBBING)
            return true;
        if (helper == ItemRendererHelper.ENTITY_ROTATION)
            return true;
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ItemInfusedSeeds item = (ItemInfusedSeeds) itemstack.getItem();
        IIcon icon = item.getIconIndex(itemstack);
        Aspect aspect = item.getAspect(itemstack);
        if (type == ItemRenderType.INVENTORY) {
            renderItemInInventory(itemstack, aspect, icon);
        } else if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            renderItemInEquipped(itemstack, aspect, icon);
        } else {
            EntityItem entityItem = (EntityItem) data[1];
            if (entityItem.worldObj == null) {
                float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
                GL11.glPushMatrix();
                GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
                renderItemAsEntity(itemstack, aspect, icon);
                GL11.glPopMatrix();
            } else {
                renderItemAsEntity(itemstack, aspect, icon);
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopAttrib();
    }

    private void renderItemInInventory(ItemStack itemstack, Aspect aspect, IIcon icon) {
        setColorForAspect(aspect);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawTexturedRectUV(0, 0, 0, 16, 16, icon);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderItemInEquipped(ItemStack itemstack, Aspect aspect, IIcon icon) {
        Tessellator tessellator = Tessellator.instance;
        setColorForAspect(aspect);
        ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    private void renderItemAsEntity(ItemStack itemstack, Aspect aspect, IIcon icon) {
        GL11.glPushMatrix();
        setColorForAspect(aspect);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawTextureIn3D(icon);
        GL11.glPopMatrix();
    }

    private void drawTextureIn3D(IIcon texture) {
        Tessellator tesselator = Tessellator.instance;
        float scale = 0.7F;
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        ItemRenderer.renderItemIn2D(tesselator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(), texture.getIconWidth(), texture.getIconHeight(), .05F);
        GL11.glPopMatrix();
    }

    private void drawTexturedRectUV(float x, float y, float z, int w, int h, IIcon icon) {
        Tessellator tesselator = Tessellator.instance;
        tesselator.startDrawingQuads();
        tesselator.addVertexWithUV(x, y + h, z, icon.getMinU(), icon.getMaxV());
        tesselator.addVertexWithUV(x + w, y + h, z, icon.getMaxU(), icon.getMaxV());
        tesselator.addVertexWithUV(x + w, y, z, icon.getMaxU(), icon.getMinV());
        tesselator.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
        tesselator.draw();
    }

    private void setColorForAspect(Aspect aspect) {
        //if(!aspect.isPrimal()){
        //    float r = (aspect.getColor() >> 16 & 0xFF) / 255.0F;
        //    float g = (aspect.getColor() >> 8 & 0xFF) / 255.0F;
        //    float b = (aspect.getColor() & 0xFF) / 255.0F;
        //    GL11.glColor3f(r, g, b);
        //}
    }
}
