package appeng.api.parts;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;

/**
 * Implemented on AE's TileEntity or AE's FMP Part.
 * 
 * Do Not Implement
 */
public interface IPartHost
{

	/**
	 * @return the facade container
	 */
	IFacadeContainer getFacadeContainer();

	/**
	 * Test if you can add a part to the specified side of the Part Host, {@link net.minecraftforge.common.util.ForgeDirection}.UNKNOWN is used to
	 * represent the cable in the middle.
	 * 
	 * @param is
	 * @param side
	 * @return returns false if the part cannot be added.
	 */
	boolean canAddPart(ItemStack part, ForgeDirection side);

	/**
	 * try to add a new part to the specified side, returns false if it failed to be added.
	 * 
	 * @param is
	 * @param side
	 * @param owner
	 * @return null if the item failed to add, the side it was placed on other wise ( may different for cables,
	 *         {@link net.minecraftforge.common.util.ForgeDirection}.UNKNOWN )
	 */
	ForgeDirection addPart(ItemStack is, ForgeDirection side, EntityPlayer owner);

	/**
	 * Get part by side ( center is {@link net.minecraftforge.common.util.ForgeDirection}.UNKNOWN )
	 * 
	 * @param side
	 * @return the part located on the specified side, or null if there is no part.
	 */
	IPart getPart(ForgeDirection side);

	/**
	 * removes the part on the side, this doesn't drop it or anything, if you don't do something with it, its just
	 * "gone" and its never coming back; think about it.
	 * 
	 * if you want to drop the part you must request it prior to removing it.
	 * 
	 * @param side
	 * @param suppressUpdate
	 *            - used if you need to replace a part's instance, without really removing it first.
	 */
	void removePart(ForgeDirection side, boolean suppressUpdate);

	/**
	 * something changed, might want to send a packet to clients to update state.
	 */
	void markForUpdate();

	/**
	 * @return the physical location of the part host in the universe.
	 */
	DimensionalCoord getLocation();

	/**
	 * @return the tile entity for the host, this can either be an FMP tile, or a AE tile
	 */
	TileEntity getTile();

	/**
	 * @return the color of the host type ( this is determined by the middle cable. ) if no cable is present, it returns
	 *         {@link appeng.api.util.AEColor} .Transparent other wise it returns the color of the cable in the center.
	 */
	AEColor getColor();

	/**
	 * destroys the part container, for internal use.
	 */
	void clearContainer();

	/**
	 * Used to test for FMP microblock blocking internally.
	 * 
	 * @return returns if microblocks are blocking this cable path.
	 */
	boolean isBlocked(ForgeDirection side);

	/**
	 * finds the part located at the position ( pos must be relative, not global )
	 * 
	 * @param pos
	 * @return a new SelectedPart, this is never null.
	 */
	SelectedPart selectPart(Vec3 pos);

	/**
	 * can be used by parts to trigger the tile or part to save.
	 */
	void markForSave();

	/**
	 * part of the {@link LayerBase}
	 */
	void partChanged();

	/**
	 * get the redstone state of host on this side, this value is cached internally.
	 * 
	 * @param side
	 * @return true of the part host is receieveing redstone from an external source.
	 */
	boolean hasRedstone(ForgeDirection side);

	/**
	 * returns false if this block contains any parts or facades, true other wise.
	 */
	boolean isEmpty();

	/**
	 * @return a mutable list of flags you can adjust to track state.
	 */
	Set<LayerFlags> getLayerFlags();
}