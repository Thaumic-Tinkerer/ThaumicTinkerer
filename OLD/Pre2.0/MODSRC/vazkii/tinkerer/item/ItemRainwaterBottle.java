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
 * File Created @ [3 Jul 2013, 01:15:43 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import vazkii.tinkerer.util.handler.RainwaterBottleHandler;

public class ItemRainwaterBottle extends ItemMod {

	public ItemRainwaterBottle(int par1) {
		super(par1);

		MinecraftForge.EVENT_BUS.register(new RainwaterBottleHandler());
	}

    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 32;
    }

    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.drink;
    }

    @Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
            par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
            return par1ItemStack;
    }

    @Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
        	 --par1ItemStack.stackSize;

            if (par1ItemStack.stackSize <= 0)
                return new ItemStack(Item.glassBottle);

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        }

        return par1ItemStack;
    }
}
