package mcp.mobius.waila.api;

import java.util.List;

import mcp.mobius.waila.api.ITaggedList.ITipList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Callback class interface used to provide Block/TileEntity tooltip informations to Waila.<br>
 * All methods in this interface shouldn't to be called by the implementing mod. An instance of the class is to be
 * registered to Waila via the {@link IWailaRegistrar} instance provided in the original registration callback method 
 * (cf. {@link IWailaRegistrar} documentation for more information).
 * @author ProfMobius
 *
 */
public interface IWailaDataProvider{
	
	/**
	 * Callback used to override the default Waila lookup system.</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar}.{@link registerStackProvider}.</br>
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return null if override is not required, an ItemStack otherwise.
	 */
	ItemStack    getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config);
	
	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar}.{@link registerHeadProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 * 
	 * @param itemStack Current block scanned, in ItemStack form.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return Modified input currenttip
	 */
	ITipList getWailaHead(ItemStack itemStack, ITipList currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config);
	
	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar}.{@link registerBodyProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 * 
	 * @param itemStack Current block scanned, in ItemStack form.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return Modified input currenttip
	 */	
	ITipList getWailaBody(ItemStack itemStack, ITipList currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config);
	
	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar}.{@link registerTailProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 * 
	 * @param itemStack Current block scanned, in ItemStack form.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return Modified input currenttip
	 */	
	ITipList getWailaTail(ItemStack itemStack, ITipList currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config);
	
	/**
	 * Callback used server side to return a custom synchronization NBTTagCompound.</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar}.{@link registerNBTProvider} server and client side.</br>
	 * You are supposed to always return the modified input NBTTagCompound tag.</br>
	 * @param te The TileEntity targeted for synchronization.
	 * @param tag Current synchronization tag (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @return Modified input NBTTagCompound tag.
	 */
	NBTTagCompound getNBTData(TileEntity te, NBTTagCompound tag, IWailaDataAccessorServer accessor);
}
