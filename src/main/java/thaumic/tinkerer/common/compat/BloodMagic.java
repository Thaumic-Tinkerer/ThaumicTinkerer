package thaumic.tinkerer.common.compat;


import thaumic.tinkerer.common.item.foci.ItemFocusDeflect;

/**
 * Created by Katrina on 31/01/2015.
 */
public class BloodMagic {



    public static void setupClass()
    {
        try {
            ItemFocusDeflect.DeflectBlacklist.add(Class.forName("WayofTime.alchemicalWizardry.common.entity.projectile.EntityParticleBeam"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
