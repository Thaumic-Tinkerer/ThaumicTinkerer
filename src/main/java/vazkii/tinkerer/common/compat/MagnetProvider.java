package vazkii.tinkerer.common.compat;

import java.util.List;

import vazkii.tinkerer.common.block.tile.TileMagnet;
import vazkii.tinkerer.common.block.tile.TileMobMagnet;
import vazkii.tinkerer.common.item.ItemSoulMould;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class MagnetProvider implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		boolean mobMagnet=accessor.getTileEntity() instanceof TileMobMagnet;
		
		TileMagnet tileMagnet=(TileMagnet)accessor.getTileEntity();
		boolean isPulling=(tileMagnet.getBlockMetadata() & 1) == 0;
		if(isPulling)
			currenttip.add("Mode: Pulling");
		else
			currenttip.add("Mode: Pushing");
		if(mobMagnet)
		{
			TileMobMagnet tileMob=(TileMobMagnet)tileMagnet;
			if(tileMob.getStackInSlot(0)==null)
			{
				currenttip.add((isPulling?"Pulling: ":"Pushing: ")+ ((tileMob.adult)?"Adults":"Children"));
			}
			else
			{
				String name=ItemSoulMould.getPatternName(tileMob.getStackInSlot(0));
				name=StatCollector.translateToLocal("entity." + name + ".name");
				currenttip.add((isPulling?"Pulling: ":"Pushing: ")+((tileMob.adult)?"Adult ":"Child ")+ name);
			}
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

}
