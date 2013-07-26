/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 *
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [6 Jul 2013, 17:49:49 (GMT)]
 */

package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Config;
import thaumcraft.common.items.armor.ItemHoverHarness;
import vazkii.tinkerer.client.model.ModelMagnet;
import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.lib.LibResources;

public class RenderTileMobMagnet extends TileEntitySpecialRenderer {

	ModelMagnet model = new ModelMagnet();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float)x, (float)y , (float)z);
	boolean blue = tileentity.worldObj == null || tileentity.getBlockMetadata() == 0;
        bindTextureByName(blue ? LibResources.MODEL_MOB_MAGNET_S : LibResources.MODEL_MOB_MAGNET_N);

        int redstone = 0;
        if(tileentity.worldObj != null) {
        	for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
    			redstone = Math.max(redstone, tileentity.worldObj.getIndirectPowerLevelTo(tileentity.xCoord + dir.offsetX, tileentity.yCoord + dir.offsetY, tileentity.zCoord + dir.offsetZ, dir.ordinal()));
        } else redstone = 15;

        GL11.glTranslatef(0.5F, 1.5F, 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        model.render();

        GL11.glRotatef(90F, 1F, 0F, 0F);
        GL11.glTranslatef(0F, 0F, -0.6F);
        Icon icon = ((ItemHoverHarness) Config.itemHoverHarness).iconLightningRing;
        for(int i = 0; i < 3; i++) {
        	for(int j = 0; j < 2; j++) {
                GL11.glScalef(1F, -1F, 1F);
                UtilsFX.renderQuadCenteredFromIcon(false, icon, redstone / 15F * 0.7F + (redstone == 0 ? 0 : 0.4F), blue ? 0F : 1F, 0F, blue ? 1F : 0F, 225, GL11.GL_ONE_MINUS_SRC_ALPHA, 0.9F);
        	}

            GL11.glTranslated(0, 0, -(Math.cos(ClientTickHandler.clientTicksElapsed / 10F) + 1) * 0.09 - 0.1);
        }
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        GL11.glPopMatrix();
	}
}
