/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Nov 24, 2013, 7:31:51 PM (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.lib.LibObfuscation;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBloodSword extends ItemSword implements IRepairable {

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		
		return super.getItemUseAction(par1ItemStack);
	}

	private static final int DAMAGE = 10;

	public ItemBloodSword(int par1) {
		super(par1, EnumHelper.addToolMaterial("TT_BLOOD", 0, 950, 0, 0, ThaumcraftApi.toolMatThaumium.getEnchantability()));
		MinecraftForge.EVENT_BUS.register(this);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", DAMAGE, 0));
        multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 0.25, 1));
        return multimap;
    }

	static int handleNext = 0;

	@ForgeSubscribe
	public void onDamageTaken(LivingAttackEvent event) {
		if(event.entity.worldObj.isRemote)
			return;

		boolean handle = handleNext == 0;
		if(!handle)
			handleNext--;

		if(event.entityLiving instanceof EntityPlayer && handle) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack itemInUse = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, LibObfuscation.ITEM_IN_USE);
			if(itemInUse != null && itemInUse.itemID == itemID) {
				
				event.setCanceled(true);
				handleNext = 3;
				player.attackEntityFrom(DamageSource.magic, 3);
			}
		}

		if(handle) {
			Entity source = event.source.getSourceOfDamage();
			if(source != null && source instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) source;
				ItemStack itemInUse = attacker.getCurrentItemOrArmor(0);
				if(itemInUse != null && itemInUse.itemID == itemID)
					attacker.attackEntityFrom(DamageSource.magic, 2);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
}
