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
 * File Created @ [8 Sep 2013, 19:35:02 (GMT)]
 */
package vazkii.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.golems.EntityGolemBase;
import vazkii.tinkerer.common.block.tile.TileGolemConnector;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvector;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

public class ItemConnector extends ItemBase {

	private static final String TAG_POS_X = "posx";
	private static final String TAG_POS_Y = "posy";
	private static final String TAG_POS_Z = "posz";
	private static final String TAG_CONNECTING_GOLEM = "ConnectingGolem";

	public ItemConnector() {
		super();

		setMaxStackSize(1);
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {

		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_INTERFACE + "1", LibResearch.KEY_INTERFACE, new ItemStack(this), new AspectList().add(Aspect.ORDER, 2),
				" I ", " WI", "S  ",
				'I', new ItemStack(Items.iron_ingot),
				'W', new ItemStack(Items.stick),
				'S', new ItemStack(ConfigItems.itemShard, 1, 4));
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par3World.isRemote)
			return false;

		TileEntity tile = par3World.getTileEntity(par4, par5, par6);

		if (getY(par1ItemStack) == -1) {
			if (tile != null && (tile instanceof TileTransvector || tile instanceof TileGolemConnector)) {
				setX(par1ItemStack, par4);
				setY(par1ItemStack, par5);
				setZ(par1ItemStack, par6);

				if (par3World.isRemote)
					par2EntityPlayer.swingItem();

				playSound(par3World, par4, par5, par6);
				if (tile instanceof TileTransvector)
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.connector.set"));
				else
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.golemconnector.set"));
			} else
				par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.connector.notinterf"));
		} else {
			int x = getX(par1ItemStack);
			int y = getY(par1ItemStack);
			int z = getZ(par1ItemStack);

			TileEntity tile1 = par3World.getTileEntity(x, y, z);
			if (tile1 == null || !(tile1 instanceof TileTransvector)) {
				setY(par1ItemStack, -1);

				par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.connector.notpresent"));
			} else {
				TileTransvector trans = (TileTransvector) tile1;

				if (tile != null && tile1 instanceof TileTransvectorInterface && tile instanceof TileTransvectorInterface) {
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.connector.interffail"));
					return true;
				}

				if (Math.abs(x - par4) > trans.getMaxDistance() || Math.abs(y - par5) > trans.getMaxDistance() || Math.abs(z - par6) > trans.getMaxDistance()) {
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.connector.toofar"));
					return true;
				}

				trans.x = par4;
				trans.y = par5;
				trans.z = par6;

				setY(par1ItemStack, -1);

				playSound(par3World, par4, par5, par6);
				par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.connector.complete"));
				par3World.markBlockForUpdate(trans.x, trans.y, trans.z);
			}
		}

		return true;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack,
	                                        EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {
		par1ItemStack = par2EntityPlayer.getCurrentEquippedItem();
		if (par2EntityPlayer.isSneaking()) {

			if (par3EntityLivingBase instanceof EntityGolemBase) {

				if (getY(par1ItemStack) == -1) {
					if (par3EntityLivingBase.worldObj.isRemote) {
						return false;
					}
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.golemconnector.notinterf"));
					return true;
				}
				int x = getX(par1ItemStack);
				int y = getY(par1ItemStack);
				int z = getZ(par1ItemStack);
				TileEntity tile1 = par2EntityPlayer.worldObj.getTileEntity(x, y, z);
				if (tile1 == null || !(tile1 instanceof TileGolemConnector)) {
					setY(par1ItemStack, -1);
					if (par3EntityLivingBase.worldObj.isRemote) {
						return false;
					}
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.golemconnector.notpresent"));
					return false;
				} else {
					if (par3EntityLivingBase.worldObj.isRemote) {
						par2EntityPlayer.swingItem();
						return false;
					}
					TileGolemConnector trans = (TileGolemConnector) tile1;

					trans.ConnectGolem(par3EntityLivingBase.getUniqueID());

					setY(par1ItemStack, -1);

					playSound(par3EntityLivingBase.worldObj, (int) par3EntityLivingBase.posX, (int) par3EntityLivingBase.posY, (int) par3EntityLivingBase.posZ);
					par2EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.golemconnector.complete"));
					par2EntityPlayer.worldObj.markBlockForUpdate(trans.xCoord, trans.yCoord, trans.zCoord);
				}
				return true;
			}
		}
		return false;
	}

	private void playSound(World world, int x, int y, int z) {
		if (!world.isRemote)
			world.playSoundEffect(x, y, z, "random.orb", 0.8F, 1F);
	}

	public static boolean getConnectingGolem(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_CONNECTING_GOLEM, false);
	}

	public static void setConnectingGolem(ItemStack stack, boolean connecting) {
		ItemNBTHelper.setBoolean(stack, TAG_CONNECTING_GOLEM, connecting);
	}

	public static void setX(ItemStack stack, int x) {
		ItemNBTHelper.setInt(stack, TAG_POS_X, x);
	}

	public static void setY(ItemStack stack, int y) {
		ItemNBTHelper.setInt(stack, TAG_POS_Y, y);
	}

	public static void setZ(ItemStack stack, int z) {
		ItemNBTHelper.setInt(stack, TAG_POS_Z, z);
	}

	public static int getX(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_POS_X, 0);
	}

	public static int getY(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_POS_Y, -1);
	}

	public static int getZ(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_POS_Z, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	public String getItemName() {
		return LibItemNames.CONNECTOR;
	}
}