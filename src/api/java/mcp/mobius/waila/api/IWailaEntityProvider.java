package mcp.mobius.waila.api;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Callback class interface used to provide Entity tooltip informations to Waila.<br>
 * All methods in this interface shouldn't to be called by the implementing mod. An instance of the class is to be
 * registered to Waila via the {@link mcp.mobius.waila.api.IWailaRegistrar} instance provided in the original registration callback method
 * (cf. {@link mcp.mobius.waila.api.IWailaRegistrar} documentation for more information).
 * @author ProfMobius
 *
 */
public interface IWailaEntityProvider {
	
	/**
	 * Callback used to override the default Waila lookup system.</br>
	 * Will be used if the implementing class is registered via {@link mcp.mobius.waila.api.IWailaRegistrar}.{@link registerOverrideEntityProvider}.</br>
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return null if override is not required, an Entity otherwise.
	 */
	Entity getWailaOverride(mcp.mobius.waila.api.IWailaEntityAccessor accessor, mcp.mobius.waila.api.IWailaConfigHandler config);
	
	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link mcp.mobius.waila.api.IWailaRegistrar}.{@link registerHeadProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 * 
	 * @param entity Current Entity scanned.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return Modified input currenttip
	 */
	List<String> getWailaHead(Entity entity, List<String> currenttip, mcp.mobius.waila.api.IWailaEntityAccessor accessor, mcp.mobius.waila.api.IWailaConfigHandler config);
	
	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link mcp.mobius.waila.api.IWailaRegistrar}.{@link registerBodyProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 * 
	 * @param entity Current Entity scanned.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return Modified input currenttip
	 */	
	List<String> getWailaBody(Entity entity, List<String> currenttip, mcp.mobius.waila.api.IWailaEntityAccessor accessor, mcp.mobius.waila.api.IWailaConfigHandler config);
	
	/**
	 * Callback used to add lines to one of the three sections of the tooltip (Head, Body, Tail).</br>
	 * Will be used if the implementing class is registered via {@link mcp.mobius.waila.api.IWailaRegistrar}.{@link registerTailProvider} client side.</br>
	 * You are supposed to always return the modified input currenttip.</br>
	 * 
	 * @param entity Current Entity scanned.
	 * @param currenttip Current list of tooltip lines (might have been processed by other providers and might be processed by other providers).
	 * @param accessor Contains most of the relevant information about the current environment.
	 * @param config Current configuration of Waila.
	 * @return Modified input currenttip
	 */	
	List<String> getWailaTail(Entity entity, List<String> currenttip, mcp.mobius.waila.api.IWailaEntityAccessor accessor, IWailaConfigHandler config);
	
	/**
	 * Callback used server side to return a custom synchronization NBTTagCompound.</br>
	 * Will be used if the implementing class is registered via {@link IWailaRegistrar}.{@link registerNBTProvider} server and client side.</br>
	 * You are supposed to always return the modified input NBTTagCompound tag.</br>
	 * @param player The player requesting data synchronization (The owner of the current connection).
	 * @param ent The Entity targeted for synchronization.
	 * @param tag Current synchronization tag (might have been processed by other providers and might be processed by other providers).
	 * @param world TileEntity's World.
	 * @return Modified input NBTTagCompound tag.
	 */
	NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world);	
}
