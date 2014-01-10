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
 * File Created @ [Dec 27, 2013, 1:03:05 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import org.lwjgl.util.vector.Vector2f;

import thaumcraft.client.codechicken.core.vec.Vector3;
import vazkii.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import vazkii.tinkerer.common.item.ItemBrightNitor;
import vazkii.tinkerer.common.item.ModItems;

public class ItemGemLegs extends ItemIchorclothArmorAdv {

	public ItemGemLegs(int par1, int par2) {
		super(par1, par2);
		setHasSubtypes(true);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			int dmg = par1ItemStack.getItemDamage();
			par1ItemStack.setItemDamage(~dmg & 1);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
			
			ToolModeHUDHandler.setTooltip(StatCollector.translateToLocal("ttmisc.light" + par1ItemStack.getItemDamage()));
			
			return par1ItemStack;
		}

		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(1);
		if(armor.getItemDamage() == 1)
			return;
		
		ItemBrightNitor.meta = 1;
		ModItems.brightNitor.onUpdate(null, player.worldObj, player, 0, false);
		ItemBrightNitor.meta = 0;

		int x = (int) Math.floor(player.posX);
		int y = (int) player.posY + 1;
		int z = (int) Math.floor(player.posZ);

        float yaw =  MathHelper.wrapAngleTo180_float(player.rotationYaw + 90F) * (float) Math.PI / 180F;
        Vector3 lookVector = new Vector3(Math.cos(yaw), Math.sin(yaw), 0).normalize();
        Vector3 newVector = new Vector3(lookVector.x, lookVector.y, 0);

        for(int i = 0; i < 5; i++) {
        	newVector = newVector.add(lookVector);

    		int x1 = x + (int) newVector.x;
    		int z1 = z + (int) newVector.y;
    		ItemBrightNitor.setBlock(x1, y, z1, player.worldObj);
        }
	}

	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onDamageTaken(LivingHurtEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if(player.getCurrentArmor(1) != null && player.getCurrentArmor(1).itemID == itemID && event.source.isFireDamage()) {
				event.setCanceled(true);
				player.heal(event.ammount);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		if(stack.getItemDamage() == 1)
			list.add(StatCollector.translateToLocal("ttmisc.light1"));
	}
}
