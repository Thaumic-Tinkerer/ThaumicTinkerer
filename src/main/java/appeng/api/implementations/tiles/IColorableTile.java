package appeng.api.implementations.tiles;

import appeng.api.util.AEColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public interface IColorableTile
{

	AEColor getColor();

	boolean recolourBlock(ForgeDirection side, AEColor colour, EntityPlayer who);

}
