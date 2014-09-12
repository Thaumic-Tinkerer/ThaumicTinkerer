/**
 * Author: thegreatunclean
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 */

package thaumic.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.block.BlockJarRenderer;
import thaumcraft.client.renderers.tile.ItemJarFilledRenderer;
import thaumic.tinkerer.common.block.tile.TileSynth;

public class RenderTileSynth extends TileEntitySpecialRenderer {

    BlockJarRenderer jarRenderer = new BlockJarRenderer();
    ItemJarFilledRenderer jarRenderer1 = new ItemJarFilledRenderer();
    
    @Override
    public void renderTileEntityAt(TileEntity te, double d0, double d1, double d2, float f) {
		TileSynth synth = (TileSynth) te;
		int meta = te.getWorldObj() == null ? 3 : te.getBlockMetadata();
		int rotation;
		switch (meta) {
			case 2:
				rotation = 0; break;
			case 3:
				rotation = 180; break;
			case 4:
				rotation = 90; break;
			case 5:
				rotation = 270; break; //rotated ccw as viewed from above
			default:
				rotation = 0;
		}
        
        for (int i = 0; i < synth.getSizeInventory(); i++) {
            ItemStack stack = synth.getStackInSlot(i);
            if (stack != null) {
			
				float jarScale = 0.75F;
                GL11.glPushMatrix();

                GL11.glTranslated(d0, d1, d2);
				
				switch (rotation) {
					case 0:
						GL11.glTranslated(0.25F + 0.5F*i, 0.25F, 0.5F); break;
					case 90:
						GL11.glTranslated(0.5F, 0.25F, 0.25F + 0.5F * i); break;
					case 180:
						GL11.glTranslated(0.75F - 0.5F*i, 0.25F, 0.5F); break;
					case 270:
						GL11.glTranslated(0.5F, 0.25F, 0.75F - 0.5F*i); break;
					default: break;
				}
				GL11.glScalef(jarScale,jarScale,jarScale);
				GL11.glRotatef(90.0F+rotation, 0.0F, 1.0F, 0.0F);
                jarRenderer1.renderItem(IItemRenderer.ItemRenderType.ENTITY, stack, (Object[]) null);
                GL11.glPopMatrix();
            }
        }
    }
}