package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.client.misc.ClientHelper;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

/**
 * TESR for the enchanter
 */
public class TileEntityEnchanterRenderer extends TileEntitySpecialRenderer<TileEntityEnchanter> {


    /**
     * renders the Enchanter
     *
     * @param te           tile entity
     * @param x            xPos of the block
     * @param y            yPos of the block
     * @param z            zPos of the block
     * @param partialTicks udpate ticks
     * @param destroyStage stage of the block destruction
     * @param alpha        alpha amount of the block
     */
    @Override
    public void render(TileEntityEnchanter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if (te.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            float tmp = 0.7f;
            float progress;
            if (te.isWorking()) {
                progress = Math.min(1.0f, ((float) te.getProgress()) / 225f);
                tmp *= progress;
            } else if (te.getCooldown() > 0) {
                progress = Math.max(0.0f, ((float) te.getCooldown()) / 28f);
                tmp = 0.7f - (0.7f * (1 - progress));
            } else {
                tmp = 0.0f;
                progress = 0f;
            }

            GlStateManager.translate((float) x + 0.5F, (float) y + 0.8F + tmp, (float) z + 0.5F);
            GlStateManager.rotate(90f * (1 - progress), 1, 0, 0);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            ClientHelper.getRenderItem().renderItem(te.getInventory().getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();

        }
    }
}
