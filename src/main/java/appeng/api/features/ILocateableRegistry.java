package appeng.api.features;

/**
 * A Registry for locatable items, works based on serial numbers.
 */
public interface ILocateableRegistry
{

	/**
	 * Attempts to find the object with the serial specified, if it can it
	 * returns the object.
	 * 
	 * @param ser
	 * @return requestedObject, or null
	 */
	public abstract Object findLocateableBySerial(long ser);

}