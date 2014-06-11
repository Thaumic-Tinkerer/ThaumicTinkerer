package vazkii.tinkerer.client.core.handler.kami;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.kami.ItemPlacementMirror;

import java.util.List;

public final class PlacementMirrorPredictionRenderer {

	RenderBlocks blockRender = new RenderBlocks();

	@SubscribeEvent
	public void onWorldRenderLast(RenderWorldLastEvent event) {

		World world = Minecraft.getMinecraft().theWorld;
		List<EntityPlayer> playerEntities = world.playerEntities;
		for (EntityPlayer player : playerEntities) {
			ItemStack currentStack = player.getCurrentEquippedItem();
			if (currentStack != null && currentStack.getItem() == ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemPlacementMirror.class) && ItemPlacementMirror.getBlock(currentStack) != Blocks.air) {
				renderPlayerLook(player, currentStack);
			}
		}
	}

	private void renderPlayerLook(EntityPlayer player, ItemStack stack) {
		ChunkCoordinates[] coords = ItemPlacementMirror.getBlocksToPlace(stack, player);
		if (ItemPlacementMirror.hasBlocks(stack, player, coords)) {
			ItemStack block = new ItemStack(ItemPlacementMirror.getBlock(stack), 1, ItemPlacementMirror.getBlockMeta(stack));
			ChunkCoordinates lastCoords = new ChunkCoordinates(0, 0, 0);

			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			for (ChunkCoordinates coord : coords) {
				renderBlockAt(block, coord, lastCoords);
				lastCoords = coord;
			}
			GL11.glPopMatrix();
		}
	}

	private void renderBlockAt(ItemStack block, ChunkCoordinates pos, ChunkCoordinates last) {
		if (block.getItem() == null)
			return;
		GL11.glPushMatrix();
		GL11.glTranslated(pos.posX + 0.5 - RenderManager.renderPosX, pos.posY + 0.5 - RenderManager.renderPosY, pos.posZ + 0.5 - RenderManager.renderPosZ);

		GL11.glColor4f(1F, 1F, 1F, 0.6F);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		blockRender.useInventoryTint = false;
		blockRender.renderBlockAsItem(Block.getBlockFromItem(block.getItem()), block.getItemDamage(), 1F);

		GL11.glPopMatrix();
	}

}
