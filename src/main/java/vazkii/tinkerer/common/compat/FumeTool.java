package vazkii.tinkerer.common.compat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.tinkerer.common.block.BlockGas;
import vazkii.tinkerer.common.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.ITurtleUpgrade;
import dan200.turtle.api.TurtleSide;
import dan200.turtle.api.TurtleUpgradeType;
import dan200.turtle.api.TurtleVerb;

public class FumeTool implements ITurtleUpgrade {

	@SideOnly(Side.CLIENT)
	public static Icon icon;
	@Override
	public int getUpgradeID() {
		return 171;
	}

	@Override
	public String getAdjective() {
		return "Dissipating";
	}

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Tool;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(ModItems.gasRemover);
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public IHostedPeripheral createPeripheral(ITurtleAccess turtle,
			TurtleSide side) {
		return new FumePeripheral();
	}

	@Override
	public boolean useTool(ITurtleAccess turtle, TurtleSide side,
			TurtleVerb verb, int direction) {
		if(verb==TurtleVerb.Dig)
		{
		Vec3 pos=turtle.getPosition();
		int xs = (int) pos.xCoord;
		int ys = (int) pos.yCoord;
		int zs = (int) pos.zCoord;

		for(int x = xs - 3; x < xs + 3; x++)
			for(int y = ys - 3; y < ys + 3; y++)
				for(int z = zs - 3; z < zs + 3; z++) {
					int id = turtle.getWorld().getBlockId(x, y, z);
					if(Block.blocksList[id] != null && Block.blocksList[id] instanceof BlockGas) {
						BlockGas gas = (BlockGas) Block.blocksList[id];
						gas.placeParticle(turtle.getWorld(), x, y, z);
						turtle.getWorld().setBlock(x, y, z, 0, 0, 1 | 2);
					}
				}

		//turtle.getWorld().playSoundAtEntity(turtle., "thaumcraft.wand", 0.2F, 1F);
		return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return icon;
	}
	@ForgeSubscribe
	public void registerIcons(TextureStitchEvent evt) {
		if (evt.map.getTextureType() == 1) icon = evt.map.registerIcon("ttinkerer:gasRemover");
	}
	
}
