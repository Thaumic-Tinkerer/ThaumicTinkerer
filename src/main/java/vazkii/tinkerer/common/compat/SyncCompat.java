package vazkii.tinkerer.common.compat;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Mod.EventHandler;
import sync.api.SyncStartEvent;

public class SyncCompat {

	public static final String TAG_FORGEDATA="ForgeData";
	public static final String TAG_PERSISTED="PlayerPersisted";
	
	@EventHandler
	public void Sync(SyncStartEvent syncEv)
	{
		if(syncEv.prevPlayerTag.hasKey(TAG_FORGEDATA))
		{
			NBTTagCompound ForgeData=syncEv.prevPlayerTag.getCompoundTag(TAG_FORGEDATA);
			if(ForgeData.hasKey(TAG_PERSISTED))
			{
				NBTTagCompound nextForgeData;
				if(!syncEv.nextPlayerTag.hasKey(TAG_FORGEDATA))
				{
					nextForgeData=new NBTTagCompound();
				}
				else
				{
					nextForgeData=syncEv.nextPlayerTag.getCompoundTag(TAG_FORGEDATA);
				}
				nextForgeData.setCompoundTag(TAG_PERSISTED, ForgeData.getCompoundTag(TAG_PERSISTED));
				syncEv.nextPlayerTag.setCompoundTag(TAG_FORGEDATA, nextForgeData);
			}
		}
		
	}
}
