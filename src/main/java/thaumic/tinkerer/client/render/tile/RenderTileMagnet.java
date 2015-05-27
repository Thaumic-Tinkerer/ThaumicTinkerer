/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [12 Sep 2013, 17:55:04 (GMT)]
 */
package thaumic.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.armor.ItemHoverHarness;
import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.client.model.ModelMagnet;

public class RenderTileMagnet extends TileEntitySpecialRenderer {

    private static final ResourceLocation blue = new ResourceLocation(LibResources.MODEL_MAGNET_S);
    private static final ResourceLocation red = new ResourceLocation(LibResources.MODEL_MAGNET_N);
    private static final ResourceLocation blueMob = new ResourceLocation(LibResources.MODEL_MOB_MAGNET_S);
    private static final ResourceLocation redMob = new ResourceLocation(LibResources.MODEL_MOB_MAGNET_N);
    public static boolean mob = false;
    ModelMagnet model = new ModelMagnet();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float) x, (float) y, (float) z);
        boolean blue = tileentity.getWorldObj() == null || (tileentity.getBlockMetadata() & 1) == 0;
        boolean mob = tileentity.getWorldObj() == null ? RenderTileMagnet.mob : (tileentity.getBlockMetadata() & 2) == 2;

        ClientHelper.minecraft().renderEngine.bindTexture(mob ? blue ? blueMob : redMob : blue ? RenderTileMagnet.blue : red);

        int redstone = 0;
        if (tileentity.getWorldObj() != null) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
                redstone = Math.max(redstone, tileentity.getWorldObj().getIndirectPowerLevelTo(tileentity.xCoord + dir.offsetX, tileentity.yCoord + dir.offsetY, tileentity.zCoord + dir.offsetZ, dir.ordinal()));
        } else redstone = 15;

        GL11.glTranslatef(0.5F, 1.5F, 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        model.render();

        GL11.glRotatef(90F, 1F, 0F, 0F);
        GL11.glTranslatef(0F, 0F, -0.6F);
        IIcon icon = ((ItemHoverHarness) ConfigItems.itemHoverHarness).iconLightningRing;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                GL11.glScalef(1F, -1F, 1F);
                UtilsFX.renderQuadCenteredFromIcon(false, icon, redstone / 15F * 0.7F + (redstone == 0 ? 0 : 0.4F), blue ? 0F : 1F, 0F, blue ? 1F : 0F, 225, GL11.GL_ONE_MINUS_SRC_ALPHA, 0.9F);
            }

            GL11.glTranslated(0, 0, -(Math.cos(System.currentTimeMillis() / 500F) + 1) * 0.09 - 0.1);
        }
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        GL11.glPopMatrix();
    }
}