package vazkii.tinkerer.common.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.lib.research.ResearchManager;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShareBook extends ItemMod {

	private static final String TAG_PLAYER = "player";
	private static final String NON_ASIGNED = "[none]";

	public ItemShareBook(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		String name = getPlayerName(par1ItemStack);
		if(name.endsWith(NON_ASIGNED)) {
			setPlayerName(par1ItemStack, par3EntityPlayer.username);
            if(isOfflineShare(par1ItemStack))
                setPlayerResearch(par1ItemStack,par3EntityPlayer.username);
			if(!par2World.isRemote)
				par3EntityPlayer.addChatMessage("ttmisc.shareTome.write");
		} else sync : {
			List<String> researchesDone = ResearchManager.getResearchForPlayer(name);

			if(researchesDone == null && ! isOfflineShare(par1ItemStack)) {
				if(!par2World.isRemote)
					par3EntityPlayer.addChatMessage("ttmisc.shareTome.notOnline");
				break sync;
			}
            if(researchesDone==null && isOfflineShare(par1ItemStack))
            {
                if(par2World.isRemote)
                    researchesDone=getPlayerResearch(par1ItemStack);
                else {
                    par3EntityPlayer.addChatMessage("ttmisc.shareTome.sync");
                    break sync;

                }
            }

			for(String key : researchesDone)
				ThaumicTinkerer.tcProxy.getResearchManager().completeResearch(par3EntityPlayer, key);

			if(!par2World.isRemote)
				par3EntityPlayer.addChatMessage("ttmisc.shareTome.sync");
		}


		return par1ItemStack;
	}

    private List<String> getPlayerResearch(ItemStack par1ItemStack) {
        List<String> retVals=new ArrayList<String>();
        NBTTagCompound cmp=ItemNBTHelper.getNBT(par1ItemStack);
        if(!cmp.hasKey("research"))
            return retVals;
        NBTTagList list=cmp.getTagList("research");
        for(int i=0;i<list.tagCount();i++)
        {
            NBTTagString str= (NBTTagString) list.tagAt(i);
            retVals.add(str.data);
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
        if(isOfflineShare(par1ItemStack))
            par3List.add(StatCollector.translateToLocal("ttmisc.shareTomb.offlineshare"));
	}

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int i=0;i<2;i++)
        {
            par3List.add(new ItemStack(this,1,i));
        }
    }

    @Override
	public boolean getShareTag() {
		return true;
	}

    public boolean isOfflineShare(ItemStack itemStack)
    {
        return itemStack.getItemDamage()==1;
    }
	private String getPlayerName(ItemStack stack) {
		return ItemNBTHelper.getString(stack, TAG_PLAYER, NON_ASIGNED);
	}

	private void setPlayerName(ItemStack stack, String playerName) {
		ItemNBTHelper.setString(stack, TAG_PLAYER, playerName);
	}

    private void setPlayerResearch(ItemStack stack,String playername)
    {
        List<String> researchesDone = ResearchManager.getResearchForPlayer(playername);
        NBTTagCompound cmp=ItemNBTHelper.getNBT(stack);
        NBTTagList list=new NBTTagList();
        for(String tag:researchesDone)
        {
            list.appendTag(new NBTTagString("tag",tag));
        }
        cmp.setTag("research",list);


    }
}