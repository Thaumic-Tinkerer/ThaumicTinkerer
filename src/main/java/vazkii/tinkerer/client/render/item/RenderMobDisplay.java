package vazkii.tinkerer.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
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
        Entity entity= EntityList.createEntityByName(item.getEntityType(itemStack),Minecraft.getMinecraft().theWorld);

        float f1 = 0.5f;
        switch(itemRenderType) {

            case ENTITY:

                GL11.glTranslatef(0.0F, 0.4F, 0.0F);
                //GL11.glRotatef((float)(par0MobSpawnerBaseLogic.field_98284_d + (par0MobSpawnerBaseLogic.field_98287_c - par0MobSpawnerBaseLogic.field_98284_d) * (double)par7) * 10.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(f1, f1, f1);
                EntityItem eItem= (EntityItem) objects[1];
                if(entity!=null) {
                    entity.setWorld(eItem.worldObj);
                    entity.copyLocationAndAnglesFrom(eItem);
                    RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0);
                }
            case EQUIPPED:
                break;
            case EQUIPPED_FIRST_PERSON:
                break;
            case INVENTORY:
                GL11.glTranslatef(0.0F, 0.4F, 0.0F);
                //GL11.glRotatef((float)(par0MobSpawnerBaseLogic.field_98284_d + (par0MobSpawnerBaseLogic.field_98287_c - par0MobSpawnerBaseLogic.field_98284_d) * (double)par7) * 10.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.4F, 0.0F);
                GL11.glScalef(f1, f1, f1);
                if(entity!=null) {
                    entity.setPosition(0,0,0);
                    RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0);
                }

            case FIRST_PERSON_MAP:
                break;
        }
    }
}