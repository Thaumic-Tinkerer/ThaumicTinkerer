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
 * File Created @ [8 May 2013, 20:53:06 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibFeatures;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTeleportSigil extends ItemMod {

	private static final String TAG_X = "x";
	private static final String TAG_Y = "y";
	private static final String TAG_Z = "z";
	private static final String TAG_DIMENSION = "dim";

	Icon enabledIcon;

	public ItemTeleportSigil(int par1) {
		super(par1);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(stack.getItemDamage() == 1)
			return true;

		if(world.isRemote)
			player.swingItem();
		else setData(stack, player.dimension, x, y, z);

		world.playSoundAtEntity(player, "thaumcraft.tool", 1F, 1F);
		for(int i = 0; i < 20; i++) {
			double x1 = x + Math.random();
			double y1 = y + Math.random() + 0.75;
			double z1 = z + Math.random();
			ThaumicTinkerer.tcProxy.wispFX2(world, x1, y1, z1, 0.25F, 5, true, -0.02F);
		}

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par1ItemStack.getItemDamage() == 1)
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
		if(getMaxItemUseDuration(stack) - count >= LibFeatures.TELEPORT_SIGIL_TIME && count % 10 == 0) {
			for(int i = 0; i < 5; i++) {
				double x = player.posX + player.width / 2 + (Math.random() - 0.5) * 4;
				double y = player.posY + player.width / 2 + (Math.random() - 0.5) * 4;
				double z = player.posZ - player.height / 2 + (Math.random() - 0.5) * 4;
				Vec3 vector = player.worldObj.getWorldVec3Pool().getVecFromPool(x, y, z);

				ThaumicTinkerer.proxy.sigilLightning(player.worldObj, player, vector);
			}
			player.worldObj.playSoundAtEntity(player, "thaumcraft.zap", 0.2F, 1F);
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {
		if(getMaxItemUseDuration(par1ItemStack) - par4 >= LibFeatures.TELEPORT_SIGIL_TIME && AuraManager.decreaseClosestAura(par2World, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, LibFeatures.TELEPORT_SIGIL_VIS)) {
			moveParticlesAndSound(par3EntityPlayer);
			if(par3EntityPlayer instanceof EntityPlayerMP && !par2World.isRemote)
				regressStatus(par1ItemStack, (EntityPlayerMP) par3EntityPlayer);
			moveParticlesAndSound(par3EntityPlayer);
			par3EntityPlayer.renderBrokenItemStack(par1ItemStack);
			par3EntityPlayer.inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
		}
	}

	private static void moveParticlesAndSound(Entity entity) {
		for(int i = 0; i < 15; i++) {
			float x = (float) (entity.posX + Math.random());
			float y = (float) (entity.posY + 1.2 + Math.random());
			float z = (float) (entity.posZ + Math.random());
			ThaumicTinkerer.tcProxy.wispFX2(entity.worldObj, x, y, z, 0.5F, 5, true, -0.02F);
		}
		entity.worldObj.playSoundAtEntity(entity, "thaumcraft.tool", 1F, 1F);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	private static void setData(ItemStack stack, int dim, int x, int y, int z) {
		ItemNBTHelper.setInt(stack, TAG_X, x);
		ItemNBTHelper.setInt(stack, TAG_Y, y);
		ItemNBTHelper.setInt(stack, TAG_Z, z);
		ItemNBTHelper.setInt(stack, TAG_DIMENSION, dim);
		stack.setItemDamage(1);
	}

	private static void regressStatus(ItemStack stack, EntityPlayerMP player) {
		int x = ItemNBTHelper.getInt(stack, TAG_X, 0);
		int y = ItemNBTHelper.getInt(stack, TAG_Y, 0);
		int z = ItemNBTHelper.getInt(stack, TAG_Z, 0);

		player.playerNetServerHandler.setPlayerLocation(x + 0.5, y + 1.6, z + 0.5, player.rotationPitch, player.rotationYaw);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == 1 ? enabledIcon : itemIcon;
	}
}
