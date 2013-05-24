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
 * File Created @ [25 Apr 2013, 21:54:08 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.lib.LibFeatures;
import vazkii.tinkerer.potion.ModPotions;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStopwatch extends ItemMod {

	public static final String TAG_USERNAME = "username";
	public static final String TAG_X = "x";
	public static final String TAG_Y = "y";
	public static final String TAG_Z = "z";
	public static final String TAG_PITCH = "pitch";
	public static final String TAG_YAW = "yaw";
	public static final String TAG_MOTION_X = "motionX";
	public static final String TAG_MOTION_Y = "motionY";
	public static final String TAG_MOTION_Z = "motionZ";
	public static final String TAG_DIMENSION = "dim";
	public static final String TAG_HP = "hp";
	public static final String TAG_FOOD = "food";
	public static final String TAG_SATURATION = "saturation";

	public ItemStopwatch(int par1) {
		super(par1);
		setMaxStackSize(1);
		setMaxDamage(11);
		setNoRepair();
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(!par2World.isRemote && par1ItemStack.getItemDamage() >= 1 && decreaseAura(par3Entity))
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);

		if(par3Entity instanceof EntityPlayer && validate(par1ItemStack, (EntityPlayer) par3Entity)) {
			EntityPlayer player = (EntityPlayer) par3Entity;
			PotionEffect effect = player.getActivePotionEffect(ModPotions.effectStopwatch);
			if(effect.duration % 20 == 0)
				par2World.playSoundAtEntity(par3Entity, "random.click", 0.5F, 1F);

			if(effect.duration == 1) {
				moveParticlesAndSound(par3Entity);

				if(par3Entity instanceof EntityPlayerMP && !par2World.isRemote) {
					regressStatus(par1ItemStack, (EntityPlayerMP) par3Entity);
					wipeData(par1ItemStack, par3Entity);
				}

				moveParticlesAndSound(par3Entity);
			}
		} else wipeData(par1ItemStack, par3Entity);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(!validate(par1ItemStack, par3EntityPlayer) && !(par3EntityPlayer instanceof FakePlayer)) {
			int charge = par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage();
			if(charge >= 10) {
				populateNBT(par1ItemStack, par3EntityPlayer);
				PotionEffect effect = new PotionEffect(ModPotions.effectStopwatch.id, LibFeatures.STOPWATCH_TIME, 0);
				effect.getCurativeItems().clear();
				par3EntityPlayer.addPotionEffect(effect);
				par2World.playSoundAtEntity(par3EntityPlayer, "thaumcraft.wand", 1F, 1F);
				par1ItemStack.damageItem(10, par3EntityPlayer);
			} else if(!par2World.isRemote)
				par3EntityPlayer.addChatMessage("The Stopwatch does not have enough charge to do that.");
		} else if(!par2World.isRemote)
			par3EntityPlayer.addChatMessage("You can not Twist Time while a Time Twister effect lingers.");

		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}

	private static void moveParticlesAndSound(Entity entity) {
		for(int i = 0; i < 15; i++) {
			float x = (float) (entity.posX + Math.random());
			float y = (float) (entity.posY + 1.2 + Math.random());
			float z = (float) (entity.posZ + Math.random());
			ThaumicTinkerer.tcProxy.wispFX(entity.worldObj, x, y, z, (float) Math.random(), 1F, 1F, 0F);
		}
		entity.worldObj.playSoundAtEntity(entity, "thaumcraft.wand", 1F, 1F);
	}

	private static boolean decreaseAura(Entity player) {
		return AuraManager.decreaseClosestAura(player.worldObj, player.posX, player.posY, player.posZ, LibFeatures.STOPWATCH_VIS / 10);
	}

	private static void populateNBT(ItemStack stack, EntityPlayer player) {
		ItemNBTHelper.setString(stack, TAG_USERNAME, player.username);
		ItemNBTHelper.setDouble(stack, TAG_X, player.posX);
		ItemNBTHelper.setDouble(stack, TAG_Y, player.posY);
		ItemNBTHelper.setDouble(stack, TAG_Z, player.posZ);
		ItemNBTHelper.setFloat(stack, TAG_PITCH, player.rotationPitch);
		ItemNBTHelper.setFloat(stack, TAG_YAW, player.rotationYaw);
		ItemNBTHelper.setDouble(stack, TAG_MOTION_X, player.motionX);
		ItemNBTHelper.setDouble(stack, TAG_MOTION_Y, player.motionY);
		ItemNBTHelper.setDouble(stack, TAG_MOTION_Z, player.motionZ);
		ItemNBTHelper.setInt(stack, TAG_DIMENSION, player.dimension);
		ItemNBTHelper.setInt(stack, TAG_HP, player.getHealth());
		ItemNBTHelper.setInt(stack, TAG_FOOD, player.getFoodStats().getFoodLevel());
		ItemNBTHelper.setFloat(stack, TAG_SATURATION, player.getFoodStats().getSaturationLevel());
	}

	private static boolean validate(ItemStack stack, EntityPlayer player) {
		if(!stack.hasTagCompound() || !player.isPotionActive(ModPotions.effectStopwatch.id))
			return false;

		String username = ItemNBTHelper.getString(stack, TAG_USERNAME, "");
		int dim = ItemNBTHelper.getInt(stack, TAG_DIMENSION, Integer.MIN_VALUE);

		return username.equals(player.username) && dim == player.dimension;
	}

	private static void regressStatus(ItemStack stack, EntityPlayerMP player) {
		double x = ItemNBTHelper.getDouble(stack, TAG_X, 0D);
		double y = ItemNBTHelper.getDouble(stack, TAG_Y, 0D);
		double z = ItemNBTHelper.getDouble(stack, TAG_Z, 0D);
		float pitch = ItemNBTHelper.getFloat(stack, TAG_PITCH, 0F);
		float yaw = ItemNBTHelper.getFloat(stack, TAG_YAW, 0F);
		double motionX = ItemNBTHelper.getDouble(stack, TAG_MOTION_X, 0D);
		double motionY = ItemNBTHelper.getDouble(stack, TAG_MOTION_Y, 0D);
		double motionZ = ItemNBTHelper.getDouble(stack, TAG_MOTION_Z, 0D);
		int health = ItemNBTHelper.getInt(stack, TAG_HP, 0);
		int food = ItemNBTHelper.getInt(stack, TAG_FOOD, 0);
		float saturation = ItemNBTHelper.getFloat(stack, TAG_SATURATION, 0F);

		player.playerNetServerHandler.setPlayerLocation(x, y, z, pitch, yaw);
		player.motionX = motionX;
		player.motionY = motionY;
		player.motionZ = motionZ;
		player.setEntityHealth(health);
		FoodStats foodStats = player.getFoodStats();
		foodStats.addStats(food - foodStats.getFoodLevel(), saturation - foodStats.getSaturationLevel());
	}

	private static void wipeData(ItemStack stack, Entity player) {
		stack.setTagCompound(null);
		if(player instanceof EntityLiving)
			((EntityLiving) player).removePotionEffect(ModPotions.effectStopwatch.id);
	}

}
