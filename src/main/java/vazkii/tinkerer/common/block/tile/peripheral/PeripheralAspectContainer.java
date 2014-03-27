/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [13 Sep 2013, 00:58:21 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.peripheral;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.ILuaContext;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.tiles.TileJarFillable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeripheralAspectContainer implements IHostedPeripheral {

    IAspectContainer container;
    public static Map<Double,Map<String, Object>> aspectsToMap(IAspectContainer container) {
        Map<Double,Map<String, Object>> aspects = Maps.newHashMap();
        AspectList aspectList = container.getAspects();
        if (aspectList == null) return aspects;
        int i=1;
        for (Aspect aspect : aspectList.getAspects()) {
            if (aspect == null) continue;
            appendAspectEntry(aspects,i++, aspect, aspectList.getAmount(aspect));
        }
        return aspects;
    }

    public static void appendAspectEntry(Map<Double,Map<String, Object>> result,int count, Aspect aspect, int quantity) {
        Map<String, Object> aspectDetails = Maps.newHashMap();
        aspectDetails.put("name", aspect.getName());
        aspectDetails.put("quantity", new Double(quantity));
        result.put(new Double(count),aspectDetails);
    }
    public PeripheralAspectContainer(IAspectContainer container) {
        this.container = container;
    }

    @Override
    public String getType() {
        return "tt_aspectContainer";
    }

    @Override
    public String[] getMethodNames() {
        if(container instanceof TileJarFillable)
            return new String[]{"getAspects", "getAspectCount","getAspectsSum","getAspectFilter"};
        else
            return new String[]{"getAspects", "getAspectCount","getAspectsSum"};
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
        switch (method) {
            case 0: {
                return getAspects();
            }
            case 1: {
                String aspectName = (String) arguments[0];
                Aspect aspect = Aspect.getAspect(aspectName);

                if (container.getAspects() == null)
                    return new Object[]{0};

                return new Object[]{container.getAspects().getAmount(aspect)};
            }
            case 2:
            {
                    AspectList aspectList = container.getAspects();
                    if (aspectList == null) return null;
                    Map<String, Integer> result = Maps.newHashMap();
                    for (Aspect aspect : aspectList.getAspects()) {
                        if (aspect == null) continue;
                        String name = aspect.getName();
                        int amount = Objects.firstNonNull(result.get(name), 0);
                        result.put(name, amount + aspectList.getAmount(aspect));
                    }
                    return new Object[]{result};
            }
            case 3:
                if(!(container instanceof  TileJarFillable))
                    return null;
                TileJarFillable jar=(TileJarFillable)container;
                return new Object[]{jar.aspectFilter!=null?jar.aspectFilter.getName():""};
        }

        return null;
    }
    public Object[] getAspects() {
        if(!(container instanceof  TileJarFillable))
            return null;
        TileJarFillable jar=(TileJarFillable)container;
        Map<Double,Map<String, Object>> result = aspectsToMap(container);
        if (result.isEmpty()) {
            Aspect filter = jar.aspectFilter;
            if (filter != null) appendAspectEntry(result, 1,filter, 0);
        }
        return new Object[]{result};
    }

    @Override
    public boolean canAttachToSide(int side) {
        return true;
    }

    @Override
    public void attach(IComputerAccess computer) {
        // NO-OP
    }

    @Override
    public void detach(IComputerAccess computer) {
        // NO-OP
    }

    @Override
    public void update() {
        // NO-OP
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        // NO-OP
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        // NO-OP
    }

}