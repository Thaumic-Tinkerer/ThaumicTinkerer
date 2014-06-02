package vazkii.tinkerer.client.core.handler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import vazkii.tinkerer.client.core.handler.kami.KamiArmorClientHandler;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.kami.armor.ItemIchorclothArmorAdv;

/**
 * Created by Katrina on 28/02/14.
 */
@SideOnly(Side.CLIENT)
public class GemArmorKeyHandler  {

    static KeyBinding SpecialAbility = new KeyBinding("ttmisc.toggleArmor", Keyboard.KEY_U,"ttmisc.keyCategory");
    public GemArmorKeyHandler()
    {
        ClientRegistry.registerKeyBinding(SpecialAbility);
    }


    public void keyUp(InputEvent.KeyInputEvent event) {
        if(SpecialAbility.isPressed())
        {
            if(Minecraft.getMinecraft().thePlayer.getCurrentArmor(0).getItem() instanceof ItemIchorclothArmorAdv && Minecraft.getMinecraft().thePlayer.getCurrentArmor(1).getItem() instanceof ItemIchorclothArmorAdv && Minecraft.getMinecraft().thePlayer.getCurrentArmor(2).getItem() instanceof ItemIchorclothArmorAdv && Minecraft.getMinecraft().thePlayer.getCurrentArmor(3).getItem() instanceof ItemIchorclothArmorAdv) {
                KamiArmorClientHandler.SetStatus(!ThaumicTinkerer.proxy.armorStatus(ThaumicTinkerer.proxy.getClientPlayer()));
            }
        }
    }


}
