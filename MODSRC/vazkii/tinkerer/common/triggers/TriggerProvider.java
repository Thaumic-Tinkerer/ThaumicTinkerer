package vazkii.tinkerer.common.triggers;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.tiles.TileWandPedestal;
import buildcraft.api.gates.ActionManager;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerProvider;
import buildcraft.api.transport.IPipe;

public class TriggerProvider implements ITriggerProvider {

	public static ITrigger aspectTrigger64 = new AspectAmountTrigger(64);
	public static ITrigger aspectTrigger32 = new AspectAmountTrigger(32);
	public static ITrigger aspectTrigger16 = new AspectAmountTrigger(16);
	public static ITrigger aspectTrigger08 = new AspectAmountTrigger(8);
	public static ITrigger aspectTriggerMinus8 = new AspectAmountTrigger(-8);
	public static ITrigger fullWandTrigger = new FullWandTrigger();
	public static ITrigger emptyWandTrigger = new EmptyWandTrigger();
	
	public static void init() {
		
		// Register BuildCraft triggers
		ActionManager.registerTrigger(aspectTriggerMinus8);
		ActionManager.registerTrigger(aspectTrigger08);
		ActionManager.registerTrigger(aspectTrigger16);
		ActionManager.registerTrigger(aspectTrigger32);
		ActionManager.registerTrigger(aspectTrigger64);
		ActionManager.registerTrigger(fullWandTrigger);
		ActionManager.registerTrigger(emptyWandTrigger);

		ActionManager.registerTriggerProvider(new TriggerProvider());
	}
	
	public static void loadTextures(TextureStitchEvent.Pre event) {
		if (event.map.textureType == 1) {
			aspectTrigger64.registerIcons(event.map);
			aspectTrigger32.registerIcons(event.map);
			aspectTrigger16.registerIcons(event.map);
			aspectTrigger08.registerIcons(event.map);
			aspectTriggerMinus8.registerIcons(event.map);
			fullWandTrigger.registerIcons(event.map);
			emptyWandTrigger.registerIcons(event.map);
		}
	}
	
	@Override
	public LinkedList<ITrigger> getPipeTriggers(IPipe pipe) {
		return null;
	}

	@Override
	public LinkedList<ITrigger> getNeighborTriggers(Block block, TileEntity tile) {
		LinkedList<ITrigger> list = new LinkedList<ITrigger>();

		if (tile != null && tile instanceof IAspectContainer && !(tile instanceof IInventory) && !(tile instanceof IFluidHandler)) {
			list.add(aspectTriggerMinus8);
			list.add(aspectTrigger08);
			list.add(aspectTrigger16);
			list.add(aspectTrigger32);
			list.add(aspectTrigger64);
		}

		if (tile instanceof TileWandPedestal) {
			list.add(fullWandTrigger);
			list.add(emptyWandTrigger);
		}

		return list;
	}
}