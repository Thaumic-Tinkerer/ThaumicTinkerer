package thaumic.tinkerer.common.registry;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/9/14.
 */
public interface ITTinkererItem extends ITTinkererRegisterable {

	public ArrayList<Object> getSpecialParameters();

	public String getItemName();

	public boolean shouldRegister();

	public boolean shouldDisplayInTab();

}
