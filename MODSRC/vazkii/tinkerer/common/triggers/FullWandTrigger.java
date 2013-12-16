package vazkii.tinkerer.common.triggers;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileWandPedestal;
import vazkii.tinkerer.common.lib.LibMisc;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FullWandTrigger implements ITrigger {

	@SideOnly(Side.CLIENT)
	private Icon icon;

	@Override
	public String getUniqueTag() {
		return LibMisc.MOD_ID + ".fullWandTrigger";
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public void registerIcons(IconRegister reg) {
		icon = reg.registerIcon(LibMisc.MOD_ID + ":triggers/fullWand");
	}

	@Override
	public String getDescription() {
		return "Wand is Fully Charged";
	}

	@Override
	public boolean isTriggerActive(ForgeDirection side, TileEntity tile, ITriggerParameter parameter) {
		if (tile != null && tile instanceof TileWandPedestal) {
			TileWandPedestal pedestal = (TileWandPedestal) tile;
			ItemStack stack = pedestal.func_70301_a(0);
			if (stack != null && stack.getItem() instanceof ItemWandCasting) {
				ItemWandCasting wand = (ItemWandCasting) stack.getItem();
				AspectList aspects = wand.getAspectsWithRoom(stack);
				if (aspects != null)
					return aspects.size() == 0;
			}
		}
		return false;
	}

	@Override
	public boolean hasParameter() {
		return false;
	}

	@Override
	public boolean requiresParameter() {
		return false;
	}

	@Override
	public ITriggerParameter createParameter() {
		return null;
	}

	@Override
	public int getLegacyId() {
		return 0;
	}
}