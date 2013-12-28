package vazkii.tinkerer.common.item.pipe;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.common.lib.LibMisc;
import buildcraft.api.core.IIconProvider;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeIconProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThaumiumPipe extends Pipe {

	@SideOnly(Side.CLIENT)
	private static Icon icon;

	public ThaumiumPipe(int itemID) {
		super(new AspectPipe(), itemID);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider() {
		return new PipeIconProvider() {

			@Override
			@SideOnly(Side.CLIENT)
			public Icon getIcon(int pipeIconIndex) {
				return ThaumiumPipe.icon;
			}
		};
	}

	@Override
	public int getIconIndex(ForgeDirection direction) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public static void registerIcons(IconRegister reg) {
		icon = reg.registerIcon(LibMisc.MOD_ID + ":" + "thaumiumPipe");
	}
}