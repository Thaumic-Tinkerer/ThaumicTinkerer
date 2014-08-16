package thaumic.tinkerer.client.render.item;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
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
        switch (type) {
            case ENTITY:
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case INVENTORY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        switch (type) {
            case ENTITY: {
                return (helper == ItemRendererHelper.ENTITY_BOBBING ||
                        helper == ItemRendererHelper.ENTITY_ROTATION
                ); // not helper == ItemRendererHelper.BLOCK_3D
            }
            case EQUIPPED: {
                return false;
                // not (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
            }
            case EQUIPPED_FIRST_PERSON: {
                return false;
                // not (helper == ItemRendererHelper.EQUIPPED_BLOCK);
            }
            case INVENTORY: {
                return false;
                // not (helper == ItemRendererHelper.INVENTORY_BLOCK);
            }
            default: {
                return false;
            }
        }
    }


    //Code borrowed by TheGreyGhost

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... objects) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        Aspect aspect = ItemInfusedSeeds.getAspect(item);
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
        if (!aspect.isPrimal()) {
            //Hex to RGB code from vanilla tesselator
            float r = (aspect.getColor() >> 16 & 0xFF) / 255.0F;
            float g = (aspect.getColor() >> 8 & 0xFF) / 255.0F;
            float b = (aspect.getColor() & 0xFF) / 255.0F;
            GL11.glColor4f(r, g, b, 1F);
        }

        if (type == ItemRenderType.INVENTORY) {
            drawAs2D(type, item);
            if (type == ItemRenderType.ENTITY) {
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.5F, 0F, 0F);
                renderItem(ItemRenderType.EQUIPPED, item, objects);
                GL11.glPopMatrix();
            }
        } else {
            drawAsSlice(type, item);
        }
        GL11.glPopAttrib();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

    }

    // draw the inventory icon as flat 2D
    // caller expects you to render over [0,0,0] to [16, 16, 0]
    private void drawAs2D(ItemRenderType type, ItemStack item) {

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        IIcon icon1 = item.getItem().getIconIndex(item);

        double minU1 = (double) icon1.getMinU();
        double minV1 = (double) icon1.getMinV();
        double maxU1 = (double) icon1.getMaxU();
        double maxV1 = (double) icon1.getMaxV();

        tessellator.addVertexWithUV(16.0, 16.0, 0.0, maxU1, maxV1);
        tessellator.addVertexWithUV(16.0, 0.0, 0.0, maxU1, minV1);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, minU1, minV1);
        tessellator.addVertexWithUV(0.0, 16.0, 0.0, minU1, maxV1);
        tessellator.draw();
    }

    private enum TransformationTypes {NONE, DROPPED, INFRAME}

    ;

    private void drawAsSlice(ItemRenderType type, ItemStack item) {
        ItemInfusedSeeds seedItem = (ItemInfusedSeeds) ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedSeeds.class);

        final float THICKNESS = 0.001F;

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        // adjust rendering space to match what caller expects
        TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
        switch (type) {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON: {
                break; // caller expects us to render over [0,0,0] to [1,1,-THICKNESS], no transformation necessary
            }
            case ENTITY: {

                // translate our coordinates so that [0,0,0] to [1,1,1] translates to the [-0.5, 0.0, 0.0] to [0.5, 1.0, 1.0] expected by the caller.
                if (RenderItem.renderInFrame) {
                    transformationToBeUndone = TransformationTypes.INFRAME; // must undo the transformation when we're finished rendering
                    GL11.glTranslatef(-0.5F, -0.3F, THICKNESS / 2.0F);
                } else {
                    transformationToBeUndone = TransformationTypes.DROPPED; // must undo the transformation when we're finished rendering
                    GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
                }
                break;
            }
            case INVENTORY: {
                break;
            }
            default:
                break; // never here
        }


        IIcon icon = seedItem.getIconIndex(item);
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double) icon.getMaxU(), (double) icon.getMaxV());
        tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double) icon.getMaxU(), (double) icon.getMinV());
        tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double) icon.getMinU(), (double) icon.getMinV());
        tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double) icon.getMinU(), (double) icon.getMaxV());


        tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double) icon.getMaxU(), (double) icon.getMaxV());
        tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double) icon.getMaxU(), (double) icon.getMinV());
        tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double) icon.getMinU(), (double) icon.getMinV());
        tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double) icon.getMinU(), (double) icon.getMaxV());


        tessellator.draw();

        switch (transformationToBeUndone) {
            case NONE: {
                break;
            }
            case DROPPED: {
                GL11.glTranslatef(0.5F, 0.0F, 0.0F);
                break;
            }
            case INFRAME: {
                GL11.glTranslatef(0.5F, 0.3F, -THICKNESS / 2.0F);
                break;
            }
            default:
                break;
        }
    }
}
