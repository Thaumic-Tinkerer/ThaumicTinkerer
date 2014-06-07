package vazkii.tinkerer.client.render.item.kami;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import vazkii.tinkerer.common.item.ModItems;

public class RenderPlacementMirror implements IItemRenderer {

	RenderItem render = new RenderItem();
	ItemRenderer renderer = new ItemRenderer(Minecraft.getMinecraft());

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		//return type != ItemRenderType.INVENTORY && type!=ItemRenderType.ENTITY;
		//return type != ItemRenderType.INVENTORY ;
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY || type == ItemRenderType.INVENTORY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case ENTITY: {
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.5F, 0F, 0F);
				renderItem(ItemRenderType.EQUIPPED, item, data);
				GL11.glPopMatrix();
				break;
			}
			case EQUIPPED: {
				for (int i = 1; i >= 0; i--) {
					IIcon icon = ModItems.placementMirror.getIconFromDamageForRenderPass(0, i);
					if (i == 0) {
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					}

					float f = icon.getMinU();
					float f1 = icon.getMaxU();
					float f2 = icon.getMinV();
					float f3 = icon.getMaxV();
					ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
				}
				GL11.glDisable(GL11.GL_BLEND);
				break;
			}
			case EQUIPPED_FIRST_PERSON: {
				renderItem(ItemRenderType.EQUIPPED, item, data);
				break;
			}
			case INVENTORY: {
				GL11.glPushMatrix();
				GL11.glRotatef(45f, 0f, 1f, 0f);
				GL11.glScalef(1.5f, 1.8f, 1.8f);
				GL11.glRotatef(-45f, 0f, 0f, 1f);
				GL11.glTranslatef(-0.46F, -0.58F, 0F);

				renderItem(ItemRenderType.EQUIPPED, item, data);
				GL11.glPopMatrix();
				break;
			}
			default:
				break;
		}
	}

}
