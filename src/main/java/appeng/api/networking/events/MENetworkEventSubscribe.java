package appeng.api.networking.events;

import appeng.api.networking.IGridCache;
import appeng.api.networking.IGridHost;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Usable on any {@link IGridHost}, or {@link IGridCache}
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MENetworkEventSubscribe {

}
