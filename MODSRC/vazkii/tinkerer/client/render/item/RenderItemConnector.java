/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [26 Jul 2013, 02:27:58 (GMT)]
 */
package vazkii.tinkerer.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.model.ModelCubeWorld;
import vazkii.tinkerer.lib.LibResources;

public class RenderItemConnector implements IItemRenderer {

    ModelCubeWorld model;

    public RenderItemConnector() {
        model = new ModelCubeWorld(ThaumicTinkerer.class.getResourceAsStream(LibResources.MODEL_CONNECTOR));
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glRotatef(90F, 0F, 0F, 1F);
        GL11.glRotatef(90F, 0F, 1F, 0F);
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        GL11.glTranslatef(-0.45F, -1.5F, 0.4F);
        GL11.glRotatef(25F, 1F, 0F, 0F);

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0F, 0F, -0.3F);
            GL11.glRotatef(25F, 1F, 0F, 0F);
            GL11.glRotatef(-15F, 0F, 1F, 0F);
        }

        model.render();
        GL11.glPopMatrix();
    }
}