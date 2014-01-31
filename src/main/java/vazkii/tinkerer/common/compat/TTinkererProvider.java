package vazkii.tinkerer.common.compat;

import java.util.List;

import thaumcraft.common.Thaumcraft;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
				currentTool="Nothing";
			}
			else
			{
				currentTool=stack.getDisplayName();
			}
			currenttip.add("Current Tool: "+currentTool);
			if(stack!=null)
			{
				if(tileAn.leftClick)
				{
					currenttip.add("Left Click");
				}
				else
				{
					currenttip.add("Right Click");
				}
				if(tileAn.redstone)
					currenttip.add("Redstone Activated");
				else
					currenttip.add("Autonomous");
			}
		}
		if(accessor.getBlock()==ModBlocks.interfase)
		{
			TileTransvectorInterface tileTrans=(TileTransvectorInterface)accessor.getTileEntity();
			String currentBlock="";
			TileEntity tile = tileTrans.getTile();
			if(tile == null)
				currentBlock="Nothing";
			else
			{
				currentBlock=tile.getBlockType().getLocalizedName();
			}
			currenttip.add("Connected to: "+currentBlock);
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
					currenttip.add("Repairing: "+ item.getDisplayName());
				else
					currenttip.add("Finished Repairing: "+item.getDisplayName());
			}
			
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
		registrar.registerBodyProvider(new MagnetProvider(), ModBlocks.magnet.blockID);
}

}
