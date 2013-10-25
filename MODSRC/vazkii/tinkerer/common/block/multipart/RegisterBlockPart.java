/**
 * @author RWTema
 * Thanks man!
 * 
 * Check out his mod, ExtraUtilities:
 * http://www.minecraftforum.net/topic/1776056-
 */
package vazkii.tinkerer.common.block.multipart;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;

// This class is used by FMP to recreate a part during loading and to convert already existing non-mutlipart blocks into multiparts. 

// this is copied from the version FMP uses for vanilla torches

public class RegisterBlockPart implements IPartFactory, IPartConverter {
	Block block = null;
	Class<? extends TMultiPart> part = null;
	String name = "";

	public RegisterBlockPart(Block block, Class<? extends TMultiPart> part) {
		//Autoloads the multi-parts name
		try {
			name = part.getConstructor().newInstance(new Object[] {}).getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegisterBlockPart(Block block, Class<? extends TMultiPart> part, String name) {
		this.block = block;
		this.part = part;
		this.name = name;
	}

	@Override
	public TMultiPart createPart(String name, boolean client) {
		if (name.equals(name))
			try {
				return part.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		return null;
	}

	//register the converter and the parts
	public void init() {
		if (name.isEmpty() || block == null || part == null)
			return;
		
		MultiPartRegistry.registerConverter(this);
		MultiPartRegistry.registerParts(this, new String[] { name });
	}

	@Override
	public boolean canConvert(int blockID) {
		return blockID == block.blockID;
	}

	@Override
	public TMultiPart convert(World world, BlockCoord pos) {
		int id = world.getBlockId(pos.x, pos.y, pos.z);
		int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
		if (id == block.blockID) {
			try {
				if(part.getName().equals("vazkii.tinkerer.common.block.multipart.PartNitor") && meta != 1)
					return null;
				
				//if(part.getDeclaredConstructors().length == 1)
					//return part.getDeclaredConstructor(int.class).newInstance(meta);
				
				return part.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}
}
