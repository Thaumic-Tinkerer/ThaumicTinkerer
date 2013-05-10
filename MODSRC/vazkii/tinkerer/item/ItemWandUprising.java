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
 * File Created @ [10 May 2013, 21:53:44 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandFrost;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWandUprising extends ItemWandFrost {
	// ItemWandFrost, in order to have it recharge. That code doesn't seem to be in the
	// ItemWandBasic class.

	public ItemWandUprising(int i) {
		super(i);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		itemIcon = IconHelper.forItem(ir, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return itemIcon;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer p) {
		Vec3 vec = p.getLookVec();
		p.motionX = (vec.xCoord / 1.5);
		p.motionY = (vec.yCoord / 1.5);
		p.motionZ = (vec.zCoord / 1.5);
		p.fallDistance = 0F;
		
		for(int i = 0; i < 5; i++)
			ThaumicTinkerer.tcProxy.smokeSpiral(world, p.posX, p.posY - p.motionY, p.posZ, 2F, (int) Math.random() * 360, (int) p.posY);
		world.playSoundAtEntity(p, "thaumcraft.wind", 0.4F, 1F);
		if(world.isRemote)
			p.swingItem();
		else itemstack.damageItem(1, p);
		
		return itemstack;
	}

}
