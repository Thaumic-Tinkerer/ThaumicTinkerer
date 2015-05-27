package thaumic.tinkerer.common.compat;


import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumic.tinkerer.common.block.tile.TileMagnet;
import thaumic.tinkerer.common.block.tile.TileMobMagnet;
import thaumic.tinkerer.common.item.ItemSoulMould;

import java.util.List;

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
        boolean mobMagnet = accessor.getTileEntity() instanceof TileMobMagnet;

        TileMagnet tileMagnet = (TileMagnet) accessor.getTileEntity();
        boolean isPulling = (tileMagnet.getBlockMetadata() & 1) == 0;
        if (isPulling)
            currenttip.add(StatCollector.translateToLocal("ttwaila.pullingMode"));
        else
            currenttip.add(StatCollector.translateToLocal("ttwaila.pushingMode"));
        if (mobMagnet) {
            TileMobMagnet tileMob = (TileMobMagnet) tileMagnet;
            if (tileMob.getStackInSlot(0) == null) {
                if (isPulling)
                    if (tileMob.adult)
                        currenttip.add(StatCollector.translateToLocal("ttwaila.pullingAdult"));
                    else
                        currenttip.add(StatCollector.translateToLocal("ttwaila.pullingChild"));
                else if (tileMob.adult)
                    currenttip.add(StatCollector.translateToLocal("ttwaila.pushingAdult"));
                else
                    currenttip.add(StatCollector.translateToLocal("ttwaila.pushingChild"));
            } else {
                String name = ItemSoulMould.getPatternName(tileMob.getStackInSlot(0));
                name = StatCollector.translateToLocal("entity." + name + ".name");
                if (isPulling)
                    if (tileMob.adult)
                        currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.pullingAdultType", name));
                    else
                        currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.pullingChildType", name));
                else if (tileMob.adult)
                    currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.pushingAdultType", name));
                else
                    currenttip.add(StatCollector.translateToLocalFormatted("ttwaila.pushingChildType", name));
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

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te,
			NBTTagCompound tag, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return tag;
	}

}
