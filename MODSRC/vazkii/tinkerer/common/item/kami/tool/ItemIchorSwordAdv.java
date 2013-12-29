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
 * File Created @ [Dec 29, 2013, 9:08:14 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.tool;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;

public class ItemIchorSwordAdv extends ItemIchorSword implements IAdvancedTool {

	Icon[] specialIcons = new Icon[3];
	
	public ItemIchorSwordAdv(int par1) {
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
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			ToolHandler.changeMode(par1ItemStack);
			return par1ItemStack;
		} else return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	boolean ignoreLeftClick = false;
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!ignoreLeftClick && entity instanceof EntityLivingBase)
			switch(ToolHandler.getMode(stack)) {
				case 0 : break;
				case 1 : {
					int range = 3;
					List<Entity> entities = player.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
					ignoreLeftClick = true;
					for(Entity entity_ : entities)
						player.attackTargetEntityWithCurrentItem(entity_);
					ignoreLeftClick = false;
					
					break;
				}
				case 2 : {
					EntityLivingBase living = (EntityLivingBase) entity;
					PotionEffect effect = new PotionEffect(Potion.weakness.id, 0, 4);
					player.addPotionEffect(effect);
					
					// TODO soul hearts
					
					break;
				}
			}

		return super.onLeftClickEntity(stack, player, entity);
	}
	
	@Override
	public Icon getIconFromDamage(int par1) {
		return par1 >= specialIcons.length ? super.getIconFromDamage(par1) : specialIcons[par1];
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("ttmisc.mode.sword." + ToolHandler.getMode(par1ItemStack)));
	}
	
	@Override
	public String getType() {
		return "sword";
	}

}
