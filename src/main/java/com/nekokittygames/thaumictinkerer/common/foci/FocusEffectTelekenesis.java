package com.nekokittygames.thaumictinkerer.common.foci;

import com.nekokittygames.thaumictinkerer.common.misc.MiscHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.NodeSetting;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.codechicken.lib.vec.Vector3;

import javax.annotation.Nullable;
import java.util.List;

public class FocusEffectTelekenesis extends FocusEffect {
    @Override
    public boolean execute(RayTraceResult paramRayTraceResult, @Nullable Trajectory paramTrajectory, float paramFloat, int paramInt) {
        if ( paramRayTraceResult.typeOfHit!= RayTraceResult.Type.BLOCK)
            return false;
        if (paramTrajectory == null)
            return false;
        Vector3 target = new Vector3(paramTrajectory.source);
        final int range = 6 + getSettingValue("power");
        final double distance = range - 1;
        if (this.getPackage().getCaster().isSneaking())
            target.add(new Vector3(this.getPackage().getCaster().getLookVec()).multiply(distance));
        target.y += 0.5;
        List<EntityItem> entities = getPackage().world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(target.x - range, target.y - range, target.z - range, target.x + range, target.y + range, target.z + range));
        if (!entities.isEmpty()) {
            for (EntityItem item : entities) {
                MiscHelper.setEntityMotionFromVector(item, target, 0.3333F);
                FXDispatcher.INSTANCE.sparkle((float) item.posX, (float) item.posY, (float) item.posZ, 0, 0, 0);
            }
        }

        return false;
    }

    @Override
    public NodeSetting[] createSettings() {
        return new NodeSetting[]{new NodeSetting("power", "focus.common.power", new NodeSetting.NodeSettingIntRange(1, 5))};
    }

    @Override
    public void renderParticleFX(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
        // empty
    }

    @Override
    public int getComplexity() {
        return 3 * getSettingValue("power");
    }

    @Override
    public Aspect getAspect() {
        return Aspect.MAGIC;
    }

    @Override
    public String getKey() {
        return "thaumictinkerer.TELEKENESIS";
    }

    @Override
    public String getResearch() {
        return "TT_TELEKENESIS";
    }
}
