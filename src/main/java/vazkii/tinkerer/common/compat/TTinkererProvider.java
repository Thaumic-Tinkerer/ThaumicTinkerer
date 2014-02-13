package vazkii.tinkerer.common.compat;

import java.util.List;

import thaumcraft.common.Thaumcraft;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class TTinkererProvider implements IWailaDataProvider {

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
		if(accessor.getBlock()==ModBlocks.animationTablet)
		{
			TileAnimationTablet tileAn=(TileAnimationTablet)accessor.getTileEntity();
			String currentTool="";
			ItemStack stack=tileAn.getStackInSlot(0);
			if(stack==null)
			{
				currentTool=StatCollector.translateToLocal("ttwaila.nothing");
			}
			else
			{
				currentTool=stack.getDisplayName();
			}
			currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.currentTool",currentTool));
			if(stack!=null)
			{
				if(tileAn.leftClick)
				{
					currenttip.add(StatCollector.translateToLocal("ttwaila.leftClick"));
				}
				else
				{
					currenttip.add(StatCollector.translateToLocal("ttwaila.rightClick"));
				}
				if(tileAn.redstone)
					currenttip.add(StatCollector.translateToLocal("ttwaila.redstone"));
				else
					currenttip.add(StatCollector.translateToLocal("ttwaila.autonomous"));
			}
		}
		if(accessor.getBlock()==ModBlocks.interfase)
		{
			TileTransvectorInterface tileTrans=(TileTransvectorInterface)accessor.getTileEntity();
			String currentBlock="";
			TileEntity tile = tileTrans.getTile();
			if(tile == null)
				currentBlock=StatCollector.translateToLocal("ttwaila.nothing");
			else
			{
				currentBlock=tile.getBlockType().getLocalizedName();
			}
			currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.connected", currentBlock));
			if(tile!=null)
				currenttip.add(String.format("x: %d y: %d z: %d", tile.xCoord,tile.yCoord,tile.zCoord));
		}
		if(accessor.getBlock()==ModBlocks.repairer)
		{
			TileRepairer tileRepair=(TileRepairer)accessor.getTileEntity();
			ItemStack item=tileRepair.getStackInSlot(0);
			if(item!=null)
			{
				if(item.getItemDamage()>0)
					currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.repairing",item.getDisplayName()));
				else
					currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.finishedRepairing",item.getDisplayName()));
			}
			
		}
		if(accessor.getBlock()==ModBlocks.warpGate)
		{
			TileWarpGate tileWarp=(TileWarpGate)accessor.getTileEntity();
			if(tileWarp.locked)
				currenttip.add(StatCollector.translateToLocal("ttwaila.allowIncoming"));
			else
				currenttip.add(StatCollector.translateToLocal("ttwaila.disallowIncoming"));
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		// TODO Auto-generated method stub
		return currenttip;
	}
	
	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new TTinkererProvider(),ModBlocks.animationTablet.blockID);
		registrar.registerBodyProvider(new TTinkererProvider(),ModBlocks.interfase.blockID);
		registrar.registerBodyProvider(new TTinkererProvider(),ModBlocks.repairer.blockID);
		registrar.registerBodyProvider(new TTinkererProvider(),ModBlocks.warpGate.blockID);
		registrar.registerBodyProvider(new MagnetProvider(), ModBlocks.magnet.blockID);
}

}