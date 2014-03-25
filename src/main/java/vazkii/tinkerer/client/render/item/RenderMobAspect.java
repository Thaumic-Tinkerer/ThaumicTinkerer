package vazkii.tinkerer.client.render.item;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import vazkii.tinkerer.common.item.ItemMobAspect;

public class RenderMobAspect implements IItemRenderer {
	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
		return false;
	}

	private static RenderItem renderItem = new RenderItem();

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();


		if(ItemMobAspect.isCondensed(item)){
			GL11.glColor3f(.5F, .5F, .5F);
		}
		if(ItemMobAspect.isInfused(item)){
			GL11.glColor3f(.7F, 0F, .7F);
		}


		switch(type) {
			case ENTITY : {
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.5F, 0F, 0F);
				renderItem(ItemRenderType.EQUIPPED, item, data);
				GL11.glPopMatrix();
				break;
			}
			case INVENTORY: {

				Icon icon = ItemMobAspect.aspectIcons[item.getItemDamage()%ItemMobAspect.aspectCount];
				renderItem.renderIcon(0, 0, icon, 16, 16);
			}
			case EQUIPPED : {

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				Icon icon = ItemMobAspect.aspectIcons[item.getItemDamage()%ItemMobAspect.aspectCount];
				float f = icon.getMinU();
				float f1 = icon.getMaxU();
				float f2 = icon.getMinV();
				float f3 = icon.getMaxV();
				ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);

				GL11.glDisable(GL11.GL_BLEND);
				break;
			}
			case EQUIPPED_FIRST_PERSON : {
				renderItem(ItemRenderType.EQUIPPED, item, data);
				break;
			}
			default : break;
		}
		GL11.glPopMatrix();
	}
}
