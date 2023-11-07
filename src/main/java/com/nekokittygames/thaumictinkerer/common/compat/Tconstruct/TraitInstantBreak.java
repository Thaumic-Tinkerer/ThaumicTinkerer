package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct;

import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTraits;
import slimeknights.tconstruct.tools.tools.Pickaxe;

public class TraitInstantBreak extends AbstractTrait {

    public TraitInstantBreak() {
        super("instantbreak", 0xFF0DCE53);
    }

    @Override
    public boolean canApplyTogether(IToolMod toolmod) {
        // Incompatible with Squeaky, Silk Touch, and Autosmelt
        return !toolmod.getIdentifier().equals(TinkerTraits.squeaky.getIdentifier())
                && !toolmod.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier())
                && !toolmod.getIdentifier().equals(TinkerTraits.autosmelt.getIdentifier());
    }

    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if(stack.getItem() instanceof Pickaxe)
        event.setNewSpeed(Float.MAX_VALUE);
    }

}