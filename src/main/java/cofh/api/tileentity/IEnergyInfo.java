package cofh.api.tileentity;

/**
 * Implement this interface on Tile Entities which can report information about their energy usage.
 * 
 * This is used for reporting purposes - Energy transactions are handled through IEnergyHandler!
 * 
 * @author King Lemming
 * 
 */
public interface IEnergyInfo {

	/**
	 * Returns energy usage/generation per tick (RF/t).
	 */
	public int getInfoEnergyPerTick();

	/**
	 * Returns maximum energy usage/generation per tick (RF/t).
	 */
	public int getInfoMaxEnergyPerTick();

	/**
	 * Returns energy stored (RF).
	 */
	public int getInfoEnergy();

	/**
	 * Returns maximum energy stored (RF).
	 */
	public int getInfoMaxEnergy();

}
