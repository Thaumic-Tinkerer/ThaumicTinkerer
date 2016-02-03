package thaumic.tinkerer.common.compat;

import crazypants.enderio.machine.farm.FarmersRegistry;
import crazypants.enderio.machine.farm.farmers.FarmersCommune;
import thaumic.tinkerer.common.lib.LibMisc;

/**
 * Created by katsw on 02/02/2016.
 */
public class EnderIO {

    public static void registerPlanters()
    {
        FarmersCommune.joinCommune(new InfusedSeedsPlanter());
    }
}
