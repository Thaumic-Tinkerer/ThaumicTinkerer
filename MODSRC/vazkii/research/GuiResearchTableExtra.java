package vazkii.research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.gui.GuiResearchTable;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.research.PlayerKnowledge;
import thaumcraft.common.tiles.TileResearchTable;

public class GuiResearchTableExtra extends GuiResearchTable {

  TileResearchTable table;
	
	Aspect aspectHovered;
	
	public GuiResearchTableExtra(TileResearchTable table) {
		super(Minecraft.getMinecraft().thePlayer, table);
		this.table = table;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);

		if(note != null) {
			ItemStack stack = table.getStackInSlot(2);
			itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, stack, guiLeft - 18, guiTop + 12);
			if(par1 >= guiLeft - 18 && par2 >= guiTop + 12 && par1 < guiLeft - 2 && par2 < guiTop + 28)
				UtilsFX.drawCustomTooltip(this, itemRenderer, fontRenderer, stack.getTooltip(mc.thePlayer, false), par1, par2, 0xb);
			
			PlayerKnowledge knowledge = Thaumcraft.proxy.getPlayerKnowledge();
			
			int y = 0;
			boolean setAspect = false;
			for(Aspect aspect : note.aspects.getAspectsSorted()) {
				int amount = note.checkedAspects.getAmount(aspect);
				if(amount == 0 || amount == 2)
					continue;
				
				if(!knowledge.hasDiscoveredAspect(mc.thePlayer.username, aspect))
					continue;
				
				int xp = guiLeft - 18;
				int yp = guiTop + y * 18 + 34;
				
				UtilsFX.drawTag(xp, yp, aspect, knowledge.getAspectPoolFor(mc.thePlayer.username, aspect), 0, zLevel);
				if(par1 >= xp && par2 >= yp && par1 < xp + 16 && par2 < yp + 16) {
					aspectHovered = aspect;
					setAspect = true;
					
					AspectList requiredAspects = getRequiredAspects(aspect);
					if(requiredAspects != null) {
						int y1 = 0;
						for(Aspect aspect1 : requiredAspects.getAspectsSorted()) {
							int yp1 = yp + y1 * 18;
							
							UtilsFX.drawTag(xp - 20, yp1, aspect1, knowledge.getAspectPoolFor(mc.thePlayer.username, aspect1), requiredAspects.getAmount(aspect1), zLevel);
							y1++;
						}
					}
					
					UtilsFX.drawCustomTooltip(this, itemRenderer, fontRenderer, Arrays.asList(aspect.getName(), aspect.getLocalizedDescription()), par1, par2, 0xb);
				}
			
				y++;
			}
			
			if(!setAspect)
				aspectHovered = null;
		}
	}

	protected void mouseClicked(int mx, int my, int par3) {
		if(aspectHovered == null) {
			super.mouseClicked(mx, my, par3);
			return;
		}
		
		Aspect lastAspect = null;
		Pair pair;
		
		List<Pair> aspects = getAspectOperations(aspectHovered, new ArrayList<Pair>(), new AspectList(), mc.thePlayer.username, Thaumcraft.proxy.playerKnowledge, false, false);
		
		if(aspects == null)
			return;
		
		if(aspects.size() == 1) {
			select1 = aspects.get(0).aspect;
			select2 = null;
			
			return;
		}
		
		ListIterator<Pair> iterator = aspects.listIterator();
		
		while(iterator.hasNext())
			iterator.next();
		
		boolean didMerge = false;
		while(iterator.hasPrevious()) {
			pair = iterator.previous();
			
			if(pair.merge) {
				if(lastAspect != null) {
					select1 = lastAspect;
					select2 = pair.aspect;
					didMerge = true;
				}

				lastAspect = null;				
			} else lastAspect = pair.aspect;
			
			if(!iterator.hasPrevious() && !didMerge) {
				select1 = pair.aspect;
				select2 = null;
			}
		}	
	}
	
	List<Pair> getAspectOperations(Aspect aspect, List<Pair> currentList, AspectList currentUsed, String username, PlayerKnowledge pk, boolean merge, boolean add) {
		if(currentList == null)
			return null;
		
		boolean hasAvailable = pk.getAspectPoolFor(username, aspect) > currentUsed.getAmount(aspect);
		if(!hasAvailable) {
			if(aspect.isPrimal())
				return null; // Impossible
			
			Aspect[] requirements = aspect.getComponents();
			currentList = getAspectOperations(requirements[0], currentList, currentUsed, username, pk, true, true);
			currentList = getAspectOperations(requirements[1], currentList, currentUsed, username, pk, false, true);
		
			if(currentList == null)
				return null;
		}
		
		currentList.add(new Pair(aspect, merge, add && hasAvailable));
		if(hasAvailable)
			currentUsed.add(aspect, 1);
		
		return currentList;
	}
	
	AspectList getRequiredAspects(Aspect aspect) {
		List<Pair> aspects = getAspectOperations(aspect, new ArrayList<Pair>(), new AspectList(), mc.thePlayer.username, Thaumcraft.proxy.playerKnowledge, false, false);
		
		if(aspects == null)
			return null;
		
		AspectList list = new AspectList();
		for(Pair pair : aspects)
			if(pair.add)
				list.add(pair.aspect, 1);
		
		return list;
	}
	
	static class Pair {
		
		Aspect aspect;
		boolean merge;
		boolean add;
		
		Pair(Aspect aspect, boolean merge, boolean add) {
			this.aspect = aspect;
			this.merge = merge;
			this.add = add;
		}
		
	}
	
}