package thaumic.tinkerer.common.compat;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraftforge.common.MinecraftForge;
import thaumic.tinkerer.common.block.tile.transvector.TileTransvectorInterface;

/**
 * Created by pixlepix on 8/26/14.
 */
public class IndustrialcraftUnloadHelper {

    public static void removeFromIC2EnergyNet(TileTransvectorInterface tileEntity) {
        if ((tileEntity.addedToICEnergyNet && !tileEntity.getWorldObj().isRemote)) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergySink) tileEntity));
            tileEntity.addedToICEnergyNet = false;
        }
    }

    public static void addToIC2EnergyNet(TileTransvectorInterface tileTransvectorInterface) {
        MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(tileTransvectorInterface));
    }
}
