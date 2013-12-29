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
 * File Created @ [Dec 29, 2013, 7:37:10 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.tool;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.client.core.helper.IconHelper;

public class ItemIchorShovelAdv extends ItemIchorShovel implements IAdvancedTool {

	Icon[] specialIcons = new Icon[3];
	
	public ItemIchorShovelAdv(int par1) {
		super(par1);
		setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		for(int i = 0; i < specialIcons.length; i++)
			specialIcons[i] = IconHelper.forItem(par1IconRegister, this, i);
	}
	
	@Override
	public Icon getIconFromDamage(int par1) {
		return par1 >= specialIcons.length ? super.getIconFromDamage(par1) : specialIcons[par1];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		ToolHandler.changeMode(par1ItemStack);
		return par1ItemStack;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		World world = player.worldObj;
		Material mat = world.getBlockMaterial(x, y, z);
		if(!ToolHandler.isRightMaterial(mat, ToolHandler.materialsShovel))
			return false;
		
		MovingObjectPosition block = ToolHandler.raytraceFromEntity(world, player, true, 4.5);
		if(block == null)
			return false;
		
		ForgeDirection direction = ForgeDirection.getOrientation(block.sideHit);
		int fortune = EnchantmentHelper.getFortuneModifier(player);
		boolean silk = EnchantmentHelper.getSilkTouchModifier(player);

		switch(ToolHandler.getMode(stack)) {
			case 0 : break;
			case 1 : {
				boolean doX = direction.offsetX == 0;
				boolean doY = direction.offsetY == 0;
				boolean doZ = direction.offsetZ == 0;
				
				ToolHandler.removeBlocksInIteration(player, world, x, y, z, doX ? -2 : 0, doY ? -1 : 0, doZ ? -2 : 0, doX ? 3 : 1, doY ? 4 : 1, doZ ? 3 : 1, -1, ToolHandler.materialsShovel, silk, fortune);
				break;
			}
			case 2 : {
				int id = world.getBlockId(x, y, z);
				ToolHandler.removeBlocksInIteration(player, world, x, y, z, 0, -8, 0, 1, 8, 1, id, ToolHandler.materialsShovel, silk, fortune);
				break;
			}
		}
		return false;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("ttmisc.mode.shovel." + ToolHandler.getMode(par1ItemStack)));
	}
	
	@Override
	public String getType() {
		return "shovel";
	}
	
}
