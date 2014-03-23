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
 * File Created @ [Dec 29, 2013, 6:01:04 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import vazkii.tinkerer.client.core.helper.IconHelper;

import java.util.List;

public class ItemIchorPickAdv extends ItemIchorPick implements IAdvancedTool {

	IIcon[] specialIcons = new IIcon[3];

	public ItemIchorPickAdv() {
		super();
		setHasSubtypes(true);
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		for(int i = 0; i < specialIcons.length; i++)
			specialIcons[i] = IconHelper.forItem(par1IconRegister, this, i);
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return par1 >= specialIcons.length ? super.getIconFromDamage(par1) : specialIcons[par1];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			ToolHandler.changeMode(par1ItemStack);
			ToolModeHUDHandler.setTooltip(ToolHandler.getToolModeStr(this, par1ItemStack));
		}

		return par1ItemStack;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
		Material mat = world.getBlock(x, y, z).getMaterial();
		if(!ToolHandler.isRightMaterial(mat, ToolHandler.materialsPick))
			return false;

		MovingObjectPosition block = ToolHandler.raytraceFromEntity(world, player, true, 4.5);
		if(block == null)
			return false;

		Block blk=world.getBlock(x, y, z);

		ForgeDirection direction = ForgeDirection.getOrientation(block.sideHit);
		int fortune = EnchantmentHelper.getFortuneModifier(player);
		boolean silk = EnchantmentHelper.getSilkTouchModifier(player);

		switch(ToolHandler.getMode(stack)) {
			case 0 : break;
			case 1 : {
				boolean doX = direction.offsetX == 0;
				boolean doY = direction.offsetY == 0;
				boolean doZ = direction.offsetZ == 0;

				ToolHandler.removeBlocksInIteration(player, world, x, y, z, doX ? -2 : 0, doY ? -1 : 0, doZ ? -2 : 0, doX ? 3 : 1, doY ? 4 : 1, doZ ? 3 : 1, null, ToolHandler.materialsPick, silk, fortune);
				if(blk== Blocks.bedrock){
					world.setBlock(x, y, z, Blocks.bedrock);
				}
				break;
			}
			case 2 : {
				int xo = -direction.offsetX;
				int yo = -direction.offsetY;
				int zo = -direction.offsetZ;

				ToolHandler.removeBlocksInIteration(player, world, x, y, z, xo >= 0 ? 0 : -10, yo >= 0 ? 0 : -10, zo >= 0 ? 0 : -10, xo > 0 ? 10 : 1, yo > 0 ? 10 : 1, zo > 0 ? 10 : 1, null, ToolHandler.materialsPick, silk, fortune);
                if(blk== Blocks.bedrock){
                    world.setBlock(x, y, z, Blocks.bedrock);
                }
				break;
			}
		}
		return false;
	}



	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(ToolHandler.getToolModeStr(this, par1ItemStack));
	}

	@Override
	public String getType() {
		return "pick";
	}

}
