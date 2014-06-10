package vazkii.tinkerer.common.registry;

import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/9/14.
 */
public interface ITTinkererItem {

	public ArrayList<Object> getSpecialParameters();

	public String getItemName();

	public boolean shouldRegister();

	public boolean shouldDisplayInTab();

	public TTResearchItem getResearchItem();

}
