package vazkii.tinkerer.common.block.tile;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import thaumcraft.common.entities.golems.EntityGolemBase;
import vazkii.tinkerer.common.core.golems.EnumGolemCores;
import vazkii.tinkerer.common.core.golems.EnumGolemDecorations;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;

public class TileGolemConnector extends TileCamo implements IPeripheral {
	public UUID golemConnected;
	public EntityGolemBase golem;
	private static final String TAG_UUID_MOST="UUIDMost";
	private static final String TAG_UUID_LEAST="UUIDLeast";
	
	public void verifyGolem() {
		if (golemConnected == null)
			return;
		List list = this.worldObj.getEntitiesWithinAABB(EntityGolemBase.class,
				AxisAlignedBB.getBoundingBox(xCoord - 30, yCoord - 30,
						zCoord - 30, xCoord + 30, yCoord + 30, zCoord + 30));
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

	@Override
	public String[] getMethodNames() {
		return new String[] { "getDecorations","getPosition","getType","getHealth","getCore" };
	}
	public String[] getGolemDecorations() throws Exception
    {
            if(golem==null ||golem.decoration==null || golem.decoration.length()==0)
                    return new String[]{};
            String[] decorations=new String[golem.decoration.length()];
            for(int i=0;i<golem.decoration.length();i++)
            {
                    EnumGolemDecorations golemDec=EnumGolemDecorations.getFromChar(golem.decoration.charAt(i));
                    if(golemDec==null)
                    	throw new Exception("Golem is wearing unknown accessory: "+golem.decoration.charAt(i));
                    decorations[i]=StatCollector.translateToLocal(golemDec.getName());
            }
           
            return decorations;
           
    }
	public String[] getGolemCore() throws Exception
    {
            if(golem==null)
                    return new String[]{};
            if(golem.getCore()==-1)
            	return new String[]{StatCollector.translateToLocal("item.ItemGolemCore.100.name")};
            String[] decorations=new String[1];
            EnumGolemCores golemCore=EnumGolemCores.getFromByte(golem.getCore());
            if(golemCore==null)
            	throw new Exception("Golem has Unknown core: "+golem.getCore());
                decorations[0]=StatCollector.translateToLocal(golemCore.getName());
           
            return decorations;
           
    }
	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		super.writeCustomNBT(cmp);
		if(golemConnected!=null)
		{
			cmp.setLong(TAG_UUID_MOST, golemConnected.getMostSignificantBits());
			cmp.setLong(TAG_UUID_LEAST, golemConnected.getLeastSignificantBits());
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(worldObj.getTotalWorldTime()%10==1)
		{
			verifyGolem();
		}
	}
	
	public void ConnectGolem(UUID golemID)
	{
		golemConnected=golemID;
		if(golemID==null)
			golem=null;
		else
		{
			verifyGolem();
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		super.readCustomNBT(cmp);
		if(cmp.hasKey(TAG_UUID_LEAST)&&cmp.hasKey(TAG_UUID_MOST))
		{
			golemConnected=new UUID(cmp.getLong(TAG_UUID_MOST), cmp.getLong(TAG_UUID_LEAST));
		}
		
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws Exception {
		switch(method)
		{
		case 0:
			return getGolemDecorations();
		case 1:
			if(golem==null)
				return new Integer[]{};
			return new Integer[]{(int)golem.posX,(int)golem.posY,(int)golem.posZ};
		case 2:
			return getGolemType();
		case 3:
			if(golem==null)
				return new Float[]{};
			return new Float[]{golem.getHealth()};
		case 4:
			return getGolemCore();
		}
		return null;
	}

	private String[] getGolemType() throws Exception {
		if(golem==null)
			return new String[]{};
		switch(golem.getGolemType())
		{
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
			throw new Exception("Unknown Golem Type: "+golem.getGolemType());
		}
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void detach(IComputerAccess computer) {
		// TODO Auto-generated method stub

	}
}
