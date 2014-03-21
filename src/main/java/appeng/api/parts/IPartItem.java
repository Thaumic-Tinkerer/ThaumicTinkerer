package appeng.api.parts;

import net.minecraft.item.ItemStack;

//@formatter:off
/**
 * This is a pretty basic requirement, once you implement the interface, and createPartFromItemStack
 * 
 * you must register your bus with the Bus renderer, using AEApi.instance().partHelper().setItemBusRenderer( this );
 * 
 * then simply add these two methods, which tell MC to use the Block Textures, and call AE's Bus Placement Code.
 * 
 * 	@Override
 * 	@SideOnly(Side.CLIENT)
 * 	public int getSpriteNumber()
 * 	{
 * 		return 0;
 * 	}
 * 
 * 	@Override
 * 	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
 * 	{
 * 		return AEApi.instance().partHelper().placeBus( is, x, y, z, side, player, world );
 * 	}
 */
public interface IPartItem
{

	/**
	 * create a new part instance, from the item stack.
	 * 
	 * @param is
	 * @return
	 */
	IPart createPartFromItemStack(ItemStack is);

}
