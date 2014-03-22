package appeng.api.features;

import appeng.api.events.LocatableEventAnnounce;

/**
 * A registration record for the {@link ILocateableRegistry} use the {@link appeng.api.events.LocatableEventAnnounce} event on the Forge
 * Event bus to update the registry.
 */
public interface ILocatable
{

	/**
	 * @return the serial for a locatable object
	 */
	long getLocatableSerial();

}