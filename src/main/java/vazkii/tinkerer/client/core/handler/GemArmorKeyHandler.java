package vazkii.tinkerer.client.core.handler;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import thaumcraft.common.lib.PacketHandler;
import vazkii.tinkerer.client.core.handler.kami.KamiArmorClientHandler;
import vazkii.tinkerer.common.ThaumicTinkerer;

import java.util.EnumSet;

/**
 * Created by Katrina on 28/02/14.
 */
@SideOnly(Side.CLIENT)
public class GemArmorKeyHandler extends KeyBindingRegistry.KeyHandler {

    static KeyBinding SpecialAbility = new KeyBinding("ttmisc.toggleArmor", Keyboard.KEY_U);
    /**
     * Pass an array of keybindings and a repeat flag for each one
     *
     */
    public GemArmorKeyHandler() {
        super(new KeyBinding[]{SpecialAbility}, new boolean[]{false});
    }

    /**
     * Called when the key is first in the down position on any tick from the {@link #ticks()}
     * set. Will be called subsequently with isRepeat set to true
     *
     * @param types    the type(s) of tick that fired when this key was first down
     * @param kb
     * @param tickEnd  was it an end or start tick which fired the key
     * @param isRepeat is it a repeat key event
     * @see #keyUp(java.util.EnumSet, net.minecraft.client.settings.KeyBinding, boolean)
     */
    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {

    }

    /**
     * Fired once when the key changes state from down to up
     *
     * @param types   the type(s) of tick that fired when this key was first down
     * @param kb
     * @param tickEnd was it an end or start tick which fired the key
     * @see #keyDown(java.util.EnumSet, net.minecraft.client.settings.KeyBinding, boolean, boolean)
     */
    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
        if(tickEnd)
        {
           KamiArmorClientHandler.SetStatus(!ThaumicTinkerer.proxy.armorStatus(ThaumicTinkerer.proxy.getClientPlayer()));
        }
    }

    /**
     * This is the list of ticks for which the key binding should trigger. The only
     * valid ticks are client side ticks, obviously.
     *
     * @see cpw.mods.fml.common.ITickHandler#ticks()
     */
    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    /**
     * A profiling label for this tick handler
     */
    @Override
    public String getLabel() {
        return "ArmorKeyHandler";
    }
}
