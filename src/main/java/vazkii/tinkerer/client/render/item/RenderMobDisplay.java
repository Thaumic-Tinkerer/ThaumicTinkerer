package vazkii.tinkerer.client.render.item;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import vazkii.tinkerer.common.core.helper.EnumMobAspect;
import vazkii.tinkerer.common.item.ItemMobAspect;
import vazkii.tinkerer.common.item.ItemMobDisplay;

public class RenderMobDisplay implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
        return true;
    }
    private static RenderItem renderItem = new RenderItem();
    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... objects) {
        ItemMobDisplay item= (ItemMobDisplay) itemStack.getItem();
        EnumMobAspect aspect=item.getEntityType(itemStack);
        Entity entity=null;
        float f1=0.4f;
        float verticalOffset=0.0f;
        if(aspect!=null) {
            entity = EnumMobAspect.getEntityFromCache(aspect);
            f1 = aspect.getScale();
            verticalOffset=aspect.getVerticalOffset();
        }
        switch(itemRenderType) {

            case ENTITY:

                GL11.glPushMatrix();
                GL11.glTranslated(0.5, 0.2 + verticalOffset, 0.5);
                GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(f1, f1, f1);
                EntityItem eItem= (EntityItem) objects[1];
                if(entity!=null) {
                    entity.worldObj = (Minecraft.getMinecraft() != null) ? Minecraft.getMinecraft().theWorld : null;
                    if (entity.worldObj != null) {
                        Render renderer = RenderManager.instance.getEntityRenderObject(entity);
                        entity.setWorld(eItem.worldObj);
                        entity.copyLocationAndAnglesFrom(eItem);
                        if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
                            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                            //if (renderWithLighting) RenderUtils.enableLightmap();
                            renderer.doRender(entity, 0, 0, 0, 0, 0);
                            GL11.glPopAttrib();
                        }

                    }
                    entity.worldObj = null;
                }
                GL11.glPopMatrix();
            case EQUIPPED:
                break;
            case EQUIPPED_FIRST_PERSON:
                break;
            case INVENTORY:
                GL11.glPushMatrix();
                GL11.glTranslated(0.5, 0.2 + verticalOffset, 0.5);
                GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(f1, f1, f1);
                if(entity!=null) {
                    entity.worldObj = (Minecraft.getMinecraft() != null) ? Minecraft.getMinecraft().theWorld : null;
                    if (entity.worldObj != null) {
                        Render renderer = RenderManager.instance.getEntityRenderObject(entity);
                        if (renderer != null && renderer.getFontRendererFromRenderManager() != null) {
                            GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                            //if (renderWithLighting) RenderUtils.enableLightmap();
                            renderer.doRender(entity, 0, 0, 0, 0, 0);
                            GL11.glPopAttrib();
                        }

                    }
                    entity.worldObj = null;
                }
                GL11.glPopMatrix();

            case FIRST_PERSON_MAP:
                break;
        }
    }
}