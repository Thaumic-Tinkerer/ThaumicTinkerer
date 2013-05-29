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
 * File Created @ [29 May 2013, 20:52:44 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.common.Config;
import thaumcraft.common.research.ResearchManager;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShareTome extends ItemMod {

	private static final String TAG_PLAYER = "player";
	private static final String NON_ASIGNED = "[Not Assigned]";

	private static final String[] INFO = new String[] {
		"This is a creative only item.",
		"It can be right clicked by a player",
		"to keep their research. After this,",
		"another player can right click it",
		"and they'll learn all that the other",
		"player knew. This is good for co-op playing.",
		"Have fun - Vazkii"
	};

	public ItemShareTome(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		String name = getPlayerName(par1ItemStack);
		if(name.endsWith(NON_ASIGNED)) {
			setPlayerName(par1ItemStack, par3EntityPlayer.username);
			if(!par2World.isRemote)
				par3EntityPlayer.addChatMessage("Research Data Written!");
		} else {
			sync : {
				List<ResearchItem> researchesDone = ResearchManager.getCompletedResearch(name);

				if(researchesDone == null) {
					if(!par2World.isRemote)
						par3EntityPlayer.addChatMessage(name + "is not online. Research could not be acquired.");
					break sync;
				}

				for(ResearchItem item : researchesDone)
					ResearchManager.completeResearch(par3EntityPlayer, item.key);

				if(!par2World.isRemote)
					par3EntityPlayer.addChatMessage("Research Data Synced!");
			}
		}


		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return Config.itemThaumonomicon.getIconFromDamage(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,	EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		String name = getPlayerName(par1ItemStack);
		par3List.add(name.equals(NON_ASIGNED) ? name : "Bound to " + name + "'s research.");
		if(GuiScreen.isShiftKeyDown())
			for(String s : INFO)
				par3List.add(s);

		else par3List.add("Press SHIFT for more info.");
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	private String getPlayerName(ItemStack stack) {
		return ItemNBTHelper.getString(stack, TAG_PLAYER, NON_ASIGNED);
	}

	private void setPlayerName(ItemStack stack, String playerName) {
		ItemNBTHelper.setString(stack, TAG_PLAYER, playerName);
	}
}