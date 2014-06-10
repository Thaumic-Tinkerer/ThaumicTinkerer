package vazkii.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.lib.research.ResearchManager;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.List;

public class ItemShareBook extends ItemBase {

	private static final String TAG_PLAYER = "player";
	private static final String NON_ASIGNED = "[none]";

	public ItemShareBook() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public TTResearchItem getResearchItem() {
		TTResearchItem research = (TTResearchItem) new TTResearchItem(LibResearch.KEY_SHARE_TOME, new AspectList(), 0, -1, 0, new ItemStack(this)).setStub().setAutoUnlock().setRound();
		if (ConfigHandler.enableSurvivalShareTome)
			research.setPages(new ResearchPage("0"), ResearchHelper.recipePage(LibResearch.KEY_SHARE_TOME));
		else research.setPages(new ResearchPage("0"));
		return research;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		String name = getPlayerName(par1ItemStack);
		if (name.endsWith(NON_ASIGNED)) {
			setPlayerName(par1ItemStack, par3EntityPlayer.getGameProfile().getName());
			setPlayerResearch(par1ItemStack, par3EntityPlayer.getGameProfile().getName());
			if (!par2World.isRemote)
				par3EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.shareTome.write"));
		} else sync:{
			List<String> researchesDone = ResearchManager.getResearchForPlayer(name);

			if (researchesDone == null) {
				if (par2World.isRemote)
					researchesDone = getPlayerResearch(par1ItemStack);
				else {
					par3EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.shareTome.sync"));
					break sync;

				}
			}

			for (String key : researchesDone)
				ThaumicTinkerer.tcProxy.getResearchManager().completeResearch(par3EntityPlayer, key);

			if (!par2World.isRemote)
				par3EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.shareTome.sync"));
		}

		return par1ItemStack;
	}

	private List<String> getPlayerResearch(ItemStack par1ItemStack) {
		List<String> retVals = new ArrayList<String>();
		NBTTagCompound cmp = ItemNBTHelper.getNBT(par1ItemStack);
		if (!cmp.hasKey("research"))
			return retVals;
		NBTTagList list = cmp.getTagList("research", Constants.NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); i++) {

			retVals.add(list.getStringTagAt(i));
		}
		return retVals;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		String name = getPlayerName(par1ItemStack);
		par3List.add(name.equals(NON_ASIGNED) ? StatCollector.translateToLocal("ttmisc.shareTome.noAssign") : String.format(StatCollector.translateToLocal("ttmisc.shareTome.playerName"), name));
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

	private void setPlayerResearch(ItemStack stack, String playername) {
		List<String> researchesDone = ResearchManager.getResearchForPlayer(playername);
		NBTTagCompound cmp = ItemNBTHelper.getNBT(stack);
		NBTTagList list = new NBTTagList();
		for (String tag : researchesDone) {
			list.appendTag(new NBTTagString(tag));
		}
		cmp.setTag("research", list);

	}

	@Override
	public String getItemName() {
		return LibItemNames.SHARE_BOOK;
	}

}