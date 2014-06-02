package vazkii.tinkerer.common.core.handler.kami;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import vazkii.tinkerer.common.lib.LibMisc;

/**
 * Created by Katrina on 02/03/14.
 */
public class KamiArmorHandler {

    private static final String COMPOUND = LibMisc.MOD_ID;
    private static final String TAG_STATUS = "GemArmor";
    private static NBTTagCompound getCompoundToSet(EntityPlayer player) {
        NBTTagCompound cmp = player.getEntityData();
        if(!cmp.hasKey(COMPOUND))
            cmp.setTag(COMPOUND, new NBTTagCompound());

        return cmp.getCompoundTag(COMPOUND);
    }



    public static boolean getArmorStatus(EntityPlayer player) {
        NBTTagCompound cmp = getCompoundToSet(player);
        return !cmp.hasKey(TAG_STATUS) || cmp.getBoolean(TAG_STATUS);
    }

    public static void setArmorStatus(EntityPlayer player, boolean status) {
        NBTTagCompound cmp = getCompoundToSet(player);
        cmp.setBoolean(TAG_STATUS, status);
    }
}
