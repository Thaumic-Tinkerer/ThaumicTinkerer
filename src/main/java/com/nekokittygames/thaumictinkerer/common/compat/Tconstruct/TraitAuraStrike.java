package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct;

import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.ATITextFragmentShader;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTraits;
import slimeknights.tconstruct.tools.tools.Pickaxe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aura.AuraHelper;

public class TraitAuraStrike extends AbstractTrait {

    public TraitAuraStrike() {
        super("aurastrike", 0xFF0DCE53);
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent event) {

        if (event.getSource().getTrueSource() == null || event.getSource().getTrueSource() instanceof FakePlayer || event.isCanceled() || !(event.getSource().getTrueSource() instanceof EntityLivingBase))
            return;

        EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
        int ChunkAura = AuraHelper.getAuraBase(attacker.getEntityWorld(), attacker.getPosition());
        float Aura = AuraHelper.getVis(attacker.getEntityWorld(), attacker.getPosition());
        NBTTagCompound tag = TinkerUtil.getModifierTag(attacker.getHeldItemMainhand(), "aurastrike");
        int level = ModifierNBT.readTag(tag).level;
        if (level > 0 && ChunkAura > 0) {
            if (Aura > ChunkAura * 1.5) {
                event.setAmount(event.getAmount() * TTConfig.AuraStrikeBonus);
                ThaumcraftApi.internalMethods.drainVis(attacker.getEntityWorld(), attacker.getPosition(), ChunkAura, false);
                float absorption1 = attacker.getAbsorptionAmount();
                if (absorption1 < 40) {
                    attacker.setAbsorptionAmount(absorption1 + event.getAmount() * 1 / 3);
                }
            } else {
                if(ChunkAura * 0.1 > 10)
                AuraHelper.addVis(attacker.getEntityWorld(), attacker.getPosition(), (float) (ChunkAura * 0.1));
                else
                    AuraHelper.addVis(attacker.getEntityWorld(), attacker.getPosition(), 10);
            }
        }
    }
}