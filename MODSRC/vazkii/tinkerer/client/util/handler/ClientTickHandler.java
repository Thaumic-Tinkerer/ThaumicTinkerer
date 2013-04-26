/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [26 Apr 2013, 23:40:32 (GMT)]
 */
package vazkii.tinkerer.client.util.handler;

import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;

import vazkii.tinkerer.client.hud.HUDStopwatch;
import vazkii.tinkerer.client.hud.IHUDElement;
import vazkii.tinkerer.lib.LibMisc;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {

	private Collection<IHUDElement> hudElements;

	public ClientTickHandler() {
		hudElements = new LinkedHashSet();
		hudElements.add(new HUDStopwatch());
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) { }

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.CLIENT)))
			clientTick();
		else renderTick((Float) tickData[0]);
	}
	
	public void clientTick() {
		for(IHUDElement element : hudElements)
			element.clientTick();
	}
	
	public void renderTick(float partialTicks) {
		for(IHUDElement element : hudElements)
			if(element.shouldRender())
				element.render(partialTicks);

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return LibMisc.MOD_ID;
	}

}
