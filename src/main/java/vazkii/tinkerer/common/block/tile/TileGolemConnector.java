package vazkii.tinkerer.common.block.tile;


import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.Marker;
import vazkii.tinkerer.common.core.golems.EnumGolemCores;
import vazkii.tinkerer.common.core.golems.EnumGolemDecorations;

import java.util.*;
@Optional.InterfaceList({
        @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
        @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
})
public class TileGolemConnector extends TileCamo  implements IPeripheral,SimpleComponent {
    private static final String TAG_UUID_MOST = "UUIDMost";
    private static final String TAG_UUID_LEAST = "UUIDLeast";
    public UUID golemConnected;
    public EntityGolemBase golem;

    @Override
    public boolean canUpdate() {
        return true;
    }

    public void verifyGolem() {
        if (golemConnected == null)
            return;
        List list = this.worldObj.getEntitiesWithinAABB(EntityGolemBase.class,
                AxisAlignedBB.getBoundingBox(xCoord - 30, yCoord - 30,
                        zCoord - 30, xCoord + 30, yCoord + 30, zCoord + 30)
        );
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            EntityGolemBase entityGolem = (EntityGolemBase) iterator.next();

            if (entityGolem.getUniqueID().equals(golemConnected)) {
                this.golem = entityGolem;
                break;
            }
        }
    }

    @Override
    public String getType() {
        return "tt_golemconnector";
    }


    public Object[] getGolemDecorationsImplementation() throws Exception {
        if (golem == null || golem.decoration == null || golem.decoration.length() == 0)
            return new String[]{};
        HashMap<Double, String> decorations = new HashMap<Double, String>();
        for (int i = 0; i < golem.decoration.length(); i++) {
            EnumGolemDecorations golemDec = EnumGolemDecorations.getFromChar(golem.decoration.charAt(i));
            if (golemDec == null)
                throw new Exception("Golem is wearing unknown accessory: " + golem.decoration.charAt(i));
            decorations.put((double) i, StatCollector.translateToLocal(golemDec.getName()));
        }

        return new Object[]{decorations};

    }

    public String[] getGolemCoreImplementation() throws Exception {
        if (golem == null)
            return new String[]{};
        if (golem.getCore() == -1)
            return new String[]{StatCollector.translateToLocal("item.ItemGolemCore.100.name")};
        String[] decorations = new String[1];
        EnumGolemCores golemCore = EnumGolemCores.getFromByte(golem.getCore());
        if (golemCore == null)
            throw new Exception("Golem has Unknown core: " + golem.getCore());
        decorations[0] = StatCollector.translateToLocal(golemCore.getName());

        return decorations;

    }

    @Override
    public void writeCustomNBT(NBTTagCompound cmp) {
        super.writeCustomNBT(cmp);
        if (golemConnected != null) {
            cmp.setLong(TAG_UUID_MOST, golemConnected.getMostSignificantBits());
            cmp.setLong(TAG_UUID_LEAST, golemConnected.getLeastSignificantBits());
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.getTotalWorldTime() % 100 == 1) {
            verifyGolem();
        }
    }

    public void ConnectGolem(UUID golemID) {
        golemConnected = golemID;
        if (golemID == null)
            golem = null;
        else {
            verifyGolem();
        }
    }

    @Override
    public void readCustomNBT(NBTTagCompound cmp) {
        super.readCustomNBT(cmp);
        if (cmp.hasKey(TAG_UUID_LEAST) && cmp.hasKey(TAG_UUID_MOST)) {
            golemConnected = new UUID(cmp.getLong(TAG_UUID_MOST), cmp.getLong(TAG_UUID_LEAST));
            //verifyGolem();
        }

    }

    @Override
    public String[] getMethodNames() {
        return new String[]{"getDecorations", "getPosition", "getType", "getHealth", "getCore", "getHome", "setHome", "getMarkers", "setMarkers", "newMarker", "addMarker", "saveMarker", "deleteMarker", "getMarker", "getMarkerCount"};
    }

    

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context,
                               int method, Object[] arguments) throws Exception {
        switch (method) {
            case 0:
                return getGolemDecorationsImplementation();
            case 1:
                if (golem == null)
                    return new Integer[]{};
                return new Integer[]{(int) golem.posX, (int) golem.posY, (int) golem.posZ};
            case 2:
                return getGolemTypeImplementation();
            case 3:
                if (golem == null)
                    return new Float[]{};
                return new Float[]{golem.getHealth()};
            case 4:
                return getGolemCoreImplementation();
            case 5:
                return getHomeImplementation();
            case 6:
                if (arguments.length != 4)
                    throw new Exception("Invalid arguments");
                return setHomeImplementation(arguments);
            case 7:
                return getMarkersImplementation();
            case 8:
                if (arguments.length != 1)
                    throw new Exception("setMarkers takes 1 argument");
                return setMarkersImplementation(arguments);
            case 9:
                return newMarkerImplementation();
            case 10:
                if (arguments.length != 1)
                    throw new Exception("addMarker must have 1 argument");
                return addMarkerImplementation(arguments);
            case 11:
                if (arguments.length != 2)
                    throw new Exception("saveMarker must have 2 arguments");
                return saveMarkerImplementation(arguments);
            case 12:
                if (arguments.length != 1)
                    throw new Exception("deleteMarker must have 1 argument");
                return deleteMarkerImplementation(arguments);
            case 13:
                if (arguments.length != 1)
                    throw new Exception("getMarker must have 1 argument");
                return getMarkerImplementation(arguments);
            case 14:
                return getMarkerCountImplementation();
        }
        return null;
    }

    private Object[] getMarkerCountImplementation() {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null)
            return new Object[]{(int) 0};
        return new Object[]{(double) markers.size()};
    }

    private Object[] getMarkerImplementation(Object[] arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null || markers.size() <= (Double) arguments[0])
            throw new Exception("marker " + (int) (double) (Double) arguments[0] + " does not exist");
        Marker mark = markers.get((int) (double) (Double) arguments[0]);

        return new Object[]{fromMarkerImplementation(mark)};
    }

    private Object[] deleteMarkerImplementation(Object[] arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null || markers.size() <= (Double) arguments[0])
            throw new Exception("marker " + (int) (double) (Double) arguments[0] + " does not exist");
        markers.remove((int) (double) (Double) arguments[0]);
        golem.setMarkers(markers);
        return new String[]{};
    }

    private Object[] saveMarkerImplementation(Object[] arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null || markers.size() <= (Double) arguments[0])
            throw new Exception("marker " + (int) (double) (Double) arguments[0] + " does not exist");
        Marker mark = toMarkerImplementation((HashMap<String, Object>) arguments[1]);
        markers.set((int) (double) (Double) arguments[0], mark);
        golem.setMarkers(markers);
        return getMarkerImplementation(new Object[]{arguments[0]});
    }

    @SuppressWarnings("unchecked")
    private Object[] addMarkerImplementation(Object[] arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null)
            markers = new ArrayList<Marker>();
        Marker mark = toMarkerImplementation((HashMap<String, Object>) arguments[0]);
        markers.add(mark);
        golem.setMarkers(markers);
        return getMarkerImplementation(new Double[]{(double) (markers.size() - 1)});
    }

    private Object[] newMarkerImplementation() {
        HashMap<String, Object> mark = new HashMap<String, Object>();
        mark.put("posX", xCoord);
        mark.put("posY", yCoord);
        mark.put("posZ", zCoord);
        mark.put("dim", this.worldObj.provider.dimensionId);
        mark.put("color", -1);
        mark.put("side", 1);
        return new Object[]{mark};
    }

    @SuppressWarnings("unchecked")
    private Object[] setMarkersImplementation(Object[] arguments) throws Exception {
        if (golem == null)
            return new String[]{};

        ArrayList<Marker> arrList = new ArrayList<Marker>();

        HashMap<Double, HashMap<String, Object>> markersToSet = (HashMap<Double, HashMap<String, Object>>) arguments[0];
        for (HashMap<String, Object> map : markersToSet.values()) {
            Marker mark = toMarkerImplementation(map);

            arrList.add(mark);

        }
        golem.setMarkers(arrList);
        return getMarkersImplementation();
    }

    /**
     * @param markerMap
     * @return
     */
    private Marker toMarkerImplementation(HashMap<String, Object> markerMap) {
        double posX = (Double) markerMap.get("posX");
        double posY = (Double) markerMap.get("posY");
        double posZ = (Double) markerMap.get("posZ");
        double dim = (Double) markerMap.get("dim");
        double side = (Double) markerMap.get("side");
        double color = (Double) markerMap.get("color");
        Marker mark = new Marker((int) posX, (int) posY, (int) posZ, (int) dim, (byte) side, (byte) color);
        return mark;
    }

    private Object[] getMarkersImplementation() {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        HashMap<Integer, HashMap<String, Object>> luaMarkers = new HashMap<Integer, HashMap<String, Object>>();
        int i = 1;
        for (Marker mark : markers) {
            HashMap<String, Object> luaMarker = fromMarkerImplementation(mark);
            luaMarkers.put(i++, luaMarker);
        }
        return new Object[]{luaMarkers};
    }

    /**
     * @param mark
     * @return
     */
    private HashMap<String, Object> fromMarkerImplementation(Marker mark) {
        HashMap<String, Object> luaMarker = new HashMap<String, Object>();

        luaMarker.put("posX", mark.x);
        luaMarker.put("posY", mark.y);
        luaMarker.put("posZ", mark.z);
        luaMarker.put("dim", mark.dim);
        luaMarker.put("color", mark.color);
        luaMarker.put("side", mark.side);
        return luaMarker;
    }

    private Object[] setHomeImplementation(Object[] arguments) throws Exception {
        if (golem == null)
            return new String[]{};

        double x = (Double) arguments[0];
        double y = (Double) arguments[1];
        double z = (Double) arguments[2];
        double facing = (Double) arguments[3];
        golem.setHomeArea((int) x, (int) y, (int) z, (int) 35);
        golem.homeFacing = (int) facing;
        return getHomeImplementation();
    }

    private Object[] getHomeImplementation() {
        if (golem == null)
            return new String[]{};
        ChunkCoordinates home = golem.getHomePosition();
        return new Integer[]{home.posX, home.posY, home.posZ, golem.homeFacing};
    }

    private String[] getGolemTypeImplementation() throws Exception {
        if (golem == null)
            return new String[]{};
        switch (golem.getGolemType()) {
            case CLAY:
                return new String[]{"Clay"};
            case FLESH:
                return new String[]{"Flesh"};
            case IRON:
                return new String[]{"Iron"};
            case STONE:
                return new String[]{"Stone"};
            case STRAW:
                return new String[]{"Straw"};
            case TALLOW:
                return new String[]{"Tallow"};
            case THAUMIUM:
                return new String[]{"Thaumium"};
            case WOOD:
                return new String[]{"Wood"};
            default:
                throw new Exception("Unknown Golem Type: " + golem.getGolemType());
        }
    }


    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void attach(IComputerAccess computer) {
        // TODO Auto-generated method stub

    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void detach(IComputerAccess computer) {
        // TODO Auto-generated method stub

    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public boolean equals(IPeripheral other) {
        return this.equals((Object)other);

    }

    @Override
    public String getComponentName() {
        return getType();
    }
}
