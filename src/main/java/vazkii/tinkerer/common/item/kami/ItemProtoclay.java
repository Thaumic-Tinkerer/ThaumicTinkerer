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
 * File Created @ [Jan 9, 2014, 10:01:41 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.item.ItemMod;
import vazkii.tinkerer.common.item.kami.tool.IAdvancedTool;
import vazkii.tinkerer.common.item.kami.tool.ToolHandler;

public class ItemProtoclay extends ItemMod {

	public ItemProtoclay() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (!(par3Entity instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) par3Entity;
		ItemStack currentStack = player.getCurrentEquippedItem();
		if (currentStack == null || !(currentStack.getItem() instanceof IAdvancedTool))
			return;
		IAdvancedTool tool = (IAdvancedTool) currentStack.getItem();

		if (tool.getType().equals("sword"))
			return;

		MovingObjectPosition pos = ToolHandler.raytraceFromEntity(par2World, par3Entity, true, 4.5F);
		String typeToFind = "";

		if (player.isSwingInProgress && pos != null) {
			Block block = par2World.getBlock(pos.blockX, pos.blockY, pos.blockZ);

			if (block != null) {
				Material mat = block.getMaterial();
				if (ToolHandler.isRightMaterial(mat, ToolHandler.materialsPick))
					typeToFind = "pick";
				else if (ToolHandler.isRightMaterial(mat, ToolHandler.materialsShovel))
					typeToFind = "shovel";
				else if (ToolHandler.isRightMaterial(mat, ToolHandler.materialsAxe))
					typeToFind = "axe";
			}
		}

		if (tool.getType().equals(typeToFind) || typeToFind.isEmpty())
			return;

		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = player.inventory.getStackInSlot(i);
			if (stackInSlot != null && stackInSlot.getItem() instanceof IAdvancedTool && stackInSlot != currentStack) {
				IAdvancedTool toolInSlot = (IAdvancedTool) stackInSlot.getItem();
				if (toolInSlot.getType().equals(typeToFind)) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, stackInSlot);
					player.inventory.setInventorySlotContents(i, currentStack);
					break;
				}
			}
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}
}
