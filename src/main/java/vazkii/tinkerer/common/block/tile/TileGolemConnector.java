package vazkii.tinkerer.common.block.tile;


import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
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
                double x=(Double)arguments[0];
                double y=(Double)arguments[0];
                double z=(Double)arguments[0];
                double facing=(Double)arguments[0];
                return setHomeImplementation(x,y,z,facing);
            case 7:
                return getMarkersImplementation();
            case 8:
                if (arguments.length != 1)
                    throw new Exception("setMarkers takes 1 argument");
                return setMarkersImplementation((HashMap<Double, HashMap<String, Object>>) arguments[0]);
            case 9:
                return newMarkerImplementation();
            case 10:
                if (arguments.length != 1)
                    throw new Exception("addMarker must have 1 argument");
                return addMarkerImplementation((Map)arguments[0]);
            case 11:
                if (arguments.length != 2)
                    throw new Exception("saveMarker must have 2 arguments");
                return saveMarkerImplementation((Double)arguments[0],(Map)arguments[1]);
            case 12:
                if (arguments.length != 1)
                    throw new Exception("deleteMarker must have 1 argument");
                return deleteMarkerImplementation((Double) arguments[0]);
            case 13:
                if (arguments.length != 1)
                    throw new Exception("getMarker must have 1 argument");
                return getMarkerImplementation((Double)arguments[0]);
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

    private Object[] getMarkerImplementation(Double arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null || markers.size() <= (Double) arguments)
            throw new Exception("marker " + (int) (double) (Double) arguments + " does not exist");
        Marker mark = markers.get((int) (double) (Double) arguments);

        return new Object[]{fromMarkerImplementation(mark)};
    }

    private Object[] deleteMarkerImplementation(double arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null || markers.size() <= (Double) arguments)
            throw new Exception("marker " + (int) (double) (Double) arguments + " does not exist");
        markers.remove((int) (double) (Double) arguments);
        golem.setMarkers(markers);
        return new String[]{};
    }

    private Object[] saveMarkerImplementation(double markerNum,Map markerArg) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null || markers.size() <= markerNum)
            throw new Exception("marker " + (int) (double) (Double) markerNum + " does not exist");
        Marker mark = toMarkerImplementation(markerArg);
        markers.set((int) (double) markerNum, mark);
        golem.setMarkers(markers);
        return getMarkerImplementation(markerNum);
    }

    @SuppressWarnings("unchecked")
    private Object[] addMarkerImplementation(Map arguments) throws Exception {
        if (golem == null)
            return new String[]{};
        ArrayList<Marker> markers = golem.getMarkers();
        if (markers == null)
            markers = new ArrayList<Marker>();
        Marker mark = toMarkerImplementation(arguments);
        markers.add(mark);
        golem.setMarkers(markers);
        return getMarkerImplementation((double)(markers.size() - 1));
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
    private Object[] setMarkersImplementation(Map arguments) throws Exception {
        if (golem == null)
            return new String[]{};

        ArrayList<Marker> arrList = new ArrayList<Marker>();

        Map markersToSet =  arguments;
        for (Object map : markersToSet.values()) {
            Marker mark = toMarkerImplementation((Map) map);

            arrList.add(mark);

        }
        golem.setMarkers(arrList);
        return getMarkersImplementation();
    }

    /**
     * @param markerMap
     * @return
     */
    private Marker toMarkerImplementation(Map markerMap) {
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

    private Object[] setHomeImplementation(double x,double y,double z,double facing) throws Exception {
        if (golem == null)
            return new String[]{};

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
        return this.equals((Object) other);

    }

    @Override
    public String getComponentName() {
        return getType();
    }


    @Callback(doc = "function():table -- Returns table of current decorations on golem")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getDecorations(Context context, Arguments args) throws Exception {
        return getGolemDecorationsImplementation();
    }


    @Callback(doc = "function():x:number,y:number,z:number -- Returns The golems current position")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getPosition(Context context, Arguments args) {
        return new Double[]{golem.posX, golem.posY, golem.posZ};
    }


    @Callback(doc = "function():string -- Returns The golems type")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getType(Context context, Arguments args) throws Exception {
        return getGolemTypeImplementation();
    }



    @Callback(doc = "function():number -- Returns The golems health")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getHealth(Context context, Arguments args) throws Exception {
        return new Float[]{golem.getHealth()};
    }

    @Callback(doc = "function():string -- Returns The golems core")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getCore(Context context, Arguments args) throws Exception {
        return getGolemCoreImplementation();
    }


    @Callback(doc = "function():x:number,y:number,z:number,facing:number -- Returns The golems home position")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getHome(Context context, Arguments args) throws Exception {
        return getHomeImplementation();
    }
    @Callback(doc = "function(x:number,y:number,z:number,facing:number):nill -- Sets The golems home position")
    @Optional.Method(modid = "OpenComputers")
    public Object[] setHome(Context context, Arguments args) throws Exception {
        return setHomeImplementation(args.checkDouble(0),args.checkDouble(1),args.checkDouble(2),args.checkDouble(3));
    }


    @Callback(doc = "function():table -- Gets list of markers")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getMarkers(Context context, Arguments args) throws Exception {
        return getMarkersImplementation();
    }

    @Callback(doc = "function(list:table):table -- Sets list of markers")
    @Optional.Method(modid = "OpenComputers")
    public Object[] setMarkers(Context context, Arguments args) throws Exception {
        return setMarkersImplementation(args.checkTable(0));
    }

    @Callback(doc = "function():table -- Returns a blank marker with sensible defaults")
    @Optional.Method(modid = "OpenComputers")
    public Object[] newMarker(Context context, Arguments args) throws Exception {
        return newMarkerImplementation();
    }

    @Callback(doc = "function(marker:table):table -- Adds the marker to the end of the marker list")
    @Optional.Method(modid = "OpenComputers")
    public Object[] addMarker(Context context, Arguments args) throws Exception {
        return addMarkerImplementation(args.checkTable(0));
    }

    @Callback(doc = "function(markerNum:number,marker:table):table -- saves current marker")
    @Optional.Method(modid = "OpenComputers")
    public Object[] saveMarker(Context context, Arguments args) throws Exception {
        return saveMarkerImplementation((double)args.checkInteger(0),args.checkTable(0));
    }

    // TODO return new String[]{"getMarker", "getMarkerCount"};
    @Callback(doc = "function(markerNum:number):table -- Deletes marker number")
    @Optional.Method(modid = "OpenComputers")
    public Object[] deleteMarker(Context context, Arguments args) throws Exception {
        return deleteMarkerImplementation((double)args.checkInteger(0));
    }

    @Callback(doc = "function(markerNum:number):table -- gets a specific marker")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getMarker(Context context, Arguments args) throws Exception {
        return getMarkerImplementation((double)args.checkInteger(0));
    }

    @Callback(doc = "function():number -- Returns number of markers")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getMarkerCount(Context context, Arguments args) throws Exception {
        return getMarkerCountImplementation();
    }

    }