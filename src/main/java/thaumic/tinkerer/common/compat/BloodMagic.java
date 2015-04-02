package thaumic.tinkerer.common.compat;

import WayofTime.alchemicalWizardry.common.entity.projectile.EntityParticleBeam;
import thaumic.tinkerer.common.item.foci.ItemFocusDeflect;

/**
 * Created by Katrina on 31/01/2015.
 */
public class BloodMagic {



    public static void setupClass()
    {
        ItemFocusDeflect.DeflectBlacklist.add(EntityParticleBeam.class);
    }
}
